package main.me.spaghetti.remarkablerats.entity.rat;

import main.me.spaghetti.remarkablerats.entity.animation.ModAnimations;
import main.me.spaghetti.remarkablerats.entity.rat.RatEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class RatModel<T extends RatEntity> extends SinglePartEntityModel<T> {
	private final ModelPart rat;
	private final ModelPart head;

	public RatModel(ModelPart root) {
		this.rat = root.getChild("Rat");
		this.head = rat.getChild("BodyBottom").getChild("Chest").getChild("Head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Rat = modelPartData.addChild("Rat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData BodyBottom = Rat.addChild("BodyBottom", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -4.0F, -4.0F, 6.0F, 4.0F, 5.0F, new Dilation(0.0F))
				.uv(12, 9).cuboid(-2.5F, -3.5F, 1.0F, 5.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 2.0F));

		ModelPartData Chest = BodyBottom.addChild("Chest", ModelPartBuilder.create().uv(0, 9).cuboid(-2.0F, 0.0F, -4.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0152F, -3.8264F));

		ModelPartData Head = Chest.addChild("Head", ModelPartBuilder.create().uv(12, 13).cuboid(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, -4.0F));

		ModelPartData Nose = Head.addChild("Nose", ModelPartBuilder.create().uv(13, 24).cuboid(-0.5F, -4.5F, -10.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 6.0F));

		ModelPartData Ears = Head.addChild("Ears", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.5F, -1.0F));

		ModelPartData LeftEar = Ears.addChild("LeftEar", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, -1.0F, 0.0F));

		ModelPartData LeftEar_r1 = LeftEar.addChild("LeftEar_r1", ModelPartBuilder.create().uv(0, 28).cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		ModelPartData RightEar = Ears.addChild("RightEar", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, -1.0F, 0.0F));

		ModelPartData RightEar_r1 = RightEar.addChild("RightEar_r1", ModelPartBuilder.create().uv(0, 28).cuboid(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData whiskers = Head.addChild("whiskers", ModelPartBuilder.create().uv(4, 27).cuboid(-3.5F, -2.5F, 0.0F, 7.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -4.0F));

		ModelPartData FrontLegs = Chest.addChild("FrontLegs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 3.0F, -2.0F));

		ModelPartData FrontLeftLeg = FrontLegs.addChild("FrontLeftLeg", ModelPartBuilder.create().uv(21, 20).cuboid(2.0F, -4.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 4.0F));

		ModelPartData FrontLeftFoot = FrontLeftLeg.addChild("FrontLeftFoot", ModelPartBuilder.create().uv(10, 20).cuboid(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, -2.0F, -4.5F));

		ModelPartData FrontRightLeg = FrontLegs.addChild("FrontRightLeg", ModelPartBuilder.create().uv(22, 11).cuboid(-3.0F, -4.0F, -5.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 4.0F));

		ModelPartData FrontRightFoot = FrontRightLeg.addChild("FrontRightFoot", ModelPartBuilder.create().uv(5, 17).cuboid(-0.5F, 0.0F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -2.0F, -4.5F));

		ModelPartData Tail = BodyBottom.addChild("Tail", ModelPartBuilder.create().uv(22, 5).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 2.0F));

		ModelPartData tail2 = Tail.addChild("tail2", ModelPartBuilder.create().uv(18, 25).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 3.0F));

		ModelPartData BackLegs = Rat.addChild("BackLegs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 1.0F));

		ModelPartData BackLeftLeg = BackLegs.addChild("BackLeftLeg", ModelPartBuilder.create().uv(5, 20).cuboid(3.0F, -1.0F, -1.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData BackLeftFoot = BackLeftLeg.addChild("BackLeftFoot", ModelPartBuilder.create().uv(13, 20).cuboid(3.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, -1.0F));

		ModelPartData BackRightLeg = BackLegs.addChild("BackRightLeg", ModelPartBuilder.create().uv(17, 0).cuboid(-4.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, -1.0F));

		ModelPartData BackRightFoot = BackRightLeg.addChild("BackRightFoot", ModelPartBuilder.create().uv(0, 17).cuboid(-4.0F, -5.0F, 0.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		rat.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return rat;
	}

	@Override
	public void setAngles(RatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform); // this line resets the transformations each time, so they don't stack
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.RAT_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.RAT_IDLE, ageInTicks, 1f);
	}
}