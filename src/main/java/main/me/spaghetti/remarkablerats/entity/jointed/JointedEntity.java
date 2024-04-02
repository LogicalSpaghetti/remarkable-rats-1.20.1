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

    Vec3d[] goals = new Vec3d[3];
    public static double jointedDirection = 0; // in radians

    public static final float width = 0.7f;
    public static float height = 1.5f;

    public float radianT = 0;

    public JointedEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {

        radianT += (float) (Math.PI/6);

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

        double distance = width;

        Point2D.Double goalA = rotatePoint(
                new Point2D.Double(this.getPos().getX() - distance, this.getPos().getZ() + distance),
                new Point2D.Double(this.getPos().getX(), this.getPos().getZ()),
                jointedDirection);
        Point2D.Double goalB = rotatePoint(
                new Point2D.Double(this.getPos().getX() - distance, this.getPos().getZ() - distance),
                new Point2D.Double(this.getPos().getX(), this.getPos().getZ()),
                jointedDirection);
        Point2D.Double goalC = rotatePoint(
                new Point2D.Double(this.getPos().getX() + distance, this.getPos().getZ() - distance),
                new Point2D.Double(this.getPos().getX(), this.getPos().getZ()),
                jointedDirection);
        Point2D.Double goalD = rotatePoint(
                new Point2D.Double(this.getPos().getX() + distance, this.getPos().getZ() + distance),
                new Point2D.Double(this.getPos().getX(), this.getPos().getZ()),
                jointedDirection);

        World world = this.getWorld();
        double particleX = this.getPos().getX();
        double particleY = this.getPos().getY();
        double particleZ = this.getPos().getZ();

        // indicates the direction the entity is "facing"
        world.addParticle(ParticleTypes.BUBBLE,
                particleX + Math.sin(jointedDirection),
                particleY + (height/2),
                particleZ + Math.cos(jointedDirection),
                0.0, 0.0, 0.0);

        world.addParticle(ParticleTypes.FLAME,
                goalA.x,
                particleY,
                goalA.y,
                0.0, 0.0, 0.0);
        world.addParticle(ParticleTypes.FLAME,
                goalB.x,
                particleY,
                goalB.y,
                0.0, 0.0, 0.0);
        world.addParticle(ParticleTypes.FLAME,
                goalC.x,
                particleY,
                goalC.y,
                0.0, 0.0, 0.0);
        world.addParticle(ParticleTypes.FLAME,
                goalD.x,
                particleY,
                goalD.y,
                0.0, 0.0, 0.0);
    }

    private Point2D.Double rotatePoint(Point2D.Double point, Point2D.Double origin, double angle) {

        Point2D.Double middleman = new Point2D.Double(point.x - origin.x, point.y - origin.y);

        return new Point2D.Double(
                (middleman.x * Math.cos(angle) - middleman.y * Math.sin(angle)) + origin.x,
                (middleman.x * Math.sin(angle) + middleman.y * Math.cos(angle)) + origin.y
        );
    }
}
