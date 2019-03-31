package app;

import java.util.LinkedList;

public class Graph {
    private int V;
    private int rowsFilled = 0;
    LinkedList<Edge> adjListArray[]; // Adjacency linked list

    public Graph(int v) {
        this.V = v;
        adjListArray = new LinkedList[V];
        for (int i = 0; i < V; i++)
            adjListArray[i] = new LinkedList<Edge>();
    }

    // this fucntion will take one extra parameter for bidirection relation
    public void addEdge(String from, String to, int cost, String direction) throws NoMoreVertexException {
        Vertex source = new Vertex(from);
        Vertex des = new Vertex(to);
        if (direction.toLowerCase().equals("bi")) {
            addEdgeHelper(source, des, cost);
            addEdgeHelper(des, source, cost);
        } else {
            addEdgeHelper(source, des, cost);
        }
    }

    // single direction relation
    public void addEdge(String from, String to, int cost) throws NoMoreVertexException {
        Vertex source = new Vertex(from);
        Vertex des = new Vertex(to);
        if (getIndex(source) == -1) {
            addEdgeHelper(source, new Vertex(null), -1);
        }
        if (getIndex(des) == -1) {
            addEdgeHelper(des, new Vertex(null), -1);
        }
        addEdgeHelper(source, des, cost);
    }

    // add edge between to vertex and their relation
    private void addEdgeHelper(Vertex from, Vertex to, int cost) throws NoMoreVertexException {
        int index = getIndex(from);
        if (index > -1) {
            Edge edge = new Edge(from, to, cost);
            adjListArray[index].addFirst(edge);
            // rowsFilled++;
        } else if (rowsFilled < V && index == -1) {
            Edge edge = new Edge(from, to, cost);
            adjListArray[rowsFilled].addFirst(edge);
            rowsFilled++;
        } else {
            throw new NoMoreVertexException("Adjacency list can\'t be greater than \"" + getV() + "\"");
        }
    }

    // getIndex value to insert on existing position or to already present position
    public int getIndex(Vertex src) {
        int index = -1; // indicate adjacency list is full

        for (int i = 0; i < getV(); i++) {
            if (isListEmpty(adjListArray[i])) {
                if (isEqual(adjListArray[i].getFirst().from, src)) {
                    index = i; // check if headpointer is already present or not
                    break;
                }
            } else {
                // index = i; // if head pointer is not present in the list
                break;
            }

        }
        return index;
    }

    private boolean isEqual(Vertex head, Vertex operand) {
        // System.out.println(head + " " + operand);
        // if (operand.data == null || head.data == null)
        // return false;
        return head.data.equals(operand.data);
    }

    private int getV() {
        return V;
    }

    public String toString() {
        String spaceWidth = "      ";
        String graphData = "";
        for (int i = 0; i < getV(); i++) {
            for (int j = 0; j < adjListArray[i].size(); j++) {
                graphData = graphData + spaceWidth + adjListArray[i].get(j);
            }
            if (adjListArray[i].size() > 0) {
                graphData += "\n";
            }

        }
        return graphData;
    }

    private int findHeuristicsIndex(Heuristics[] h, Vertex v) {
        for (int i = 0; i < h.length && h[i] != null; i++) {
            if (isEqual(v, h[i].obj)) {
                return i;
            }
        }
        return -1;
    }

    private Edge getMinHeuristicsEdge(LinkedList<Edge> l, Heuristics[] h) {
        double min = 0;
        int index = -1;
        if (l.size() > 1) {
            int ind = findHeuristicsIndex(h, l.get(0).to);
            min = h[ind].heuristics;
            index = 0;
        }
        for (int i = 1; i < l.size() - 1; i++) {
            int ind = findHeuristicsIndex(h, l.get(i).to);
            if (min > h[ind].heuristics) {
                min = h[ind].heuristics;
                index = i;
            }

        }
        return index != -1 ? l.get(index) : null;
    }

