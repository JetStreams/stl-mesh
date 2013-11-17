/*
 * 
 */
package de.science.hack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import toxi.geom.AABB;
import toxi.geom.mesh.Mesh3D;

/**
 *
 * @author Mario
 */
public class ModelReaderTest {

    private ModelReader classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new ModelReader();
    }

    /**
     * Test of readEarth method, of class ModelReader.
     */
    @Test
    public void testReadEarth() {
        Mesh3D result = classUnderTest.readEarth();
        assertNotNull(result);
        assertEquals(480335, result.getNumFaces());
        AABB box = result.getBoundingBox();
        assertFalse(box.getMax().x == 0.0);
    }
    
    
}
