//package com.bridgelabz.fundoonotes.controller;
///**
// * @author shaik shaiksha vali
// * 
// * */
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import com.bridgelabz.fundoonotes.model.UserInformationSearch;
//import com.bridgelabz.fundoonotes.repository.UserDaoSearch;
//import com.bridgelabz.fundoonotes.service.UserServiceSearch;
//
//@RestController
//public class UserControllerSearch {
//	
//@Autowired
//private UserServiceSearch userServicesearch;
//
//	//Api for Registration
//	
//	@PostMapping("/saveUsers")
//	public int saveUsers(@RequestBody List<UserInformationSearch> customers) {
//		userServicesearch.saveAll(customers);
//		return customers.size();
//	}
//	
//	@GetMapping("/findByName/{name}")
//	public List<UserInformationSearch> findByFirstName(@PathVariable String name) {
//		return userServicesearch.findByFirstName(name);
//		
//	}
//	@GetMapping("/findAll")
//	public Iterable<UserInformationSearch> findAllCustomers() {
//		return userServicesearch.findAll();
//		
//	}
//
//}
