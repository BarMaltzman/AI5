import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import graphs.BeliefState;
import graphs.Graph;
import graphs.Pair;
import graphs.Vertex;

public class Simulator {
	public static final double LEAKAGE_WITH_BRC = 0.001;
	public static final double LEAKAGE_WITHOUT_BRC = 0.01;
	public static final double PT = 0.1;
	public static final double PF = 0.2;
	public static final double QT = 1 - PT;
	public static final double QF =  1- PF;
	
	private int numberOfBlockaids;
	private double pbrc;
	
	private Graph graph;
	
	public static void main(String[] args) {
		Simulator simulator = new Simulator();
		simulator.startSimulator();
		
		
		
		
	}
	public Simulator(){
		initGraph();
	}
	public List<List<Double>> permutations(int vertexId, boolean permKeys){
		List<List<Double>> ans = new ArrayList<List<Double>>();
		if (vertexId == graph.size()){
			if (permKeys)
				return permutations(0, false);
			else
				return ans;
		}
			
		List<List<Double>> temp = permutations(vertexId+1, permKeys);
		double prob = (permKeys)? graph.getVertixes().get(vertexId).getKeyProbability() :
			graph.getVertixes().get(vertexId).getBlockProbability();
		if(temp.isEmpty()){
			List<Double> temp1 = new ArrayList<Double>();
			temp1.add(0.0);
			List<Double> temp2 = new ArrayList<Double>();
			temp2.add(1.0);
			ans.add(temp1);
			ans.add(temp2);
			
			if(prob != 0 && prob != 1){
				List<Double> temp3 = new ArrayList<Double>();
				temp3.add(prob);
				ans.add(temp3);
			}
				
		}
		else{
			for(List<Double> perm : temp){
				List<Double> temp2 = new ArrayList<Double>(perm);
				temp2.add(0,0.0);
				ans.add(temp2);
				List<Double> temp3 = new ArrayList<Double>(perm);
				temp3.add(0,1.0);
				ans.add(temp3);
				if(prob!= 0 && prob!= 1){
					List<Double> temp4 = new ArrayList<Double>(perm);
					temp4.add(0,prob);
					ans.add(temp4);
				}
			}
		}
		return ans;
	}
	public void getAllStates(){
		List<List<Double>> keyPerm = null;
		List<List<Double>> blockPerm = null;
		int size = graph.size();
	}
	
	public void startSimulator(){
		//System.out.println(bn);
		HashMap<Integer,List<BeliefState>> states = new HashMap<Integer,List<BeliefState>>(); 
		List<List<Double>> perm = permutations(0,true);
		for (int i = 0; i<graph.size(); i++){
			states.put(i, new ArrayList<BeliefState>());
			for(List<Double> p : perm){
				states.get(i).add(new BeliefState(i, graph, p,0));
				states.get(i).add(new BeliefState(i, graph, p,1));
			}
		}
		for(List<BeliefState> bs : states.values())
			for(BeliefState b : bs)
				b.updateNeighbors(states);
			
		
		System.out.println(perm.size());
		double[] keysProbs = new double[graph.size()];
		double[] blocksProbs = new double[graph.size()];
		
		for(Vertex v : graph.getVertixes()){
			
		}
		
		Scanner scanner = new Scanner(System.in);
		
		boolean shouldExit = true;
		while(!shouldExit)
		{
			
	}
	}
	
	private void initGraph() {
		int numberOfVertex = 0;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter File Name:");
		String filename = scanner.nextLine();
		File file = new File(filename);
		BufferedReader reader = null;
		try {
			String nextLine;
			reader = new BufferedReader(new FileReader(file));
			boolean hasVertexs = false;
			while ((nextLine = reader.readLine()) != null){
				String[] splits = nextLine.split(" ");
				switch(splits[0]){
				case "#V":
					if(!hasVertexs){
						numberOfVertex = Integer.parseInt(splits[1]);
						graph = new Graph(numberOfVertex);
						hasVertexs = true;
					}
					else {
						int currVertexId = Integer.parseInt(splits[1]);
						
						int id = Integer.parseInt(splits[3]);
						double probability = Double.parseDouble(splits[4]);
						if(splits[2].equals("K"))
							graph.getVertixes().get(currVertexId).addKeyProbability(probability);
						else
								graph.getVertixes().get(currVertexId).addBlockageProbability(probability);
						break; // notice, we do not add the keys yet, because they might not even exist.
						
					}
					break;
				case "#E":
					int v1num = Integer.parseInt(splits[1]);
					int v2num = Integer.parseInt(splits[2]);
					double weight = Double.parseDouble(splits[3].substring(1));
					graph.addEdge(v1num,v2num,weight);
					break;
				
				case "#B":
					numberOfBlockaids = Integer.parseInt(splits[1]);
					break;
				}
			}	
		}
		catch( IOException e){
			e.printStackTrace();
			scanner.close();

		}

		//scanner.close();
	}
}


