package com.GraphGeneration.graph;

import com.GraphGeneration.GraphIsomorphismUtils.PermIterator;

import java.util.ArrayList;

public class Cycle {
	public ArrayList<Integer> vertices = new ArrayList<Integer>();
	public int[] perm_verts;
	public PermIterator p;
	public int[] index_array;
	
	public Cycle(ArrayList<Integer> v){
		this.vertices = v;
		this.perm_verts = new int[v.size()];
		for(int i=0;i<v.size();i++){
			perm_verts[i]=v.get(i);
		}
		this.reset();
		this.index_array=new int[v.size()];
		for(int i=0;i<v.size();i++){
			this.index_array[i]=i;
		}
	}
	public String toString(){
		String s="";
		for(int i=0;i<this.vertices.size();i++){
			s+= this.vertices.get(i) + "\t";
		}
		s+="\n";
		return s;
	}
	public boolean hasNext(){
		if(this.vertices.size()>1 && this.p.hasNext()) return true;
		return false;
	}
	public void next(){
		this.index_array = this.p.next();
		for(int i=0;i<this.index_array.length;i++){
			this.perm_verts[i]=this.vertices.get(this.index_array[i]);
		}
	}

	public void reset(){
		this.p = new PermIterator(this.vertices.size());
		this.p.next();
		for(int i=0;i<this.vertices.size();i++){
			this.perm_verts[i]=this.vertices.get(i);
		}
	}
}
