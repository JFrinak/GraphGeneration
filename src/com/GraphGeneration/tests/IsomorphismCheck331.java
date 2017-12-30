package com.GraphGeneration.tests;

import com.GraphGeneration.Generation;
import com.GraphGeneration.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.GraphGeneration.GraphIsomorphismUtils.IsomorphismCheck.checkIsomorphism;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsomorphismCheck331 {

    /*
    -------------------------------------------------------
    Set Verts, Edges, and Loops to 3, 3, and 1 respectively
    -------------------------------------------------------
     */

    @Test
    public void checkParameters(){
        assertTrue(Generation.verts == 3);
        assertTrue(Generation.edges == 3);
        assertTrue(Generation.Loops == 1);
        assertTrue(Generation.minimumVertexDegree == 0);
    }

    @Test
    public void EdgePermutationTest(){
        final int[][] IM1 = {{1,-1,0},{0,0,-1,},{0,1,1}};
        final boolean[] isLoop = {true,false,false};
        Graph g1 = new Graph(IM1,isLoop);

        final int[][] IM2 = {{1,0,-1},{0,-1,0},{0,1,1}};
        Graph g2 = new Graph(IM2,isLoop);

        g1.generate_ordered_unoriented_IM();
        g2.generate_ordered_unoriented_IM();

        List<Graph> graphList = new ArrayList<Graph>();
        graphList.add(g2);

        assertFalse(checkIsomorphism(g1,graphList));
    }

    @Test
    public void VertPermutationTest(){
        final int[][] IM1 = {{1,-1,0},{0,0,-1,},{0,1,1}};
        final boolean[] isLoop = {true,false,false};
        Graph g1 = new Graph(IM1,isLoop);

        final int[][] IM2 = {{1, -1, 0}, {0, -1, 1}, {0, 0, 1}};
        Graph g2 = new Graph(IM2,isLoop);

        g1.generate_ordered_unoriented_IM();
        g2.generate_ordered_unoriented_IM();

        List<Graph> graphList = new ArrayList<Graph>();
        graphList.add(g2);

        assertFalse(checkIsomorphism(g1,graphList));
    }

    @Test
    public void NonIsomorphismTest(){
        //Set Edges and verts to 3 and 3 with loops equal to 0
        final int[][] IM1 = {{1,-1,0},{0,0,-1,},{0,1,1}};
        final boolean[] isLoop = {true,false,false};
        Graph g1 = new Graph(IM1,isLoop);

        final int[][] IM2 = {{1,-1,-1},{0,1,0},{0,0,1}};
        Graph g2 = new Graph(IM2,isLoop);

        g1.generate_ordered_unoriented_IM();
        g2.generate_ordered_unoriented_IM();

        List<Graph> graphList = new ArrayList<Graph>();
        graphList.add(g2);

        assertTrue(checkIsomorphism(g1,graphList));
    }
}
