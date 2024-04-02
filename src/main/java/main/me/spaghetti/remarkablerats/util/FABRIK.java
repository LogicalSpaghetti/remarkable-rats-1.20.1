package main.me.spaghetti.remarkablerats.util;

import net.minecraft.client.model.ModelTransform;
import net.minecraft.util.math.Vec3d;


public class FABRIK {

    final float tolerance = 0.1f;

    /**
     * returns the ModelTransform of every leg
     */
    public ModelTransform[] getNewAngles(Vec3d origin, Vec3d goal, final double[] lengths, ModelTransform[] t, ModelTransform[] defaultTransform) {
        ModelTransform[] transform = sumTransforms(t, defaultTransform); // the REAL transform. might not even be necessary, we'll see how the math works out and if it's communicative

        int attempts = 0;

        float difference = tolerance + 1;
        while (attempts < 10 || difference > tolerance) {
            attempts++;

            forward(origin, goal, transform, lengths);
            backward(origin, goal, forward(origin, goal, transform, lengths), lengths);
            calculateEnd(transform, lengths);
        }



        return subTransforms(transform, defaultTransform);
    }

    private Vec3d calculateEnd(ModelTransform[] transform, double[] lengths) {
        return null;
    }

    // creates a new transform pointing from the goal to the origin
    public ModelTransform[] forward(Vec3d origin, Vec3d goal, ModelTransform[] transform, double[] lengths) {
        Vec3d sudoGoal = goal;

        ModelTransform[] newTransform = new ModelTransform[transform.length];

        // take the last leg, point it towards the goal, reverse it, attach it to the goal, and find its tip
        // move the sudo-goal to its tip
        // repeat for all legs

        for (int i = 0; i < transform.length; i++) {

        }

        return null;
    }

    // creates a new transform pointing from the origin to the goal
    public void backward(Vec3d origin, Vec3d goal, ModelTransform[] forwardTransform, double[] lengths) {

    }

    public ModelTransform[] sumTransforms(ModelTransform[] a, ModelTransform[] b) {
        for (int i = 0; i < a.length; i++) {
            a[i] = addSubModelTransform(a[i], b[i], true);
        }
        return a;
    }

    public ModelTransform[] subTransforms(ModelTransform[] a, ModelTransform[] subtractTerm) {
        for (int i = 0; i < a.length; i++) {
            a[i] = addSubModelTransform(a[i], subtractTerm[i], false);
        }
        return a;
    }


    public ModelTransform addSubModelTransform(ModelTransform a, ModelTransform b, boolean add) {
        int m = add ? 1 : -1; // multiplier

        return ModelTransform.of(
                a.pivotX + (b.pivotX * m),
                a.pivotY + (b.pivotY * m),
                a.pivotZ + (b.pivotZ * m),
                a.pitch + (b.pitch * m),
                a.yaw + (b.yaw * m),
                a.roll + (b.roll * m)
        );
    }
}
