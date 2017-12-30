package com.GraphGeneration;

import com.GraphGeneration.GraphGeneration.GenerateGraphs;

public class Generation {
    public static final int verts=4;
    public static final int edges=5;
    public static final int Loops = 1;
    public static final int minimumVertexDegree = 0; //minimumVertexValancy, Actually 1 less than minimum vertex degree.

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        assert(Loops <= edges - verts +1);
        assert(Loops<=verts); //Program will need to be altered in case where number of loops
        // is greater than number of verts. (Easy)

        GenerateGraphs gg = new GenerateGraphs();
        long endTime = System.currentTimeMillis();
        System.out.println("Compilation took "+(endTime - startTime) + " ms");
    }
}
