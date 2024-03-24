package main.me.spaghetti.remarkablerats.entity.client;

import main.me.spaghetti.remarkablerats.entity.custom.JointedEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class JointedModel<T extends JointedEntity> extends SinglePartEntityModel<T> {
	private final ModelPart bb_main;
	public JointedModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -30.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(1.0F, -32.0F, -4.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-4.0F, -32.0F, -4.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-4.0F, -32.0F, 1.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(1.0F, -32.0F, 1.0F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F))
		.uv(12, 0).cuboid(1.5F, -20.0F, 1.5F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(12, 0).cuboid(-3.5F, -20.0F, 1.5F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(12, 0).cuboid(-3.5F, -20.0F, -3.5F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(12, 0).cuboid(1.5F, -20.0F, -3.5F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F))
		.uv(20, 0).cuboid(-3.0F, -10.0F, 2.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 0).cuboid(2.0F, -10.0F, 2.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 0).cuboid(2.0F, -10.0F, -3.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 0).cuboid(-3.0F, -10.0F, -3.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return null;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		// if this is all I need to make the joints work, I will laugh SO hard
	}
}