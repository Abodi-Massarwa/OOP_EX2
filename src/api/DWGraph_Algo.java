package api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;


public class DWGraph_Algo implements dw_graph_algorithms {

	private directed_weighted_graph m_graph;
	
	@Override
	public void init(directed_weighted_graph g) {
		m_graph = g;
	}

	@Override
	public directed_weighted_graph getGraph() {
		return m_graph;
	}

	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph copy = new DWGraph_DS();
		HashMap<Integer, Integer> oldToCopy = new HashMap<Integer, Integer>();
		for (node_data oldNode : m_graph.getV()) {
			node_data newNode = new NodeData();
			oldToCopy.put(oldNode.getKey(), newNode.getKey());
			copy.addNode(newNode);
		}
		
		for (node_data oldNode : m_graph.getV()) {
			for (edge_data oldEdge : m_graph.getE(oldNode.getKey())) {
				copy.connect(oldToCopy.get(oldEdge.getSrc()), oldToCopy.get(oldEdge.getDest()), oldEdge.getWeight());
			}
		}
		
		return copy;
	}

	@Override
	public boolean isConnected() {
		int count = 0;
		
		int vertices = m_graph.nodeSize(), edges = m_graph.edgeSize();
		if(vertices <= 1 || (vertices-1)*(vertices-2)/2 < edges)
			return true;
		
		Queue<node_data> nodes = new LinkedList<node_data>();
		node_data source = null;

		// Making all nodes WHITE (in case they were BLACK before)
		for (node_data n : m_graph.getV()) {
			n.setTag(NodesDataHolder.WHITE);
			source = n;
		}
		
		nodes.add(source);

		while (!nodes.isEmpty()) {
			node_data n = nodes.poll();

			if (n.getTag() == NodesDataHolder.WHITE) {
				++count;
				n.setTag(NodesDataHolder.GRAY);
			}

			if (n.getTag() == NodesDataHolder.GRAY) {
				for (edge_data edge : m_graph.getE(n.getKey())) {
					if (edge.getTag() == NodesDataHolder.WHITE) {
						++count;
						edge.setTag(NodesDataHolder.GRAY);
						nodes.add(NodesDataHolder.getNodeByKey(edge.getDest()));
					}
				}

				n.setTag(NodesDataHolder.BLACK);
			}
		}

		return m_graph.getV().size() == count;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		node_data srcNode = m_graph.getNode(src), destNode = m_graph.getNode(dest);

		HashMap<node_data, Double> dist = new HashMap<node_data, Double>();

		for (node_data n : m_graph.getV()) {
			dist.put(n, -1.0);
		}

		dist.put(destNode, 0.0);

		TreeMap<Double, ArrayList<node_data>> q = new TreeMap<Double, ArrayList<node_data>>();
		q.put(0.0, new ArrayList<node_data>(Arrays.asList(destNode)));

		while (!q.isEmpty()) {
			Double closest = q.firstKey();
			node_data u = q.get(closest).remove(0);
			if(q.get(closest).isEmpty())
				q.remove(closest);

			for (edge_data edge : m_graph.getE(u.getKey())) {
				node_data v = NodesDataHolder.getNodeByKey(edge.getDest());
				double alt = dist.get(u) + m_graph.getEdge(v.getKey(), u.getKey()).getWeight();
				if (dist.get(v) == -1.0 || alt < dist.get(v)) {
					dist.put(v, alt);
					
					q.computeIfPresent(alt, (k,list) -> {
						list.add(v);
						
						return list;
					});
					q.putIfAbsent(alt, new ArrayList<node_data>(Arrays.asList(v)));
				}
			}
		}

		return dist.get(srcNode);
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		node_data srcNode = m_graph.getNode(src), destNode = m_graph.getNode(dest);

		HashMap<node_data, node_data> prev = new HashMap<node_data, node_data>();
		HashMap<node_data, Double> dist = new HashMap<node_data, Double>();

		for (node_data n : m_graph.getV()) {
			prev.put(n, null);
			dist.put(n, -1.0);
		}

		dist.put(destNode, 0.0);
		prev.put(destNode, null);

		TreeMap<Double, ArrayList<node_data>> q = new TreeMap<Double, ArrayList<node_data>>();
		q.put(0.0, new ArrayList<node_data>(Arrays.asList(destNode)));

		while (!q.isEmpty()) {
			Double closest = q.firstKey();
			node_data u = q.get(closest).remove(0);
			if(q.get(closest).isEmpty())
				q.remove(closest);

			for (edge_data edge : m_graph.getE(u.getKey())) {
				node_data v = NodesDataHolder.getNodeByKey(edge.getDest());
				double alt = dist.get(u) + m_graph.getEdge(v.getKey(), u.getKey()).getWeight();
				if (dist.get(v) == -1.0 || alt < dist.get(v)) {
					dist.put(v, alt);
					prev.put(v, u);
					
					q.computeIfPresent(alt, (k,list) -> {
						list.add(v);
						
						return list;
					});
					q.putIfAbsent(alt, new ArrayList<node_data>(Arrays.asList(v)));
				}
			}
		}

		if (dist.get(srcNode) == -1)
			return null;

		List<node_data> ret = new ArrayList<node_data>();

		while (srcNode != destNode) {
			ret.add(srcNode);
			srcNode = prev.get(srcNode);
		}

		ret.add(srcNode);

		return ret;
	}

	@Override
	public boolean save(String file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean load(String file) {
		// TODO Auto-generated method stub
		return false;
	}

}