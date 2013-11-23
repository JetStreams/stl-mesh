/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. 
 */
package de.science.hack;

import java.io.File;
import org.apache.commons.cli.ParseException;
import org.junit.Before;
import org.junit.Test;

import toxi.geom.mesh.Mesh3D;
import static org.junit.Assert.*;
import toxi.geom.mesh.TriangleMesh;

/**
 *
 * @author Mario
 */
public class MainTest {
    
    private ModelReader reader;

    @Before
    public void setUp() {
        reader = new ModelReader();
    }

    @Test
    public void testWrite() throws ParseException {
        
        TriangleMesh meshBefore = reader.readEarth();
        
        String path = getClass().getResource("wind.txt").getFile();
        File dataDirectory = new File(path).getParentFile();
        File out = new File(getClass().getResource(".").getPath(), "jetstreams.stl");
        
        String [] arguments = new String[] {"-d", dataDirectory.getPath(), "-o", out.getPath()};
        Main.main(arguments);
        
        assertTrue(out.exists());
        
        Mesh3D exported = reader.read(out.getPath());
        assertNotNull(exported);
        assertFalse(exported.getNumVertices() == meshBefore.getNumVertices());
    }

}
