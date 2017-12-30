package com.GraphGeneration.tests;

import com.GraphGeneration.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.GraphGeneration.GraphIsomorphismUtils.IsomorphismCheck.checkIsomorphism;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsomorphismCheckTest {
    @Test
    public void EdgePermutationTest(){
        //Set Edges and verts to 3 and 3 with loops equal to 0
        final int[][] IM1 = {{-1,-1,0},{1,0,-1,},{0,1,1}};
        final boolean[] isLoop = {false,false,false};
        Graph g1 = new Graph(IM1,isLoop);

        final int[][] IM2 = {{0,-1,-1},{-1,0,1,},{1,1,0}};
        Graph g2 = new Graph(IM2,isLoop);

        g1.generate_ordered_unoriented_IM();
        g2.generate_ordered_unoriented_IM();

        List<Graph> graphList = new ArrayList<Graph>();
        graphList.add(g2);

        assertFalse(checkIsomorphism(g1,graphList));
    }

    @Test
    public void VertPermutationTest(){
        //Set Edges and verts to 3 and 3 with loops equal to 0
        final int[][] IM1 = {{-1,-1,0},{1,0,-1,},{0,1,1}};
        final boolean[] isLoop = {false,false,false};
        Graph g1 = new Graph(IM1,isLoop);

        final int[][] IM2 = {{1, 0, -1,}, {0, 1, 1}, {-1, -1, 0}};
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
        final int[][] IM1 = {{-1,-1,0},{1,0,-1,},{0,1,1}};
        final boolean[] isLoop = {false,false,false};
        Graph g1 = new Graph(IM1,isLoop);

        final int[][] IM2 = {{-1,-1,0},{1,1,-1},{0,0,1}};
        Graph g2 = new Graph(IM2,isLoop);

        g1.generate_ordered_unoriented_IM();
        g2.generate_ordered_unoriented_IM();

        List<Graph> graphList = new ArrayList<Graph>();
        graphList.add(g2);

        assertTrue(checkIsomorphism(g1,graphList));
    }

}
