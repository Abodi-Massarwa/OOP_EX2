package api;

public class EgdeLocation implements edge_location {
	
	private edge_data m_edge;
	private double m_ratio = 0;
	
	public EgdeLocation(edge_data edge) {
		m_edge = edge;
	}

	@Override
	public edge_data getEdge() {
		return m_edge;
	}

	@Override
	public double getRatio() {
		return m_ratio;
	}
	
	
	public void setRatio(double ratio) {
		m_ratio = ratio;
	}
}
