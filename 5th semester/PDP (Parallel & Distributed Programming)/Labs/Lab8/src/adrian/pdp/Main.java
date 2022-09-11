package adrian.pdp;

import mpi.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static void main(String[] args) {
        MPI.Init(args);

        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (me == 0) {
            mainWorker(size);
        }
        else {
            childWorker(me);
        }

        MPI.Finalize();
    }

    private static void mainWorker(int mpiSize) {
        System.out.println("MAIN");

        //generate subscriptions
        boolean[] subs = getRandomBooleanArray(mpiSize);
        boolean[] subs2 = getRandomBooleanArray(mpiSize);

        System.out.println("MAIN: subs= " + Arrays.toString(subs));
        System.out.println("MAIN: subs2= " + Arrays.toString(subs2));

        //process changes
        while (true) {
            long[] changes = new long[5];
            MPI.COMM_WORLD.Recv(changes, 0, changes.length, MPI.LONG, MPI.ANY_SOURCE, MPI.ANY_TAG);

            long mpiChild = changes[0];
            long change = changes[1];
            boolean first = (changes[2] == 0);
            long value = changes[3];
            long time = changes[4];

            System.out.println("Main: received from " + mpiChild + " (change " + change + "), " + "var" + (first ? "" : "2") + "= " + value + " and time= " + time);

            for (int me = 1; me < mpiSize; me++) {
                if ( me != mpiChild && (first ? subs[me] : subs2[me]) ) {
                    System.out.println("Main: sent to " + me + ", var" + (first ? "" : "2") + "= " + value + " and time= " + time);
                    MPI.COMM_WORLD.Send(changes, 2, changes.length - 2, MPI.LONG, me, 0);
                }
            }
        }
    }

    private static void childWorker(int mpiMe) {
        //make sure main host is set up
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        System.out.println("CHILD: " + mpiMe);

        AtomicLong var = new AtomicLong(0L);
        AtomicLong var2 = new AtomicLong(0L);
        AtomicLong time = new AtomicLong(System.nanoTime());

        Thread thread = new Thread(() -> childChangePerformer(mpiMe, var, var2, time));
        thread.start();

        while (true) {
            long[] data = new long[3];
            MPI.COMM_WORLD.Recv(data, 0, data.length, MPI.LONG, 0, MPI.ANY_TAG);

            boolean first = (data[0] == 0);
            long value = data[1];
            long current = data[2];

            System.out.println(mpiMe + ": received var" + (first ? "" : "2") + "= " + value+ " and time= " + current);

            if (first)
                var.set(value);
            else
                var2.set(value);

            if (time.get() < current) {
                System.out.println(mpiMe + ": updated time= " + current + " (before " + time.get() + ")");
                time.set(current);
            }
        }
    }

    private static void childChangePerformer(int mpiMe, AtomicLong var, AtomicLong var2, AtomicLong time) {
        //initialise
        final int CHANGES = 3;

        //perform changes
        for (int change = 0; change < CHANGES; change++) {
            long value = new Random().nextLong() % 1000;
            boolean first = new Random().nextBoolean();
            long current = System.nanoTime();
            time.set(current);

            if (first)
                var.set(value);
            else
                var2.set(value);

            long[] data = new long[]{mpiMe, change, (first ? 0L : 1L), value, current};
            System.out.println(mpiMe + ": sent var" + (first ? "" : "2") + "= " + value + " (change " + change + ")");
            MPI.COMM_WORLD.Send(data, 0, data.length, MPI.LONG, 0, 0);
        }
    }

    private static boolean[] getRandomBooleanArray(int length) {
        boolean[] array = new boolean[length];

        for (int index = 0; index < length; index++) {
            array[index] = (Math.random() >= 0.5);
        }

        return array;
    }
}
