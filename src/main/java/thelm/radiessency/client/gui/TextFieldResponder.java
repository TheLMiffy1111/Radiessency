package thelm.radiessency.client.gui;

import net.minecraft.client.gui.GuiPageButtonList;

public interface TextFieldResponder extends GuiPageButtonList.GuiResponder {

	@Override
	default void setEntryValue(int id, boolean value) {}

	@Override
	default void setEntryValue(int id, float value) {}
}
