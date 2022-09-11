package adrian.pdp;

import adrian.pdp.graph.ColorsGraph;
import adrian.pdp.graph.DirectedGraph;
import adrian.pdp.graph.GraphColoring;
import adrian.pdp.graph.GraphColoringException;
import mpi.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        MPI.Init(args);

        //MPI param.'s
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

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

        if (me == 0) { //main process
            System.out.println("MAIN");

            try {
                long tic = System.nanoTime();

                System.out.println(GraphColoring.graphColoringMain(size, graph, colors));

                long tac = System.nanoTime();
                long time = tac - tic;

                System.out.println("Time: " + (time / 1000) + " microsec.");
            } catch (GraphColoringException gce) {
                System.out.println(gce);
            }
        }
        else { //child process
            System.out.println("Process " + me);

            int codesNo = colors.getColorsNo();
            GraphColoring.graphColoringWorker(me, size, graph, codesNo);
        }

        MPI.Finalize();
    }
}
