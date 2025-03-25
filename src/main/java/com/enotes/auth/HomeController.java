package com.enotes.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.utils.CommonUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/home")
@AllArgsConstructor
public class HomeController {
	
	private final HomeService homeService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccountHandler(@RequestParam Integer uid ,@RequestParam String code){
		Boolean isVerified = homeService.verifyAccount(uid, code);
		if(isVerified) {
			return CommonUtil.createBuildResponseMessage(HttpStatus.OK,"account verified successfully");
		}
		return CommonUtil.createErrorResponseMessage(HttpStatus.BAD_REQUEST	,"invalid verification link");	
		
	}

}
