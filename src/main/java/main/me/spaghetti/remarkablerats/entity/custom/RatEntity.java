package main.me.spaghetti.remarkablerats.entity.custom;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.entity.ModEntities;
import main.me.spaghetti.remarkablerats.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.IntFunction;

public class RatEntity extends TameableEntity implements Bucketable, VariantHolder<RatEntity.RatVariant> {
    private boolean isFromBucket = false;

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private static final TrackedData<Integer> VARIANT;
    public static final String VARIANT_KEY = "Variant";

    public RatEntity(EntityType<? extends TameableEntity> type, World world) {
        super(type, world);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (spawnReason == SpawnReason.BUCKET) {
            return entityData;
        } else {
            Random random = world.getRandom();
            if (!(entityData instanceof RatData)) {
                entityData = new RatData(RatVariant.getRandom(random), RatVariant.getRandom(random));
            }
            this.setVariant(((RatData)entityData).getRandomVariant(random));

            return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        }
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(VARIANT_KEY, this.getVariant().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(RatVariant.byId(nbt.getInt(VARIANT_KEY)));
    }

    public void setVariant(RatVariant ratVariant) {
        this.dataTracker.set(VARIANT, ratVariant.getId());
    }

    public RatVariant getVariant() {
        return RatVariant.byId(this.dataTracker.get(VARIANT));
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }
    @Override
    public boolean canBreatheInWater() {
        return true;
    }
    @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }


    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            this.idleAnimationTimeout--;
        }
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        // bundling
        if (item == Items.BUNDLE) {
            return tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
        }

        if (this.isTamed()) {
            // healing
            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                if (item.getFoodComponent() != null) {
                    this.heal(item.getFoodComponent().getHunger());
                }
                return ActionResult.SUCCESS;
            }

            if (this.isOwner(player)) {
                // sitting
                if ((!this.isBreedingItem(itemStack))) {
                    this.setSitting(!this.isSitting());
                    return ActionResult.SUCCESS;
                }
            }
        } else if (item.getFoodComponent() != null) { // taming
            if (!player.getAbilities().creativeMode) {
                player.getStackInHand(hand).decrement(1);
            }
            if (this.random.nextInt(Math.max(1, 6 - item.getFoodComponent().getHunger())) == 0) {
                this.setOwner(player);
                this.navigation.stop();
                this.setTarget(null);
                this.getWorld().sendEntityStatus(this, (byte) 7);
            } else {
                for (int i = 0; i < 7; ++i) {
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }
            }
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.0));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4f));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 10.0f, 2.0f, false));
        //this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));
        this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
    }

    public static DefaultAttributeContainer.Builder createRatAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.RAT.create(world);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return RemarkableRats.RAT_SQUEAK_EVENT;
    }

    // what does this do
    @Override
    public EntityView method_48926() {
        return super.getWorld();
    }

    @Override
    public boolean isFromBucket() {
        return isFromBucket;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        isFromBucket = fromBucket;
    }

    static {
        VARIANT = DataTracker.registerData(RatEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public void copyDataToStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (this.hasCustomName()) {
            stack.setCustomName(this.getCustomName());
        }
        if (this.isAiDisabled()) {
            nbtCompound.putBoolean("NoAI", this.isAiDisabled());
        }
        if (this.isSilent()) {
            nbtCompound.putBoolean("Silent", this.isSilent());
        }
        if (this.hasNoGravity()) {
            nbtCompound.putBoolean("NoGravity", this.hasNoGravity());
        }
        if (this.isGlowingLocal()) {
            nbtCompound.putBoolean("Glowing", /*this.isGlowingLocal()*/true);
        }
        if (this.isInvulnerable()) {
            nbtCompound.putBoolean("Invulnerable", this.isInvulnerable());
        }
        nbtCompound.putFloat("Health", this.getHealth());

        if (this.getOwner() != null) {
            nbtCompound.putUuid("Owner", this.getOwnerUuid());
        }

        nbtCompound.putInt(VARIANT_KEY, this.getVariant().getId());
    }

    public void copyDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("NoAI")) {
            this.setAiDisabled(nbt.getBoolean("NoAI"));
        }
        if (nbt.contains("Silent")) {
            this.setSilent(nbt.getBoolean("Silent"));
        }
        if (nbt.contains("NoGravity")) {
            this.setNoGravity(nbt.getBoolean("NoGravity"));
        }
        if (nbt.contains("Glowing")) {
            this.setGlowing(nbt.getBoolean("Glowing"));
        }
        if (nbt.contains("Invulnerable")) {
            this.setInvulnerable(nbt.getBoolean("Invulnerable"));
        }
        if (nbt.contains("Health", NbtElement.NUMBER_TYPE)) {
            this.setHealth(nbt.getFloat("Health"));
        }

        if (nbt.contains("Owner") && nbt.getUuid("Owner") != null) {
            this.setOwnerUuid(nbt.getUuid("Owner"));
            setTamed(true);
        }

        this.setVariant(RatVariant.byId(nbt.getInt(VARIANT_KEY)));
        RemarkableRats.LOGGER.info(RatVariant.byId(nbt.getInt(VARIANT_KEY)).name);
        RemarkableRats.LOGGER.info(this.getVariant().name);
    }

    public static <T extends LivingEntity> Optional<ActionResult> tryBucket(PlayerEntity player, Hand hand, T entity) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.BUNDLE && entity.isAlive()) {
            entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 1.0f, 1.0f);
            ItemStack itemStack2 = ((Bucketable) entity).getBucketItem();
            ((Bucketable) entity).copyDataToStack(itemStack2);
            ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, player, itemStack2, false);
            player.setStackInHand(hand, itemStack3);
            World world = entity.getWorld();
            if (!world.isClient) {
                Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)player, itemStack2);
            }
            entity.discard();
            return Optional.of(ActionResult.success(world.isClient));
        }
        return Optional.empty();
    }

    @Override
    public ItemStack getBucketItem() {
        return ModItems.BUNDLE_OF_RATS.getDefaultStack();
    }

    @Override
    public SoundEvent getBucketFillSound() {
        return SoundEvents.ITEM_BUNDLE_INSERT;
    }

    public enum RatVariant {
        // every new variant needs to be listed here,
        // given a texture,
        // given a json for spawning locations,
        // added to getVariantFromPos,
        // and needs its biomes added to ModEntityGeneration
        BROWN(0, "brown"),
        TUXEDO(1, "tuxedo"),
        WHITE(2, "white"),
        BLACK(3, "black");

        private static final IntFunction<RatVariant> BY_ID = ValueLists.createIdToValueFunction(RatVariant::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);

        private final int id;
        private final String name;

        RatVariant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static RatVariant byId(int id) {
            return BY_ID.apply(id);
        }

        public int getId() {
            return this.id;
        }
        public String getName() {
            return this.name;
        }

        private static RatVariant getRandom(Random random) {
            RatVariant[] variants = Arrays.stream(values()).toArray(RatVariant[]::new);
            return Util.getRandom(variants, random);
        }
    }

        public static class RatData extends PassiveEntity.PassiveData {
        public final RatVariant[] ratVariants;

        public RatData(RatVariant... ratVariants) {
            super(false);
            this.ratVariants = ratVariants;
        }

        public RatVariant getRandomVariant(Random random) {
            return this.ratVariants[random.nextInt(this.ratVariants.length)];
        }
    }

    private static RatVariant getTypeFromPos(WorldAccess world, BlockPos pos) {
        RegistryEntry<Biome> registryEntry = world.getBiome(pos);
        int i = world.getRandom().nextInt(100);
        if (registryEntry.isIn(BiomeTags.SPAWNS_WHITE_RABBITS)) {
            return i < 80 ? RatVariant.WHITE : RatVariant.BLACK;
        } else if (registryEntry.isIn(BiomeTags.SPAWNS_GOLD_RABBITS)) {
            return RatVariant.TUXEDO;
        } else {
            // 1/2 chance of BLACK, 2/5 chance of BROWN, 1/10 chance of TUXEDO
            return i < 50 ? RatVariant.BLACK : (i < 90 ? RatVariant.BROWN : RatVariant.TUXEDO);
        }
    }
}
