package api;

public class NodeData implements node_data {
	private static final String m_defaultInfo = "EMPTY_INFO";
	
	private int m_key, m_tag;
	private String m_info;
	private double m_weight;
	private boolean m_visited;
	private geo_location m_GEOLocation;
	
	public NodeData() {
		m_key = NodesDataHolder.addNode_GetKey(this);
		m_info = m_defaultInfo;
	}

	public NodeData(int id, geo_location ge) {
		this.m_key=id;
		this.m_GEOLocation=ge;
	}

	@Override
	public int getKey() {
		return m_key;
	}

	@Override
	public geo_location getLocation() {
		return m_GEOLocation;
	}

	@Override
	public void setLocation(geo_location p) {
		m_GEOLocation = p;
	}

	@Override
	public double getWeight() {
		return m_weight;
	}

	@Override
	public void setWeight(double w) {
		m_weight = w;
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