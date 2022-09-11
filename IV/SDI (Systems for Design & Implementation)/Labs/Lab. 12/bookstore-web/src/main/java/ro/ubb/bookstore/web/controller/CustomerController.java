package ro.ubb.bookstore.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.ubb.bookstore.core.service.CustomerService;
import ro.ubb.bookstore.web.converter.CustomerConverter;
import ro.ubb.bookstore.web.dto.CustomerDto;

import java.util.Set;

@RestController
public class CustomerController {
    public static final Logger log= LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerConverter customerConverter;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public Set<CustomerDto> getCustomers() {
        log.trace("getCustomers --- method entered");

        Set<CustomerDto> customerDtos = (Set<CustomerDto>) customerConverter.convertModelsToDtos(customerService.getAllCustomers());

        log.trace("getCustomers --- method finished: customerDtos={}", customerDtos);
        return customerDtos;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public CustomerDto saveCustomer(@RequestBody final CustomerDto customerDto) {
        log.trace("saveCustomer --- method entered");

        CustomerDto savedCustomerDto = customerConverter.convertModelToDto(
                customerService.saveCustomer(customerConverter.convertDtoToModel(customerDto)));

        log.trace("saveCustomer --- method finished: customerDto={}", savedCustomerDto);
        return savedCustomerDto;
    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.PUT)
    @Transactional
    public CustomerDto updateCustomer(@PathVariable final Long customerId,
                                      @RequestBody final CustomerDto customerDto) {
        log.trace("updateCustomer --- method entered: customerId={}, customerDto={}", customerId, customerDto);

        customerDto.setId(customerId);
        CustomerDto updatedCustomerDto = customerConverter.convertModelToDto(
                customerService.updateCustomer(customerConverter.convertDtoToModel(customerDto)));

        log.trace("updateCustomer --- method finished: customerDto={}", updatedCustomerDto);
        return updatedCustomerDto;
    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> deleteCustomer(@PathVariable final Long customerId){
        log.trace("deleteCustomer --- method entered: customerId={}", customerId);

        customerService.deleteCustomer(customerId);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        log.trace("deleteCustomer --- method finished: responseEntity={}", responseEntity);
        return responseEntity;
    }
}
