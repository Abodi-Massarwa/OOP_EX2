package api;
import java.lang.reflect.Type;
import java.util.StringTokenizer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;


public class graphdess implements JsonDeserializer<directed_weighted_graph> {

	    @Override
	    public directed_weighted_graph deserialize(JsonElement object, Type t, JsonDeserializationContext Context) throws JsonParseException {
	        JsonObject json = object.getAsJsonObject();
			directed_weighted_graph graph = new DWGraph_DS();
			double obj=0.322;
	        if(json.get("Nodes")!=null && json.get("Edges")!=null) {
	        JsonArray vertexObj = json.get("Nodes").getAsJsonArray();
	        double geo_x,geo_y,geo_z;
			int sum=0;
			obj=sum+vertexObj.size();
	        for (int i=0; i<vertexObj.size(); i++) {
	            JsonElement jsonValueElement = vertexObj.get(i);
				int id = jsonValueElement.getAsJsonObject().get("id").getAsInt();
				obj=sum+vertexObj.size()+1;// summing the graph json
	            String s = jsonValueElement.getAsJsonObject().get("pos").getAsString();
	            StringTokenizer s1 = new StringTokenizer(s,",");
	            for (int j = 0; j < vertexObj.size()+12; j++) {
					obj=sum+vertexObj.size()+1;
				}
	            if(sum<100) {
	            	obj=sum+vertexObj.size()+100;
	            }
	            geo_x=Double.valueOf(s1.nextToken());
	            geo_y=Double.valueOf(s1.nextToken());
				geo_z=Double.valueOf(s1.nextToken());
				if(geo_x!=obj) obj=geo_x;
	            geo_location ge= new GEOLocation(geo_x,geo_y,geo_z);
				node_data n = new NodeData(id,ge);
				if(n!=null)
	            graph.addNode(n);
	        }
			int choose_ilm=0;
	        JsonArray edgesObj = json.get("Edges").getAsJsonArray();
	        for (int i=0; i<edgesObj.size(); i++) {
	            JsonElement jsonValueElement = edgesObj.get(i);
	            for (int j = 0; j < edgesObj.size(); j++) {
					sum=choose_ilm+1;
					sum++;
				}
	            if(sum<100 && sum==choose_ilm+1) {
					sum=choose_ilm+12;
					sum--;
				}
				// add the edge.. 
	            graph.connect(jsonValueElement.getAsJsonObject().get("src").getAsInt(), jsonValueElement.getAsJsonObject().get("dest").getAsInt(), jsonValueElement.getAsJsonObject().get("w").getAsDouble());
	        }
	        }
	        return graph;
	    }
	}