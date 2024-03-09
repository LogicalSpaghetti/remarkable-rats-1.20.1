package main.me.spaghetti.remarkablerats.entity.custom;

import main.me.spaghetti.remarkablerats.RemarkableRats;
import main.me.spaghetti.remarkablerats.entity.ModEntities;
import main.me.spaghetti.remarkablerats.item.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RatEntity extends TameableEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public RatEntity(EntityType<? extends TameableEntity> type, World world) {
        super(type, world);
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
    public boolean canGather(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canTarget(EntityType<?> type) {
        return super.canTarget(type);
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
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
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
            player.setStackInHand(player.getActiveHand(), ModItems.RAT_SPAWN_EGG.getDefaultStack());

            RemarkableRats.LOGGER.info("Bundling Rat?");

            return ActionResult.SUCCESS;
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
                //return ActionResult.PASS;
            }

            if (this.isOwner(player)) {
                // picking up


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
                    double d = this.random.nextGaussian() * 0.02D;
                    double e = this.random.nextGaussian() * 0.02D;
                    double f = this.random.nextGaussian() * 0.02D;
                    this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                }
            }
            return ActionResult.SUCCESS;
        }


        return super.interactMob(player, hand);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new TemptGoal(
                this, 1.25D, Ingredient.ofItems(Items.POISONOUS_POTATO), false));

        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(4, new LookAroundGoal(this));
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

    @Override
    public EntityView method_48926() {
        return super.getWorld();
    }
}