    public void GBFS(String from, String to, Heuristics[] heuristics) {
        boolean doesPathExist = false;
        Vertex source = new Vertex(from);
        Vertex des = new Vertex(to);

        int index = getIndex(source);
        LinkedList<Edge> path = new LinkedList<Edge>();

        while (index > -1) {
            Edge minE = getMinHeuristicsEdge(adjListArray[index], heuristics);
            path.add(minE);
            if (minE != null) {
                index = getIndex(minE.to);
            } else {
                index = -1;
            }

            // System.out.println(minE);
            if (minE != null && isEqual(minE.to, des)) {
                doesPathExist = true;
                break;
            }
        }
        if (doesPathExist)
            System.out.println("GBFS " + path);
        else {
            System.out.println("No GBFS Path Exist");
        }

        // return null;
    }

    public void UCS(String from, String to) {
        // LinkedList<Edge> list[] = new LinkedList<Edge>();
        boolean isPathExist = false;
        Vertex source = new Vertex(from);
        Vertex des = new Vertex(to);
        LinkedList<Edge> queue = new LinkedList<Edge>();
        int index = getIndex(source);

        // queue.add(adjListArray[index].getFirst());
        if (index > rowsFilled)
            System.out.println(" Index of " + from + " = " + index);

        LinkedList<Edge> path = new LinkedList<Edge>();
        int sourceIndex = getIndex(source);
        while (sourceIndex > -1) {
            Edge minE = getMinCostEdge(adjListArray[sourceIndex]);
            path.add(minE);

            if (minE != null) {
                sourceIndex = getIndex(minE.to);
            } else {
                sourceIndex = -1;
            }

            // System.out.println(minE);
            if (minE != null && isEqual(minE.to, des)) {
                isPathExist = true;
                break;
            }
        }
        if (isPathExist)
            System.out.println("UCS " + path);
        else {
            System.out.println("No UCS Path Exist");
        }
        // System.out.println("UCS = " +sort(adjListArray[1]));

    }

    private Edge getMinCostEdge(LinkedList<Edge> eL) {
        if (eL.size() > 1) {
            int min = eL.get(0).cost;
            int index = 0;
            for (int i = 0; i < eL.size() - 1; i++) {
                if (min >= eL.get(i).cost) {
                    min = eL.get(i).cost;
                    index = i;
                }
            }
            return eL.get(index);
        }
        return null;
    }

    private boolean isListEmpty(LinkedList<Edge> edges) {
        return edges.size() > 0;
    }

    private LinkedList<Edge> sort(LinkedList<Edge> edgeList) {
        Edge input[] = convertLinkedListToArray(edgeList);

        Edge temp;
        for (int i = 1; i < input.length; i++) {
            for (int j = i; j > 0; j--) {
                if (input[j].cost < input[j - 1].cost) {
                    temp = input[j];
                    input[j] = input[j - 1];
                    input[j - 1] = temp;
                }
            }
        }
        return convertArrayToList(input);
    }

    private Edge[] convertLinkedListToArray(LinkedList<Edge> l) {
        Edge input[] = new Edge[l.size()];
        for (int i = 0; i < l.size(); i++) {
            input[i] = l.get(i);
        }
        return input;
    }

    private LinkedList<Edge> convertArrayToList(Edge[] l) {
        LinkedList<Edge> list = new LinkedList<Edge>();
        for (int i = 0; i < l.length; i++) {
            list.add(l[i]);
        }
        return list;
    }

    public void GBFS() {

    }

    public void BFS(String root) {
        Vertex src = new Vertex(root);
        LinkedList<Vertex> visited = new LinkedList<Vertex>();
        int index = getIndex(src);

        LinkedList<Vertex> queue = new LinkedList<Vertex>();

        if (index > -1) {
            queue.add(src);
            while (!queue.isEmpty()) {

                Vertex my_v = queue.remove();
                visited.add(my_v);
                index = getIndex(my_v);
                for (int j = 0; index > -1 && j < adjListArray[index].size() - 1; j++) {
                    // System.out.println(adjListArray[index].size() + " s");
                    Vertex my_addV = adjListArray[index].get(j).to;
                    if (!isContain(visited, my_addV) && !isContain(queue, my_addV)) {
                        queue.add(my_addV);
                    } else {
                        // System.out.println(my_addV + " k");
                    }
                }

            }
            System.out.println("BFS  from " + root + "  " + visited);
        }
    }

