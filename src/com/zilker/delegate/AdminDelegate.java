package com.zilker.delegate;

import java.sql.SQLException;
import java.util.ArrayList;
import com.zilker.beans.Rental;
import com.zilker.dao.AdminDAO;


public class AdminDelegate {


	public ArrayList<Rental> displayRequestedHouse(Rental rental)throws SQLException
	{
		ArrayList<Rental> requestedList = new ArrayList<>();
		try {
			
			AdminDAO adminDao = new AdminDAO();
			requestedList = adminDao.displayRequestedHouse(rental);
			return requestedList;
		}catch(SQLException e)
		{
			throw e;
		}
	}
	public void acceptHouse(Rental rental)throws SQLException
	{
		try {
			AdminDAO adminDao = new AdminDAO();
			adminDao.acceptHouse(rental);
			
		}catch(SQLException e)
		{
			throw e;
		}
	}
}
