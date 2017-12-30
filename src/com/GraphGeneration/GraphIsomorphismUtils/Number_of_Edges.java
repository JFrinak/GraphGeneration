package com.GraphGeneration.GraphIsomorphismUtils;

import java.util.ArrayList;
import java.util.Collections;

public class Number_of_Edges {
	private int edges;
	private ArrayList<int[]> Partitions = new ArrayList<int[]>();
	private long sum = 0;
	private int mu; // max_value +1 -- number of possible edges between specific number of verts.
	private int maximumRepeatedEdges;
	
	public Number_of_Edges(int edges, int mu, int maximumRepeatedEdges){
		this.edges = edges;
		this.mu = mu;
		this.maximumRepeatedEdges = maximumRepeatedEdges;
		int[] integer_count = new int[this.edges];
		integer_count[0]=edges;
		int[] copy_Array = new int[this.edges];
		System.arraycopy(integer_count, 0, copy_Array, 0, this.edges);
		this.Partitions.add(copy_Array);
		while(integer_count[0] != 1){
			Iterate_count(0,integer_count);
			copy_Array = new int[this.edges];
			System.arraycopy(integer_count, 0, copy_Array, 0, this.edges);
			Partitions.add(copy_Array);
		}
		this.Sum();
		
	}
	private  void Iterate_count(int col,int[] ic){
		if(col==this.edges-1 || ic[col]==0){return;}
		else{
			if(ic[col]>ic[col+1] && ic[col+1]<=1){
				ic[col]--;
				ic[col+1]++;
				if(ic[col]<ic[col+1]){
					Iterate_count(col+1,ic);
				}
			}
			else Iterate_count(col+1,ic);
		}
	}

	private void Sum(){
		for(int[] partition : Partitions){
		    //Need to check that partition does not have more than the maximum allowable repeated edges.
			if(partition[0]<=this.maximumRepeatedEdges){
				ArrayList<Integer> counts = new ArrayList<>();
				int count = 1;
				for(int e=0;e<this.edges;e++){
					if(partition[e]==0) break;
					if(e==this.edges-1){
						counts.add(count);
					}
					else if(partition[e]==partition[e+1]){
						count++;
					}
					else{
						counts.add(count);
						count = 1;
					}
				}
				Collections.sort(counts);
				int variables_used = 0;
				long prod = 1;
				for(int c=0;c<counts.size();c++){
					if(counts.get(c)==1){
						prod *= (this.mu-variables_used);
						variables_used++;
					}
					else{
						prod *= choose(this.mu-variables_used, counts.get(c));
					}
				}
				this.sum+=prod;
			}
		}
	}
	private long choose(int x, int y) {
	    if (y < 0 || y > x) 
	       return 0;
	    if (y == 0 || y == x) 
	       return 1;
		    long answer = 1;
	    for (int i = x - y + 1; i <= x; i++) {
	        answer *= i;
	    }
	    for (int j = 1; j <= y; j++) {
	        answer /= j;
	    }
	    return answer;
	}
	public long getSum(){ return this.sum;}
}
