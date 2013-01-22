package com.blastedstudios.gdxworld.world.quest.manifestation;

public class DialogManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	public static DialogManifestation DEFAULT = new DialogManifestation("Dialog","Origin");
	/**
	 * Dialog contents, for example, "You brought five volume units of 
	 * food, high five"
	 */
	private String dialog;
	/**
	 * Origin of the dialog, for example, a character name referring to an
	 * NPC
	 */
	private String origin;
	
	public DialogManifestation(){}
	
	public DialogManifestation(String dialog, String origin){
		this.dialog = dialog;
		this.origin = origin;
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

	@Override public void execute() {
		executor.addDialog(dialog, origin);
	}

	@Override public Object clone() {
		return new DialogManifestation(dialog, origin);
	}

	@Override public String toString() {
		return "[DialogManifestation: dialog:" + dialog + " origin:" + origin + "]";
	}
}