package com.GraphGeneration.GraphIsomorphismUtils;

import com.GraphGeneration.Generation;
import com.GraphGeneration.graph.Cycle;

import java.util.ArrayList;


public class EdgePerm {
	public boolean[] iL = new boolean[Generation.edges];
	public ArrayList<Cycle> cycles = new ArrayList<Cycle>();
	public int[] edge_array = new int[Generation.edges];

	public EdgePerm(boolean[] il){
		this.iL=il;
		for(int e = 0; e< Generation.edges; e++){
			this.edge_array[e]=e;
		}
		this.getCycles();
	}
	public void getCycles(){
		ArrayList<Integer> Loops = new ArrayList<Integer>();
		ArrayList<Integer> non_loops = new ArrayList<Integer>();
		for(int e = 0; e< Generation.edges; e++){
			if(this.iL[e]) Loops.add(e);
			else non_loops.add(e);
		}
		if(Loops.size()>0){
			Cycle loop_cycle = new Cycle(Loops);
			this.cycles.add(loop_cycle);
		}
		Cycle nl_cycle = new Cycle(non_loops);
		this.cycles.add(nl_cycle);
	}
	public void next(int c){
		boolean flag =false;
		if(this.cycles.get(c).hasNext()){
			this.cycles.get(c).next();
			flag = true;
		}
		else if(!this.cycles.get(c).hasNext()&& c!=0){
			this.cycles.get(c).reset();
			c--;
			next(c);
		}
		if(flag){
			int i =0;
			int cycle_num=0;
			for(int k = 0; k< Generation.edges; k++){
				if(i<this.cycles.get(cycle_num).vertices.size()){
					this.edge_array[k]=this.cycles.get(cycle_num).perm_verts[i];
				}else{
					i=-1;
					cycle_num++;
					k--;
				}
				i++;
			}
		}

	}
	public boolean hasNext(){
		for(int i=0;i<this.cycles.size();i++){
			if(this.cycles.get(i).hasNext()){
				return true;
			}
		}
		return false;
	}

}
