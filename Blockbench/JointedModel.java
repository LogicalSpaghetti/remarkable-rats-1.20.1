// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class JointedModel extends EntityModel<Entity> {
	private final ModelPart main;
	private final ModelPart A;
	private final ModelPart A2;
	private final ModelPart A3;
	private final ModelPart B;
	private final ModelPart B2;
	private final ModelPart B3;
	private final ModelPart C;
	private final ModelPart C2;
	private final ModelPart C3;
	private final ModelPart D;
	private final ModelPart D2;
	private final ModelPart D3;
	public JointedModel(ModelPart root) {
		this.main = root.getChild("main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		ModelPartData A = main.addChild("A", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.5708F, 0.7854F, 0.0F));

		ModelPartData A2 = A.addChild("A2", ModelPartBuilder.create().uv(12, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

		ModelPartData A3 = A2.addChild("A3", ModelPartBuilder.create().uv(12, 12).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

		ModelPartData B = main.addChild("B", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.5708F, 2.3562F, 0.0F));

		ModelPartData B2 = B.addChild("B2", ModelPartBuilder.create().uv(12, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

		ModelPartData B3 = B2.addChild("B3", ModelPartBuilder.create().uv(12, 12).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

		ModelPartData C = main.addChild("C", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.5708F, -2.3562F, 0.0F));

		ModelPartData C2 = C.addChild("C2", ModelPartBuilder.create().uv(12, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

		ModelPartData C3 = C2.addChild("C3", ModelPartBuilder.create().uv(12, 12).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));

		ModelPartData D = main.addChild("D", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F, 0.0F));

		ModelPartData D2 = D.addChild("D2", ModelPartBuilder.create().uv(12, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

		ModelPartData D3 = D2.addChild("D3", ModelPartBuilder.create().uv(12, 12).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}