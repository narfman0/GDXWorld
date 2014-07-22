package com.blastedstudios.gdxworld.plugin.mode.live;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.util.PluginUtil;

class LiveWindow extends AbstractWindow {
	private final HashMap<ILiveOptionProvider, Table> tableMap = new HashMap<>();
	private final Table table;
	
	public LiveWindow(final Skin skin) {
		super("Live Options", skin);
		table = new Table(skin);
		Collection<ILiveOptionProvider> providers = PluginUtil.getPlugins(ILiveOptionProvider.class);
		for(ILiveOptionProvider provider : providers)
			tableMap.put(provider, provider.getTable(skin, this));
		
		final SelectBox<LiveOptionSelectboxStruct> liveOptionsSelectbox = new SelectBox<>(skin);
		liveOptionsSelectbox.setItems(LiveOptionSelectboxStruct.create(new ArrayList<>(providers)));
		liveOptionsSelectbox.addListener(new ChangeListener() {
			@Override public void changed(ChangeEvent event, Actor actor) {
				update(skin, tableMap.get(liveOptionsSelectbox.getSelected().object));
			}
		});
		add(liveOptionsSelectbox);
		row();
		add(table);
		setMovable(false);
		if(!providers.isEmpty())
			update(skin, tableMap.get(liveOptionsSelectbox.getSelected().object));
	}
	
	private void update(Skin skin, Table optionTable){
		table.clear();
		table.add(optionTable);
		pack();
		setPosition(0f, 0f);
	}
}
