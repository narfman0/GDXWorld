package com.blastedstudios.gdxworld.world.quest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.blastedstudios.gdxworld.plugin.quest.manifestation.dialog.DialogManifestation;
import com.blastedstudios.gdxworld.plugin.quest.trigger.aabb.AABBTrigger;
import com.blastedstudios.gdxworld.plugin.quest.trigger.activate.ActivateTrigger;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;
import com.blastedstudios.gdxworld.world.quest.manifestation.AbstractQuestManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.IQuestManifestationExecutor;
import com.blastedstudios.gdxworld.world.quest.trigger.IQuestTriggerInformationProvider;

public class GDXQuestManagerTest {
	private World world;
	private GDXQuestManager manager;
	private GDXLevel level1;
	private GDXQuest quest1, quest2;
	private Vector2 playerPosition;
	
	@Before public void setUp() throws Exception {
		Gdx.app = nullApplicaton;
		world = new World(new Vector2(), true);
		playerPosition = new Vector2();
		level1 = new GDXLevel();
		level1.setName("level1");
		quest1 = new GDXQuest();
		quest1.setName("quest1");
		quest1.setManifestation(new DialogManifestation("Quest 1 dialog", "Origin 1", "Type1"));
		quest1.setTrigger(new AABBTrigger(1, 1, 3, 3));
		level1.getQuests().add(quest1);
		quest2 = new GDXQuest();
		quest2.setName("quest2");
		quest2.setManifestation(new DialogManifestation("Quest 2 dialog", "Origin 2", "Type2"));
		quest2.setPrerequisites("quest1");
		quest2.setTrigger(new AABBTrigger(4, 4, 6, 6));
		level1.getQuests().add(quest2);
		
		IQuestManifestationExecutor executor = new IQuestManifestationExecutor() {
			@Override public Body getPhysicsObject(String name) {
				Array<Body> bodyArray = new Array<>(world.getBodyCount());
				world.getBodies(bodyArray);
				for(Body body : bodyArray)
					if(body.getUserData().equals(name))
						return body;
				return null;
			}
			@Override public Joint getPhysicsJoint(String name) {
				Array<Joint> joints = new Array<>(world.getJointCount());
				world.getJoints(joints);
				for(Joint joint : joints)
					if(joint.getUserData().equals(name))
						return joint;
				return null;
			}
			@Override public World getWorld() {
				return world;
			}
		};
		IQuestTriggerInformationProvider provider = new IQuestTriggerInformationProvider() {
			@Override public boolean isDead(String name) {
				return false;
			}
			@Override public Vector2 getPlayerPosition() {
				return playerPosition;
			}
			@Override public boolean isNear(String origin, String target, float distance) {
				Body originBody = null, targetBody = null;
				Array<Body> bodyArray = new Array<>(world.getBodyCount());
				world.getBodies(bodyArray);
				for(Body body : bodyArray){
					if(body.getUserData().equals(origin))
						originBody = body;
					if(body.getUserData().equals(target))
						targetBody = body;
				}
				return originBody.getPosition().dst(targetBody.getPosition()) < distance;
			}
			@Override public Body getPhysicsObject(String name) {
				Array<Body> bodyArray = new Array<>(world.getBodyCount());
				world.getBodies(bodyArray);
				for(Body body : bodyArray)
					if(body.getUserData().equals(name))
						return body;
				return null;
			}
			@Override public boolean isAction() {
				return false;
			}
		};
		manager = new GDXQuestManager();
		manager.initialize(provider, executor);
	}

	@Test public void testActive() {
		manager.setCurrentLevel(level1);
		manager.tick();
		assertFalse(manager.isActive(quest2));
		assertTrue(manager.isActive(quest1));
		playerPosition.set(2,2);
		manager.tick();
		assertFalse(manager.isCompleted(quest1));
		manager.setStatus(quest1.getName(), CompletionEnum.COMPLETED);
		assertTrue(manager.isCompleted(quest1));
		assertTrue(manager.isActive(quest2));
		playerPosition.set(5,5);
		manager.tick();
		assertFalse(manager.isCompleted(quest2));
		manager.setStatus(quest2.getName(), CompletionEnum.COMPLETED);
		assertTrue(manager.isCompleted(quest2));
	}

	private float count = 0f;
	@Test public void testRepeat() {
		GDXQuest quest = new GDXQuest();
		quest.setManifestation(new AbstractQuestManifestation() {
			private static final long serialVersionUID = 1L;
			@Override public String toString() {return "";}
			@Override public CompletionEnum execute() {
				count++;
				return CompletionEnum.COMPLETED;
			}
			@Override public AbstractQuestManifestation clone() {return this;}
		});
		quest.setTrigger(new ActivateTrigger());
		quest.setRepeatable(true);
		quest.setName("Repeatable quest name");
		GDXLevel level = new GDXLevel();
		level.getQuests().add(quest);
		manager.setCurrentLevel(level);
		assertTrue(manager.isActive(quest));
		assertFalse(manager.isCompleted(quest));
		assertEquals(0, count, 1e-6);
		manager.tick();
		assertTrue(manager.isCompleted(quest));
		assertTrue(manager.isActive(quest));
		assertEquals(1, count, 1e-6);
		playerPosition.set(2,2);
		manager.tick();
		assertTrue(manager.isCompleted(quest));
		assertTrue(manager.isActive(quest));
		assertEquals(2, count, 1e-6);
	}

	private static Application nullApplicaton = new Application() {
		@Override public void setLogLevel(int logLevel) {}
		@Override public void removeLifecycleListener(LifecycleListener listener) {}
		@Override public void postRunnable(Runnable runnable) {}
		@Override public void log(String tag, String message, Exception exception) {}
		@Override public void log(String tag, String message) {}
		@Override public int getVersion() {return 0;}
		@Override public ApplicationType getType() {return null;}
		@Override public Preferences getPreferences(String name) {return null;}
		@Override public Net getNet() {return null;}
		@Override public long getNativeHeap() {return 0;}
		@Override public long getJavaHeap() {return 0;}
		@Override public Input getInput() {return null;}
		@Override public Graphics getGraphics() {return null;}
		@Override public Files getFiles() {return null;}
		@Override public Clipboard getClipboard() {return null;}
		@Override public Audio getAudio() {return null;}
		@Override public ApplicationListener getApplicationListener() {return null;}
		@Override public void exit() {}
		@Override public void error(String tag, String message, Throwable exception) {}
		@Override public void error(String tag, String message) {}
		@Override public void debug(String tag, String message, Throwable exception) {}
		@Override public void debug(String tag, String message) {}
		@Override public void addLifecycleListener(LifecycleListener listener) {}
	};
}