    private boolean isContain(LinkedList<Vertex> list, Vertex v) {
        for (int i = 0; i < list.size(); i++) {
            if (isEqual(list.get(i), v)) {
                return true;
            }
        }
        return false;
    }

    
    public void getMinPath(String from, String to, Heuristics[] h) {
        Vertex src = new Vertex(from);
        Vertex des = new Vertex(to);
        // PriorityQueue<Dijkastra> q = new PriorityQueue<Dijkastra>();
        // LinkedList<Dijkastra> visited = new LinkedList<Dijkastra>();
        Dijkastra[] dList = initializeDijkastrasArray();
        int index = getIndex(src);
        LinkedList<Dijkastra> dQueue = new LinkedList<Dijkastra>();

        if (index > -1) {
            dQueue.add(new Dijkastra(src, null, 0));
            while (!dQueue.isEmpty()) {

                // index = getMinCostDijkastra(dQueue);
                index = getMinHeuristicsCostDijkastra(dQueue ,h );

                Dijkastra my_v = dQueue.remove(index);

                dList[index].status = 1;

                int indexInAdj = getIndex(my_v.current);

                LinkedList<Edge> eList = adjListArray[indexInAdj];

                for (int i = 0; i < eList.size(); i++) {
                    Edge my_Edge = eList.get(i);
                    int srcIndex = getIndex(my_Edge.to);

                    if (srcIndex > -1) {
                        if (dList[srcIndex].status != 1) {

                            // if (dList[srcIndex].cost == Integer.MAX_VALUE ) {
                            // dList[srcIndex].cost = my_Edge.cost;
                            // dList[srcIndex].pervious = my_Edge.from;

                            if (dList[srcIndex].status == -1) {
                                dQueue.add(dList[srcIndex]);
                                dList[srcIndex].status = 0;
                            }
                            int newCost = my_Edge.cost + my_v.cost;// + dList[srcIndex].cost;
                            if (newCost < dList[srcIndex].cost) {
                                dList[srcIndex].cost = newCost;
                                dList[srcIndex].pervious = my_v.current;
                                updateCostInDijkastra(dQueue, dList[srcIndex].current, newCost);

                            }
                            // }
                        }
                    } else {

                    }

                }
            }
        }
        // for (Dijkastra d : dList)
        System.out.print("Min Path from " + src + " to " + des + " [ ");
        while (!src.data.equals(des.data)) {
            System.out.print(des + " <- ");
            int ind = getIndex(des);

            des = dList[ind].pervious;
        }
        System.out.println(des + "]");
    }

    private Dijkastra[] initializeDijkastrasArray() {
        Dijkastra[] dList = new Dijkastra[adjListArray.length];
        for (int i = 0; i < rowsFilled; i++) {
            dList[i] = new Dijkastra(adjListArray[i].getFirst().from, new Vertex(""), Integer.MAX_VALUE);

        }
        // System.out.println( dList);
        return dList;
    }

    private int getMinCostDijkastra(LinkedList<Dijkastra> d) {
        int index = 0;
        int min = d.getFirst().cost;
        for (int i = 0; i < d.size(); i++) {
            if (min > d.get(i).cost) {
                min = d.get(i).cost;
                index = i;
            }
        }
        return index;
    }
    private int getMinHeuristicsCostDijkastra(LinkedList<Dijkastra> d , Heuristics[] h) {
        int index = 0;
        double min = d.getFirst().cost +  h[findHeuristicsIndex(h, d.getFirst().current)].heuristics;

        // int heuristics = findHeuristicsIndex(h, d.getFirst().current);
        for (int i = 0; i < d.size(); i++) {
            if (min > d.get(i).cost) {
                min = d.get(i).cost + h[findHeuristicsIndex(h, d.get(i).current)].heuristics;
                index = i;
            }
        }
        return index;
    }

    private boolean updateCostInDijkastra(LinkedList<Dijkastra> d, Vertex v, int cost) {
        for (int i = 0; i < d.size(); i++) {
            if (d.get(i).current.data.equals(v.data)) {
                d.get(i).cost = cost;
                return true;
            }
        }
        return false;
    }

}
