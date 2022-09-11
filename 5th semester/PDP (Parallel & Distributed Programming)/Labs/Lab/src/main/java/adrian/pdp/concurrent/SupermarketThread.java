package adrian.pdp.concurrent;

import adrian.pdp.model.Bill;
import adrian.pdp.service.SupermarketService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Getter
@Setter
public class SupermarketThread extends Thread {
    private List<Bill> bills;
    private SupermarketService supermarketService;

    public SupermarketThread() {
        bills = new ArrayList<>();
    }

    @Override
    public void run() {
        super.run();

        Random random = new Random();

        for (Bill bill :
                bills) {
            supermarketService.processBill(bill);

            if (random.nextBoolean()) {
                supermarketService.checkInventory();
            }
        }
    }

    @Override
    public String toString() {
        return "SupermarketThread(" +
                "noOfBills=" + bills.size() +
                ", bills=[" +
                bills.stream()
                        .map(bill -> Integer.toString(bill.getBillNo()))
                        .reduce("", (accumulator, current) -> accumulator + "billNo." + current + " ") +
                "]" +
                ", supermarketService=" + supermarketService +
                ") " +
                super.toString();
    }
}
