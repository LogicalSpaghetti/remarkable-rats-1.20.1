package main.me.spaghetti.remarkablerats.util;


import net.minecraft.client.model.ModelTransform;
import net.minecraft.util.math.Vec3d;

// goal is normalized
public class Kinematics {
    public float[][] getFABRIKAngles(ModelTransform[] transforms, double[] lengths, Vec3d goal) {
        int numLegs = transforms.length;
        int numJoints = numLegs++;

        Vec3d[] startPoints = new Vec3d[numJoints];
        startPoints[0] = new Vec3d(0, 0, 0);
        for (int i = 1; i < numJoints; i++) {
            ModelTransform t = transforms[i];
            startPoints[i] = new Vec3d (
                    lengths[i] * Math.cos(t.yaw) * Math.cos(t.pitch),
                    lengths[i] * Math.sin(t.pitch),
                    lengths[i] * -Math.sin(t.yaw) * Math.cos(t.pitch)
            );
            startPoints[i].add(startPoints[i - 1]);
        }

        Vec3d[] newPoints = new Vec3d[numJoints];

        // end points are initialized, these are the tips of the vectors, not the roots.
        // the origin is included as a point, so there is one more point than there are vectors

        // last point is set to goal
        // second to last point is set to last point + last length * rotated vector pointing from last point to second to last point
        // repeat until the last point gets moved
        newPoints[newPoints.length - 1] = goal;
        for (int i = numJoints - 2; i >= 0; i++) {
            float[] angles = getAngleBetweenVec(newPoints[i + 1], startPoints[i]);
            newPoints[i] = newPoints[i + 1].add(new Vec3d(lengths[i], 0, 0)
                    .rotateY(angles[1])
                    .rotateZ(angles[0])
            );
        }


        // subtract the default transform values before returning it

        return new float[numLegs][3];
    }

    // returns the pitch and yaw which can be used to point a vector from one point to another
    private float[] getAngleBetweenVec(Vec3d start, Vec3d end) {
        Vec3d rooted = end.subtract(start).normalize();

        float pitch = (float) Math.asin(rooted.getY());

        float yaw = (float) Math.acos(rooted.getX() / Math.cos(pitch));

        return new float[] {
                pitch, yaw
        };
    }
}
