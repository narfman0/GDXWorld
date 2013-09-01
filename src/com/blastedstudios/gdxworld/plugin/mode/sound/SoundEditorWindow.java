package com.blastedstudios.gdxworld.plugin.mode.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.world.GDXSound;

public class SoundEditorWindow extends AbstractWindow {
	private final SoundTable soundTable;
	private final TextButton applyButton;
	
	public SoundEditorWindow(Skin skin, final GDXSound sound) {
		super("Sound Editor", skin);
		soundTable = new SoundTable(skin, sound);
		applyButton = new TextButton("Apply", skin);
		applyButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				soundTable.update(sound);
			}
		});
		add(soundTable);
		row();
		add(applyButton);
		pack();
		setMovable(false);
		setX(Gdx.graphics.getWidth());
	}
}
