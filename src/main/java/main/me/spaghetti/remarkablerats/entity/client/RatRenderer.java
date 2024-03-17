package main.me.spaghetti.remarkablerats.entity.client;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.entity.custom.RatEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RatRenderer extends MobEntityRenderer<RatEntity, RatModel<RatEntity>> {
    public RatRenderer(EntityRendererFactory.Context context) {
        super(context, new RatModel<>(context.getPart(ModModelLayers.RAT)), 0.3f);
    }

    @Override
    public Identifier getTexture(RatEntity entity) {
        return new Identifier(RemarkableRats.MOD_ID, "textures/entity/"+ entity.getVariant().getName() + "_rat.png");
    }

    @Override
    public void render(RatEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {

        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f); // is this necessary?
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
