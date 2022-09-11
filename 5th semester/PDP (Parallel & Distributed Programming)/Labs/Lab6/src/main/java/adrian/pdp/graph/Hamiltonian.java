package adrian.pdp.graph;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Hamiltonian {

    public static List<Integer> cycle(DirectedGraph graph, int threadsNo) {
        Vector<Integer> cycle = new Vector<>();

        hasCycleRec(
                graph,
                new AtomicInteger(threadsNo),
                new Vector<>(),
                new ReentrantLock(),
                cycle
        );

        return cycle.isEmpty() ? null : cycle;
    }

    private static void hasCycleRec(DirectedGraph graph, AtomicInteger threadsNo, Vector<Integer> partialCycle, Lock lock, Vector<Integer> cycle) {
        if (!cycle.isEmpty()) {
            return;
        }

        // is solution?
        if (partialCycle.size() == graph.getNodesNo()) {
            int firstNode = partialCycle.get(0);
            int lastNode = partialCycle.get(partialCycle.size() - 1);
            if (graph.isVertex(lastNode, firstNode)) {
                lock.lock();

                if (cycle.isEmpty()) {
                    cycle.addAll(partialCycle);
                    cycle.add(partialCycle.get(0));
                }

                lock.unlock();
            }

            return;
        }

        // candidate nodes
        Vector<Integer> nextNodes = new Vector<>();
        if (partialCycle.isEmpty()) {
            nextNodes.addAll(
                    IntStream.range(0, graph.getNodesNo())
                            .boxed()
                            .collect(Collectors.toList())
            );
        }
        else {
            int lastNode = partialCycle.get(partialCycle.size() - 1);

            for (int node = 0; node < graph.getNodesNo(); node++) {
                if (!partialCycle.contains(node) && graph.isVertex(lastNode, node)) {
                    nextNodes.add(node);
                }
            }
        }

        // threads
        List<Thread> threads = new ArrayList<>();

        int index = 0;
        while (index < nextNodes.size() && threadsNo.getAndDecrement() > 0) {
            Vector<Integer> newPartialCycle = new Vector<>(partialCycle);
            newPartialCycle.add(nextNodes.get(index));

            Thread thread = new Thread(() -> hasCycleRec(graph, threadsNo, newPartialCycle, lock, cycle));
            threads.add(thread);

            index++;
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        // sequential
        while (index < nextNodes.size()) {
            Vector<Integer> newPartialCycle = new Vector<>(partialCycle);
            newPartialCycle.add(nextNodes.get(index));
            hasCycleRec(graph, threadsNo, newPartialCycle, lock, cycle);

            index++;
        }
    }
}
