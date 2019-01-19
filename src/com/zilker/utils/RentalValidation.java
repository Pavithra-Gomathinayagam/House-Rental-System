package com.zilker.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.zilker.constant.RegexConstant;

/*
 * This class is to perform rental validation
 */
public class RentalValidation {

	private static final Logger logger = Logger.getLogger(RentalValidation.class.getName());
	
	/*
	 * This method is used to validate email
	 */
	public int validEmail(String email)
	{
		int counter = 0;
		Pattern pattern = Pattern.compile(RegexConstant.EMAIL);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==true)
		{
			email = String.valueOf(email);
			counter = 1;
		}
		else
			logger.log(Level.INFO,"Invalid email");
		return counter;	
	}
	/*
	 * This method is used to validate phone number
	 */
	public int validPhoneno(Long phoneno)
	{
		int counter = 0;
		Pattern pattern = Pattern.compile(RegexConstant.PHONE_NO);
		Matcher matcher = pattern.matcher(String.valueOf(phoneno));
		if(matcher.matches()==true)
		{
			phoneno = phoneno;
			counter = 1;

		}
		else
			logger.log(Level.INFO,"Invalid Home no");
		return counter;
	}
}

