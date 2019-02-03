package com.zilker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zilker.beans.Rental;
import com.zilker.utils.DBUtils;

public class AdminDAO {

	private Connection connection = null;
	private PreparedStatement prepareStatement = null;
	private ResultSet resultSet = null;
	
	public ArrayList<Rental> displayRequestedHouse(Rental rental)throws SQLException
	{
		ArrayList<Rental> requestedList = new ArrayList<Rental>();
		try {
			
			connection = DBUtils.getConnection();
			prepareStatement = connection.prepareStatement("SELECT DISTINCT RENTAL_DETAILS.HOUSE_ID,ADDRESS.ADDRESS,AREA.AREA,AREA.ZIPCODE FROM RENTAL_DETAILS,ADDRESS,AREA WHERE RENTAL_DETAILS.ADDRESS_ID = ADDRESS.ADDRESS_ID AND ADDRESS.AREA_ID = AREA.AREA_ID AND STATUS_ID = 1");
			resultSet = prepareStatement.executeQuery();
			while(resultSet.next())
			{
				Rental rent = new Rental();
				rent.setHouseid(resultSet.getInt(1));
				rent.setRentaddress(resultSet.getString(2));
				rent.setRentarea(resultSet.getString(3));
				rent.setZipcode(resultSet.getInt(4));
				requestedList.add(rent);
			}
			
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
		return requestedList;
	}
	
	
	public void acceptHouse(Rental rental)throws SQLException
	{
		try {
			connection = DBUtils.getConnection();
			prepareStatement = connection.prepareStatement("UPDATE RENTAL_DETAILS SET STATUS_ID = ? WHERE HOUSE_ID = ?");
			prepareStatement.setInt(1, rental.getStatusId());
			prepareStatement.setInt(2, rental.getHouseid());
			prepareStatement.executeUpdate();
			
		}catch(SQLException e)
		{
			
		}finally {
		
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
}
