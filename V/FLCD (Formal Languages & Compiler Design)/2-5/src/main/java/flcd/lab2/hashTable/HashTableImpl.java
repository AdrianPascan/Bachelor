package flcd.lab2.hashTable;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@ToString
@Getter
public class HashTableImpl implements HashTable {
    private int capacity;
    private int noOfElements;
    private List<String> elements;

    public HashTableImpl() {
        capacity = 11;
        noOfElements = 0;
        elements = new ArrayList<>(Collections.nCopies(capacity, null));
    }

    @Override
    public int add(String element) {
        int probeIndex = 0;
        while(probeIndex < capacity) {
            int positionInHT = hash(element, capacity, probeIndex);
            String elementInHT = elements.get(positionInHT);
            if (elementInHT == null) {
                elements.set(positionInHT, element);
                break;
            }
            probeIndex++;
        }

        if (probeIndex == capacity) {
            // RESIZE + REHASH
            int newCapacity = nextCapacity(capacity);
            List<String> newElements = new ArrayList<>(Collections.nCopies(newCapacity, null));

            IntStream.range(0, capacity)
                    .forEach(index -> {
                        String oldElement = elements.get(index);
                        if (oldElement != null) {
                            for (int newProbeIndex = 0; newProbeIndex < capacity; newProbeIndex++) {
                                int newPositionInHT = hash(oldElement, newCapacity, newProbeIndex);
                                String newElementInHT = newElements.get(newPositionInHT);
                                if (newElementInHT == null) {
                                    newElements.set(newPositionInHT, oldElement);
                                    break;
                                }
                            }
                        }
                    });

            capacity = newCapacity;
            elements = newElements;

            probeIndex = 0;
            while(probeIndex < capacity) {
                int positionInHT = hash(element, capacity, probeIndex);
                String elementInHT = elements.get(positionInHT);
                if (elementInHT == null) {
                    elements.set(positionInHT, element);
                    break;
                }
                probeIndex++;
            }
        }

        noOfElements++;
        return hashCode(element);
    }

    @Override
    public int remove(String element) {
        for (int probeIndex = 0; probeIndex < capacity; probeIndex++) {
            int positionInHT = hash(element, capacity, probeIndex);
            String elementInHT = elements.get(positionInHT);
            if (elementInHT != null && elementInHT.equals(element) ) {
                elements.set(positionInHT, null);
                noOfElements--;
                return hashCode(element);
            }
        }
        return -1;
    }

    @Override
    public int search(String element) {
        for (int probeIndex = 0; probeIndex < capacity; probeIndex++) {
            int positionInHT = hash(element, capacity, probeIndex);
            String elementInHT = elements.get(positionInHT);
            if (elementInHT != null && elementInHT.equals(element) ) {
                return hashCode(element);
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return noOfElements;
    }

    @Override
    public boolean isEmpty() {
        return noOfElements == 0;
    }

    private static int hash(String element, int capacity, int probeIndex) {
        int code = hashCode(element);
        int hashValue = code % capacity;
        int hashValue2 = 1 + (code % (capacity - 1));

        return (hashValue + probeIndex * hashValue2) % capacity;
    }

    private static int hashCode(String element) {
        int code = element.hashCode();
        if (code < 0) {
            code = -code;
        }
        return code;
    }

    private static int nextCapacity(int capacity) {
        int newCapacity = capacity * 2 + 1;
        while (!isPrime(newCapacity)) {
            newCapacity += 2;
        }
        return newCapacity;
    }

    private static boolean isPrime(int no) {
        if (no < 2 || (no != 2 && no % 2 == 0)) {
            return false;
        }
        for (int oddDivisor = 3; oddDivisor * oddDivisor <= no; oddDivisor += 2) {
            if (no % oddDivisor == 0) {
                return false;
            }
        }

        return true;
    }

    public String toPrintable() {
        return "Hash Table: \n[" +
                "\n\t capacity= " + capacity +
                "\n\t noOfElements= " + noOfElements +
                "\n\t elements=" + elements +
                "\n]";
    }

    public String toPrintable(int tabNo) {
        return "\t".repeat(tabNo) + "Hash Table: \n" +
                "\t".repeat(tabNo) + "[" +
                "\n" + "\t".repeat(tabNo + 1) + "capacity= " + capacity +
                "\n" + "\t".repeat(tabNo + 1) + "noOfElements= " + noOfElements +
                "\n" + "\t".repeat(tabNo + 1) + "elements=" + elements +
                "\n" + "\t".repeat(tabNo) + "]";
    }
}
