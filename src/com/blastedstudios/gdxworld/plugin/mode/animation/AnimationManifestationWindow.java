package com.blastedstudios.gdxworld.plugin.mode.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.plugin.mode.quest.IQuestComponent.IQuestComponentManifestation;
import com.blastedstudios.gdxworld.plugin.mode.quest.ManifestationTable;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.util.PluginUtil;

public class AnimationManifestationWindow extends AbstractWindow {
	private ManifestationTable table;

	public AnimationManifestationWindow(final Skin skin, final AnimationStructRow animationStructRow, final IRemovedListener listener) {
		super("Animation Manifestation Editor", skin);
		for(IQuestComponentManifestation manifestationPlugin : PluginUtil.getPlugins(IQuestComponentManifestation.class))
			if(manifestationPlugin.getDefault().getClass() == animationStructRow.getStruct().manifestation.getClass())
				table = (ManifestationTable) manifestationPlugin.createTable(skin, animationStructRow.getStruct().manifestation);
		add(table);
		final Button updateButton = new TextButton("Update", skin);
		updateButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				animationStructRow.getStruct().manifestation = table.apply();
				AnimationManifestationWindow.this.remove();
				listener.removed();
			}
		});
		final Button cancelButton = new TextButton("Cancel", skin);
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				AnimationManifestationWindow.this.remove();
				listener.removed();
			}
		});
		row();
		Table controls = new Table();
		controls.add(updateButton);
		controls.add(cancelButton);
		add(controls);
		pack();
		setX(Gdx.graphics.getWidth()/2f - getWidth()/2f);
	}
	
	interface IRemovedListener{
		void removed();
	}
}
