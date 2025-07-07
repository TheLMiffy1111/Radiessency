package thelm.radiessency.client.gui;

import java.text.DecimalFormat;
import java.util.Arrays;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import thelm.radiessency.api.radiessence.IRadiessenceStorage;
import thelm.radiessency.container.BaseContainer;

public abstract class BaseContainerGui<C extends BaseContainer> extends GuiContainer {

	public static final DecimalFormat BALANCE_FORMAT = new DecimalFormat("0.000");

	public final C container;

	public BaseContainerGui(C container) {
		super(container);
		this.container = container;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	protected abstract ResourceLocation getBackgroundTexture();

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(getBackgroundTexture());
		GlStateManager.color(1, 1, 1, 1);
		if(xSize > 256 || ySize > 256) {
			GuiHelper.blit(guiLeft, guiTop, 0, 0, xSize, ySize, 512, 512);
		}
		else {
			GuiHelper.blit(guiLeft, guiTop, 0, 0, xSize, ySize);
		}
	}

	protected void drawContainerTitle() {
		String containerTitle = container.getDisplayName().getUnformattedText();
		String inventoryTitle = container.playerInventory.getDisplayName().getUnformattedText();
		fontRenderer.drawString(containerTitle, xSize/2 - fontRenderer.getStringWidth(containerTitle)/2, 6, 0x404040);
		fontRenderer.drawString(inventoryTitle, container.getPlayerInvX(), container.getPlayerInvY()-11, 0x404040);
	}

	public boolean mouseInArea(int mouseX, int mouseY, int x, int y, int width, int height) {
		return mouseX-guiLeft >= x && mouseX-guiLeft < x+width && mouseY-guiTop >= y && mouseY-guiTop < y+height;
	}

	public void drawRadiessenceTooltip(IRadiessenceStorage storage, int x, int y) {
		drawHoveringText(Arrays.asList(
				I18n.translateToLocalFormatted("misc.radiessency.radiessence_storage", storage.getAmount(), storage.getCapacity()),
				I18n.translateToLocalFormatted("misc.radiessency.balance", BALANCE_FORMAT.format(storage.getBalance()))),
				x, y);
	}

	public float getScaledAmount(int amount, int max, int scale) {
		return GuiHelper.getScaledAmount(amount, max, scale);
	}
}
