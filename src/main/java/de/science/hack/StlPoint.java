/*
 * Represents coordinates in ECEF, howeever the origin is not within the sphere
 * but in the lower left corner of a cube around the sphere.
 */
package de.science.hack;

import javax.vecmath.Point3d;

/**
 * A point in the STL world.
 * @author Mario
 */
public class StlPoint extends Point3d{

    public StlPoint(double x, double y, double z) {
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
