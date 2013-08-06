package com.blastedstudios.gdxworld.world.quest;

import java.io.Serializable;
import java.util.Comparator;

public class QuestStatus implements Comparable<QuestStatus>, Serializable{
	private static final long serialVersionUID = 1L;
	public final String levelName, questName;
	private CompletionEnum completed = CompletionEnum.NOT_STARTED;
	
	public QuestStatus(String levelName, String questName){
		this.levelName = levelName;
		this.questName = questName;
	}

	public CompletionEnum getCompleted() {
		return completed;
	}

	public void setCompleted(CompletionEnum completed) {
		this.completed = completed;
	}
	
	@Override public int hashCode(){
		return (levelName + questName + completed).hashCode();
	}

	@Override public int compareTo(QuestStatus status) {
		return (levelName+questName+completed).compareTo(
				status.levelName+status.questName+status.completed);
	}
	
	@Override public boolean equals(Object obj){
		if(obj instanceof QuestStatus)
			return ((QuestStatus)obj).levelName.equals(levelName) && 
					((QuestStatus)obj).questName.equals(questName) &&
					((QuestStatus)obj).completed == completed;
		return false;
	}
	
	@Override public String toString(){
		return "[QuestStatus levelName:" + levelName + " questName:" + questName + 
				" completed:" + completed + "]";
	}
	
	/**
	 * Used for sorting completed tasks. Completed comes last, incomplete first
	 */
	public static class CompletionComparator implements Comparator<QuestStatus>{
		@Override public int compare(QuestStatus s1, QuestStatus s2) {
			if(s1.completed.compareTo(s2.completed) != 0)
				return s1.completed.compareTo(s2.completed);
			return s1.levelName.compareTo(s2.levelName);
		}
		
	}
	
	public enum CompletionEnum{
		NOT_STARTED, EXECUTING, COMPLETED;
	}
}