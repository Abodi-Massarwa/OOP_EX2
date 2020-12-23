package api;

import java.util.ArrayList;

public class NodesDataHolder {
	private static ArrayList<node_data> s_allNodes = new ArrayList<node_data>();
	private static int s_keyGenerator = 0;
	
	static final int WHITE = 0, GRAY = 1, BLACK = 2; 
	
	//  Package modifier (default)
	static int addNode_GetKey(node_data nodeData) {
		s_allNodes.add(nodeData);
		return s_keyGenerator++;
	}
	
	static node_data getNodeByKey(int key) {
		
		return s_allNodes.size() > key? s_allNodes.get(key): null;
	}
}