package tests;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.NodeData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {
    static DWGraph_Algo mydwgraph;
    static DWGraph_DS myGraph;
    @BeforeEach
    void build(){
        mydwgraph= new DWGraph_Algo();
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
        mydwgraph.init(myGraph);
        //square
    }
    @Test
    void test_shortestpath()
    {
        double distance=mydwgraph.shortestPathDist(0,1);
        System.out.println(distance);
        assertTrue(distance==15);
    }

}