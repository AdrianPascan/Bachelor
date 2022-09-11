package ro.ubb.bookstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.bookstore.core.service.CustomerService;
import ro.ubb.bookstore.web.converter.CustomerConverter;
import ro.ubb.bookstore.web.dto.CustomerDto;
import ro.ubb.bookstore.web.dto.CustomersDto;

@RestController
public class CustomerController {
    public static final Logger log= LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerConverter customerConverter;


    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    CustomersDto getCustomers() {
        log.trace("getCustomers --- method entered");

        CustomersDto dtos = new CustomersDto(
                customerConverter.convertModelsToDtos(
                        customerService.getAllCustomers()
                )
        );

        log.trace("getCustomers --- customersDto={}", dtos);
        log.trace("getCustomers --- method finished");

        return dtos;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    CustomerDto saveCustomer(@RequestBody CustomerDto customerDto) {
        log.trace("saveCustomer --- method entered");

        CustomerDto dto = customerConverter.convertModelToDto(
                customerService.saveCustomer(
                        customerConverter.convertDtoToModel(customerDto)
                )
        );

        log.trace("saveCustomer --- customerDto={}", dto);
        log.trace("saveCustomer --- method finished");

        return dto;
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    CustomerDto updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        log.trace("updateCustomer --- method entered");

        CustomerDto dto = customerConverter.convertModelToDto(
                customerService.updateCustomer(
                        id,
                        customerConverter.convertDtoToModel(customerDto)
                )
        );

        log.trace("updateCustomer --- customerDto={}", dto);
        log.trace("updateCustomer --- method finished");

        return dto;
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        log.trace("deleteCustomer --- method entered");

        customerService.deleteCustomerById(id);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        log.trace("deleteCustomer --- responseEntity={}", responseEntity);
        log.trace("deleteCustomer --- method finished");

        return responseEntity;
    }
}
