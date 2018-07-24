package pkg;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;

@SuppressWarnings("unused")
public class Solver {
	private MutableNetwork<Node, Arc> graph;
	private int nodes;
	private double alpha;
	private int[][] coordinates;
	private double[][] danger;
	private double[][] distances;
	private boolean[] leaf;
	private ArrayList<Arc> sol;
	private ArrayList<Node> listNodes;
	private ArrayList<Arc> solEdges;
	private ArrayList<MutableNetwork<Node, Arc>> solPaths;
	
	public Solver(int nodes, double alpha, int[][] coordinates, double[][] danger){
		this.nodes = nodes;
		this.alpha = alpha;
		this.danger = new double[nodes+1][];
		this.distances = new double[nodes+1][nodes+1];
		this.sol = new ArrayList<Arc>();
		this.leaf = new boolean[nodes+1];
		this.solEdges = new ArrayList<Arc>();
		this.solPaths = new ArrayList<MutableNetwork<Node, Arc>>();
		
		listNodes = new ArrayList<Node>();
		
		// Create the network
		graph = NetworkBuilder.directed().allowsParallelEdges(true).build();
		
		for(int i = 0; i < nodes + 1; i++){
			// Copy the dangers array
			this.danger[i] = new double[nodes + 1];
			System.arraycopy(danger[i], 0, this.danger[i], 0, danger[i].length);	// arraycopy is faster than for loops
			
			// Initialize the leaf array elements to true
			this.leaf[i] = false;
			
			// Calculate distances with Euclidean Distance
			for(int j = 0; j < nodes + 1; j++){
				if(i == 0){
					if(j == i)
						distances[i][j] = 0;
					else
						distances[i][j] = Double.POSITIVE_INFINITY;
				}
				else{
					if(i == j)
						distances[i][i] = 0;
					else
						distances[i][j] = euclideanDistance(coordinates[i], coordinates[j]);
				}
			}
			
			// Add all nodes
			listNodes.add(new Node(i, false));
			graph.addNode(listNodes.get(i));
		}
		
		// Add all edges
		for(int i = 0; i < nodes + 1; i++){
			for(int j = 0; j < nodes + 1; j++){
				if(i == 0 && j != 0)
					graph.addEdge(listNodes.get(i), listNodes.get(j), new Arc(i, j, Double.POSITIVE_INFINITY, danger[i][j]));
				else if(i != j)
					graph.addEdge(listNodes.get(i), listNodes.get(j), new Arc(i, j, distances[i][j], danger[i][j]));
			}
		}
	}
	
	public ArrayList<Arc> solve(){
		// Call solver
		recursiveDFS(graph, null, ((Node)graph.nodes().toArray()[0]), 0);
		return solEdges;
	}
	
	// Function that calculates Euclidean distance between two nodes with 4 decimal point precision
	private double euclideanDistance(int[] xy1, int[] xy2){
		double distance = Math.sqrt(Math.pow(xy1[0]-xy2[0], 2) + Math.pow(xy1[1]-xy2[1], 2));
		return BigDecimal.valueOf(distance).setScale(6, RoundingMode.HALF_UP).doubleValue();
	}
	
	// This method orders the adjacent arcs of given node excluding node 0
	private Set<Arc> orderArcsSol(Node v){
		TreeSet<Arc> tmp = new TreeSet<Arc>(new ArcComparator());
		for(Node n : graph.adjacentNodes(v)){
			if(n.number != 0)
				tmp.add((Arc)graph.edgesConnecting(n, v).toArray()[0]);
		}
		return tmp;
	}
		
	// Minimum Leaf Spanning Tree Solver
	private boolean recursiveDFS(MutableNetwork<Node, Arc> g, MutableNetwork<Node, Arc> p, Node root, double distance){
		root.visited = true;
		boolean isLeaf = true;
		boolean nextIsLeaf = false;
		Set<Arc> orderedNeighbors = orderArcsSol(root);
		for(Arc a : orderedNeighbors){
			if(root.number == 0){
				p = NetworkBuilder.directed().build();
				p.addNode(root);
				distance = 0;
			}
			Node n = g.incidentNodes(a).adjacentNode(root); 
			if(!n.visited){
				double newDistance = distance + a.distance;
				if(newDistance / distances[n.number][0] <= alpha){
					n.distance = newDistance;
					p.addNode(n);
					p.addEdge(n, root, a);
					n.numNodes = p.edges().size();
					isLeaf = false;
					solEdges.add(a);
					nextIsLeaf = recursiveDFS(g, p, n, newDistance);						
					
					if(root.number != 0 && nextIsLeaf)
						return nextIsLeaf;
				}
			}
		}
		if(isLeaf)	this.leaf[root.number] = true;
		else 		this.leaf[root.number] = false;
		return isLeaf;
	}
}
