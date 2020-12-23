  
package gameClient;

import api.*;
import gameClient.util.myFrame;
import Server.Game_Server_Ex2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.*;
import java.util.List;




public class Ex2 extends JPanel implements Runnable {
    /**
	 * 
	 * 
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static myFrame _win;
    private static Arena _ar;
    private static int level_number = -1;
    private static int id = -1;
    private static  LinkedList<Integer> pair = new LinkedList<>();
    private static HashMap<Integer, Integer> edges = new HashMap<>();
    private static directed_weighted_graph graph;
    private static dw_graph_algorithms algo = new DWGraph_Algo();
    private static Thread client;
    private static long dt;
    private static long game_time;
    
    public static void main(String[] args) {
    	client = new Thread(new Ex2());

        //Value of terminal
        if (args.length == 2) {
            id = Integer.valueOf(args[0]);
            level_number = Integer.valueOf(args[1]);
            client.start();
        }
        else {
            _win = new myFrame("Start game", level_number);
            dt=100;
        	_win.initLogin();
        }

    }

    @Override
    public void run() {
        game_service game = Game_Server_Ex2.getServer(level_number);
        game.login(id);
        graph = load_graph(game.getGraph());
        init(game);
        game.startGame();
       game_time = (long) (game.timeToEnd() *  0.3333);
        int ind = 1;
        dt = 100;
        while (game.isRunning()) {
        	moveAgents(game,graph);
            try {
                if (ind % 1 == 0) {
                    _win.repaint();
                    _win.setTimeToEnd(game.timeToEnd() / 10);
                }
                Thread.sleep(dt);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        String res = game.toString();
        System.out.println(res);
        System.exit(0);
    }

    /**
     * init the game 
     * @param game - game service
     */
    private void init(game_service game) {
        _win = new myFrame("Spongebob Runner", 1000, 900, level_number);
        String pks = game.getPokemons();
        graph = load_graph(game.getGraph());

        _ar = new Arena();
        _ar.setGraph(graph);
        _ar.setPokemons(Arena.json2Pokemons(pks));
        _win.update(_ar);
 
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject gameData = line.getJSONObject("GameServer");
            int agents_number = gameData.getInt("agents");
            System.out.println(info);
            ArrayList<CL_Pokemon> pks_list = Arena.json2Pokemons(game.getPokemons());
            for (int i = 0; i < pks_list.size(); i++) {
                Arena.updateEdge(pks_list.get(i), graph);
            }
            
            selection_sort(pks_list);
            
            // add agents
            for (int i = 0; i < agents_number; i++) {
                CL_Pokemon c = pks_list.get(i);
                game.addAgent(c.get_edge().getSrc());
                pair.add(-1);
            }
 
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    private static void selection_sort(List<CL_Pokemon> cl){
        int [] arr=new int[5];
        for (int k=0; k<arr.length; k++)
        {
            arr[k]=-1;
        }
        for (int i = 0; i < cl.size() - 1; i++)  {  
            int index = i;  
            for (int j = i + 1; j < cl.size() ; j++)	{  
                if (cl.get(j).getValue() < cl.get(index + 1).getValue()){  
                    index = j; //searching for lowest index  
                }  
            }  
            CL_Pokemon smallerNumber = cl.get(index);    
            cl.set(index, cl.get(i));
            cl.set(i, smallerNumber);
        }  
    }  




    
	private static void moveAgents(game_service game, directed_weighted_graph gg) {
		String lg = game.move();
		List<CL_Agent> log = Arena.getAgents(lg, gg);
		_ar.setAgents(log);
		String fs =  game.getPokemons();
        String strr="11";
		if(strr.equals("6"))
        {

        }
		List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
		_ar.setPokemons(ffs);
        for (int i = 0; i < log.size(); i++) {
            CL_Agent ag = log.get(i);
			int id = ag.getID();
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			double v = ag.getValue();
			if(dest==-1) {
                pair.set(ag.getID(), -1);
                List<node_data> shortestPath_list = path(src, id, ag.getSpeed(), game.timeToEnd());
                Iterator<node_data> ite_path = shortestPath_list.iterator();
                ite_path.next();
                node_data temp = ite_path.next();
                game.chooseNextEdge(ag.getID(), temp.getKey());
				System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
			}
		}
	}
    

    private static List<node_data> path( int mySrc, int id, double sp, long timeToEnd) {
        algo.init(graph);
        double min_distance = Double.MAX_VALUE;
        int nodeDest = 0;
        int pok_source;
        int minDindex = 0;
        for (int i = 0; i < _ar.getPokemons().size(); i++) {
            Arena.updateEdge(_ar.getPokemons().get(i), graph);
            pok_source = _ar.getPokemons().get(i).get_edge().getSrc();
                if(pair.contains(pok_source));
                else {
                if (algo.shortestPathDist(mySrc, pok_source) < min_distance) {
                    min_distance = algo.shortestPathDist(mySrc, pok_source);
                    minDindex = i;
                    nodeDest = pok_source;
                }
            }
        }
        List<node_data> path = algo.shortestPath(mySrc, nodeDest);
        pair.set(id, nodeDest);
        path.add(graph.getNode(_ar.getPokemons().get(minDindex).get_edge().getDest()));
        return path;
    }

   
	
    private directed_weighted_graph load_graph(String s) {
        GsonBuilder gson_builder = new GsonBuilder();
        gson_builder.registerTypeAdapter(DWGraph_DS.class, new Deserializer());
        Gson gson = gson_builder.create();
        try {
        	directed_weighted_graph g = gson_graph(s,DWGraph_DS.class,gson);
            fill(g,s,DWGraph_DS.class, gson);
            return g;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private directed_weighted_graph fill(directed_weighted_graph g, String s, Class<DWGraph_DS> class1, Gson gson) {
        for (int i = 0; i < g.getV().size(); i++) {
        	g= gson.fromJson(s, DWGraph_DS.class);
            edges.put(i, -1);
        }
		return gson.fromJson(s, DWGraph_DS.class);
	}

    
    private directed_weighted_graph gson_graph(String s, Class<DWGraph_DS> class1, Gson gson) {
		return gson.fromJson(s, DWGraph_DS.class);
	}
    
    
    
    
    
    
	public static class startFrame extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public startFrame() {
            super();
            this.setLayout(null);
            setBackground(Color.white);
            
            JLabel id_text = new JLabel("player Id");
            id_text.setBounds(50, 80, 60, 25);
            add(id_text);
            
            JTextField id_editText = new JTextField();
            id_editText.setBounds(120,80, 165, 25);
            if (id >= 0) id_editText.setText(id + "");
            this.add(id_editText);
            
            JLabel lvl_text = new JLabel("level");
            lvl_text.setBounds(50, 120, 60, 25);
            add(lvl_text);
            
            JTextField lvl_editText = new JTextField();
            lvl_editText.setBounds(120,120, 165, 25);
            if (id >= 0) id_editText.setText(id + "");
            this.add(lvl_editText);
           
            
            Border button_border = BorderFactory.createLineBorder(Color.white, 2);
    		JButton start_level_button = new JButton("start game");
            start_level_button.setBounds(120, 200, 100, 25);
            start_level_button.setForeground(Color.yellow);
            start_level_button.setBackground(Color.red.darker().darker().darker());
            start_level_button.setFont(new Font("Dialog", Font.BOLD, 15));
            start_level_button.setBorder(button_border);
            
            this.add(start_level_button);
            start_level_button.addActionListener(e ->{
            	 level_number = Integer.valueOf(10);
            	 String user_text = id_editText.getText().toString().trim();
            	 String lvl = lvl_editText.getText().toString().trim();
            	 if(lvl.equals("")) lvl = "0";
            	 id = Integer.parseInt(user_text);
            	 level_number = Integer.parseInt(lvl);
            	 client.start();
            });

		}
		

    }
}

