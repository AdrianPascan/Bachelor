package adrian.pdp;

import java.util.Arrays;

public class Common {
    public static int[] getSubarray(int[] array, int startInclusive, int endExclusive) {
        return Arrays.copyOfRange(array, startInclusive, endExclusive);
    }

    public static int[] addToArrayAtTheBeginning(int[] array, int value, int count) {
        int[] newArray = new int[array.length + count];

        for (int index = 0; index < count; index++) {
            newArray[index] = value;
        }
        for (int index = 0; index < array.length; index++) {
            newArray[index + count] = array[index];
        }

        return newArray;
    }

    public static int[] addArrays(int[] first, int[] second) {
        int[] newArray = new int[Math.max(first.length, second.length)];

        for (int index = 0; index < newArray.length; index++) {
            newArray[index] =
                    (index < first.length ? first[index] : 0) +
                            (index < second.length ? second[index] : 0);
        }

        return newArray;
    }

    public static int[] subtractArrays(int[] first, int[] second) {
        int[] newArray = new int[Math.max(first.length, second.length)];

        for (int index = 0; index < newArray.length; index++) {
            newArray[index] =
                    (index < first.length ? first[index] : 0) -
                            (index < second.length ? second[index] : 0);
        }

        //check if polynomial order got smaller
        int index = newArray.length - 1;
        while (index > 0 && newArray[index] == 0) {
            index--;
        }

        // no
        if (index == newArray.length - 1) {
            return newArray;
        }

        // yes, trim the array
        int[] subarray = new int[index + 1];
        for (int subindex = 0; subindex <= index; subindex++) {
            subarray[subindex] = newArray[index];
        }
        return subarray;
    }
}
