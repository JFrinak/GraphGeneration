package com.GraphGeneration.GraphIsomorphismUtils;

import com.GraphGeneration.Generation;
import com.GraphGeneration.graph.Cycle;

import java.util.ArrayList;

public class VertPerm {
public Integer[] vert_valency = new Integer[Generation.verts];
public int[] vertices = new int[Generation.verts];
public ArrayList<Cycle> cycles = new ArrayList<Cycle>();

	public VertPerm(Integer[] c){
		for(int i = 0; i< Generation.verts; i++){
			vertices[i]=i;
		}
		this.vert_valency = c;
		getCycles();
	}
	
	public void getCycles(){
		ArrayList<Integer> cycle = new ArrayList<Integer>();
		cycle.add(0);
		for(int i=1;i<vert_valency.length;i++){
			if(vert_valency[i]==vert_valency[i-1]){
				cycle.add(i);
			}
			else{
				Cycle c = new Cycle(cycle);
				this.cycles.add(c);
				cycle = new ArrayList<Integer>();
				cycle.add(i);
			}
		}
		Cycle c = new Cycle(cycle);
		this.cycles.add(c);
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
			for(int k = 0; k< Generation.verts; k++){
				if(i<this.cycles.get(cycle_num).vertices.size()){
					this.vertices[k]=this.cycles.get(cycle_num).perm_verts[i];
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
