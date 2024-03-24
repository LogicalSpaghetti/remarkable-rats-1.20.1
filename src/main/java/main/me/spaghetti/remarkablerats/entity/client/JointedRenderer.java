package main.me.spaghetti.remarkablerats.entity.client;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.entity.custom.JointedEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class JointedRenderer extends MobEntityRenderer<JointedEntity, JointedModel<JointedEntity>> {
    private static final Identifier TEXTURE = new Identifier(RemarkableRats.MOD_ID, "textures/entity/jointed.png");

    public JointedRenderer(EntityRendererFactory.Context context) {
        super(context, new JointedModel<>(context.getPart(ModModelLayers.JOINTED)), 0.3f);
    }

    @Override
    public Identifier getTexture(JointedEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(JointedEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {

        if(mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f); // is this necessary?
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}