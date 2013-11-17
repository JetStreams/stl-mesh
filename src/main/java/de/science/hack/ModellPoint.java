/*
 * Represents coordinates in ECEF, howeever the origin is not within the sphere
 * but in the lower left corner of a cube around the sphere.
 */
package de.science.hack;

import javax.vecmath.Point3d;

/**
 * A point in the model world.
 * @author Mario
 */
public class ModellPoint extends Point3d{

    public ModellPoint(double x, double y, double z) {
        super(x, y ,z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    
    
}