package adrian.pdp;

import adrian.pdp.concurrent.SupermarketThread;
import adrian.pdp.model.Bill;
import adrian.pdp.model.BillItem;
import adrian.pdp.model.Product;
import adrian.pdp.repository.BillRepository;
import adrian.pdp.repository.ProductRepository;
import adrian.pdp.repository.Repository;
import adrian.pdp.service.SupermarketService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("APP STARTED...\n");

        Random random = new Random();
        double averageNoOfProductsPerBill = 0.0;
        long startTime = 0;
        long stopTime = 0;
        long elapsedTime = 0;

        // POPULATE Product Repository
        int noOfProducts = 10 + random.nextInt(91); // 10 - 100
        List<Product> products = IntStream.range(0, noOfProducts)
                .mapToObj(index -> new Product(
                        "product" + index,
                        (5 + random.nextInt(995)) / 10.0, // 0.5 - 99.9
                        10000 + random.nextInt(101)) // 100 - 200
                )
                .collect(Collectors.toList());

        // OUTPUT Products
        System.out.println("PRODUCTS (" + noOfProducts + ")");
        products.forEach(System.out::println);
        System.out.println("\n");

        // POPULATE Bill Repository
        int noOfBills = 1 + random.nextInt(10); // 1 - 10
        List<Bill> bills = IntStream.range(0, noOfBills)
                .mapToObj(billIndex -> {
                    Bill bill = new Bill();

                    bill.setBillNo(billIndex);

                    int noOfBillItems = 1 + random.nextInt(10); // 1 - 10
                    List<Integer> productIndexes = new ArrayList<>();
                    List<BillItem> billItems =
                            IntStream.range(0, noOfBillItems)
                                    .mapToObj(billItemIndex -> {
                                        BillItem billItem = new BillItem();

                                        int productIndex = random.nextInt(noOfProducts);
                                        while (productIndexes.contains(productIndex)) {
                                            productIndex = random.nextInt(noOfProducts);
                                        }
                                        productIndexes.add(productIndex);
                                        billItem.setProduct(products.get(productIndex));

                                        billItem.setQuantity(1 + random.nextInt(10));

                                        return billItem;
                                    })
                                    .collect(Collectors.toList());

                    bill.setBillItems(billItems);

                    return bill;
                })
                .collect(Collectors.toList());

        // OUTPUT Bills
        System.out.println("BILLS (" + noOfBills + ")");
        bills.forEach(System.out::println);
        System.out.println("\n");

        // COMPUTE Average No. of Products per Bill
        int noOfProductsInBills = bills.stream()
                        .map(bill -> bill.getBillItems().size())
                        .reduce(0, (totalNoOfProducts, currentNoOfProducts) -> totalNoOfProducts += currentNoOfProducts);
        averageNoOfProductsPerBill = (double) noOfProductsInBills / noOfBills;

        // REPOSITORIES
        Repository<Product> productRepository = new ProductRepository();
        Repository<Bill> billRepository = new BillRepository();
        productRepository.addItems(products);
        billRepository.addItems(bills);

        // SERVICES
        SupermarketService supermarketService = new SupermarketService();
        supermarketService.setProductRepository(productRepository);
        supermarketService.setBillRepository(billRepository);

        // THREADS
        List<SupermarketThread> supermarketThreads = new ArrayList<>();
        List<Integer> unassignedBillIndexes = IntStream.range(0, noOfBills)
                .boxed()
                .collect(Collectors.toList());
        int noOfUnassignedBills = noOfBills;
        while (noOfUnassignedBills > 0) {
            int noOfBillsToBeAssigned = 1 + random.nextInt(noOfUnassignedBills); // 1 - noOfUnassignedBills

            if (noOfBillsToBeAssigned == noOfUnassignedBills) {
                List<Bill> supermarketThreadBills = unassignedBillIndexes.stream()
                        .map(bills::get)
                        .collect(Collectors.toList());
                SupermarketThread supermarketThread = new SupermarketThread(supermarketThreadBills, supermarketService);
                supermarketThreads.add(supermarketThread);
            }
            else {
                List<Bill> supermarketThreadBills = IntStream.range(0, noOfBillsToBeAssigned)
                        .mapToObj(billNo -> {
                            int unassignedBillIndex = random.nextInt(noOfBills);
                            while (!unassignedBillIndexes.contains(unassignedBillIndex)) {
                                unassignedBillIndex = random.nextInt(noOfBills);
                            }
                            unassignedBillIndexes.remove(Integer.valueOf(unassignedBillIndex));

                            return bills.get(unassignedBillIndex);
                        })
                        .collect(Collectors.toList());

                SupermarketThread supermarketThread = new SupermarketThread(supermarketThreadBills, supermarketService);
                supermarketThreads.add(supermarketThread);
            }

            noOfUnassignedBills -= noOfBillsToBeAssigned;
        }
        int noOfSupermarketThreads = supermarketThreads.size();

        // OUTPUT Threads
        System.out.println("THREADS (" + noOfSupermarketThreads + ")");
        supermarketThreads.forEach(System.out::println);
        System.out.println("\n");

        // LAUNCH Threads
        startTime = System.nanoTime();

        for (SupermarketThread supermarketThread :
                supermarketThreads) {
            supermarketThread.start();
        }

        for (SupermarketThread supermarketThread :
                supermarketThreads) {
            supermarketThread.join();
        }

        stopTime = System.nanoTime();
        elapsedTime = stopTime - startTime;

        // MAIN Inventory Check
        System.out.println("MAIN INVENTORY CHECK: ");
        supermarketService.checkInventory();
        System.out.println("\n");

        // OUTPUT Elapsed Time
        System.out.println("NO. OF THREADS: "  + noOfSupermarketThreads);
        System.out.println("ELAPSED TIME: " + elapsedTime / 1000000 + "\n");

        // OUTPUT Average No. of Products per Bill
        System.out.println("NO. OF PRODUCTS: " + noOfProducts);
        System.out.println("NO. OF BILLS: " + noOfBills);
        System.out.println("AVERAGE NO. OF PRODUCTS PER BILL: " + averageNoOfProductsPerBill + "\n");

        System.out.println("APP STOPPED.");
    }
}
