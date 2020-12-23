package api;

public class EdgeData implements edge_data {
	
	private node_data m_srcNode, m_destNode;
	private double m_weight;
	private String m_info;
	private int m_tag;
	
	public EdgeData(node_data source, node_data dest) {
		m_srcNode = source;
		m_destNode = dest;
	}
	
	public EdgeData(node_data source, node_data dest, double weight) {
		m_srcNode = source;
		m_destNode = dest;
		m_weight = weight;
	}
	
	public EdgeData(int source, int dest) {
		m_srcNode = NodesDataHolder.getNodeByKey(source);
		m_destNode = NodesDataHolder.getNodeByKey(dest);
	}
	
	public EdgeData(int source, int dest, double weight) {
		m_srcNode = NodesDataHolder.getNodeByKey(source);
		m_destNode = NodesDataHolder.getNodeByKey(dest);
		m_weight = weight;
	}

	@Override
	public int getSrc() {
		return m_srcNode.getKey();
	}

	@Override
	public int getDest() {
		return m_destNode.getKey();
	}

	@Override
	public double getWeight() {
		// Possible solution for a bug (possible implementation)
		//return m_srcNode.getWeight() + m_destNode.getWeight();
		return m_weight;
	}

	@Override
	public String getInfo() {
		return m_info;
	}

	@Override
	public void setInfo(String s) {
		m_info = s;
	}

	@Override
	public int getTag() {
		return m_tag;
	}

	@Override
	public void setTag(int t) {
		m_tag = t;
	}

}