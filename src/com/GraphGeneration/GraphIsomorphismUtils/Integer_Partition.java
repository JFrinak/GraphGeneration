package com.GraphGeneration.GraphIsomorphismUtils;

import com.GraphGeneration.Generation;

public class Integer_Partition {
	public int[] integer_count = new int[Generation.Loops];
	public int[] loop_array = new int[Generation.Loops];
	public Integer_Partition(){
		this.integer_count[0]= Generation.Loops;

	}
	public boolean has_Next(){
		if(this.integer_count[0]==1) return false;
		Iterate_count(0);
		Iterate_loop();
		return true;
	}
	public  void Iterate_count(int col){
		if(col== Generation.Loops-1 || this.integer_count[col]==0){return;}
		else{
			if(this.integer_count[col]>this.integer_count[col+1] && this.integer_count[col+1]<=1){
				this.integer_count[col]--;
				this.integer_count[col+1]++;
				if(this.integer_count[col]<this.integer_count[col+1]){
					Iterate_count(col+1);
				}
			}
			else Iterate_count(col+1);
		}
	}
	public  void Iterate_loop(){
		int index=0;
		for(int i = 0; i< Generation.Loops; i++){
			if(index+this.integer_count[i]>= Generation.Loops) return;
			if(this.loop_array[this.integer_count[i]+index]==i){
				this.loop_array[this.integer_count[i]+index]++;
			}
			index+=this.integer_count[i];
		}
	}
	public void print_array(int[] B){
		for(int i = 0; i< Generation.Loops; ++i){
			System.out.print(B[i] + "\t");
		}
		System.out.println();
	}

}
