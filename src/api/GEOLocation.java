package api;

public class GEOLocation implements geo_location {



	private double[] m_coordination = {0.0, 0.0, 0.0};
	
	public GEOLocation(double x, double y, double z) {
		m_coordination[0] = x;
		m_coordination[1] = y;
		m_coordination[2] = z;
	}
	public GEOLocation(GEOLocation g)
	{
		for (int i=0; i<this.m_coordination.length;i++)
		{
			this.m_coordination[i]=g.m_coordination[i];
		}
	}
	@Override
	public double x() {
		return m_coordination[0];
	}

	@Override
	public double y() {
		return m_coordination[1];
	}

	@Override
	public double z() {
		return m_coordination[2];
	}

	@Override
	public double distance(geo_location g) {
		double suqaredSum = Math.pow(g.x() - this.x(), 2) + Math.pow(g.y() - this.y(), 2) + Math.pow(g.z() - this.z(), 2);
		
		return Math.sqrt(suqaredSum);
	}

}
