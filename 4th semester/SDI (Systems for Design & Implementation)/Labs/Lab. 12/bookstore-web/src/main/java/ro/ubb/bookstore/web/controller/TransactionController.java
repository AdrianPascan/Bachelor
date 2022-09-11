package ro.ubb.bookstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.ubb.bookstore.core.model.CustomerBook;
import ro.ubb.bookstore.core.model.validators.BookStoreException;
import ro.ubb.bookstore.core.service.TransactionService;
import ro.ubb.bookstore.web.converter.CustomerBookConverter;
import ro.ubb.bookstore.web.dto.CustomerBookDto;

import java.util.Set;

@RestController
public class TransactionController {
    public static final Logger log= LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerBookConverter customerBookConverter;


    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public Set<CustomerBookDto> getTransactions() {
        log.trace("getTransactions --- method entered");

        Set<CustomerBookDto> customerBookDtos = (Set<CustomerBookDto>) customerBookConverter.convertModelsToDtos(transactionService.getAllTransactions());

        log.trace("getTransactions --- method finished: customerBookDtos={}", customerBookDtos);
        return customerBookDtos;
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    @Transactional
    public CustomerBookDto saveTransaction(@RequestBody final CustomerBookDto customerBookDto) {
        log.trace("saveTransaction --- method entered: customerBookDto={}", customerBookDto);

        CustomerBook customerBook = customerBookConverter.convertDtoToModel(customerBookDto);
        CustomerBookDto savedCustomerBookDto = null;
        try {
            CustomerBook savedCustomerBook = transactionService.getTransaction(customerBook.getCustomer().getId(), customerBook.getBook().getId());
            customerBook.setQuantity(customerBook.getQuantity() + savedCustomerBook.getQuantity());
            savedCustomerBookDto = customerBookConverter.convertModelToDto(
                    transactionService.updateTransaction(customerBook));
        }
        catch (BookStoreException exception) {
            savedCustomerBookDto = customerBookConverter.convertModelToDto(
                    transactionService.saveTransaction(customerBook));
        }

        log.trace("saveTransaction --- method finished: customerBookDto={}", savedCustomerBookDto);
        return savedCustomerBookDto;
    }

    @RequestMapping(value = "/transactions/{customerId}/{bookId}", method = RequestMethod.PUT)
    @Transactional
    public CustomerBookDto updateTransaction(@PathVariable final Long customerId,
                                             @PathVariable final Long bookId,
                                             @RequestBody final CustomerBookDto customerBookDto) {
        log.trace("updateTransaction --- method entered: customerId={}, bookId={}, customerBookDto={}", customerId, bookId, customerBookDto);

        CustomerBookDto updatedCustomerBookDto = customerBookConverter.convertModelToDto(
                transactionService.updateTransaction(customerBookConverter.convertDtoToModel(customerBookDto)));

        log.trace("updateTransaction --- method finished: customerBookDto={}", updatedCustomerBookDto);
        return updatedCustomerBookDto;
    }

    @RequestMapping(value = "/transactions/{customerId}/{bookId}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> deleteTransaction(@PathVariable final Long customerId,
                                               @PathVariable final Long bookId){
        log.trace("deleteTransaction --- method entered: customerId={}, bookId={}", customerId, bookId);

        transactionService.deleteTransaction(customerId, bookId);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        log.trace("deleteTransaction --- method finished: responseEntity={}", responseEntity);
        return responseEntity;
    }
}
