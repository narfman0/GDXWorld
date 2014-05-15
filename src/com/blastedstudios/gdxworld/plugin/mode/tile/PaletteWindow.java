package com.blastedstudios.gdxworld.plugin.mode.tile;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	private static final int DEFAULT_SPACING = 2, DEFAULT_MARGIN = 2, DEFAULT_TILESIZE = 21;
	private final TileMode tileMode;
	private final List<PaletteTile> tiles;
	private final ScrollPane palette;
	private final Table tileTable;
	private final TextField tilesetFileField;
	private final TextField marginField;
	private final TextField spacingField;
	private final TextField tilesizeField;
	private String tilesetFile;
	private int margin;
	private int spacing;
	private int tilesize;
	
	public PaletteWindow(final Skin skin, final TileMode tileMode) {
		super("Palette", skin);
		this.tileMode = tileMode;
		
		tiles = new ArrayList<PaletteTile>();
		tilesetFile = Properties.get("tilemode.tilesetFile", "");
		
		final Button load = new TextButton("Load", skin);
		load.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				if(validateInput())
					loadPalette(tilesetFile, margin, spacing, tilesize);
			}
		});
		tilesetFileField = new TextField(tilesetFile, skin);
		marginField = new TextField(String.valueOf(DEFAULT_MARGIN), skin);
		spacingField = new TextField(String.valueOf(DEFAULT_SPACING), skin);
		tilesizeField = new TextField(String.valueOf(DEFAULT_TILESIZE), skin);
		
		final Table table = new Table();
		table.add(new Label("Tileset:", skin));
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
		table.add(load).colspan(2);
		table.row();
		add(table);
		row();
		tileTable = new Table();
		palette = new ScrollPane(tileTable, skin);
		palette.setFadeScrollBars(false);
		palette.setSmoothScrolling(false);
		palette.setScrollingDisabled(false, false);
		palette.setFlickScroll(false);
		add(palette).fill().expand().maxHeight(600).maxWidth(250);
		row();
		validate();
		pack();
	}

	private boolean validateInput() {
		try {
			tilesetFile = tilesetFileField.getText();
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
	
	private void loadPalette(final String tilesetFile, final int margin, final int spacing, final int tilesize) {
		Gdx.app.log("TileMode.PaletteWindow.loadPalette", "Loading palette file " + tilesetFile);
		FileHandle file = FileUtil.find(Gdx.files.internal("data"), tilesetFile);
		if(null == file) {
			Gdx.app.error("PaletteWindow.loadPalette", "File " + tilesetFile + " not found.");
			return;
		}
		clean();
		tiles.addAll(split(new Texture(file), margin, spacing, tilesize));
		for(int i=0; i < tiles.size(); i++) {
			tiles.get(i).setResource(tilesetFile);
			if(i % 10 == 0)
				tileTable.row();
			tileTable.add(tiles.get(i));
		}
		pack();
	}
	
	/** Parses texture and returns list of PaletteTiles */
	private List<PaletteTile> split(final Texture texture, final int margin, final int spacing, final int tilesize) {
		List<PaletteTile> tiles = new ArrayList<>();
		int stopWidth = texture.getWidth() - tilesize - margin;
		int stopHeight = texture.getHeight() - tilesize - margin;
		
		for(int y = margin; y <= stopHeight; y += tilesize + spacing) {
			for(int x = margin; x <= stopWidth; x += tilesize + spacing) {
				final PaletteTile tile = new PaletteTile(new Sprite(new TextureRegion(texture, x, y, tilesize, tilesize)), tilesetFile, x, y, tilesize);
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
	
	public int getTilesize() {
		return tilesize;
	}
	
	public void clean() {
		tiles.clear();
		tileTable.clear();
	}
}
