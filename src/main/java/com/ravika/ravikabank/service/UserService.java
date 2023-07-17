package com.ravika.ravikabank.service;

import java.util.List;

import com.ravika.ravikabank.dto.BankResponse;
import com.ravika.ravikabank.dto.UserRequest;
import com.ravika.ravikabank.dto.UserResponse;

public interface UserService {
	BankResponse createAccount(UserRequest userRequest);
	
	List<UserResponse> getAllUsers();
	
	UserResponse userDetailsByAccountNumber(UserRequest userRequest);
}
