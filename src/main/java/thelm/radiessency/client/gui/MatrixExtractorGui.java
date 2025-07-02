package thelm.radiessency.client.gui;

import net.minecraft.util.ResourceLocation;
import thelm.radiessency.container.MatrixExtractorContainer;

public class MatrixExtractorGui extends BaseContainerGui<MatrixExtractorContainer> {

	public static final ResourceLocation BACKGROUND = new ResourceLocation("radiessency:textures/gui/matrix_extractor.png");

	public MatrixExtractorGui(MatrixExtractorContainer container) {
		super(container);
	}

	@Override
	protected ResourceLocation getBackgroundTexture() {
		return BACKGROUND;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		GuiHelper.drawRadiessence(container.tile.radiessenceStorageWrapper, guiLeft+82, guiTop+18, 12, 32);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		drawContainerTitle();
		if(mouseInArea(mouseX, mouseY, 82, 18, 12, 32)) {
			drawRadiessenceTooltip(container.tile.radiessenceStorageWrapper, mouseX-guiLeft, mouseY-guiTop);
		}
	}
}
