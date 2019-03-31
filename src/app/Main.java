package app;


public class Main {
    public static void main(String[] args) {
        int v = 15;
        Graph graph = new Graph(v);

        // Vertex seven = new Vertex("7");

        try {
            graph.addEdge("S", "A", 2);
            graph.addEdge("S", "B", 3);

            graph.addEdge("A", "C", 3);

            graph.addEdge("B", "C", 1);
            graph.addEdge("B", "D", 3);
            
            graph.addEdge("C", "E", 3);

            graph.addEdge("C", "D", 1);
            graph.addEdge("D", "F", 2);

            graph.addEdge("E", "G", 2);

            graph.addEdge("F", "G", 1);

            graph.UCS("S","G");
            graph.BFS("S");
            // graph.getMinPath("S", "G");

            Heuristics[] heuristics = new Heuristics[v];
            heuristics[0] = new Heuristics("S", 6);
            heuristics[1] = new Heuristics("A", 4);
            heuristics[2] = new Heuristics("B", 4);
            heuristics[3] = new Heuristics("C", 4);
            heuristics[4] = new Heuristics("D", 3.5);
            heuristics[5] = new Heuristics("E", 1);
            heuristics[6] = new Heuristics("F", 1);
            heuristics[7] = new Heuristics("G", 0);

            graph.getMinPath("S", "G" , heuristics) ;
            graph.GBFS("S","G",heuristics);


        } catch (NoMoreVertexException e) {
            e.printStackTrace();
        }

        System.out.println(graph);

    }

}
