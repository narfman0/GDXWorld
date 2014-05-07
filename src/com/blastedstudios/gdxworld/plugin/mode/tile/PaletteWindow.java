package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.util.FileUtil;

public class PaletteWindow extends AbstractWindow {
	private final TileMode tileMode;
	private File paletteFile;
	
	public PaletteWindow(Skin skin, TileMode tileMode) {
		super("Palette Editor", skin);
		this.tileMode = tileMode;
		final Button loadButton = new TextButton("Load tile set", skin);
		loadButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				loadPalette(paletteFile = FileUtil.fileChooser(false, false));
			}
		});
		add(loadButton);
	}
	
	private boolean loadPalette(File file) {
		Gdx.app.log("PaletteWindow.loadPalette", "Loading palette file " + file);
		return false;
	}
}
