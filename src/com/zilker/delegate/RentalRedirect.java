package com.zilker.delegate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import com.zilker.beans.Rental;
import com.zilker.beans.User;
import com.zilker.dao.RentalDAO;


public class RentalRedirect {
	
	
	Scanner sc = new Scanner(System.in);
	String email;
	
	/*
	 * This method allows to register
	 * the user
	 */
	public void register(User user)throws SQLException
	{
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			rentaldao.registerUser(user);
			
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
			 
			RentalDAO rentaldao = new RentalDAO();
			role = rentaldao.loginUser(user);
			 		
		}catch(SQLException e)
		{
			throw e;
		}
		return role;
	}
	/*
	 * This method allows to display the house
	 * rating and review 
	 */
	public ArrayList<Rental> displayReview(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			RentalDAO rentaldao = new RentalDAO();
			al = rentaldao.displayReview(rental);
			
			}catch(SQLException e)
			{
				throw e;
			}
		return al;
	}
	/*
	 * This method allows to display the house
	 * facility 
	 */
	public ArrayList<Rental> displayFacility(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			RentalDAO rentaldao = new RentalDAO();
			al = rentaldao.displayFacility(rental);
			
			}catch(SQLException e)
			{
				throw e;
			}
		return al;
	}
	/*
	 * This method allows to search the house
	 * by location 
	 */
	public ArrayList<Rental> search(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			RentalDAO rentaldao = new RentalDAO();
			al = rentaldao.search(rental);
			
			}catch(SQLException e)
			{
				throw e;
			}
		return al;
	}
	/*
	 * This method is to display the owner
	 * contact number
	 */
	public long displayContact(Rental rental)throws SQLException
	{
		
		try {
			RentalDAO rentaldao = new RentalDAO();
			long phoneno = rentaldao.displayContact(rental);
			return phoneno;
			
		}catch(SQLException e)
		{
			throw e;
		}
		
	}
	/*
	 * This method is to mark interest on the
	 * house
	 */
	public int markInterest(Rental rental)throws SQLException
	{
		int counter = 0;
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			counter = rentaldao.markInterest(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
		return counter;
	}
	/*
	 * This method allows to add
	 * rental details
	 */
	public int addRentalDetails(Rental rental)throws SQLException
	{
		try {
			
			int flag = 0;
			RentalDAO rentaldao = new RentalDAO();
			flag = rentaldao.addRentalDetails(rental);
			return flag;
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
	/*
	 * This method is add facilities for the house
	 */
	public void addFacility(Rental rental)throws SQLException
	{
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			rentaldao.addFacility(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
		
	}
	/*
	 * This method allows to update
	 * rental details
	 */
	public int updateRentType(Rental rental)throws SQLException
	{
		int flag = 0;
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			flag = rentaldao.updateRentType(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
		return flag;
	}
	
	public int updateRentChoice(Rental rental)throws SQLException
	{
		int flag = 0;
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			flag =rentaldao.updateRentChoice(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
		return flag;
	}
	
	public int updateRentAddress(Rental rental)throws SQLException
	{
		int flag = 0;
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			flag =rentaldao.updateRentAddress(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
		return flag;
	}
	
	public int updateRentPrice(Rental rental)throws SQLException
	{
		int flag = 0;
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			flag =rentaldao.updateRentPrice(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
		return flag;
	}
	/*
	 * This method is to display the address of the house
	 */
	public ArrayList<Rental> displayAddress(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			al = rentaldao.displayAddress(rental);
			return al;
			
		}catch(SQLException e)
		{
		throw e;	
		}
	}
	/*
	 * This method displays the house details
	 */
	public ArrayList<Rental> viewHouse(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			al = rentaldao.viewHouse(rental);
	
		}catch(SQLException e)
		{
			throw e;
		}
		return al;
	}
	/*
	 * This method allows to add
	 * reviews for the house
	 */
	public void addReview(Rental rental)throws SQLException
	{
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			rentaldao.addReview(rental);
	
		}catch(SQLException e)
		{
			throw e;
		}
		
	}
	/*
	 * This method allows to 
	 * view interested tenants
	 */
	public ArrayList<User> viewInterestedTenant(User user)throws SQLException
	{
		ArrayList<User> al = new ArrayList<>();
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			al = rentaldao.viewInterestedTenant(user);
			return al;
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
	/*
	 * This method allows owner to accept user 
	 * requested house
	 */
	public void acceptUserRequest(Rental rental)throws SQLException
	{
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			rentaldao.acceptUserRequest(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
	/*
	 * This method allows owner to view the status 
	 * of the rental house
	 */
	public ArrayList<Rental> viewStatus(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			
			RentalDAO rentaldao = new RentalDAO();
			al = rentaldao.viewStatus(rental);
			return al;
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
}