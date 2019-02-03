package com.zilker.delegate;

import java.sql.SQLException;
import java.util.ArrayList;
import com.zilker.beans.Rental;
import com.zilker.beans.User;
import com.zilker.dao.CustomerDAO;

public class CustomerDelegate {


	
	/*
	 * This method allows to register
	 * the user
	 */
	public void register(User user)throws SQLException
	{
		try {
			
			CustomerDAO customerDao = new CustomerDAO();
			customerDao.registerUser(user);
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
	/*
	 * This method allows to login and
	 * verifies the user email and password
	 */
	public String login(User user)throws SQLException
	{
		String role = "";
		try {
			 
			CustomerDAO customerDao = new CustomerDAO();
			role = customerDao.loginUser(user);
			 		
		}catch(SQLException e)
		{
			throw e;
		}
		return role;
	}
	/*
	 * This method allows to search the house
	 * by location 
	 */
	public ArrayList<Rental> search(Rental rental)throws SQLException
	{
		ArrayList<Rental> searchList = new ArrayList<>();
		try {
			CustomerDAO customerDao = new CustomerDAO();
			searchList = customerDao.search(rental);
			
			}catch(SQLException e)
			{
				throw e;
			}
		return searchList;
	}
	
	public ArrayList<Rental> searchRent(Rental rental)throws SQLException
	{
		ArrayList<Rental> searchList = new ArrayList<>();
		try {
			CustomerDAO customerDao = new CustomerDAO();
			searchList = customerDao.searchRent(rental);
			
			}catch(SQLException e)
			{
				throw e;
			}
		return searchList;
	}
	/*
	 * This method is to display the owner
	 * contact number
	 */
	public long displayContact(Rental rental)throws SQLException
	{
		
		try {
			CustomerDAO customerDao = new CustomerDAO();
			long phoneNo = customerDao.displayContact(rental);
			return phoneNo;
			
		}catch(SQLException e)
		{
			throw e;
		}
		
	}
	/*
	 * This method allows to display the house
	 * facility 
	 */
	public ArrayList<Rental> displayFacility(Rental rental)throws SQLException
	{
		ArrayList<Rental> facilityList = new ArrayList<>();
		try {
			CustomerDAO customerDao = new CustomerDAO();
			facilityList = customerDao.displayFacility(rental);
			
			}catch(SQLException e)
			{
				throw e;
			}
		return facilityList;
	}
	
	public ArrayList<Rental> displayReview(Rental rental)throws SQLException
	{
		ArrayList<Rental> reviewList = new ArrayList<>();
		try {
			CustomerDAO customerDao = new CustomerDAO();
			reviewList = customerDao.displayReview(rental);
			
			}catch(SQLException e)
			{
				throw e;
			}
		return reviewList;
	}
	
	public ArrayList<Rental> searchPg(Rental rental)throws SQLException
	{
		ArrayList<Rental> searchList = new ArrayList<>();
		try {
			CustomerDAO customerDao = new CustomerDAO();
			searchList = customerDao.searchPg(rental);
			
			}catch(SQLException e)
			{
				throw e;
			}
		return searchList;
	}
	/*
	 * This method is to mark interest on the
	 * house
	 */
	public int checkMarkInterest(Rental rental)throws SQLException
	{
		try {
			CustomerDAO customerDao = new CustomerDAO();
			int flag = customerDao.checkMarkInterest(rental);
			return flag;
		}catch(SQLException e)
		{
			throw e;
		}
	}
	
	public int markInterest(Rental rental)throws SQLException
	{
		try {
			
			CustomerDAO customerDao = new CustomerDAO();
			int counter = customerDao.markInterest(rental);
			return counter;
		}catch(SQLException e)
		{
			throw e;
		}
		
	}
	/*
	 * This method is to display the address of the house
	 */
	public ArrayList<Rental> displayAddress(Rental rental)throws SQLException
	{
		ArrayList<Rental> addressList = new ArrayList<>();
		try {
			
			CustomerDAO customerDao = new CustomerDAO();
			addressList = customerDao.displayAddress(rental);
			return addressList;
			
		}catch(SQLException e)
		{
		throw e;	
		}
	}
	/*
	 * This method allows to add
	 * reviews for the house
	 */
	public void addReview(Rental rental)throws SQLException
	{
		try {
			
			CustomerDAO customerDao = new CustomerDAO();
			customerDao.addReview(rental);
	
		}catch(SQLException e)
		{
			throw e;
		}
		
	}
	public int checkReviewRating(Rental rental)throws SQLException
	{
		try {
			CustomerDAO customerDao = new CustomerDAO();
			int flag = customerDao.checkReviewRating(rental);
			return flag;
		}catch(SQLException e)
		{
			throw e;
		}
		
	}
	public ArrayList<Rental> viewApprovedHouse(Rental rental)throws SQLException
	{
		ArrayList<Rental> approvedHouseList = new ArrayList<>();
		try {
			
			CustomerDAO customerDao = new CustomerDAO();
			approvedHouseList = customerDao.viewApprovedHouse(rental);
	
		}catch(SQLException e)
		{
			throw e;
		}
		return approvedHouseList;
	}
	public void conformHouse(Rental rental)throws SQLException
	{
		try {
			
			CustomerDAO customerDao = new CustomerDAO();
			customerDao.conformHouse(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
	public ArrayList<Rental> viewUserStatus(Rental rental)throws SQLException
	{
			ArrayList<Rental> userStatusList = new ArrayList<Rental>();
			try {
				
				CustomerDAO customerDao = new CustomerDAO();
				userStatusList = customerDao.viewUserStatus(rental);
				return userStatusList;
			}
			catch(SQLException e)
			{
				throw e;
			}
	}
	
	
}
