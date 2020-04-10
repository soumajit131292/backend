package com.techchefs.gmailloginlogout.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface UserDetailsService {

	public boolean getUserDetails(String idToken) throws GeneralSecurityException, IOException ;
}
