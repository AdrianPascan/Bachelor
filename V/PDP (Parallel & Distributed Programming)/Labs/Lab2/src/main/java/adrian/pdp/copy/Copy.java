//package adrian.pdp.copy;
//
//import adrian.pdp.exception.MyException;
//import lombok.*;
//
//import java.util.Vector;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//import java.util.stream.IntStream;
//
//public class Copy {
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Getter
//    @Setter
//    @Builder
//    public class ConsumerThread extends Thread {
//        private Integer product;
//        private Boolean sent;
//        private boolean done;
//        private Lock mutex;
//        private Condition condition;
//
//        @Override
//        public void run() {
//            super.run();
//
//            try {
//                int sum = 0;
//
//                mutex.lock();
//
//                while(!done) {
//                    if (!sent) {
//                        condition.await();
//                    }
//                    System.out.println(
//                            String.format("CONSUMER: received product=%d; sent=%b time=%d", product, sent, System.nanoTime()));
//                    if (product != null) {
//                        sum += product;
//                    } else {
//                        done = true;
//                        System.out.println("CONSUMER: done");
//                    }
//                    if (!done) {
//                        condition.signal();
//                    }
//                }
//            } catch (InterruptedException exception) {
//                exception.printStackTrace();
//                this.interrupt();
//            }
//            finally {
//                mutex.unlock();
//            }
//        }
//    }
//
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Getter
//    @Setter
//    @Builder
//    public class ProducerThread extends Thread {
//        private Vector<Integer> firstVector;
//        private Vector<Integer> secondVector;
//        private Integer product;
//        private Boolean sent;
//        private Lock mutex;
//        private Condition condition;
//
//        @Override
//        public void run() {
//            super.run();
//
//            if (firstVector.size() != secondVector.size()) {
//                throw new MyException(
//                        String.format("Vector sizes are different: %d and %d", firstVector.size(), secondVector.size()));
//            }
//
//            try {
//                mutex.lock();
//
//                sleep(10000);
//
//                IntStream.range(0, firstVector.size())
//                        .forEach(index -> {
//                            try {
//                                if (sent) {
//                                    condition.await();
//                                }
//
//                                product = firstVector.get(index) * secondVector.get(index);
////                            System.out.println("PRODUCER prod=" + product);
//                                sent = true;
//                                System.out.println(
//                                        String.format("PRODUCER: sent product=%d; sent=%b time=%d", product, sent, System.nanoTime()));
//
//                                condition.signal();
//                            } catch (InterruptedException exception) {
//                                exception.printStackTrace();
//                                this.interrupt();
//                            }
//                        });
//
//                if (sent) {
//                    condition.await();
//                }
//                product = null;
//                sent = true;
//                condition.signal();
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                mutex.unlock();
//            }
//        }
//    }
//
//}
