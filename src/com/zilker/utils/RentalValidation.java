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
	public boolean validateEmail(String email)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.EMAIL);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()==true)
		{
			email = String.valueOf(email);
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid email");
		return counter;	
	}
	/*
	 * This method is used to validate HouseType
	 */
	public boolean validateHouseType(String houseType)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.HOUSE_TYPE);
		Matcher matcher = pattern.matcher(houseType);
		if(matcher.matches()==true)
		{
			houseType = String.valueOf(houseType);
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid houseType");
		return counter;	
	}
	/*
	 * This method is used to validate name
	 */
	public boolean validateFname(String fname)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.FNAME);
		Matcher matcher = pattern.matcher(fname);
		if(matcher.matches()==true)
		{
			fname = String.valueOf(fname);
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid name");
		return counter;	
	}
	/*
	 * This method is used to validate name
	 */
	public boolean validateLname(String lname)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.LNAME);
		Matcher matcher = pattern.matcher(lname);
		if(matcher.matches()==true)
		{
			lname = String.valueOf(lname);
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid name");
		return counter;	
	}
	/*
	 * This method is used to validate area
	 */
	public boolean validateArea(String area)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.AREA);
		Matcher matcher = pattern.matcher(area);
		if(matcher.matches()==true)
		{
			area = String.valueOf(area);
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid area");
		return counter;	
	}
	/*
	 * This method is used to validate address
	 */
	public boolean validateAddress(String address)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.ADDRESS);
		Matcher matcher = pattern.matcher(address);
		if(matcher.matches()==true)
		{
			address = String.valueOf(address);
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid address");
		return counter;	
	}
	/*
	 * This method is used to validate zipcode
	 */
	public boolean validateZipcode(int zipcode)
	{
		String zipc=String.valueOf(zipcode);
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.ZIPCODE);
		Matcher matcher = pattern.matcher(zipc);
		if(matcher.matches()==true)
		{
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid Zipcode");
		return counter;	
	}
	/*
	 * This method is used to validate password
	 */
	public boolean validatePassword(String password)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.PASSWORD);
		Matcher matcher = pattern.matcher(password);
		if(matcher.matches()==true)
		{
			password = String.valueOf(password);
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid Password");
		return counter;	
	}
	/*
	 * This method is used to validate phone number
	 */
	public boolean validatePhoneno(Long phoneno)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.PHONE_NO);
		Matcher matcher = pattern.matcher(String.valueOf(phoneno));
		if(matcher.matches()==true)
		{
			counter = true;

		}
		else
			logger.log(Level.INFO,"Invalid Phone number");
		return counter;
	}
	/*
	 * This method is used to validate pay
	 */
	public boolean validateReqPay(int reqpay)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.INTEGERNUMBERS);
		Matcher matcher = pattern.matcher(String.valueOf(reqpay));
		if(matcher.matches()==true)
		{

			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid ReqPay");
		return counter;	
	}
	/*
	 * This method is used to validate rental price
	 */
	public boolean validatePrice(int price)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.INTEGERNUMBERS);
		Matcher matcher = pattern.matcher(String.valueOf(price));
		if(matcher.matches()==true)
		{
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid Price");
		return counter;	
	}
	/*
	 * This method is used to validate number
	 */
	public boolean validateNumber(int number)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.INTEGERNUMBERS);
		Matcher matcher = pattern.matcher(String.valueOf(number));
		if(matcher.matches()==true)
		{
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid");
		return counter;	
	}
	/*
	 * This method is used to validate string
	 */
	public boolean validateCharacters(String text)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.CHARACTERSONLY);
		Matcher matcher = pattern.matcher(String.valueOf(text));
		if(matcher.matches()==true)
		{
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid");
		return counter;	
	}
	/*
	 * This method is used to validate rating
	 */
	public boolean validateRating(String rating)
	{
		boolean counter = false;
		Pattern pattern = Pattern.compile(RegexConstant.RATING);
		Matcher matcher = pattern.matcher(rating);
		if(matcher.matches()==true)
		{
			counter = true;
		}
		else
			logger.log(Level.INFO,"Invalid Rating");
		return counter;	
	}
	
}

