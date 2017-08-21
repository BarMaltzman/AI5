package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeliefState {
	
	private int loc;
	private Graph graph;
	private List<Double> keysProb;
	private List<Double> locksProb;
	private int carrying;
	private Map<Integer,BeliefState> neighboringStates;
	public BeliefState(int loc, Graph graph, List<Double> probs, int carrying){
		this.loc = loc;
		this.graph = graph;
		this.keysProb = probs.subList(0, graph.size() - 1);
		this.locksProb = probs.subList(graph.size(),probs.size()-1);
		this.carrying = carrying;
	}
	public void updateNeighbors(HashMap<Integer, List<BeliefState>> states) {
		for(Vertex v : graph.getNeighbors(loc))
		
	}
	
}
