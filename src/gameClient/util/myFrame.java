package gameClient.util;

import api.*;
import gameClient.Arena;
import gameClient.CL_Agent;
import gameClient.CL_Pokemon;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;
import gameClient.util.Range2Range;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class myFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Arena _arena;
    private Range2Range _w2f;
    private int game_number;
    private float time;
    
    
    public myFrame(String str, int level) {
        super(str);
        this.setSize(new Dimension( 350, 300));
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game_number = level;
        this.setVisible(true);
    }

    public myFrame(String str, int i, int j, int level_number) {
        super(str);
        this.setSize(new Dimension( i, j));
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game_number = level_number;
        this.setVisible(true);
	}

	public void update(Arena ar) {
        this._arena = ar;
        updateFrame();
        this.revalidate();
    }

    private void updateFrame() {
        Range2D frame = new Range2D(new Range(20, this.getWidth() - 50),new Range(this.getHeight() - 150, 150));
        if (this._arena != null) {
            directed_weighted_graph g = _arena.getGraph();
            _w2f = Arena.w2f(g, frame);
            this.revalidate();
            this.setVisible(true);
        }
    }
    public void paint(Graphics g) {	
        this.add(new gamePanel(this._w2f));
        updateFrame();
        this.revalidate();
        
    }

    public void initLogin() {
    	gameClient.Ex2.startFrame panel = new gameClient.Ex2.startFrame();
        this.add(panel);
        this.setVisible(true);
        panel.setVisible(true);
    }

    public void setTimeToEnd(long timeTo) {
        time = (float) timeTo / 100;
    }

    
    
    private class gamePanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Range2Range _w2f;

        public gamePanel(Range2Range _w2f) {
            if (_w2f != null)
                this._w2f = _w2f;
            this.revalidate();
        }

        public void paintComponent(Graphics g) {
            Graphics2D gg = (Graphics2D) g;
            super.paintComponent(g);
            if(_arena!=null && _w2f != null) {
            	drawGraph(gg);
                drawPokemons(gg);
                drawAgants(gg);
            }
            
            this.revalidate();
            this.setVisible(true);
        }

    	private void drawGraph(Graphics g) {
    		directed_weighted_graph gg = _arena.getGraph();
    		Iterator<node_data> iter = gg.getV().iterator();
    		while(iter.hasNext()) {
    			node_data n = iter.next();
    			g.setColor(Color.blue);
    			drawNode(n,5,g);
    			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
    			while(itr.hasNext()) {
    				edge_data e = itr.next();
    				g.setColor(Color.gray);
    				drawEdge(e, g);
    			}
    		}
    	}
        
    	private void drawPokemons(Graphics g) {
    		List<CL_Pokemon> fs = _arena.getPokemons();
    		if(fs!=null) {
    		Iterator<CL_Pokemon> itr = fs.iterator();
    		
    		while(itr.hasNext()) {
    			
    			CL_Pokemon f = itr.next();
    			Point3D c = f.getLocation();
    			int r=10;
    			g.setColor(Color.green);
    			if(f.getType()<0) {g.setColor(Color.orange);}
    			if(c!=null) {

    				geo_location fp = this._w2f.world2frame(c);
    				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
    			//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    				
    			}
    		}
    		}
    	}
    	private void drawAgants(Graphics g) {
    		List<CL_Agent> rs = _arena.getAgents();
    	//	Iterator<OOP_Point3D> itr = rs.iterator();
    		g.setColor(Color.red);
    		int i=0;
    		while(rs!=null && i<rs.size()) {
    			geo_location c = rs.get(i).getLocation();
    			int r=8;
    			i++;
    			if(c!=null) {

    				geo_location fp = this._w2f.world2frame(c);
    				g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
    			}
    		}
    	}
        
    	private void drawNode(node_data n, int r, Graphics g) {
    		geo_location pos = n.getLocation();
    		geo_location fp = this._w2f.world2frame(pos);
    		g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
    		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
    	}
    	private void drawEdge(edge_data e, Graphics g) {
    		directed_weighted_graph gg = _arena.getGraph();
    		geo_location s = gg.getNode(e.getSrc()).getLocation();
    		geo_location d = gg.getNode(e.getDest()).getLocation();
    		geo_location s0 = this._w2f.world2frame(s);
    		geo_location d0 = this._w2f.world2frame(d);
    		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
    	//	g.drawString(""+n.getKey(), fp.ix(), fp.iy()-4*r);
    	}

    }
}
