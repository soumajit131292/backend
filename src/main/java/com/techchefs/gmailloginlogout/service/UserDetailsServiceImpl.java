package com.techchefs.gmailloginlogout.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.techchefs.gmailloginlogout.dto.ErrorResponse;
import com.techchefs.gmailloginlogout.dto.UserResponse;
import com.techchefs.gmailloginlogout.model.UserDetails;
import com.techchefs.gmailloginlogout.repository.UserRepositoryImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepositoryImpl userRepo;

	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	@Override
	public boolean getUserDetails(String idToken) throws GeneralSecurityException, IOException {
		System.out.println("in 1st mwthod");
		System.out.println(idToken);

		HttpGet request = new HttpGet("https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken);
		try (CloseableHttpResponse response = httpClient.execute(request)) {

			System.out.println(response.getStatusLine().toString());

			HttpEntity entity = response.getEntity();
			StatusLine statusLine = response.getStatusLine();
			System.out.println(statusLine.getStatusCode());

			if (statusLine.getStatusCode() == 200) {
				String result = EntityUtils.toString(entity);
				UserResponse userResponse = new ObjectMapper().readValue(result, UserResponse.class);
				String emailId = userResponse.getEmail();
				Optional<UserDetails> details = userRepo.findOneData(emailId);

				System.out.println("is it present ?     " + details.isPresent());

				if (!details.isPresent()) {
					getProfileInfo(idToken);

					return true;
				}

			} else {
				String result = EntityUtils.toString(entity);
				ErrorResponse ronaldo = new ObjectMapper().readValue(result, ErrorResponse.class);
				System.out.println(ronaldo.getError_description());
				return false;
			}

		}
		return true;
	}

	private static final JacksonFactory jacksonFactory = new JacksonFactory();

	public void getProfileInfo(String token) throws GeneralSecurityException, IOException {
		System.out.println("in wnd method");
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
				.setAudience(Collections
						.singletonList("259286130713-3iojpgt4b6bih0gk9ba2nu4p733g87bl.apps.googleusercontent.com"))
				.build();
		
		 
        
		System.out.println("after building verifier");
		GoogleIdToken idToken = verifier.verify(token);

		Payload payload = idToken.getPayload();
		
		String userId = payload.getSubject();
		System.out.println(userId);
System.out.println(payload.toPrettyString());
		String email = payload.getEmail();
		boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		String name = (String) payload.get("name");
		String pictureUrl = (String) payload.get("picture");
		String locale = (String) payload.get("locale");
		String familyName = (String) payload.get("family_name");
		String givenName = (String) payload.get("given_name");
		System.out.println(name + " " + email + " ");
		UserDetails user = new UserDetails(email, emailVerified, familyName, givenName, locale, name, pictureUrl);
		userRepo.save(user);

	}

}
