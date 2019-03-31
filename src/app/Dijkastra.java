package app;

public class Dijkastra /*implements Comparable*/ {
    public Vertex current;
    public Vertex pervious;
    public int cost;
    public int status = -1; // -1 not discovered , 0 partially discovered, 1 fully discovered

    public Dijkastra(Vertex current, Vertex previous, int cost) {
        this.current = current;
        this.pervious = previous;
        this.cost = cost;
    }

    public String toString(){
        return "["+ current +" prevoius  " + pervious+ " cost = " + cost+"]";
    }
    /*
    @Override
    public int compareTo(Object arg0) {
        Dijkastra ob = (Dijkastra)arg0;
        if(this.cost < ob.cost){
            return -1;
        }
        else if (this.cost > ob.cost){
            return 1;
        }
        else{
            return 0;
        }
    }
    */
}