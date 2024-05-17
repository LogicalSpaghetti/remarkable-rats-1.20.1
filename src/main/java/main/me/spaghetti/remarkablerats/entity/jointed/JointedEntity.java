package main.me.spaghetti.remarkablerats.entity.jointed;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.geom.Point2D;

public class JointedEntity extends GolemEntity {

    public Vec3d[] legGoal = new Vec3d[4];

    // the direction the jointed is facing in the XZ plane, represented in radians
    public static double jointedDirection = 0;

    public static final float width = 0.7f;
    public static float height = 1.125f; // 18 pixels

    public Vec3d legRoot = new Vec3d(this.getX(), this.getY() + height, this.getZ());

    public float radianT = 0;
    public float radianTT = 0;
    public int clicked = 0;

    public float[] legLengths = {
            12, 10, 10
    };

    public JointedEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {

        clicked = 1;
        if (player.isSneaking()) {
            radianTT += (float) (Math.PI/6);
        } else {
            radianT += (float) (Math.PI/6);
        }


        return ActionResult.SUCCESS;
        //return super.interactMob(player, hand);
    }

    public static DefaultAttributeContainer.Builder createJointedAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1);
    }

    @Override
    public void tick() {
        super.tick();

        adjustGoals();

        createParticles();


    }

    private void adjustGoals() {
        legGoal[0] = new Vec3d(this.getX() + 1, this.getY(), this.getZ() - 1);
        legGoal[1] = new Vec3d(this.getX() - 1, this.getY(), this.getZ() - 1);
        legGoal[2] = new Vec3d(this.getX() - 1, this.getY(), this.getZ() + 1);
        legGoal[3] = new Vec3d(this.getX() + 1, this.getY(), this.getZ() + 1);
    }

    private void createParticles() {
        World world = this.getWorld();

        // indicates the direction the entity is "facing"
        world.addParticle(ParticleTypes.BUBBLE,
                this.getPos().getX() + Math.sin(jointedDirection),
                this.getPos().getY() + (height/2),
                this.getPos().getZ() + Math.cos(jointedDirection),
                0.0, 0.0, 0.0);

        world.addParticle(ParticleTypes.FLAME, legGoal[0].getX(), legGoal[0].getY(), legGoal[0].getZ(), 0.0, 0.0, 0.0);
        world.addParticle(ParticleTypes.FLAME, legGoal[1].getX(), legGoal[1].getY(), legGoal[1].getZ(), 0.0, 0.0, 0.0);
        world.addParticle(ParticleTypes.FLAME, legGoal[2].getX(), legGoal[2].getY(), legGoal[2].getZ(), 0.0, 0.0, 0.0);
        world.addParticle(ParticleTypes.FLAME, legGoal[3].getX(), legGoal[3].getY(), legGoal[3].getZ(), 0.0, 0.0, 0.0);
    }

    private Point2D.Double rotatePoint(Point2D.Double point, Point2D.Double origin, double angle) {

        Point2D.Double middleman = new Point2D.Double(point.x - origin.x, point.y - origin.y);

        return new Point2D.Double(
                (middleman.x * Math.cos(angle) - middleman.y * Math.sin(angle)) + origin.x,
                (middleman.x * Math.sin(angle) + middleman.y * Math.cos(angle)) + origin.y
        );
    }
}
