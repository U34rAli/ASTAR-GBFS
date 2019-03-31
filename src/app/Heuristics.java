package app;
public class Heuristics{
    Vertex obj;
    double heuristics;
    public Heuristics(String vertex, double d){
        obj = new Vertex(vertex);
        heuristics = d;
    }
}