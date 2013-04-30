package com.blastedstudios.gdxworld.world.quest.manifestation;

public class DialogManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	public static DialogManifestation DEFAULT = new DialogManifestation("Dialog","Origin","Type");
	/**
	 * Dialog contents, for example, "You brought five volume units of 
	 * food, high five"
	 */
	private String dialog = "";
	/**
	 * Origin of the dialog, for example, a character name referring to an
	 * NPC
	 */
	private String origin = "";
	/**
	 * Type for the dialog, like exclamation, declaration, world event, etc
	 */
	private String type = "";
	
	public DialogManifestation(){}
	
	public DialogManifestation(String dialog, String origin, String type){
		this.dialog = dialog;
		this.origin = origin;
		this.type = type;
	}
	
	public String getDialog() {
		return dialog;
	}
	
	public void setDialog(String dialog) {
		this.dialog = dialog;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override public void execute() {
		executor.addDialog(dialog, origin, type);
	}

	@Override public AbstractQuestManifestation clone() {
		return new DialogManifestation(dialog, origin, type);
	}

	@Override public String toString() {
		return "[DialogManifestation: dialog:" + dialog + " origin:" + origin +
				" type:" + type + "]";
	}
}