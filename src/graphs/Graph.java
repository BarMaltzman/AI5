package graphs;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class Graph {
	private ArrayList <Vertex> vertexes;
	private ArrayList <Edge> edges;

	private int size;

	public Graph(int size) {
		this.size = size;
		vertexes = new ArrayList<Vertex>();
		for(int i=0 ; i < size ; i++)
			vertexes.add(i, new Vertex(i));
		edges = new ArrayList<Edge>();
	}
	public Graph(Graph other){
		this.vertexes = new ArrayList<Vertex>();
		this.size = other.size;
		for(int i=0 ; i < size ; i++){
			vertexes.add(i, new Vertex(i));
			for(Key key : other.vertexes.get(i).getKeys())
				vertexes.get(i).addKey(new Key(key));
			for(Lock lock : other.vertexes.get(i).getLocks())
				vertexes.get(i).addLock(new Lock(lock));
		}
		edges = new ArrayList<Edge>();
		for(Edge e : other.edges)
			this.addEdge(e.getV1().getId(),e.getV2().getId(), e.getWeight());
		}
	
	@Override
	public String toString() {
		String res = "#V "+vertexes.size() +"\n";
		for (Edge e : edges)
			res += e +"\n";
		for (Vertex v : vertexes)
			res += v + "\n";

		return res;
	}
	public ArrayList<Vertex> getVertixes() {
		return vertexes;
	}
	public void setVertixes(ArrayList<Vertex> vertixes) {
		this.vertexes = vertixes;
	}
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	// moves an agent from current vertex to destination. returns true if moved successfully.
	

	public int size() {
		return vertexes.size();
	}
	public List<Vertex> getNeighbors(Vertex v) {
		return v.getNeighbors();
	}
	
	public List<Vertex> getNeighbors(int v) {
		return getNeighbors(vertexes.get(v));
	}
	public Double getWeight(Vertex v1, Vertex v2) {
		for(Edge e: edges){
			if((e.getV1().equals(v1)&&e.getV2().equals(v2)||
					(e.getV1().equals(v2)&&e.getV2().equals(v1)))){
				return e.getWeight();
			}
		}
		if(v2 ==null || v1.getId() == v2.getId())
			return 1.0; // no-op
		else
			return  Double.MAX_VALUE;
	}
	public double getWeight(int id1, int id2) {
		Vertex v1 = null;
		Vertex v2 = null;
		for (Vertex v : vertexes){
			if(v.getId() == id1)
				v1 = v;
			else if(v.getId() == id2)
				v2 = v;
		}
		return getWeight(v1, v2);
	}
	public void addKeyToVertex(int currVertexId, Key key) {
		vertexes.get(currVertexId).addKey(key);	
	}
	public void addLockToVertex(int currVertexId, Lock lock) {
		vertexes.get(currVertexId).addLock(lock);
	}
	public void addEdge(int v1num, int v2num, double weight) {
		Vertex v1 = vertexes.get(v1num);
		Vertex v2 = vertexes.get(v2num);
		edges.add(new Edge(v1,v2,weight));
		v1.addNeighbor(v2);
		v2.addNeighbor(v1);
	}

	
	public boolean equals(Object other) {
		if(other instanceof Graph){
			Graph otherGraph = (Graph) other;
			for(Vertex v: vertexes)
				if(!otherGraph.vertexes.contains(v))
					return false;
			return true;
		}
		return false;
	}

	public double calculatePathWeight(List<Integer> path) {
		double ans = 0;
		for (int i = 0 ; i < path.size()-1 ; i++){
			ans += this.getWeight(path.get(i), path.get(i+1));
		}
		return ans;
	}

}
