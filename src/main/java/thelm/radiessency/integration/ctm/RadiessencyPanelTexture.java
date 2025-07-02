package thelm.radiessency.integration.ctm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import team.chisel.ctm.Configurations;
import team.chisel.ctm.api.texture.ISubmap;
import team.chisel.ctm.api.texture.ITextureContext;
import team.chisel.ctm.api.util.TextureInfo;
import team.chisel.ctm.client.texture.ctx.TextureContextCTM;
import team.chisel.ctm.client.texture.render.TextureCTM;
import team.chisel.ctm.client.util.CTMLogic;
import team.chisel.ctm.client.util.Dir;
import team.chisel.ctm.client.util.Quad;
import team.chisel.ctm.client.util.Submap;

public class RadiessencyPanelTexture extends TextureCTM<RadiessencyPanelTextureType> {

	public static final ISubmap[] UVS = new ISubmap[40];

	static {
		float div = 8/3F;
		for(int u = 0; u < 3; ++u) {
			for(int v = 0; v < 3; ++v) {
				int index = v*3+u;
				UVS[index*4] = new Submap(div, div, (u*2)*div, (v*2+1)*div);
				UVS[index*4+1] = new Submap(div, div, (u*2+1)*div, (v*2+1)*div);
				UVS[index*4+2] = new Submap(div, div, (u*2+1)*div, (v*2)*div);
				UVS[index*4+3] = new Submap(div, div, (u*2)*div, (v*2)*div);
			}
		}
		UVS[36] = Submap.X2[1][0];
		UVS[37] = Submap.X2[1][1];
		UVS[38] = Submap.X2[0][1];
		UVS[39] = Submap.X2[0][0];
	}

	public static final Dir[][] ADJACENT = new Dir[][] {
		{ Dir.BOTTOM, Dir.LEFT, Dir.BOTTOM_LEFT },
		{ Dir.BOTTOM, Dir.RIGHT, Dir.BOTTOM_RIGHT },
		{ Dir.TOP, Dir.RIGHT, Dir.TOP_RIGHT },
		{ Dir.TOP, Dir.LEFT, Dir.TOP_LEFT },
	};

	public static final Dir[][] OPPOSITE = new Dir[][] {
		{ Dir.RIGHT, Dir.BOTTOM_RIGHT, Dir.TOP, Dir.TOP_LEFT },
		{ Dir.LEFT, Dir.BOTTOM_LEFT, Dir.TOP, Dir.TOP_RIGHT },
		{ Dir.LEFT, Dir.TOP_LEFT, Dir.BOTTOM, Dir.BOTTOM_RIGHT },
		{ Dir.RIGHT, Dir.TOP_RIGHT, Dir.BOTTOM, Dir.BOTTOM_LEFT },
	};

	public RadiessencyPanelTexture(RadiessencyPanelTextureType type, TextureInfo info) {
		super(type, info);
	}

	@Override
	public List<BakedQuad> transformQuad(BakedQuad bq, ITextureContext context, int quadGoal) {
		Quad quad = makeQuad(bq, context);
		if(context == null || Configurations.disableCTM) {
			return Collections.singletonList(quad.transformUVs(sprites[0]).rebake());
		}
		CTMLogic ctm = ((TextureContextCTM)context).getCTM(bq.getFace());
		Quad[] quads = quad.subdivide(4);
		for(int i = 0; i < quads.length; i++) {
			Quad q = quads[i];
			if(q != null) {
				int quadrant = q.getUvs().normalize().getQuadrant();
				int offset = 9;
				TextureAtlasSprite sprite = sprites[0];
				if(ctm.connectedOr(Dir.TOP, Dir.RIGHT, Dir.BOTTOM, Dir.LEFT)) {
					sprite = sprites[1];
					if(ctm.connectedAnd(Dir.VALUES)) {
						offset = 4;
					}
					else {
						offset = 0;
						Dir[] adjacent = ADJACENT[quadrant];
						if(!ctm.connectedAnd(adjacent)) {
							if(ctm.connected(adjacent[0])) {
								offset += 1;
							}
							if(ctm.connected(adjacent[1])) {
								offset += 2;
							}
						}
						else {
							offset = 5;
							Dir[] opposite = OPPOSITE[quadrant];
							if(ctm.connectedAnd(opposite[0], opposite[1])) {
								offset += 1;
							}
							if(ctm.connectedAnd(opposite[2], opposite[3])) {
								offset += 2;
							}
						}
					}
				}
				ISubmap submap = UVS[offset*4 + quadrant];
				quads[i] = q.grow().transformUVs(sprite, submap.normalize());
			}
		}
		return Arrays.stream(quads).filter(Objects::nonNull).map(Quad::rebake).collect(Collectors.toList());
	}
}
