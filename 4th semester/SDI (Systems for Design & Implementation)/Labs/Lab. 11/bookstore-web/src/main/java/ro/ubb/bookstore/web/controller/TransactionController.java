package ro.ubb.bookstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.bookstore.core.service.TransactionService;
import ro.ubb.bookstore.web.converter.TransactionConverter;
import ro.ubb.bookstore.web.dto.TransactionDto;
import ro.ubb.bookstore.web.dto.TransactionsDto;

@RestController
public class TransactionController {
    public static final Logger log= LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionConverter transactionConverter;


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    TransactionsDto getTransactions() {
        log.trace("getTransactions --- method entered");

        TransactionsDto dtos = new TransactionsDto(
                transactionConverter.convertModelsToDtos(
                        transactionService.getAllTransactions()
                )
        );

        log.trace("getTransactions --- transactionsDto={}", dtos);
        log.trace("getTransactions --- method finished");

        return dtos;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    TransactionDto saveTransaction(@RequestBody TransactionDto transactionDto) {
        log.trace("saveTransaction --- method entered");

        TransactionDto dto = transactionConverter.convertModelToDto(
                transactionService.saveTransaction(
                        transactionConverter.convertDtoToModel(transactionDto)
                )
        );

        log.trace("saveTransaction --- transactionDto={}", dto);
        log.trace("saveTransaction --- method finished");

        return dto;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.PUT)
    TransactionDto updateTransaction(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {
        log.trace("updateTransaction --- method entered");

        TransactionDto dto = transactionConverter.convertModelToDto(
                transactionService.updateTransaction(
                        id,
                        transactionConverter.convertDtoToModel(transactionDto)
                )
        );

        log.trace("updateTransaction --- transactionDto={}", dto);
        log.trace("updateTransaction --- method finished");

        return dto;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteTransaction(@PathVariable Long id){
        log.trace("deleteTransaction --- method entered");

        transactionService.deleteTransactionById(id);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        log.trace("deleteTransaction --- responseEntity={}", responseEntity);
        log.trace("deleteTransaction --- method finished");

        return responseEntity;
    }
}
