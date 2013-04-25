package com.blastedstudios.gdxworld.world.quest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.blastedstudios.gdxworld.GDXWorldEditor;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.quest.manifestation.DialogManifestation;
import com.blastedstudios.gdxworld.world.quest.manifestation.IQuestManifestationExecutor;
import com.blastedstudios.gdxworld.world.quest.trigger.AABBTrigger;
import com.blastedstudios.gdxworld.world.quest.trigger.IQuestTriggerInformationProvider;

public class GDXQuestManagerTest {
	private World world;
	private GDXQuestManager manager;
	private GDXLevel level1;
	private GDXQuest quest1, quest2;
	private Vector2 playerPosition;
	
	@Before public void setUp() throws Exception {
		Gdx.app = new LwjglApplication(new GDXWorldEditor(), "GDX World Editor", 1280, 1024, false);
		playerPosition = new Vector2();
		//world = new World(new Vector2(), true);
		level1 = new GDXLevel();
		level1.setName("level1");
		quest1 = new GDXQuest();
		quest1.setName("quest1");
		quest1.setManifestation(new DialogManifestation("Quest 1 dialog", "Origin 1"));
		quest1.setTrigger(new AABBTrigger(1, 1, 3, 3));
		level1.getQuests().add(quest1);
		quest2 = new GDXQuest();
		quest2.setName("quest2");
		quest2.setManifestation(new DialogManifestation("Quest 2 dialog", "Origin 2"));
		quest2.setPrerequisites("quest1");
		quest2.setTrigger(new AABBTrigger(4, 4, 6, 6));
		level1.getQuests().add(quest2);
		
		IQuestManifestationExecutor executor = new IQuestManifestationExecutor() {
			@Override public Body getPhysicsObject(String name) {
				for(Iterator<Body> iter = world.getBodies(); iter.hasNext();){
					Body body = iter.next();
					if(body.getUserData().equals(name))
						return body;
				}
				return null;
			}
			@Override public void addDialog(String dialog, String origin) {
				Gdx.app.log("QuestManifestationExecutor.addDialog", 
						"Dialog received:" + dialog + " origin: " + origin);
			}
			@Override public void endLevel(boolean success) {
				Gdx.app.log("QuestManifestationExecutor.endLevel","success: " + success);
			}
			@Override public Joint getPhysicsJoint(String name) {
				for(Iterator<Joint> iter = world.getJoints(); iter.hasNext();){
					Joint joint = iter.next();
					if(joint.getUserData().equals(name))
						return joint;
				}
				return null;
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
				for(Iterator<Body> iter = world.getBodies(); iter.hasNext();){
					Body body = iter.next();
					if(body.getUserData().equals(origin))
						originBody = body;
					if(body.getUserData().equals(target))
						targetBody = body;
				}
				return originBody.getPosition().dst(targetBody.getPosition()) < distance;
			}
		};
		manager = new GDXQuestManager(provider, executor);
	}

	@Test public void testActive() {
		manager.setCurrentLevel(level1);
		manager.tick(1);
		assertFalse(manager.isActive(quest2));
		assertTrue(manager.isActive(quest1));
		playerPosition.set(2,2);
		manager.tick(1);
		assertTrue(manager.isCompleted(quest1));
		assertTrue(manager.isActive(quest2));
		playerPosition.set(5,5);
		manager.tick(1);
		assertTrue(manager.isCompleted(quest2));
	}

}
