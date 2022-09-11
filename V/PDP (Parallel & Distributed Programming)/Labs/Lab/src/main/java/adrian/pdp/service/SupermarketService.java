package adrian.pdp.service;

import adrian.pdp.exception.SupermarketException;
import adrian.pdp.model.Bill;
import adrian.pdp.model.BillItem;
import adrian.pdp.model.Product;
import adrian.pdp.model.Sales;
import adrian.pdp.repository.BillRepository;
import adrian.pdp.repository.Repository;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class SupermarketService {
    private Sales sales;
    private Repository<Bill> billRepository;
    private Repository<Product> productRepository;

    public SupermarketService() {
        sales = new Sales();
    }

    public synchronized void processBill(Bill bill) {
        if (bill.isProcessed()) {
            throw new SupermarketException("Bill already processed..");
        }

        bill.setTotal(0);

        for (BillItem billItem :
                bill.getBillItems()) {
            Product product = billItem.getProduct();

            if (product.getUnits() < billItem.getQuantity()) {
                throw new SupermarketException(
                        String.format("Product %s: required %d units when there are only %d left", product.getName(), billItem.getQuantity(), product.getUnits()));
            }

            product.setUnits(
                    product.getUnits() - billItem.getQuantity());
            sales.setAmount(
                    sales.getAmount() + product.getPricePerUnit() * billItem.getQuantity());
            bill.setTotal(bill.getTotal() + product.getPricePerUnit() * billItem.getQuantity());
        }

        bill.setProcessed(true);
    }

    public synchronized void checkInventory() {
        Sales testSales = new Sales();

        for (Bill bill :
                billRepository.getItems()) {
            if (bill.isProcessed()) {
                testSales.setAmount(testSales.getAmount() + bill.getTotal());
            }
        }

        if (Math.abs(testSales.getAmount() - sales.getAmount()) >= 0.1) {
            throw new SupermarketException(
                    String.format("Inventory check failed: accounted amount is %.2f, when in fact it should be %.2f", sales.getAmount(), testSales.getAmount()));
        }

        System.out.println(
                String.format("Inventory check passed: accounted amount is %.2f", sales.getAmount()));
    }
}
