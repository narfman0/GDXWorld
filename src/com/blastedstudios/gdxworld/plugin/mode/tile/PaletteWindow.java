package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
	private final List<PaletteTile> tiles;
	private final ScrollPane palette;
	private final Table tileTable;
	private final TextField tilesetFileField;
	private final TextField marginField;
	private final TextField spacingField;
	private final TextField tilesizeField;
	private String paletteFilePath;
	private PaletteTile activeTile;
	private int margin;
	private int spacing;
	private int tilesize;
	
	public PaletteWindow(final Skin skin, final TileMode tileMode) {
		super("Palette", skin);
		this.tileMode = tileMode;
		
		tiles = new ArrayList<PaletteTile>();
		paletteFilePath = Properties.get("tilemode.tilesetFile");
		
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
				Properties.set("tilemode.tilesetFile", tilesetFileField.getText());
			}
		});
		final Button clear = new TextButton("Clear", skin);
		clear.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				
			}
		});
		tilesetFileField = new TextField(getFilename(paletteFilePath), skin);
		tilesetFileField.setDisabled(true);
		marginField = new TextField("2", skin);
		spacingField = new TextField("2", skin);
		tilesizeField = new TextField("21", skin);
		
		final Table table = new Table();
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
		tileTable = new Table();
		palette = new ScrollPane(tileTable, skin);
		palette.setScrollingDisabled(false, false);
		palette.setFlickScroll(false);
		add(palette).fill().expand().maxHeight(600);
		row();
		validate();
		pack();
	}

	public int getTilesize() {
		return tilesize;
	}
	
	public void clean() {
		tiles.clear();
		tileTable.clear();
	}
	
	private String getFilename(final String filepath) {
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
	
	/** Parses texture and returns list of PaletteTiles */
	private List<PaletteTile> split(final Texture texture, final int margin, final int spacing, final int tilesize) {
		List<PaletteTile> tiles = new ArrayList<>();
		int stopWidth = texture.getWidth() - tilesize - margin;
		int stopHeight = texture.getHeight() - tilesize - margin;
		
		for(int y = margin; y <= stopHeight; y += tilesize + spacing) {
			for(int x = margin; x <= stopWidth; x += tilesize + spacing) {
				final PaletteTile tile = new PaletteTile(new TextureRegion(texture, x, y, tilesize, tilesize), tilesize);
				tile.addListener(new ClickListener() {
					@Override public void clicked(final InputEvent event, final float x, final float y) {
						tileMode.setActiveTile(tile);
					}
				});
				tiles.add(tile);
			}
		}
		return tiles;
	}
	
	private void loadPalette(final String paletteFilePath, final int margin, final int spacing, final int tilesize) {
		Gdx.app.log("TileMode.PaletteWindow.loadPalette", "Loading palette file " + paletteFilePath);
		FileHandle file = Gdx.files.absolute(paletteFilePath);
		if(!file.exists()) {
			Gdx.app.error("PaletteWindow.loadPalette", "File " + paletteFilePath + " not found.");
			return;
		}
		Texture texture = new Texture(file);
		clean();
		tiles.addAll(split(texture, margin, spacing, tilesize));
		for(int i=0; i < tiles.size(); i++) {
			tileTable.add(tiles.get(i));
			if(i % 10 == 0)
				tileTable.row();
		}
		palette.setSize(10 * tilesize, 20 * tilesize);
		pack();
	}
}
