package com.blastedstudios.gdxworld.plugin.quest.manifestation.dialog;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.blastedstudios.gdxworld.world.quest.QuestStatus.CompletionEnum;

@PluginImplementation
public interface IDialogHandler extends Plugin{
	CompletionEnum addDialog(String dialog, String origin, String type);
}
