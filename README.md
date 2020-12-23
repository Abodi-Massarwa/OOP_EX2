#   Assignment_2 Summary 
___
## Section A
* _in the first part of this assignment we modified the classes to become compatible with the concept of directed graph,
 in other words we slightly modified the algorithms in isconnected(), shortestPath() & shortestPathDest()we didn't need to work hard on the modifications
 since in the very first assignment (Ex0) our graph was compatible with directed manner a single edge would appear in both enteries of the source node and the destination node in our m_Edges hashmap_


 * regrading the tests we modified the tests to be also compatible with the concept of directed-weighted-graph :
 1) DWGraph_AlgoTest
 2) DWGraph_DSTest

 * in WDGraph_DS we used 3 hashmaps : 
 1) ***vertices hashmap(hashmap <Integer,NodeData>)***
 2) ***Edges hashmap(hashmap <Integer,Hashmap<Integer,EdgeData>>)***
 3) ***Neighbors hashmap (hashmap < Integer,ArrayList<Integer> ) (for the purposes of optimization when removing a node out of the graph so we can get instant access to it's neighbors pointers, this things saves us from traversing in the graph so we can find each Node's neighbors)***


___

## Section B
* regarding the Graphical User Interface that was semi-made, we worked on fixing it and customizing it with the help of Java Swing library

* when it comes to placing Agents in the graph to collect the Rewards , we achieved it with the help of shortest path algorithm from different Nodes and placed number of agents in arbitrary close point to the reward.
 with the respect of not spawning more than 1 agent on the same edge 

___
## Links:
* **javaTpoint**
  * [java-swing](https://www.javatpoint.com/java-swing)
  * [java-MultiThreading](https://www.javatpoint.com/multithreading-in-java)
* **Baeldung**
    * [directed-graph](https://www.baeldung.com/java-graphs)
    * [BFS](https://www.geeksforgeeks.org/best-first-search-informed-search/)
    * [Graph in Java](https://www.geeksforgeeks.org/graph-data-structure-and-algorithms/)

  ---
  <!--Images-->
  ![Illustration](https://camo.githubusercontent.com/9e29360d1cfc8412e9e2ae066471bed45b6a0159470388191f4e6f7b6fccecf9/68747470733a2f2f7777772e7265736561726368676174652e6e65742f70726f66696c652f4c6a75626973615f5374616e6b6f7669632f7075626c69636174696f6e2f3332393335303136332f6669677572652f666967312f41533a37333035303137393038353531373040313535313137363630373932312f416e2d6578616d706c652d6f662d612d77656967687465642d67726170682e706e67)
  ![Gui-instance-1](https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/-Insert_image_here-.svg/1280px--Insert_image_here-.svg.png)
  ![Gui-instance-2](https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/-Insert_image_here-.svg/1280px--Insert_image_here-.svg.png)
___
<!-- ### Illustration
1.
```bash
./frequency
```
**the bash command above gives**
```bash
age  2
best  1
foolishness  1
it  4
of  4
the  4
times  2
was  4
wisdom  1
worst  1
ubuntu:~/environment (master) $ ...
```
2.
```bash
./frequency_r
```
**the bash command above gives**
```bash
worst  1
wisdom  1
was  4
times  2
the  4
of  4
it  4
foolishness  1
best  1
age  2
ubuntu:~/environment (master) $ ...
``` -->
---
**Contributed by:**
* **Abed El Kareem Massarweh**
* **Nour Bsoul**
---

# End of Summary
