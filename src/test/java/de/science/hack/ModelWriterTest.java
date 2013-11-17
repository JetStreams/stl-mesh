/*
 * 
 */
package de.science.hack;

import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import toxi.geom.mesh.Mesh3D;
import toxi.geom.mesh.TriangleMesh;

/**
 *
 * @author Mario
 */
public class ModelWriterTest {
    
    private ModelWriter classUnderTest;
    
    private ModelReader reader;
    
    private Mesh3D source;
    
    @Before
    public void setUp() {
        classUnderTest = new ModelWriter();
        reader = new ModelReader();
        source = reader.readEarth();
    }

    /**
     * Test of write method, of class ModelWriter.
     */
    @Test
    public void testWrite() {
        
        TriangleMesh out = new TriangleMesh();
        out.addMesh(source);
        
        String path = getClass().getResource(".").getFile();
        File file = new File(path, "out.stl");
        classUnderTest.write(file, out);
        assertTrue(file.exists());
        
        Mesh3D exported = reader.read(file.getPath());
        assertNotNull(exported);
        assertEquals(source.getFaces().size(), exported.getFaces().size());
    }
}