package com.ravika.ravikabank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ravika.ravikabank.dto.BankResponse;
import com.ravika.ravikabank.dto.UserRequest;
import com.ravika.ravikabank.dto.UserResponse;
import com.ravika.ravikabank.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public BankResponse createAccount(@RequestBody UserRequest userRequest) {
		return userService.createAccount(userRequest);
	}
	
	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<UserResponse> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/getUserDetails")
	@ResponseStatus(value = HttpStatus.OK)
	public UserResponse getUserDetails(@RequestBody UserRequest userRequest) {
		return userService.userDetailsByAccountNumber(userRequest);
	}
}
