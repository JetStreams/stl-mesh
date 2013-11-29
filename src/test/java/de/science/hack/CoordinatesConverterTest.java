/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. 
 */
package de.science.hack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mario
 */
public class CoordinatesConverterTest {

    private static final double DIFF = 0.1;

    /**
     * simple test from coordinate to xyz-point
     */
    @Test
    public void testToStl() {
        ModelPoint point = CoordinatesConverter.toModel(new Coordinate(0, 0, 0));
        assertNotNull(point);
        assertEquals(12756274.0, point.getX(), DIFF);
        assertEquals(CoordinatesConverter.RADIUS, point.getY(), DIFF);
        assertEquals(CoordinatesConverter.RADIUS, point.getZ(), DIFF);

        point = CoordinatesConverter.toModel(new Coordinate(-180, 0, 0));
        assertNotNull(point);
        assertEquals(0.0, point.getX(), DIFF);
        assertEquals(CoordinatesConverter.RADIUS, point.getY(), DIFF);
        assertEquals(CoordinatesConverter.RADIUS, point.getZ(), DIFF);
    }

    /**
     * Creates coordinates around one longitude and dumps the output.
     * The dump can be plotted with gnuplot: <code>plot 'dump.txt' using 1:3 w points</code>
     */
    @Test
    public void testCircle() {

        String path = getClass().getResource(".").getFile();
        File file = new File(path, "dump.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (int i = 0; i < 360; i++) {
                ModelPoint point1 = CoordinatesConverter.toModel(new Coordinate(0, i, 0));
                assertNotNull(point1);
                ModelPoint point2 = CoordinatesConverter.toModel(new Coordinate(180, 360 - i, 0));
                assertNotNull(point2);

                writer.write(strip(point1));
                writer.newLine();
                writer.write(strip(point2));
                writer.newLine();
            }

            writer.close();
        } catch (IOException exc) {
            fail(exc.getMessage());
        }
    }

    private String strip(ModelPoint point) {
        String asString = point.toString();
        return asString.replace("(", "").replace(")", "").trim();
    }
}