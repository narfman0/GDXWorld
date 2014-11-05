package com.blastedstudios.gdxworld.util.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.blastedstudios.gdxworld.util.FileUtil;
import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.ui.FileChooser.Listener;

public class FileChooserWrapper {
	private final TreeFileChooser fc;
	
	private FileChooserWrapper(Stage stage, Skin skin, final IFileChooserHandler fileChooserHandler){
		Listener listener = new Listener() {
			@Override public void choose(Array<FileHandle> files) {
				fc.remove();
				Log.error("FileChooserWrapper::Listener.choose", "Can't handle more than one selected file, returning first!");
				fileChooserHandler.handle(files.get(0));
			}
			@Override public void choose(FileHandle file) {
				fc.remove();
				fileChooserHandler.handle(file);
			}
			@Override public void cancel() {
				fc.remove();
			}
		};
		fc = new TreeFileChooser(skin, listener);
		fc.add(FileUtil.ROOT_DIRECTORY);
		fc.add(Gdx.files.external("/"));
		fc.setWidth(300f);
		fc.setHeight(500f);
		fc.setX(Gdx.graphics.getWidth()/2f - fc.getWidth()/2f);
		fc.setY(Gdx.graphics.getHeight()/2f - fc.getHeight()/2f);
		stage.addActor(fc);
	}
	
	public interface IFileChooserHandler{
		public void handle(FileHandle handle);
	}
	
	public static FileChooserWrapper createFileChooser(Stage stage, Skin skin, final IFileChooserHandler fileChooserHandler){
		return new FileChooserWrapper(stage, skin, fileChooserHandler);
	}
}
