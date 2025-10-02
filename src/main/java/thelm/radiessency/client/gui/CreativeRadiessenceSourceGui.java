package thelm.radiessency.client.gui;

import java.io.IOException;

import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import thelm.radiessency.container.CreativeRadiessenceSourceContainer;
import thelm.radiessency.network.PacketHandler;
import thelm.radiessency.network.packet.CreativeRadiessenceSourceModifyPacket;

public class CreativeRadiessenceSourceGui extends BaseContainerGui<CreativeRadiessenceSourceContainer> {

	public static final ResourceLocation BACKGROUND = new ResourceLocation("radiessency:textures/gui/creative_source.png");

	protected GuiTextField balanceField;
	protected GuiTextField amountField;

	public CreativeRadiessenceSourceGui(CreativeRadiessenceSourceContainer container) {
		super(container);
	}

	@Override
	protected ResourceLocation getBackgroundTexture() {
		return BACKGROUND;
	}

	@Override
	public void initGui() {
		super.initGui();

		balanceField = new GuiTextField(0, fontRenderer, guiLeft+63, guiTop+33, 83, fontRenderer.FONT_HEIGHT);
		balanceField.setEnableBackgroundDrawing(false);
		balanceField.setText(String.valueOf(container.tile.balance));
		balanceField.setTextColor(0xFFFFFF);
		balanceField.setValidator(s->{
			if(s.isEmpty()) {
				return true;
			}
			try {
				float balance = Float.parseFloat(s);
				return balance >= 0;
			}
			catch(NumberFormatException e) {
				return false;
			}
		});
		balanceField.setGuiResponder((TextFieldResponder)(id, value)->{
			try {
				float balance = Floats.constrainToRange(Float.parseFloat(value), 0, 2);
				if(balance != container.tile.balance) {
					container.tile.setStored(balance, container.tile.amount);
					PacketHandler.INSTANCE.sendToServer(new CreativeRadiessenceSourceModifyPacket(balance, container.tile.amount));
				}
			}
			catch(NumberFormatException e) {}
		});

		amountField = new GuiTextField(0, fontRenderer, guiLeft+63, guiTop+55, 83, fontRenderer.FONT_HEIGHT);
		amountField.setEnableBackgroundDrawing(false);
		amountField.setText(String.valueOf(container.tile.amount));
		amountField.setTextColor(0xFFFFFF);
		amountField.setValidator(s->{
			if(s.isEmpty()) {
				return true;
			}
			try {
				int amount = Integer.parseInt(s);
				return amount >= 1 && amount <= 1000000000;
			}
			catch(NumberFormatException e) {
				return false;
			}
		});
		amountField.setGuiResponder((TextFieldResponder)(id, value)->{
			try {
				int amount = Ints.constrainToRange(Integer.parseInt(value), 1, 1000000000);
				if(amount != container.tile.amount) {
					container.tile.setStored(container.tile.balance, amount);
					PacketHandler.INSTANCE.sendToServer(new CreativeRadiessenceSourceModifyPacket(container.tile.balance, amount));
				}
			}
			catch(NumberFormatException e) {}
		});
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		GuiHelper.drawRadiessence(container.tile.radiessenceStorageWrapper, guiLeft+28, guiTop+18, 12, 50);
		balanceField.drawTextBox();
		amountField.drawTextBox();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		drawContainerTitle();
		fontRenderer.drawString(I18n.translateToLocal("misc.radiessency.balance"), 62, 22, 0x404040);
		fontRenderer.drawString(I18n.translateToLocal("misc.radiessency.amount"), 62, 44, 0x404040);
		if(mouseInArea(mouseX, mouseY, 28, 18, 12, 50)) {
			drawRadiessenceTooltip(container.tile.radiessenceStorageWrapper, mouseX-guiLeft, mouseY-guiTop);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		balanceField.mouseClicked(mouseX, mouseY, mouseButton);
		amountField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(balanceField.textboxKeyTyped(typedChar, keyCode) || amountField.textboxKeyTyped(typedChar, keyCode)) {
			return;
		}
		if(mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode) && (balanceField.isFocused() || amountField.isFocused())) {
			return;
		}
		super.keyTyped(typedChar, keyCode);
	}
}
