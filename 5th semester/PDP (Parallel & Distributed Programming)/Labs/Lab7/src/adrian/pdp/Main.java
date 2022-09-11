package adrian.pdp;

import mpi.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        MPI.Init(args);

        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        System.out.println("Hello world from <"+me+"> of <"+size+">");

        if (me == 0) {
            polyMul(size);
        }
        else {
            polyMulWorker(me);
        }

        MPI.Finalize();
    }

    private static void polyMul(int mpiSize) {
        //examples
        int[][] firsts = new int[][] {
                new int[]{1},
                new int[]{2, 1},
                new int[]{4, 3, 2, 1},
                new int[]{8, 7, 6, 5, 4, 3, 2, 1}
        };

        int[][] seconds = new int[][] {
                new int[]{2},
                new int[]{4, 3},
                new int[]{8, 7, 6, 5},
                new int[]{16, 15, 14, 13, 12, 11, 10, 9}
        };

        int[][] results = new int[][] {
                new int[]{2},
                new int[]{8, 10, 3},
                new int[]{32, 52, 61, 60, 34, 16, 5},
                new int[]{128, 232, 313, 372, 410, 428, 427, 408, 308, 224, 155, 100, 58, 28, 9}
        };


        //run example
        final int EXAMPLE_INDEX = 1;

        long tic = 0;
        long tac = 0;
        int size = firsts[EXAMPLE_INDEX].length;

        tic = System.nanoTime();
//        int[] result = Regular.polyMul(size, firsts[EXAMPLE_INDEX], seconds[EXAMPLE_INDEX], mpiSize);
        int[] result = Karatsuba.polyMul(size, firsts[EXAMPLE_INDEX], seconds[EXAMPLE_INDEX], mpiSize);
        tac = System.nanoTime();

        System.out.println("FIRST= " + Arrays.toString(firsts[EXAMPLE_INDEX]));
        System.out.println("SECOND= " + Arrays.toString(seconds[EXAMPLE_INDEX]));
        System.out.println("RESULT:\n" +
                "\texpected= " + Arrays.toString(results[EXAMPLE_INDEX]) + "\n" +
                "\tactual= " + Arrays.toString(result));
        System.out.println("TIME= " + ((tac-tic) / 1000) + " microsec.");
    }

    private static void polyMulWorker(int mpiMe) {
//        Regular.polyMulWorker(mpiMe);
        Karatsuba.polyMulWorker(mpiMe);
    }
}
