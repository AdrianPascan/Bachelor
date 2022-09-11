package adrian.pdp;

import adrian.pdp.graph.DirectedGraph;
import adrian.pdp.graph.Hamiltonian;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("APP STARTED...\n");

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

        // performance
        System.out.println("NO. OF THREADS :  TIME (ms)");
        System.out.println();

        for (int threadsNo = 0; threadsNo < 30; threadsNo++) {
            long tic = System.nanoTime();
            List<Integer> solution = Hamiltonian.cycle(graph, threadsNo);
            long tac = System.nanoTime();
            long time = tac - tic;

            System.out.println(String.format("%d :  %d", threadsNo, time / 1000));
            System.out.println("\t" + solution);
            System.out.println();
        }

        System.out.println("\nAPP STOPPED.");
    }
}
