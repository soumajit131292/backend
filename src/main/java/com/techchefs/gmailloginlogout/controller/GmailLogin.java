package com.techchefs.gmailloginlogout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techchefs.gmailloginlogout.config.TokenConfig;
import com.techchefs.gmailloginlogout.utility.Response;

@RestController
@RequestMapping("/gmaillogin")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class GmailLogin {
	
	
	@Autowired
	private TokenConfig tokenConfig;
	
	
	@GetMapping("/generate")
	public ResponseEntity<Response> generateToken(@RequestHeader("Authorization") String googlekey) {
		System.out.println("enterd in to conotroller");
		String JWT=tokenConfig.generateJWT(googlekey);
		System.out.println("jwt token "+JWT);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("sucesfully generated token", HttpStatus.ACCEPTED.value(), JWT));
	}
	
	@GetMapping("/decode")
	public ResponseEntity<Response> decodeToken(@RequestHeader("Authorization") String jwttoken) {
		System.out.println("hiiii");
		System.out.println("enterd in to conotroller");
		String googlekey=tokenConfig.parseJWT(jwttoken);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("sucessfully generated token", HttpStatus.ACCEPTED.value(), googlekey));
	}
	

}
