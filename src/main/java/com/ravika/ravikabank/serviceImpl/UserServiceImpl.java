package com.ravika.ravikabank.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ravika.ravikabank.dto.AccountInfo;
import com.ravika.ravikabank.dto.BankResponse;
import com.ravika.ravikabank.dto.UserRequest;
import com.ravika.ravikabank.dto.UserResponse;
import com.ravika.ravikabank.entity.User;
import com.ravika.ravikabank.repository.UserRepository;
import com.ravika.ravikabank.service.UserService;
import com.ravika.ravikabank.utils.AccountUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public BankResponse createAccount(UserRequest userRequest) {
		/**
		 *  Creating an account - saving a new user into the db
		 *  check if user already has an account
		 */
		
		if(userRepository.existsByEmail(userRequest.getEmail())) {
			log.info("Account already exists");
			return BankResponse.builder()
						.responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
						.responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
						.accountInfo(null)
						.build();
			
		}
		User newUser  = User.builder()
				.firstName(userRequest.getFirstName())
				.lastName(userRequest.getLastName())
				.gender(userRequest.getGender())
				.address(userRequest.getAddress())
				.accountNumber(AccountUtils.generateAccountNumber())
				.accountBalance(BigDecimal.ZERO)
				.email(userRequest.getEmail())
				.phoneNumber(userRequest.getPhoneNumber())
				.status("ACTIVE")
				.build();
		
		User savedUser = userRepository.save(newUser);
		log.info("User {} is saved", savedUser.getId());
		
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS_CODE)
				.responseMessage(AccountUtils.ACCOUNT_CREATION_SUCCESS_MESSAGE)
				.accountInfo(AccountInfo.builder()
								.accountBalance(savedUser.getAccountBalance())
								.accountNumber(savedUser.getAccountNumber())
								.accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
								.build())
				.build();
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(this::mapToUserResponse).toList();
	}
	
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
					.id(user.getId())
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.gender(user.getGender())
					.address(user.getAddress())
					.accountNumber(user.getAccountNumber())
					.accountBalance(user.getAccountBalance())
					.email(user.getEmail())
					.phoneNumber(user.getPhoneNumber())
					.status(user.getStatus())
					.build();
	}

	@Override
	public UserResponse userDetailsByAccountNumber(UserRequest userRequest) {
		User userObj = userRepository.findByAccountNumber(userRequest.getAccountNumber());
		return mapToUserResponse(userObj);
	}

}
