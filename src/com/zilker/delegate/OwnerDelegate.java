package com.zilker.delegate;

import java.sql.SQLException;
import java.util.ArrayList;
import com.zilker.beans.Rental;
import com.zilker.beans.User;
import com.zilker.dao.OwnerDAO;

public class OwnerDelegate {
	

	/*
	 * This method allows to add
	 * rental details
	 */
	public int addRentalDetails(Rental rental)throws SQLException
	{
		try {
			
			int flag = 0;
			OwnerDAO ownerDao = new OwnerDAO();
			flag = ownerDao.addRentalDetails(rental);
			return flag;
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
	
	public void addRentDetails(Rental rental)throws SQLException
	{
		try {
			OwnerDAO ownerDao = new OwnerDAO();
			ownerDao.addRentDetails(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
	
	public void addPgDetails(Rental rental)throws SQLException
	{
		try {
			OwnerDAO ownerDao = new OwnerDAO();
			ownerDao.addPgDetails(rental);
			
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
			
			OwnerDAO ownerDao = new OwnerDAO();
			ownerDao.addFacility(rental);
			
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
			
			OwnerDAO ownerDao = new OwnerDAO();
			flag = ownerDao.updateRentType(rental);
			
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
			
			OwnerDAO ownerDao = new OwnerDAO();
			flag =ownerDao.updateRentAddress(rental);
			
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
			
			OwnerDAO ownerDao = new OwnerDAO();
			flag =ownerDao.updateRentPrice(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
		return flag;
	}
	/*
	 * This method displays the house details
	 */
	public ArrayList<Rental> viewHouse(Rental rental)throws SQLException
	{
		ArrayList<Rental> houseList = new ArrayList<>();
		try {
			
			OwnerDAO ownerDao = new OwnerDAO();
			houseList = ownerDao.viewHouse(rental);
	
		}catch(SQLException e)
		{
			throw e;
		}
		return houseList;
	}
	/*
	 * This method allows to 
	 * view interested tenants
	 */
	public ArrayList<User> viewInterestedTenant(User user)throws SQLException
	{
		ArrayList<User> customerList = new ArrayList<>();
		try {
			
			OwnerDAO ownerDao = new OwnerDAO();
			customerList = ownerDao.viewInterestedTenant(user);
			return customerList;
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
	
	public int checkAcceptedRequest(Rental rental)throws SQLException
	{
		try {
			
			OwnerDAO ownerDao = new OwnerDAO();
			int flag = ownerDao.checkAcceptedRequest(rental);
			return flag;
			
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
			
			OwnerDAO ownerDao = new OwnerDAO();
			ownerDao.acceptUserRequest(rental);
			
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
		ArrayList<Rental> statusList = new ArrayList<>();
		try {
			
			OwnerDAO ownerDao = new OwnerDAO();
			statusList = ownerDao.viewStatus(rental);
			return statusList;
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
	public ArrayList<Rental> viewConformedCustomer(Rental rental)throws SQLException
	{
		try {
			ArrayList<Rental> customerList = new ArrayList<>();
			OwnerDAO ownerDao = new OwnerDAO();
			customerList = ownerDao.viewConformedCustomer(rental);
			return customerList;
		}catch(SQLException e)
		{
			throw e;
		}
	}
	public ArrayList<Rental> viewRejected(Rental rental)throws SQLException
	{
		try {
			ArrayList<Rental> customerList = new ArrayList<>();
			OwnerDAO ownerDao = new OwnerDAO();
			customerList = ownerDao.viewRejected(rental);
			return customerList;
		}catch(SQLException e)
		{
			throw e;
		}
	}
}
