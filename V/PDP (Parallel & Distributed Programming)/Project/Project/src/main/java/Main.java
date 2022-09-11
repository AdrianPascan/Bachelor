import graph.ColorsGraph;
import graph.DirectedGraph;
import graph.GraphColoring;
import graph.GraphColoringException;

public class Main {

    public static void main(String[] args) {
        System.out.println("APP STARTED...\n");

        //example
        DirectedGraph graph = new DirectedGraph(5);
        graph.addVertex(0,1);
        graph.addVertex(1,2);
        graph.addVertex(2,3);
        graph.addVertex(3,4);
        graph.addVertex(4,0);
        graph.addVertex(2,0);
        graph.addVertex(0,4);
        graph.addVertex(4,3);
        graph.addVertex(3,1);

        ColorsGraph colors = new ColorsGraph(3);
        colors.addCodeToColor(0, "red");
        colors.addCodeToColor(1, "green");
        colors.addCodeToColor(2, "blue");

        int threadsNo = 4;

        try {
            long tic = System.nanoTime();

            System.out.println(GraphColoring.getGraphColoring(threadsNo, graph, colors));

            long tac = System.nanoTime();
            long time = tac - tic;

            System.out.println("Time: " + (time / 1000) + " microsec.");
        } catch (GraphColoringException gce) {
            System.out.println(gce);
        }

        System.out.println("\nAPP STOPPED.");
    }
}
