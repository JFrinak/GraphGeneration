package com.GraphGeneration.tests;

import com.GraphGeneration.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicGraphTests {
    @Test
    public void GraphCopyIM(){
        //Set Edges and verts to 3 and 3 with loops equal to 0
        final int[][] IM1 = {{-1,-1,0},{1,0,-1,},{0,1,1}};
        final boolean[] isLoop = {false,false,false};
        Graph g = new Graph(IM1,isLoop);
        System.out.println(g.toString());
        //IM1 will be changed
        final int[][] IM2 = {{-1,-1,0},{1,0,-1,},{0,1,1}};
        assertTrue(Arrays.deepEquals(IM2,g.get_IM_Copy()));
        assertTrue(g.getHomologyBasis().size() == 1);
        assertTrue(g.get_isConnected());
        assertFalse(g.getHomologyBasis().contains(0));
    }

    @Test
    public void OrderedUnorderedTest(){
        //Set Edges and verts to 3 and 3 with loops equal to 0
        final int[][] IM1 = {{-1,-1,0},{1,0,-1,},{0,1,1}};
        final boolean[] isLoop = {false,false,false};
        Graph g = new Graph(IM1,isLoop);
        g.generate_ordered_unoriented_IM();
        final int[][] orderedUnorientedIM = {{1,1,0},{1,0,1},{0,1,1}};
        assertTrue(Arrays.deepEquals(orderedUnorientedIM,g.getOrderedUnoriented()));
        final Integer[] vertexValencies = {2,2,2};
        assertTrue(Arrays.equals(vertexValencies,g.getVertValency()));
    }
}
