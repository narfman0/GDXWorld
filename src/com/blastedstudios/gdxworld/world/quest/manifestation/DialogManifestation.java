package com.blastedstudios.gdxworld.world.quest.manifestation;

public class DialogManifestation extends AbstractQuestManifestation{
	private static final long serialVersionUID = 1L;
	/**
	 * Dialog contents, for example, "You brought five volume units of 
	 * food, high five"
	 */
	private final String dialog;
	/**
	 * Origin of the dialog, for example, a character name referring to an
	 * NPC
	 */
	private final String origin;
	
	public DialogManifestation(String dialog, String origin){
		this.dialog = dialog;
		this.origin = origin;
	}
	
	public String getDialog() {
		return dialog;
	}
	
	public String getOrigin() {
		return origin;
	}

	@Override public void execute() {
		executor.addDialog(dialog, origin);
	}

	@Override public Object clone() {
		return new DialogManifestation(dialog, origin);
	}
}