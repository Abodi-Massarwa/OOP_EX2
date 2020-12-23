package tests;

import api.DWGraph_DS;
import api.NodeData;
import api.edge_data;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_DSTest {

   static DWGraph_DS myGraph;
   @BeforeEach
    void build(){
       myGraph= new DWGraph_DS();
       myGraph.addNode(new NodeData()); //0
       myGraph.addNode(new NodeData()); ///1
       myGraph.addNode(new NodeData()); //2
       myGraph.addNode(new NodeData()); //3
       myGraph.connect(0,1,255);
       myGraph.connect(1,0,255);
       myGraph.connect(1,2,100);
       myGraph.connect(2,1,100);
       myGraph.connect(2,3,50);
       myGraph.connect(3,2,50);
       myGraph.connect(3,0,25);
       myGraph.connect(0,3,25);
       myGraph.connect(0,2,15);
       //square
   }
    @Test
    void Test_add()
    {
        assertTrue(myGraph.getV().size()==4);

    }
    @Test
    void test_connect()
    {
        //System.out.println(myGraph.getEdges().get(0).get(1).getSrc()==0);
       assertTrue(myGraph.getEdges().get(0).get(1).getSrc()==0);
        assertTrue(myGraph.getEdges().get(1).get(0).getSrc()==1);
        assertTrue(myGraph.getEdges().get(1).get(2).getSrc()==1);
        assertTrue(myGraph.getEdges().get(2).get(1).getSrc()==2);
        assertTrue(myGraph.getEdges().get(0).get(3).getSrc()==0);

    }
    @Test
    void test_nodesize()
    {
        myGraph.removeNode(1);
        assertTrue(myGraph.nodeSize()==3);

    }
    @Test
    void test_getmc()
    {
        myGraph.removeNode(1);

        assertTrue(myGraph.getMC()==14);

    }
    @Test
    void test_edgesize()
    {
        assertTrue(myGraph.edgeSize()==9);


    }
}