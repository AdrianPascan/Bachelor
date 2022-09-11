package adrian.pdp;

import mpi.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        MPI.Init(args);

        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (me == 0) {
            int[] result = primes(new int[]{1,2,3,4,5}, new int[]{4,5,6,7,8}, size);
//            int[] result = primes(new int[]{1,2}, new int[]{4,5}, size);
            System.out.println("RESULT=" + Arrays.toString(result));
        }
        else {
            worker(me, size);
        }

        MPI.Finalize();
    }

    private static final int elementsTag = 1;

    public static int[] primes(final int[] a, final int[] b, int nrProcs) {
        if (a.length != b.length) {
            throw new RuntimeException("Vectors differ in length: " + a.length + "!=" + b.length);
        }

        MPI.COMM_WORLD.Bcast(new int[]{a.length}, 0, 1, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(a, 0, a.length, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(b, 0, a.length, MPI.INT, 0);

        int[] result = new int[a.length];
        for (int k = 0; k < a.length; k += nrProcs) {
            result[k] = convolveForK(a, b, k);
        }

        for (int proc = 1; proc < nrProcs && proc < a.length; proc++) {
            int elementsNo = a.length / nrProcs;
            if (a.length % nrProcs >= proc) {
                elementsNo++;
            }

            int[] elements = new int[elementsNo];
            MPI.COMM_WORLD.Recv(elements, 0, elementsNo, MPI.INT, proc, elementsTag);

            for (int index = 0; index < elementsNo; index++) {
                result[index * (a.length / nrProcs) + proc] = elements[index];
            }
        }

        return result;
    }

    public static void worker(int myId, int nrProcs) {
        int[] metadata = new int[1];
        MPI.COMM_WORLD.Bcast(metadata, 0, 1, MPI.INT, 0);
        int length = metadata[0];

        int[] a = new int[length];
        int[] b = new int[length];
        MPI.COMM_WORLD.Bcast(a, 0, length, MPI.INT, 0);
        MPI.COMM_WORLD.Bcast(b, 0, length, MPI.INT, 0);

        if (a.length <= myId) { //no work for this process
            return;
        }

        int partialElementsNo = a.length / nrProcs;
        if (a.length % nrProcs >= myId) {
            partialElementsNo++;
        }

        int[] partialElements = new int[partialElementsNo];
        for (int index = 0; index < partialElementsNo; index++) {
            int k = (a.length / nrProcs) * index + myId;
            partialElements[index] = convolveForK(a, b, k);
        }

        MPI.COMM_WORLD.Send(partialElements, 0, partialElementsNo, MPI.INT, 0, elementsTag);
    }

    public static int convolveForK(final int[] a, final int[] b, int k){
        int result = 0;
        int index = 0;
        while (index < a.length && k - index >= 0) {
            result += a[index] * b[k - index];
            index++;
        }

        return result;
    }
}
