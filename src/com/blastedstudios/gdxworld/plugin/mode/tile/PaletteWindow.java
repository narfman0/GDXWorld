package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.blastedstudios.gdxworld.util.Properties;

public class PaletteWindow extends AbstractWindow {
	private final TileMode tileMode;
	private final Set<Tile> tiles;
	private Table tileTable;
	private String paletteFilePath;
	private TextField tilesetFileField;
	private TextField marginField;
	private TextField spacingField;
	private TextField tilesizeField;
	private int margin;
	private int spacing;
	private int tilesize;
	
	public PaletteWindow(final Skin skin, final TileMode tileMode) {
		super("Palette Editor", skin);
		this.tileMode = tileMode;
		paletteFilePath = Properties.get("tilemode.tilesetFile");
		tiles = new HashSet<Tile>();
		tileTable = new Table();
		final Button load = new TextButton("Load", skin);
		load.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(validateInput())
					loadPalette(paletteFilePath, margin, spacing, tilesize);
			}
		});
		final Button select = new TextButton("Select tile set", skin);
		select.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				File pFile = FileUtil.fileChooser(true, false);
				if(pFile == null)
					return;
				paletteFilePath = pFile.getAbsolutePath();
				tilesetFileField.setText(getFilename(paletteFilePath));
			}
		});
		final Button clear = new TextButton("Clear", skin);
		clear.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		
		tilesetFileField = new TextField(getFilename(paletteFilePath), skin);
		tilesetFileField.setDisabled(true);
		marginField = new TextField("0", skin);
		spacingField = new TextField("1", skin);
		tilesizeField = new TextField("70", skin);
		
		Table table = new Table();
		table.add(select);
		table.add(tilesetFileField);
		table.row();
		table.add(new Label("Margin:", skin));
		table.add(marginField);
		table.row();
		table.add(new Label("Spacing:", skin));
		table.add(spacingField);
		table.row();
		table.add(new Label("Tile Size:", skin));
		table.add(tilesizeField);
		table.row();
		table.add(load);
		table.row();
		add(table);
		row();
		add(tileTable);
		row();
		setMovable(false);
		pack();
	}

	private String getFilename(String filepath) {
		if(filepath.equals("") || filepath == null)
			return "";
		String[] filename = filepath.split("/");
		return filename[filename.length-1];
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
	
	private List<Tile> split(Texture texture, int margin, int spacing, int tilesize) {
		List<Tile> tiles = new ArrayList<>();
		int stopWidth = texture.getWidth() - tilesize - margin;
		int stopHeight = texture.getHeight() - tilesize - margin;
		
		for(int y = margin; y <= stopHeight; y += tilesize + spacing) {
			for(int x = margin; x <= stopWidth; x += tilesize + spacing) {
				tiles.add(new Tile(new TextureRegion(texture, x, y, tilesize, tilesize)));
			}
		}
		return tiles;
		
	}
	
	/**
	 * Loads a png file containing a spritesheet and creates a set of tiles that can
	 * be added to the world
	 */
	private void loadPalette(final String paletteFilePath, final int margin, final int spacing, final int tilesize) {
		Gdx.app.log("TileMode.PaletteWindow.loadPalette", "Loading palette file " + paletteFilePath);
		Texture texture = new Texture(Gdx.files.absolute(paletteFilePath));
		tiles.clear();
		tiles.addAll(split(texture, margin, spacing, tilesize));
		reloadPalette();
		pack();
	}
	
	/**
	 * Clears old tiles and loads last loaded tile set into palette window
	 */
	private void reloadPalette() {
		tileTable.remove();
		tileTable = new Table();
		for(Tile tile : tiles)
			tileTable.add(tile);
		add(tileTable);
	}
}
