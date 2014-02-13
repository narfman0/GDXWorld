package com.blastedstudios.gdxworld.plugin.mode.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.world.GDXBackground;

public class BackgroundChooserWindow extends AbstractWindow{
	public BackgroundChooserWindow(final Skin skin, final BackgroundMode mode, 
			final java.util.List<GDXBackground> backgrounds) {
		super("Background Chooser", skin);
		final List backgroundList = new List(backgrounds.toArray(), skin);

		final Button selectButton = new TextButton("Select", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		selectButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.clean();
				mode.touchBegin(backgrounds.get(backgroundList.getSelectedIndex()));
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.clean();
			}
		});
		
		add(backgroundList);
		row();
		Table controlTable = new Table();
		controlTable.add(selectButton);
		controlTable.add(cancelButton);
		add(controlTable).colspan(2);
		pack();
		setMovable(false);
		setX(Gdx.graphics.getWidth());
	}
}
