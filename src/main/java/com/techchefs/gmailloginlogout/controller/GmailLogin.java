package com.techchefs.gmailloginlogout.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techchefs.gmailloginlogout.config.TokenConfig;
import com.techchefs.gmailloginlogout.service.UserDetailsService;
import com.techchefs.gmailloginlogout.service.UserDetailsServiceImpl;
import com.techchefs.gmailloginlogout.utility.Response;

@RestController
@RequestMapping("/gmaillogin")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
public class GmailLogin {

	@Autowired
	private TokenConfig tokenConfig;

	@Autowired
	private UserDetailsService userDetailsServiceImpl;

	String CLIENT_ID = "363482789414-87stbgvtbo5aamv5vprg3t9lnht6kuoq.apps.googleusercontent.com";
	String idTokenStringOld = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI1N2Y2YTU4MjhkMWU0YTNhNmEwM2ZjZDFhMjQ2MWRiOTU5M2U2MjQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNzQ0NjYwNzE0NzM5LWEzb21ucjNkYnBocmRiaDFqcWg0YnBtc3U1bjU3ZXA4LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNzQ0NjYwNzE0NzM5LWEzb21ucjNkYnBocmRiaDFqcWg0YnBtc3U1bjU3ZXA4LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA2ODMxODYwNTgxNTg1NjkzMjgyIiwiZW1haWwiOiJzb3VtYWppdDEzMTI5MkBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaWF0IjoxNTg2NDIwNTE3LCJleHAiOjE1ODY0MjQxMTcsImp0aSI6IjYwZjM5NDNmYWQ4YTJmOTQ1ODUyNDU1NzAyNzlkNmFhMzg4MjVhNTMifQ.WdQ1cleuAxvNjMMVNLiEIBpA54HSmCChJ8oq8uwnvEM5dskfO2Rrf5NPTNm-g-9zpuG_E60Hc0E7hHeU5j8ygQ2c2LsryaGuiEw480QyktJ4JLaz8zjzmgZAUBoEROrsEEtDIwSgQOdyhI-lj5CUXDbFgINDLG7V_hvzNdUSM4zt13jN-RZzxSZOo3CaK5xBnCLuaL493dx1yEvlCbNEPjFUovN2WNNQaK5Ff-3n1xHTMgilRsMhqxaygAnNlKv1JX8x-McJj2iVSNth_Yzca6CYQQ1jt0zBK1yCkaHcvdYlu2iEnDDX-YEKy6RoVdJr_s7OjvvAFxDs8ZMobzOEkw";

	String idTokenString="eyJhbGciOiJSUzI1NiIsImtpZCI6IjI1N2Y2YTU4MjhkMWU0YTNhNmEwM2ZjZDFhMjQ2MWRiOTU5M2U2MjQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNzQ0NjYwNzE0NzM5LWEzb21ucjNkYnBocmRiaDFqcWg0YnBtc3U1bjU3ZXA4LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNzQ0NjYwNzE0NzM5LWEzb21ucjNkYnBocmRiaDFqcWg0YnBtc3U1bjU3ZXA4LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA2ODMxODYwNTgxNTg1NjkzMjgyIiwiZW1haWwiOiJzb3VtYWppdDEzMTI5MkBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaWF0IjoxNTg2NDI1NzUyLCJleHAiOjE1ODY0MjkzNTIsImp0aSI6IjdmOWI1NTYzZDdhNWEwNTQ3ZTQzNGE3YzE4YmJhMTYzMGMxMDdjZWMifQ.EgI-ps3xtGEI9cpwuhgoWGynKnkm_Mxy48miKzDwUKlgpzFTXHmRGxb_XdtpUQrNCEOmINDESTo_J3UB3NR06FV4vEc-yaUUUAj871IdTYednBq-n9LAJ5oG9fhel8KmefpsuuQOVpLkICt4c0FTi1yOJ-yLvSZFez3aeR7izuvfcjMYcHhyOnTbWBwvgMG_kYE3pvG6xNpXlv_zpEzN_MzkWYsqJ6GYAN4gsvfphLzS0C2RB6E03SSIZX0EwB_ZfmfhkrxcM6aRlXcjXHRr9ixxE8isIhJdsHt_bbNDSlzzlsy38pbYr--7Z_1JIjVAVk8NDJcqJX42PprQPPb9XQ";
	
	
	@GetMapping("/generate")
	public ResponseEntity<Response> generateToken(@RequestHeader("Authorization") String googlekey)
			throws GeneralSecurityException, IOException {
		System.out.println("enterd in to conotroller");
		String JWT = tokenConfig.generateJWT(googlekey);
		System.out.println("jwt token " + JWT);
		
		if(userDetailsServiceImpl.getUserDetails(googlekey))
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("sucesfully generated token", HttpStatus.ACCEPTED.value(), JWT));
		else
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("sucesfully generated token", HttpStatus.BAD_REQUEST.value(), "Invalied Token"));
	}

	@GetMapping("/decode")
	public ResponseEntity<Response> decodeToken(@RequestHeader("Authorization") String jwttoken) {
		System.out.println("hiiii");
		System.out.println("enterd in to conotroller");
		String googlekey = tokenConfig.parseJWT(jwttoken);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("sucessfully generated token", HttpStatus.ACCEPTED.value(), googlekey));
	}

}
