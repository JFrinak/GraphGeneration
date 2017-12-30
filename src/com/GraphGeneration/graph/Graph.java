package com.GraphGeneration.graph;


import com.GraphGeneration.Generation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Graph {
	//public objects

	//Protected Objects
	private int[][] Incidence_Matrix = new int[Generation.verts][Generation.edges];
	private int[][] IM_Copy = new int[Generation.verts][Generation.edges];
	private boolean[] is_loop = new boolean[Generation.edges];
	private boolean isConnected = false;
	private List< int[]> Homology_Basis = new ArrayList<int[]>();

	//private Objects
	private int number_of_loops = 0;
	private int[][] ordered_unoriented_IM = new int[Generation.verts][Generation.edges];
	private Integer[] vert_valency = new Integer[Generation.verts];
	private int min_valency =-1;
		
	//Public functions
	public Graph(final int[][] IM, final boolean[] iL) {
		int rowNumber = 0;
	    for (final int[] row : IM){
	        this.Incidence_Matrix[rowNumber]=Arrays.copyOf(row, Generation.edges);
	        rowNumber++;
		}
		Copy_IM();
		System.arraycopy(iL, 0, is_loop, 0, Generation.edges);
		set_number_of_loops();
		find_homology_basis();
	}
	void set_number_of_loops(){
		int loops = 0;
		for(int i = 0; i< Generation.edges; i++){
			if(this.is_loop[i]){loops++;}
		}
		this.number_of_loops=loops;
	}
	private int start(final int i){
		assert(i< Generation.edges);
		for(int j = 0; j< Generation.verts; j++){
			if(IM_Copy[j][i]==-1){
				return j;
			}
		}
		return -1;
	}
	private int end(final int j){
		assert(j< Generation.edges);
		for(int i = 0; i< Generation.verts; i++){
			if(IM_Copy[i][j]==1){return i;}
		}
		return -1;
	}
	public boolean get_isConnected(){
			return this.isConnected;
		}
	public boolean is_isomorphic(final int[][] perm_IM){
		return Arrays.deepEquals(this.ordered_unoriented_IM,perm_IM);
	}
	@Override
	public String toString(){
        StringBuilder sb = new StringBuilder("The Base Graph is: \n");
        for(int i = 0; i< Generation.verts; i++){
        	for(int j = 0; j< Generation.edges; j++){
        		sb.append(this.IM_Copy[i][j]).append("\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	public int[][] getOrderedUnoriented(){
		return this.ordered_unoriented_IM;
	}
	public Integer[] getVertValency(){
		return this.vert_valency;
	}
	public boolean[] getIsLoop(){
		return this.is_loop;
	}

	public int[][] get_IM_Copy(){
		return this.IM_Copy;
	}
	int get_number_of_loops(){
		return this.number_of_loops;
	}
	public int get_min_valency(){
		return this.min_valency;
	}
	public List<int[]> getHomologyBasis(){
		return this.Homology_Basis;
	}
	//Protected functions
	private void swap_rows(final int i, final int j){
		assert(i>=0);
		assert(i< Generation.verts);
		assert(j>=0);
		assert(j< Generation.verts);
			for(int col = 0; col< Generation.edges; col++){
			int temp = Incidence_Matrix[i][col];
			Incidence_Matrix[i][col]=Incidence_Matrix[j][col];
			Incidence_Matrix[j][col]=temp;
		}
	}
	private void divide_row(final int i, final int scalar){
		assert(i>=0);
		assert(i< Generation.verts);
		assert(scalar !=0);

		for(int col = 0; col< Generation.edges; col++){
			Incidence_Matrix[i][col]/= scalar;
		}
	}
	private void add_multiple_row(final int i, final  int j, final int scalar){
		assert(i>=0);
		assert(i< Generation.verts);
		assert(j>=0);
		assert(j< Generation.verts);

		for(int col = 0; col< Generation.edges; col++){
			Incidence_Matrix[i][col]+= scalar*Incidence_Matrix[j][col];
		}
	}
	private void to_reduced_row_echelon_form(){
		int lead=0;
		for(int row = 0; row< Generation.verts; row++){
			if(lead>= Generation.edges){return;}
			int i=row;
			while(Incidence_Matrix[i][lead]==0){
				i++;
				if(i>= Generation.verts){
					i=row;
					++lead;
					if(lead>= Generation.edges){return;}
				}
			}
			swap_rows(i,row);
			divide_row(row,Incidence_Matrix[row][lead]);
			for(i=0; i< Generation.verts; i++){
				if(i!=row){
					add_multiple_row(i,row,-Incidence_Matrix[i][lead]);
				}
			}
		}
	}
	private void find_homology_basis(){
		//Convert Loops to zero columns
		for(int i = 0; i< Generation.edges; i++){
			if(is_loop[i]){
				for(int j = 0; j< Generation.verts; j++){
					this.Incidence_Matrix[j][i]=0;
				}
			}
		}
		to_reduced_row_echelon_form();
		int row=0;
		boolean[] free_variables = new boolean[Generation.edges];
		int rank= Generation.edges;
		for(int col = 0; col< Generation.edges; col++){
			if(row>= Generation.verts){break;}
			else if(Incidence_Matrix[row][col]==1){row++;}
			else{
				int free_inc=0;
				int[] b_vector = new int[Generation.edges];
				for(int k=0;k<col;k++){
					if(free_variables[k]){
						b_vector[k]=0;
						free_inc++;
					}
					else{
						b_vector[k]=Incidence_Matrix[k-free_inc][col];
					}
				}
				b_vector[col]=-1;
				Homology_Basis.add(b_vector);
				free_variables[col]=true;
				rank-=1;
			}
		}
		//Check if Graph is connected, that is if rank=verts-1
		if(rank== Generation.verts-1){this.isConnected=true;}
	}
	private void Copy_IM(){
		int row_count =0;
		for(final int[] row : this.Incidence_Matrix){
		    System.arraycopy(row,0,this.IM_Copy[row_count], 0, row.length);
		    row_count++;
		}
	}
	public void generate_ordered_unoriented_IM(){
		//Remove orientation
		for(int row = 0; row< Generation.verts; row++){
			for(int col = 0; col< Generation.edges; col++){
				if(this.IM_Copy[row][col]==1||this.IM_Copy[row][col]==-1){
					this.ordered_unoriented_IM[row][col]=1;
				}
				else{ this.ordered_unoriented_IM[row][col]=0;}
			}
		}

		//order verts by valency (highest valency to lowest)
		Arrays.sort(this.ordered_unoriented_IM, new Comparator<int[]>() {
			public int compare(int[] v1, int[] v2) {
				int sum_v1=0;
				int sum_v2=0;
				for(int i=0;i<v1.length;i++){
					sum_v1+=v1[i];
					sum_v2+=v2[i];
				}
				return sum_v2-sum_v1;
			}
		});
		//populate vert_valency
		for(int v = 0; v< Generation.verts; v++){
			int sum = 0;
			for(int e = 0; e< Generation.edges; e++){
				if(this.is_loop[e]){
					sum+=2*this.ordered_unoriented_IM[v][e];
				}
				else{
					sum += this.ordered_unoriented_IM[v][e];
				}
			}
			this.vert_valency[v]=sum;
		}
		//Set the minimum valency... this will help us discard the irrelevant graphs.
		this.min_valency = this.vert_valency[Generation.verts-1];
		//
	}

	public List<Integer> generate_Completely_Covered(final boolean[] Cverts, final boolean[] Cedges){
		List<Integer> completely_covered_loops = new ArrayList<Integer>();
		boolean flag = true;
		for(int i=0;i<this.Homology_Basis.size();i++){
			flag=true;
			for(int j = 0; j< Generation.edges; j++){
				if(this.Homology_Basis.get(i)[j]!=0){
					//Condition 0: edge j is not covered
					boolean condition_0 = !Cedges[j];
					//Condition 1: edge j is a loop an the start of the loop is not covered.
					boolean condition_1 = this.is_loop[j]&&!Cverts[this.start(j)];
					//Condition 2: edge j is not a loop, start of edge j is not covered.
					boolean condition_2 = !this.is_loop[j]&&this.Homology_Basis.get(i)[j]==-1&&!Cverts[this.start(j)];
					//Condition 3: edge j is not a loop, end of edge j is not covered.
					boolean condition_3 = !this.is_loop[j]&&this.Homology_Basis.get(i)[j]==1&&!Cverts[this.end(j)];
					if(condition_0 || condition_1 || condition_2 || condition_3){
						flag=false;
						break;
					}
				}
			}
			if(flag){completely_covered_loops.add(i);}
		}
		return completely_covered_loops;
	}

}
