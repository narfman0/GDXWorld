/** 
 *  Taken from https://bitbucket.org/dermetfan/libgdx-utils
 *  
 *  Copyright 2014 Robin Stumm (serverkorken@gmail.com, http://dermetfan.net)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License. */

package com.blastedstudios.gdxworld.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/** provides useful methods for scene2d
 *  @author dermetfan */
public class Scene2DUtils {

	/** Some methods return this, so if you get your hands on it make sure to make a copy! This is used internally so it might change unexpectedly. */
	private static Vector2 tmp = new Vector2();

	/** @param c the event to copy to
	 *  @param e the event to copy from */
	public static void copy(Event c, Event e) {
		c.setTarget(e.getTarget());
		c.setStage(e.getStage());
		c.setCapture(e.isCapture());
		c.setBubbles(e.getBubbles());
		c.setListenerActor(e.getListenerActor());
	}

	/** @see #copy(Event, Event)  */
	public static void copy(InputEvent c, InputEvent e) {
		copy((Event) c, e);
		c.setStageX(e.getStageX());
		c.setStageY(e.getStageY());
		c.setButton(e.getButton());
		c.setCharacter(e.getCharacter());
		c.setKeyCode(e.getKeyCode());
		c.setPointer(e.getPointer());
		c.setType(e.getType());
		c.setRelatedActor(e.getRelatedActor());
		c.setScrollAmount(e.getScrollAmount());
	}

	/** @param vec the coordinates
	 *  @param actor the actor in which coordinate system vec is given
	 *  @param other the actor into which coordinate system to convert the coordinates to
	 *  @throws IllegalArgumentException if the given actors are not in the same hierarchy */
	public static void localToOtherCoordinates(Vector2 vec, Actor actor, Actor other) {
		Group lastParent = lastParent(actor);
		if(lastParent == null || lastParent != lastParent(other))
			throw new IllegalArgumentException(actor + " and " + other + " are not in the same hierarchy");
		actor.localToAscendantCoordinates(lastParent, vec);
		lastParent.localToDescendantCoordinates(other, vec);
	}

	/** @return the highest parent in the hierarchy tree of the given actor */
	public static Group lastParent(Actor actor) {
		if(!actor.hasParent())
			return null;
		Group parent = actor.getParent();
		while(parent.hasParent())
			parent = parent.getParent();
		assert !parent.hasParent();
		return parent;
	}

	/** @param actor the actor which position in stage coordinates to return
	 *  @return the position of the given actor in the stage coordinate system */
	public static Vector2 positionInStageCoordinates(Actor actor) {
		if(actor.hasParent())
			actor.localToStageCoordinates(tmp.set(0, 0));
		else
			tmp.set(actor.getX(), actor.getY());
		return tmp;
	}

	/** Adds the given Actor to the given Group at the coordinates relative to the Stage.
	 *  @param actor the Actor to add to the given Group
	 *  @param newParent the Group to add the given Actor to */
	public static void addAtStageCoordinates(Actor actor, Group newParent) {
		tmp.set(positionInStageCoordinates(actor));
		newParent.stageToLocalCoordinates(tmp);
		newParent.addActor(actor);
		actor.setPosition(tmp.x, tmp.y);
	}

	/** @see #pointerPosition(Stage, int) */
	public static Vector2 pointerPosition(Stage stage) {
		return pointerPosition(stage, 0);
	}

	/** @param stage the Stage which coordinate system should be used
	 *  @param pointer the pointer which position to return
	 *  @return the position of the given pointer in stage coordinates */
	public static Vector2 pointerPosition(Stage stage, int pointer) {
		tmp.set(Gdx.input.getX(pointer), Gdx.input.getY(pointer));
		stage.screenToStageCoordinates(tmp);
		return tmp;
	}

	/** @see #newButton(ButtonStyle, String) */
	public static Button newButton(ButtonStyle style) {
		return newButton(style, "");
	}

	/** creates a {@link Button} according to the given {@link ButtonStyle} instance that may be {@link ButtonStyle}, {@link TextButtonStyle}, {@link ImageButtonStyle} or {@link ImageTextButtonStyle} */
	public static Button newButton(ButtonStyle style, String textIfAny) {
		if(style instanceof ImageTextButtonStyle)
			return new ImageTextButton(textIfAny, (ImageTextButtonStyle) style);
		if(style instanceof TextButtonStyle)
			return new TextButton(textIfAny, (TextButtonStyle) style);
		if(style instanceof ImageButtonStyle)
			return new ImageButton((ImageButtonStyle) style);
		return new Button(style);
	}

	/** Tries to load a {@link TextButtonStyle}, then {@link ImageButtonStyle}, then {@link ImageTextButtonStyle} and then {@link ButtonStyle} using {@link Json#readValue(String, Class, JsonValue)} brutally by catching NPEs. Nasty... */
	public static ButtonStyle readButtonStyle(String name, Json json, JsonValue jsonValue) {
		try {
			return json.readValue(name, TextButtonStyle.class, jsonValue);
		} catch(NullPointerException e) {
			try {
				return json.readValue(name, ImageButtonStyle.class, jsonValue);
			} catch(NullPointerException e1) {
				try {
					return json.readValue(name, ImageTextButtonStyle.class, jsonValue);
				} catch(NullPointerException e2) {
					try {
						return json.readValue(name, ButtonStyle.class, jsonValue);
					} catch(NullPointerException e3) {
						return null;
					}
				}
			}
		}
	}

}