package com.GraphGeneration.GraphGeneration;

import com.GraphGeneration.Generation;
import com.GraphGeneration.GraphIsomorphismUtils.Integer_Partition;
import com.GraphGeneration.GraphIsomorphismUtils.Number_of_Edges;
import com.GraphGeneration.graph.Graph;
import com.GraphGeneration.gui.GUI;
import java.util.ArrayList;

import static com.GraphGeneration.GraphIsomorphismUtils.IsomorphismCheck.checkIsomorphism;

public class GenerateGraphs {
	private int verts = Generation.verts;
	private int edges = Generation.edges;
	private int Loops = Generation.Loops;
	private int minimumVertexDegree = Generation.minimumVertexDegree;
	private final int maximumRepeatedEdges = (int) ((float) 2*this.edges/this.verts);
	
	private ArrayList<Graph> All_Graphs = new ArrayList<Graph>();
	
	public GenerateGraphs() {
		//Create all possible loops distributions for Graphs with fixed number of loops and vertices. 
		ArrayList<int[]> LIMAs = new ArrayList<int[]>();

		if(Loops>0){
			Integer_Partition IP = new Integer_Partition();
			int[] copy_la = new int[Loops];
			System.arraycopy(IP.loop_array, 0, copy_la, 0, Loops);
			LIMAs.add(copy_la);
			while(IP.has_Next()){
				copy_la = new int[Loops];
				System.arraycopy(IP.loop_array, 0, copy_la, 0, Loops);
				LIMAs.add(copy_la);
			}
		}
			
		//Construct is_loop for all graphs with fixed number of loops
		boolean[] iL = new boolean[edges];
		for(int i=0;i<Loops;i++){
			iL[i]=true;
		}

		//GENERATE ALL POSSIBLE Non-Loop EDGES
				
		ArrayList<int[]> All_Possible_Loop_Edges = new ArrayList<>();
		for(int i=0;i<verts;++i){
			int[] A =new int[verts];
			A[i]=-1;
			All_Possible_Loop_Edges.add(A);
		}
		ArrayList<int[]> All_Possible_Edges = new ArrayList<>();
		for(int i=0;i<verts-1;++i){
			for(int j=i+1;j<verts;++j){
				if(i!=j){
					int[] A = new int[verts];
					A[i]=-1;
					A[j]=1;
					All_Possible_Edges.add(A);
				}
			}
		}
		int max_value = All_Possible_Edges.size()-1;	
		
		//-----------For GUI------------------------------------------------------
		long count =0;
		//Get the number of all possible IMAs
		Number_of_Edges NE = new Number_of_Edges(this.edges-this.Loops,max_value+1,
                this. maximumRepeatedEdges);
		long all_possible = NE.getSum();//subtract all zeros array.
		int previous_value = 0;
		//------------------------------------------------------------------------

		GUI gui = new GUI();

		//Loop Through all combinations of all possible graphs and add them to APG
		//Fix a Loop configuration
		if(this.Loops>0){
			for(int[] la : LIMAs){
				int[] Incidence_Matrix_Array = new int[edges-Loops];
				resetVector(Incidence_Matrix_Array,0);
				do{
					count++;
					Graph G= new Graph(Construct_IM(All_Possible_Loop_Edges,la, All_Possible_Edges,Incidence_Matrix_Array), iL);
					if(G.get_isConnected()){
						G.generate_ordered_unoriented_IM();
						if(G.get_min_valency()>minimumVertexDegree && checkIsomorphism(G,All_Graphs)){
							All_Graphs.add(G);
							System.out.println(G.toString());
							gui.refresh1(All_Graphs.size(),previous_value);
						}
					}
					float quot = ((float)count/all_possible)*100;
					int progress = (int) quot;
					if(progress>previous_value){
						gui.refresh1(All_Graphs.size(),progress);
						previous_value = progress;
					}
				} while (iterateVector(Incidence_Matrix_Array,max_value));
			}
		}
		else{
			int[] Incidence_Matrix_Array = new int[edges-Loops];
			do{
				count++;
				Graph G= new Graph(Construct_IM(All_Possible_Edges,Incidence_Matrix_Array), iL);
				if(G.get_isConnected()){
					G.generate_ordered_unoriented_IM();
					if(G.get_min_valency()>minimumVertexDegree){
						if(checkIsomorphism(G,All_Graphs)){
							System.out.println(G.toString());
							All_Graphs.add(G);
							System.out.println(G.toString());
							gui.refresh1(All_Graphs.size(),previous_value);
							continue;
						}
					}
				}
				float quot = ((float)count/all_possible)*100;
				int progress = (int) quot;
				if(progress>previous_value){
					gui.refresh1(All_Graphs.size(),progress);
					previous_value = progress;
				}
				if(count % 10000 ==0){
					System.out.println("Count = "+ count);
				}
			} while (iterateVector(Incidence_Matrix_Array,max_value));
		}
		StringBuilder sb = new StringBuilder("There are ");
		sb.append(All_Graphs.size());
		sb.append(" graphs with dimensions: ");
		sb.append(this.verts);
		sb.append(" vetices, ");
		sb.append(this.edges);
		sb.append(" edges, and ");
		sb.append(this.Loops);
		sb.append(" loops.");
		System.out.println(sb);

	}
		

	private void resetVector(int[] B, final int index){
		int repeated = 1;
		if(index == B.length-1){
			return;
		} else{
			int temp = B[index];
			if(index>0){
				for(int k = 1; k<Math.min(index,this.maximumRepeatedEdges); k++){
					if(B[index-k]==temp){
						repeated++;
					} else{
						break;
					}

				}
			}
			for(int l=index+1; l<B.length; l++){
				if(repeated == this.maximumRepeatedEdges){
					temp++;
					repeated =1;
				} else{
					repeated ++;
				}
				B[l]=temp;
			}
		}
	}

	private boolean iterateVector(int[] B, final int maxValue){
		for(int i=0;i<B.length;i++){
			if(B[(B.length-1)-i]!= maxValue - (int)((float) i/this.maximumRepeatedEdges)){
				B[(B.length-1)-i] ++;
				resetVector(B,B.length-1-i);
				return true;
			}
		}
		return false;
	}

	//No Loops Constructor
	private int[][] Construct_IM(ArrayList<int[]> APE, int[] IMA){
		int[][] IM = new int[this.verts][this.edges];
		for(int row=0;row<this.verts;++row){
			for(int col=0;col<this.edges;++col){
				IM[row][col]=APE.get(IMA[col])[row];
			}
		}
		return IM;
	}
	//Loops Constructor
	private int[][] Construct_IM(ArrayList<int[]> Loop_List, int[] LIMA, ArrayList<int[]> APE, int[] IMA){
		int[][] IM = new int[verts][edges];
		for(int row=0;row<verts;++row){
			//Loops first
			for(int col=0;col<Loops;++col){
				IM[row][col]=Loop_List.get(LIMA[col])[row];
			}
			for(int col=0;col<edges-Loops;col++){
				IM[row][col+Loops]=APE.get(IMA[col])[row];
			}
		}
		return IM;
	}

}
