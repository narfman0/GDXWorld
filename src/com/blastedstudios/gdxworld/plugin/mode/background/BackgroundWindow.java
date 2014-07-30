package com.blastedstudios.gdxworld.plugin.mode.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blastedstudios.gdxworld.ui.AbstractWindow;
import com.blastedstudios.gdxworld.ui.GDXRenderer;
import com.blastedstudios.gdxworld.ui.leveleditor.VertexTable;
import com.blastedstudios.gdxworld.world.GDXBackground;

class BackgroundWindow extends AbstractWindow {
	private final VertexTable centerTable, scissorLowerLeft, scissorUpperRight;
	private final GDXBackground background;
	private final TextField depthField, scaleField;
	private final CheckBox scissor;
	private final Table scissorContents;
	private final BackgroundMode mode;
	private VertexTable selected = null;
	
	public BackgroundWindow(final Skin skin, final BackgroundMode mode, final GDXBackground background) {
		super("Background Editor", skin);
		this.background = background;
		this.mode = mode;
		scissorContents = new Table(skin);
		centerTable = new VertexTable(background.getCoordinates(), skin);
		Button centerButton = new TextButton("+", skin);
		centerButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selected = centerTable;
			}
		});
		final TextField textureField = new TextField("", skin);
		textureField.setMessageText("<background texture>");
		textureField.setText(background.getTexture());
		depthField = new TextField(background.getDepth()+"", skin);
		depthField.setMessageText("<depth>");
		scaleField = new TextField(background.getScale()+"", skin);
		scaleField.setMessageText("<scale>");
		scissor = new CheckBox("Use Scissor", skin);
		scissor.setChecked(background.isScissor());
		scissor.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				scissorContents.setVisible(scissor.isChecked());
				pack();
			}
		});
		scissorLowerLeft = new VertexTable(background.getScissorLowerLeft(), skin);
		Button scissorLowerLeftButton = new TextButton("+", skin);
		scissorLowerLeftButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selected = scissorLowerLeft;
			}
		});
		scissorUpperRight = new VertexTable(background.getScissorUpperRight(), skin);
		Button scissorUpperRightButton = new TextButton("+", skin);
		scissorUpperRightButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				selected = scissorUpperRight;
			}
		});
		
		final Button acceptButton = new TextButton("Accept", skin);
		final Button cancelButton = new TextButton("Cancel", skin);
		final Button deleteButton = new TextButton("Delete", skin);
		acceptButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				background.setTexture(textureField.getText());
				background.setCoordinates(centerTable.getVertex());
				background.setDepth(getDepth());
				background.setScale(getScale());
				background.setScissor(scissor.isChecked());
				background.setScissorUpperRight(scissorUpperRight.getVertex());
				background.setScissorLowerLeft(scissorLowerLeft.getVertex());
				mode.addBackground(background);
				mode.clean();
			}
		});
		cancelButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.clean();
			}
		});
		deleteButton.addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				mode.removeBackground(background);
				mode.clean();
			}
		});

		add(new Label("Texture: ", skin));
		add(textureField);
		row();
		add(new Label("Depth: ", skin));
		add(depthField);
		row();
		add(new Label("Scale: ", skin));
		add(scaleField);
		row();
		add(new Label("Center: ", skin));
		add(centerTable);
		add(centerButton);
		row();
		add(scissor);
		row();
		scissorContents.add("Scissor Lower Left");
		scissorContents.add(scissorLowerLeft);
		scissorContents.add(scissorLowerLeftButton);
		scissorContents.row();
		scissorContents.add("Scissor Upper Right");
		scissorContents.add(scissorUpperRight);
		scissorContents.add(scissorUpperRightButton);
		scissorContents.setVisible(background.isScissor());
		add(scissorContents).colspan(3);
		row();
		Table controlTable = new Table();
		controlTable.add(acceptButton);
		controlTable.add(deleteButton);
		controlTable.add(cancelButton);
		add(controlTable).colspan(3);
		setMovable(false);
		pack();
	}

	public void setCenter(Vector2 center) {
		centerTable.setVertex(center.x, center.y);
	}
	
	public boolean clicked(Vector2 pos){
		if(selected != null){
			if(selected == scissorLowerLeft || selected == scissorUpperRight){
				Texture tex = mode.getScreen().getGDXRenderer().getTexture(background.getTexture());
				Vector2 world = pos.cpy().add(new Vector2(tex.getWidth()/2f, tex.getHeight()/2f).
						scl(getScale() * (selected == scissorLowerLeft ? -1f : 1f)));
				Gdx.app.log("BackgroundWindow.clicked", "Finish calculating correct parallax for world: " + world.toString());
				pos = GDXRenderer.fromParallax(getDepth(), pos, mode.getScreen().getCamera());
			}
			selected.setVertex(pos.x, pos.y);
			selected = null;
			return true;
		}
		return false;
	}

	public GDXBackground getGDXBackground() {
		return background;
	}
	
	public float getDepth(){
		return Float.parseFloat(depthField.getText());
	}
	
	public float getScale(){
		return Float.parseFloat(scaleField.getText());
	}
}
