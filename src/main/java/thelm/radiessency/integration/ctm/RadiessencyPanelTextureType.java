package thelm.radiessency.integration.ctm;

import team.chisel.ctm.api.texture.ICTMTexture;
import team.chisel.ctm.api.texture.TextureType;
import team.chisel.ctm.api.util.TextureInfo;
import team.chisel.ctm.client.texture.type.TextureTypeCTM;

@TextureType("radiessency_panel")
public class RadiessencyPanelTextureType extends TextureTypeCTM {

	@Override
	public ICTMTexture<? extends TextureTypeCTM> makeTexture(TextureInfo info) {
		return new RadiessencyPanelTexture(this, info);
	}

	@Override
	public int requiredTextures() {
		return 2;
	}
}
