package thelm.radiessency.client.gui;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class RadiessencyIcons {

	public static TextureAtlasSprite radiessenceBalanced;
	public static TextureAtlasSprite radiessenceStasis;
	public static TextureAtlasSprite radiessenceChaos;

	public static void registerIcons(TextureMap textureMap) {
		radiessenceBalanced = textureMap.registerSprite(new ResourceLocation("radiessency:gui/radiessence_balanced"));
		radiessenceStasis = textureMap.registerSprite(new ResourceLocation("radiessency:gui/radiessence_stasis"));
		radiessenceChaos = textureMap.registerSprite(new ResourceLocation("radiessency:gui/radiessence_chaos"));
	}
}
