package com.techchefs.gmailloginlogout.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

private boolean email_verified;

private String email;

private String iss;

private String aud;

private String exp;

private String alg;

private String name;

private String picture;

private String given_name;

private String family_name;

private String locale;

public boolean isEmail_verified() {
	return email_verified;
}

public void setEmail_verified(boolean email_verified) {
	this.email_verified = email_verified;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getIss() {
	return iss;
}

public void setIss(String iss) {
	this.iss = iss;
}

public String getAud() {
	return aud;
}

public void setAud(String aud) {
	this.aud = aud;
}

public String getExp() {
	return exp;
}

public void setExp(String exp) {
	this.exp = exp;
}

public String getAlg() {
	return alg;
}

public void setAlg(String alg) {
	this.alg = alg;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getPicture() {
	return picture;
}

public void setPicture(String picture) {
	this.picture = picture;
}

public String getGiven_name() {
	return given_name;
}

public void setGiven_name(String given_name) {
	this.given_name = given_name;
}

public String getFamily_name() {
	return family_name;
}

public void setFamily_name(String family_name) {
	this.family_name = family_name;
}

public String getLocale() {
	return locale;
}

public void setLocale(String locale) {
	this.locale = locale;
}



}
