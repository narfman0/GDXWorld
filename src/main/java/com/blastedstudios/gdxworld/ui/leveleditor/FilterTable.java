package com.blastedstudios.gdxworld.ui.leveleditor;

import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class FilterTable extends Table {
	private final TextField maskBitsLabel, categoryBitsLabel, groupIndexLabel;
	
	public FilterTable(final Skin skin, final Filter filter){
		maskBitsLabel = new TextField(filter.maskBits+"", skin);
		maskBitsLabel.setMessageText("<mask bits, default -1>");
		categoryBitsLabel = new TextField(filter.categoryBits+"", skin);
		categoryBitsLabel.setMessageText("<category bits, default 1>");
		groupIndexLabel = new TextField(filter.groupIndex+"", skin);
		groupIndexLabel.setMessageText("<group index, default 0>");
		add(new Label("Mask: ", skin));
		add(maskBitsLabel);
		row();
		add(new Label("Cat: ", skin));
		add(categoryBitsLabel);
		row();
		add(new Label("Grp: ", skin));
		add(groupIndexLabel);
	}
	
	public Filter createFilterFromUI(){
		Filter filter = new Filter();
		filter.maskBits = Short.parseShort(maskBitsLabel.getText());
		filter.categoryBits = Short.parseShort(categoryBitsLabel.getText());
		filter.groupIndex = Short.parseShort(groupIndexLabel.getText());
		return filter;
	}
}
