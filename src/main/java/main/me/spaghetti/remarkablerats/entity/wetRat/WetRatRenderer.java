package main.me.spaghetti.remarkablerats.entity.wetRat;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.entity.client.ModModelLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WetRatRenderer extends MobEntityRenderer<WetRatEntity, WetRatModel<WetRatEntity>> {
    private static final Identifier TEXTURE = new Identifier(RemarkableRats.MOD_ID, "textures/entity/wet_rat.png");

    public WetRatRenderer(EntityRendererFactory.Context context) {
        super(context, new WetRatModel<>(context.getPart(ModModelLayers.WET_RAT)), 0.3f);
    }

    @Override
    public Identifier getTexture(WetRatEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(WetRatEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {

        float scalingX = 0.25f/7.5f;
        float scalingY = 0.5f/15.328125f;


        if(mobEntity.isBaby()) {
            matrixStack.scale(scalingX/2, scalingY/2, scalingX/2);
        } else {
            matrixStack.scale(scalingX, scalingY, scalingX);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}