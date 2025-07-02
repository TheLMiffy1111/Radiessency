package thelm.radiessency.client.gui;

import net.minecraft.util.ResourceLocation;
import thelm.radiessency.container.IrradiationChamberContainer;

public class IrradiationChamberGui extends BaseContainerGui<IrradiationChamberContainer> {

	public static final ResourceLocation BACKGROUND = new ResourceLocation("radiessency:textures/gui/irradiation_chamber.png");

	public IrradiationChamberGui(IrradiationChamberContainer container) {
		super(container);
	}

	@Override
	protected ResourceLocation getBackgroundTexture() {
		return BACKGROUND;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		float scaledProgress = getScaledAmount(container.tile.progress, container.tile.cost, 12);
		GuiHelper.blit(guiLeft+82, guiTop+38, 176, 0, scaledProgress, 10);
		GuiHelper.drawRadiessence(container.tile.radiessenceStorageWrapper, guiLeft+10, guiTop+18, 12, 32);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		drawContainerTitle();
		if(mouseInArea(mouseX, mouseY, 10, 18, 12, 32)) {
			drawRadiessenceTooltip(container.tile.radiessenceStorageWrapper, mouseX-guiLeft, mouseY-guiTop);
		}
	}
}
