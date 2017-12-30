package com.GraphGeneration.GraphIsomorphismUtils;

import com.GraphGeneration.Generation;
import com.GraphGeneration.graph.Graph;

import java.util.List;

public class IsomorphismCheck {
    IsomorphismCheck(){
        throw new RuntimeException("IsomorphismCheck class should not be constructed");
    }

    public static boolean checkIsomorphism(final Graph g, final List<Graph> comparableGraphs){
        int[] permV = new int[Generation.verts]; // Permutation on the vertices of G
        VertPerm vp = new VertPerm(g.getVertValency());
        System.arraycopy(vp.vertices, 0, permV, 0, Generation.verts);
        boolean V_terminate = false;
        if(comparableGraphs.size()== 0) return true;
        while(true){
            boolean E_terminate = false;
            EdgePerm ep = new EdgePerm(g.getIsLoop());
            int[] permE = new int[Generation.edges]; // Permutation on the edges of G
            System.arraycopy(ep.edge_array, 0, permE, 0, Generation.edges);
            while(true){
                //Create new permutation of ordered, unoriented IM using PermV and PermE
                int[][] permutated_u_IM=new int[Generation.verts][Generation.edges];
                permutated_u_IM= permutate_u_IM(g,permV,permE);
                //Check new permuatation against all ordered, unoriented graph IMs in Gs
                for(int i=0;i<comparableGraphs.size();i++){
                    if(comparableGraphs.get(i).is_isomorphic(permutated_u_IM)) return false;//There is an isomorphism
                }
                ep.next(ep.cycles.size()-1);
                System.arraycopy(ep.edge_array, 0, permE, 0, Generation.edges);
                if(E_terminate) break;
                if(!ep.hasNext()) E_terminate = true;

            }
            vp.next(vp.cycles.size()-1);
            System.arraycopy(vp.vertices, 0, permV, 0, Generation.verts);
            if(V_terminate) break;
            if(!vp.hasNext()) V_terminate = true;
        }
        return true;//There is not an isomorphism
    }

    private static int[][] permutate_u_IM(final Graph g, final int[] permV, final int[] permE){
        int[][] temp = new int[Generation.verts][Generation.edges];
        //Permutate rows of G_IM
        for(int i = 0; i< Generation.verts; i++){
            System.arraycopy(g.getOrderedUnoriented()[permV[i]], 0, temp[i], 0, Generation.edges);
        }
        //Permutate Columns of G_IM
        int[][] temp2 = new int[Generation.verts][Generation.edges];
        for(int j = 0; j< Generation.edges; j++){
            for(int k = 0; k< Generation.verts; k++){
                temp2[k][j]=temp[k][permE[j]];
            }
        }
        return temp2;
    }
}
