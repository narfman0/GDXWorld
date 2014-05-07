package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.util.FileUtil;

public class PaletteWindow extends AbstractWindow {
	private final TileMode tileMode;
	private String paletteFile;
	private TextField marginField;
	private int margin;
	private TextField spacingField;
	private int spacing;
	private TextField tilesizeField;
	private int tilesize;	
	
	public PaletteWindow(final Skin skin, final TileMode tileMode) {
		super("Palette Editor", skin);
		this.tileMode = tileMode;
		final Button loadButton = new TextButton("Load tile set", skin);
		
		marginField = new TextField("", skin);
		
		
		spacingField = new TextField("", skin);
		
		
		tilesizeField = new TextField("", skin);
		
		loadButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				paletteFile = FileUtil.fileChooser(true, false).getAbsolutePath();
				if(validateInput())
					tileMode.loadPalette(paletteFile, margin, spacing, tilesize);
			}
		});
		add(loadButton);
		row();
		Table table = new Table();
		table.add(new Label("Margin:", skin));
		table.add(marginField);
		table.row();
		table.add(new Label("Spacing:", skin));
		table.add(spacingField);
		table.row();
		table.add(new Label("Tile Size:", skin));
		table.add(tilesizeField);
		table.row();
		add(table);
		setMovable(false);
		pack();
	}
	
	private boolean validateInput() {
		try {
			margin = Integer.parseInt(marginField.getText());
			spacing = Integer.parseInt(spacingField.getText());
			tilesize = Integer.parseInt(tilesizeField.getText());
		} catch(NumberFormatException nfe) {
			Gdx.app.log("PaletteWindow.validateInput", "Invalid input");
			margin = 0;
			spacing = 0;
			tilesize = 0;
			return false;
		}
		Gdx.app.log("PaletteWindow.validateInput", "Valid input");
		return true;
	}
}
