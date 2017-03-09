package com.axiata.rest.controller;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.axiata.rest.model.Customer;
import com.axiata.rest.service.CustomerService;


@RestController
@RequestMapping("/customer")
public class CustomerController {

	final static Logger logger = Logger.getLogger(CustomerController.class);


	@Autowired
	CustomerService customerService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
		customer = customerService.save(customer);
		logger.debug("Added:: " + customer);
		return new ResponseEntity<Customer>(customer,HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateEmployee(@RequestBody Customer customer) {
		Customer existingEmp = customerService.getById(customer.getId());
		if (existingEmp == null) {
			logger.debug("Customer with id " + customer.getId() + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			customerService.save(customer);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Customer>> getAllCustomer() {
		List<Customer> customers = customerService.getAll();
		if (customers.isEmpty()) {
			logger.debug("Customer does not exists");
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Found " + customers.size() + " Customers");
		logger.debug(customers);
		logger.debug(Arrays.toString(customers.toArray()));
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
		Customer customer = customerService.getById(id);
		if(customer==null){
			logger.debug("Customer does not exists");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
		Customer customer = customerService.getById(id);
		if (customer == null) {
			logger.debug("Employee with id " + id + " does not exists");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		} else {
			customerService.delete(id);
			logger.debug("Employee with id " + id + " deleted");
			return new ResponseEntity<Void>(HttpStatus.GONE);
		}
	}


}
