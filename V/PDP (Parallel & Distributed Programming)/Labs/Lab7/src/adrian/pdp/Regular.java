package adrian.pdp;

import mpi.MPI;

import java.util.Arrays;

public class Regular {
    public static int[] polyMul(int size, int[] first, int[] second, int mpiSize) {
        return polyMulRec(size, first, second, 0, mpiSize, 0);
    }

    public static int[] polyMulRec(int size, int[] first, int[] second, int mpiMe, int mpiSize, int power) {
        //MPI destinations
        int coefficient = (int) Math.pow(4, power);
        int firstDest = mpiMe + coefficient;
        int secondDest = mpiMe + coefficient * 2;
        int thirdDest = mpiMe + coefficient * 3;

        if (size <= 1) {
            //stop MPI child hosts
            int[] returnMetadata = new int[]{-1, -1, -1};
            if (firstDest < mpiSize) {
                MPI.COMM_WORLD.Send(returnMetadata, 0, returnMetadata.length, MPI.INT, firstDest, 0);
            }
            if (secondDest < mpiSize) {
                MPI.COMM_WORLD.Send(returnMetadata, 0, returnMetadata.length, MPI.INT, secondDest, 0);
            }
            if (thirdDest < mpiSize) {
                MPI.COMM_WORLD.Send(returnMetadata, 0, returnMetadata.length, MPI.INT, thirdDest, 0);
            }

            if (size <= 0) {
                return new int[]{0};
            }
            return new int[]{first[0] * second[0]};
        }

        int middle = size / 2;
        int[] metadata = new int[]{mpiMe, middle, power + 1};

        //split polynomials
        int[] firstLower = Common.getSubarray(first, 0, middle);
        int[] firstUpper = Common.getSubarray(first, middle, size);
        int[] secondLower = Common.getSubarray(second, 0, middle);
        int[] secondUpper = Common.getSubarray(second, middle, size);

        //multiplications
        int[] secondMul = null;
        int[] thirdMul = null;
        int[] fourthMul = null;

        if (firstDest < mpiSize) {
            MPI.COMM_WORLD.Send(metadata, 0, metadata.length, MPI.INT, firstDest, 0);

            MPI.COMM_WORLD.Send(firstUpper, 0, middle, MPI.INT, firstDest, 0);
            MPI.COMM_WORLD.Send(secondLower, 0, middle, MPI.INT, firstDest, 0);
        }
        else {
            secondMul = polyMulRec(middle, firstUpper, secondLower, firstDest, mpiSize, power + 1);
        }
        if (secondDest < mpiSize) {
            MPI.COMM_WORLD.Send(metadata, 0, metadata.length, MPI.INT, secondDest, 0);

            MPI.COMM_WORLD.Send(firstLower, 0, middle, MPI.INT, secondDest, 0);
            MPI.COMM_WORLD.Send(secondUpper, 0, middle, MPI.INT, secondDest, 0);
        }
        else {
            thirdMul = polyMulRec(middle, firstLower, secondUpper, secondDest, mpiSize, power + 1);
        }
        if (thirdDest < mpiSize) {
            MPI.COMM_WORLD.Send(metadata, 0, metadata.length, MPI.INT, thirdDest, 0);

            MPI.COMM_WORLD.Send(firstLower, 0, middle, MPI.INT, thirdDest, 0);
            MPI.COMM_WORLD.Send(secondLower, 0, middle, MPI.INT, thirdDest, 0);
        }
        else {
            fourthMul = polyMulRec(middle, firstLower, secondLower, thirdDest, mpiSize, power + 1);
        }
        int[] firstMul = polyMulRec(middle, firstUpper, secondUpper, mpiMe, mpiSize, power + 1);

        //receive multiplication results from child hosts
        if (firstDest < mpiSize) {
            int[] mulSize = new int[1];
            MPI.COMM_WORLD.Recv(mulSize, 0, mulSize.length, MPI.INT, firstDest, MPI.ANY_TAG);

            secondMul = new int[mulSize[0]];
            MPI.COMM_WORLD.Recv(secondMul, 0, mulSize[0], MPI.INT, firstDest, MPI.ANY_TAG);
        }
        if (secondDest < mpiSize) {
            int[] mulSize = new int[1];
            MPI.COMM_WORLD.Recv(mulSize, 0, mulSize.length, MPI.INT, secondDest, MPI.ANY_TAG);

            thirdMul = new int[mulSize[0]];
            MPI.COMM_WORLD.Recv(thirdMul, 0, mulSize[0], MPI.INT, secondDest, MPI.ANY_TAG);
        }
        if (thirdDest < mpiSize) {
            int[] mulSize = new int[1];
            MPI.COMM_WORLD.Recv(mulSize, 0, mulSize.length, MPI.INT, thirdDest, MPI.ANY_TAG);

            fourthMul = new int[mulSize[0]];
            MPI.COMM_WORLD.Recv(fourthMul, 0, mulSize[0], MPI.INT, thirdDest, MPI.ANY_TAG);
        }

        //terms
        int[] firstTerm = Common.addToArrayAtTheBeginning(firstMul, 0, size);
        int[] secondTerm = Common.addToArrayAtTheBeginning(
                Common.addArrays(secondMul, thirdMul),
                0,
                middle
        );
        int[] thirdTerm = fourthMul;

        return Common.addArrays(
                firstTerm,
                Common.addArrays(
                        secondTerm,
                        thirdTerm
                )
        );
    }

    public static void polyMulWorker(int mpiMe) {
        //RECV
        //metadata
        int[] metadata = new int[3];
        MPI.COMM_WORLD.Recv(metadata, 0, 3, MPI.INT, MPI.ANY_SOURCE, MPI.ANY_TAG);

        int parent = metadata[0];
        int size = metadata[1];
        int power = metadata[2];

        //stop if result already computed
        if (parent == -1) {
            return;
        }

        //polynomials
        int[] first = new int[size];
        int[] second = new int[size];

        MPI.COMM_WORLD.Recv(first, 0, size, MPI.INT, MPI.ANY_SOURCE, MPI.ANY_TAG);
        MPI.COMM_WORLD.Recv(second, 0, size, MPI.INT, MPI.ANY_SOURCE, MPI.ANY_TAG);

        //SEND
        int[] mul = polyMulRec(size, first, second, mpiMe, MPI.COMM_WORLD.Size(), power);
        int[] mulSize = new int[]{mul.length};

        MPI.COMM_WORLD.Send(mulSize, 0, mulSize.length, MPI.INT, parent, 0);
        MPI.COMM_WORLD.Send(mul, 0, mul.length, MPI.INT, parent, 0);
    }
}
