package com.zilker.constant;

public class RegexConstant {

	public static final String EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
	public static final String PHONE_NO = "^[6789]\\d{9}$";
	public static final String FNAME =  "[a-zA-Z]*";
	public static final String LNAME =  "[a-zA-Z]*";
	public static final String PASSWORD = "^(?=.*[A-Za-z#?!@$%^&*-])(?=.*\\d)[A-Za-z\\d#?!@$%^&*-]{8,15}$";
	public static final String ADDRESS = "\\d{1,5}\\s\\w.\\s(\\b\\w*\\b\\s){1,2}\\w*\\.";
	public static final String ZIPCODE = "^\\d{6}(?:[-\\s]\\d{4})?$";
	public static final String AREA =  "[a-zA-Z]*";
	public static final String INTEGERNUMBERS = "^[0-9]+$";
	public static final String HOUSE_TYPE = "[1-3]bhk|4[\\+]bhk|[1]rk";
	public static final String CHARACTERSONLY = "^[a-zA-Z. ]{2,}$";
	public static final String RATING="^[[0-4]{1}([.][0-9]+)]?|[5]$";
}
