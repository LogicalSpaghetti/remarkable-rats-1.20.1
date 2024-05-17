package main.me.spaghetti.remarkablerats.util;


import main.me.spaghetti.remarkablerats.RemarkableRats;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;

import static java.lang.Math.*;

/**
 * the inputted goal is assumed to be normalized relative to the origin of the limb
*/
public class Kinematics {

    static float marginOfError = 0.1f;
    static int maxPermutations = 20;

    public static void transformLimb(ModelPart[] limb, float[] lengths, Vec3d goal) {
        ModelTransform[] initialTransforms = new ModelTransform[limb.length];
        RemarkableRats.LOGGER.info("number of limb segments = " + limb.length);
        for(int i = 0; i < limb.length; i++) {
            initialTransforms[i] = limb[i].getTransform();
            RemarkableRats.LOGGER.info("initialTransforms: " + initialTransforms[i]);
        }
        ModelTransform[] newTransforms = getFABRIKAngles(initialTransforms, lengths, goal);
        for(int i = 0; i < limb.length; i++) {
            limb[i].pitch = newTransforms[i].pitch;
            limb[i].yaw = newTransforms[i].yaw;
        }
    }

    private static ModelTransform[] getFABRIKAngles(ModelTransform[] initialTransforms, float[] lengths, Vec3d goal) {

        // there is one more point than there is leg
        Vec3d[] permutedPoints = transformsToPoints(initialTransforms, lengths);

        int permutations = 0;
        while (permutedPoints[permutedPoints.length - 1].distanceTo(goal) > marginOfError && permutations <= maxPermutations) {
            permutedPoints =
                    backwards(
                            forwards(goal, permutedPoints, lengths), lengths);
            permutations++;
        }

        // snap all vectors to the origin and normalize them
        for (int i = permutedPoints.length - 1; i > 0; i--) {
            permutedPoints[i] = permutedPoints[i].subtract(permutedPoints[i - 1]);
        }
        for (int i = 0; i < permutedPoints.length; i++) {
            RemarkableRats.LOGGER.info("permutedPoints: " + permutedPoints[i].getX() + " " + permutedPoints[i].getY() + " " + permutedPoints[i].getZ());
            permutedPoints[i] = permutedPoints[i].normalize();
            RemarkableRats.LOGGER.info("normalized permutedPoints: " + permutedPoints[i].getX() + " " + permutedPoints[i].getY() + " " + permutedPoints[i].getZ());
        }

        // convert back to ModelTransform
        ModelTransform[] returnTransforms = new ModelTransform[permutedPoints.length];

        for (int i = 0; i < returnTransforms.length; i++) {
            float yaw = (float) asin(permutedPoints[i].getY());
            float pitch = (float) acos(permutedPoints[i].getX() / cos(yaw));
            returnTransforms[i] = ModelTransform.rotation(
                    pitch,
                    yaw,
                    0
            );
            RemarkableRats.LOGGER.info("Final coordinate: " + permutedPoints[i].getX() + " " + permutedPoints[i].getY() + " " + permutedPoints[i].getZ());
            RemarkableRats.LOGGER.info("Final transform: " + pitch + " " + yaw);
        }



        return returnTransforms;
    }

    /**
     * forwards FABRIK permutation.
     */
    private static Vec3d[] forwards(Vec3d goal, Vec3d[] initialPoints, float[] lengths) {
        RemarkableRats.LOGGER.info("length being entered into forwards: " + initialPoints.length);
        RemarkableRats.LOGGER.info(Arrays.toString(initialPoints));
        Vec3d[] permutedPoints = new Vec3d[initialPoints.length + 1];
        RemarkableRats.LOGGER.info("perm length = " + (initialPoints.length + 1));
        permutedPoints[permutedPoints.length - 1] = goal;
        for (int i = initialPoints.length - 1; i >= 0; i--) {
            RemarkableRats.LOGGER.info("i = " + i);
            permutedPoints[i] = permutedPoints[i + 1].add(
                    initialPoints[i].subtract(permutedPoints[i + 1]).normalize().multiply(lengths[i])
            );
        }
        RemarkableRats.LOGGER.info("length exiting from forwards: " + permutedPoints.length);
        RemarkableRats.LOGGER.info(Arrays.toString(permutedPoints));
        return permutedPoints;
    }

    /**
     * backwards FABRIK permutation.
     */
    private static Vec3d[] backwards(Vec3d[] forwardsPoints, float[] lengths) {
        RemarkableRats.LOGGER.info("length being entered into backwards: " + forwardsPoints.length);
        RemarkableRats.LOGGER.info(Arrays.toString(forwardsPoints));
        Vec3d[] permutedPoints = new Vec3d[forwardsPoints.length];
        RemarkableRats.LOGGER.info("perm length = " + (permutedPoints.length));
        permutedPoints[0] = new Vec3d(0, 0, 0);
        for (int i = 1; i < (permutedPoints.length); i++) {
            RemarkableRats.LOGGER.info("i = " + i);
            permutedPoints[i] = permutedPoints[i - 1].add(
                    forwardsPoints[i].subtract(permutedPoints[i - 1]).normalize().multiply(lengths[i - 1])
            );
        }
        RemarkableRats.LOGGER.info("first loop of backwards done: ");
        Vec3d[] returnPoints = new Vec3d[permutedPoints.length - 1];
        System.arraycopy(permutedPoints, 1, returnPoints, 0, returnPoints.length);

        RemarkableRats.LOGGER.info("length exiting from backwards: " + returnPoints.length);
        RemarkableRats.LOGGER.info(Arrays.toString(returnPoints));
        return returnPoints;
    }

    /**
     * takes an array of transforms and an array of lengths, and returns the corresponding array of vectors.
     */
    private static Vec3d[] transformsToPoints(ModelTransform[] transforms, float[] lengths) {
        RemarkableRats.LOGGER.info("length being entered into transforms to points: " + transforms.length);
        RemarkableRats.LOGGER.info(Arrays.toString(transforms));
        Vec3d[] points = new Vec3d[lengths.length];

        // sets every consecutive point to its proper value, added to the end of the previous one
        for (int i = 0; i < points.length; i++) {
            ModelTransform t = transforms[i];
            float l = lengths[i];
            points[i] = i == 0 ? new Vec3d(0, 0, 0): points[i - 1];
            RemarkableRats.LOGGER.info("      " + points[i]);
            RemarkableRats.LOGGER.info("      " + t.pitch + " " + t.yaw);
            points[i] = points[i].add(
                    l  * cos(t.pitch) * cos(t.yaw),
                    l * sin(t.yaw),
                    l * sin(t.pitch) * sin(t.yaw)
            );
            RemarkableRats.LOGGER.info("            " + points[i]);
        }

        RemarkableRats.LOGGER.info("length exiting from transforms to points: " + points.length);
        RemarkableRats.LOGGER.info(Arrays.toString(points));
        return points;
    }
}
