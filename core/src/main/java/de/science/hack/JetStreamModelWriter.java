/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. 
 */
package de.science.hack;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import toxi.geom.mesh.TriangleMesh;

/**
 * Creates the model of the globe with the wind data.
 *
 * @author Mario
 */
public class JetStreamModelWriter {
    
    private final MeshReader modelReader;
    private final FileModelWriter writer;
    private final List<TriangleMesh> windModels;
    
    private GlobeType globeType;

    /**
     * Default constuctor which produces a model based on {@link GlobeType.Full}
     */
    public JetStreamModelWriter() {
        this(GlobeType.Full);
    }
    
    /**
     * Constructor which uses the given {@link GlobeType} for the output.  
     * @param globeType {@link GlobeType}
     */
    public JetStreamModelWriter(GlobeType globeType) {
        windModels = new ArrayList<>();
        modelReader = new MeshReader();
        writer = new FileModelWriter();
        this.globeType = globeType;
    }

    /**
     * Adds a new wind model.
     * @param model the mesh to be added.
     */
    public void addWindModel(TriangleMesh model) {
        windModels.add(model);
    }

    /**
     * Reads the globe data, adds wind model and writes it together 
     * to the given output file.
     *
     * @param outputFile path to the output file
     */
    public void write(String outputFile) {
        TriangleMesh earth = build();
        writer.write(outputFile, earth);
    }
    
    /**
     * Reads the globe data, adds wind model and writes it together 
     * to the given output stream.
     *
     * @param outputStream stream to write
     */
    public void write(OutputStream outputStream) {
        TriangleMesh earth = build();
        earth.saveAsSTL(outputStream);
    }

    private TriangleMesh build() {
        TriangleMesh earth = modelReader.readGlobe(globeType);
        windModels.forEach(w -> earth.addMesh(w));
        return earth;
    }
}
