package com.zilker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zilker.beans.Rental;
import com.zilker.beans.User;
import com.zilker.constant.SqlConstant;
import com.zilker.utils.DBUtils;

public class CustomerDAO {

	private Connection connection = null;
	private PreparedStatement prepareStatement,prepareStatement0,prepareStatement2,prepareStatement3 = null;
	private ResultSet resultSet,resultSet0,resultSet2,resultSet3 = null;
	
	/*
	 * This method registeresultSet the 
	 * user details
	 */
	public void registerUser(User user)throws SQLException
	{
		try {
		
			connection = DBUtils.getConnection();
			
			/*
			 * AREA
			 */
			int areaId = 0;
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_AREA_ID);
			prepareStatement.setInt(1, user.getZipcode());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
			{
				String id = resultSet.getString(1);
				areaId = Integer.parseInt(id);
			}
		
			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_USER_ADDRESS);
			prepareStatement.setString(1, user.getAddress());
			prepareStatement.setInt(2, areaId);
			prepareStatement.setString(3, user.getFname());
			prepareStatement.setString(4, user.getFname());
			prepareStatement.executeUpdate();
			
			/*
			 * ADDRESS
			 */
			int addressId = 0;
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_ADDRESS);
			prepareStatement.setString(1, user.getAddress());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
				if(resultSet.getString(2).equalsIgnoreCase(user.getAddress()))
				{
					String id = resultSet.getString(1);
					addressId = Integer.parseInt(id);
				}
			
			
			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_USER);
			prepareStatement.setString(1, user.getFname());
			prepareStatement.setString(2, user.getEmail());
			prepareStatement.setString(3, user.getPassword());
			prepareStatement.setLong(4, user.getPhoneno());
			prepareStatement.setString(5, user.getOccupation());
			prepareStatement.setInt(6, addressId);
			prepareStatement.setInt(7, 2);
			prepareStatement.setString(8, user.getFname());
			prepareStatement.setString(9, user.getFname());
			prepareStatement.setString(10, user.getLname());
			
			prepareStatement.executeUpdate();
			
			
		}catch(SQLException e)
		{
			throw e;
		}
		finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	/*This method verifies the user email
	 * and password
	 * 
	 */
	public String loginUser(User user)throws SQLException
	{
		String role = "";
		try {
			
			connection = DBUtils.getConnection();
			prepareStatement = connection.prepareStatement(SqlConstant.SELECT_USER);
			prepareStatement.setString(1, user.getEmail());
			prepareStatement.setString(2, user.getPassword());
			int roleId = 0;
		
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
			{
				if((resultSet.getString(1).equals(user.getEmail())) && (resultSet.getString(2).equals(user.getPassword())))
				{
					roleId = resultSet.getInt(3);					
				}
			}
			
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_ROLE_ID);
			prepareStatement.setInt(1, roleId);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
			{
				if(resultSet.getString(1).equals(String.valueOf(roleId)))
				{
					role = resultSet.getString(2);
				}
			}
		}
		catch(SQLException e){
			
			throw e;	
		}
		finally{
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
		return role;
	}
	
	/*
	 * This method search the house by location
	 */
	public ArrayList<Rental> search(Rental rental)throws SQLException
	{
		ArrayList<Rental> searchList = new ArrayList<>();
		try {
			connection = DBUtils.getConnection();
			
			prepareStatement = connection.prepareStatement(SqlConstant.SEARCH);
			prepareStatement.setString(1, rental.getRentarea());
			prepareStatement.setString(2, rental.getRentchoice());
			prepareStatement.setString(3, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			int houseId = 0;
			
			while(resultSet.next())
			{
				int flag = 0;
				houseId = resultSet.getInt(1);
				prepareStatement0 = connection.prepareStatement(SqlConstant.INTEREST_HOUSE_ID);
				prepareStatement0.setInt(1, houseId);
				resultSet0 = prepareStatement0.executeQuery();
				int id = 0;
				
				while(resultSet0.next())
				{
					id = resultSet0.getInt(1);
					
				}
				if(id !=0)
				{
					
					prepareStatement2 = connection.prepareStatement(SqlConstant.GET_INTEREST_HOUSE_ID);
					prepareStatement2.setInt(1, houseId);
					resultSet2 = prepareStatement2.executeQuery();
					while(resultSet2.next())
					{
						int rentHouseId = resultSet2.getInt(1);
						if(rentHouseId == id)
							flag = 1;
					}
				}

				if(flag!=1)
				{
					Rental rent = new Rental();
					rent.setHouseid(resultSet.getInt(1));
					rent.setRentaddress(resultSet.getString(2));
					rent.setRentarea(resultSet.getString(3));
					rent.setZipcode(resultSet.getInt(4));
					rent.setHousetype(resultSet.getString(5));
					rent.setPrice(resultSet.getInt(6));
					searchList.add(rent);
				}
				
			}
		}catch(Exception e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
		return searchList;
	}
	
	public ArrayList<Rental> searchRent(Rental rental)throws SQLException
	{
		ArrayList<Rental> searchList = new ArrayList<>();
		try {
			connection = DBUtils.getConnection();
			
			prepareStatement = connection.prepareStatement(SqlConstant.SEARCH_RENT);
			prepareStatement.setInt(1, rental.getHouseid());
			resultSet=prepareStatement.executeQuery();
			while(resultSet.next())
			{
				Rental rent = new Rental();
				
				rent.setHouseid(resultSet.getInt(1));
				rent.setRentaddress(resultSet.getString(2));
				rent.setRentarea(resultSet.getString(3));
				rent.setZipcode(resultSet.getInt(4));
				rent.setLandmark(resultSet.getString(5));
				rent.setHousetype(resultSet.getString(6));
				rent.setPrice(resultSet.getInt(7));
				rent.setDeposit(resultSet.getInt(8));
				rent.setBuiltSqFeet(resultSet.getInt(9));
				rent.setTotalFloor(resultSet.getInt(10));
				rent.setFloorNo(resultSet.getInt(11));
				
				searchList.add(rent);
				
			}	
			return searchList;
			
		}catch(SQLException e)
		{
			throw e;
		
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	
	public ArrayList<Rental> searchPg(Rental rental)throws SQLException
	{
		ArrayList<Rental> searchList = new ArrayList<>();
		try {
			
			connection = DBUtils.getConnection();
			prepareStatement = connection.prepareStatement(SqlConstant.SEARCH_PG);
			prepareStatement.setInt(1, rental.getHouseid());
			resultSet=prepareStatement.executeQuery();
			while(resultSet.next())
			{
				Rental rent = new Rental();
				rent.setHouseid(resultSet.getInt(1));
				rent.setRentaddress(resultSet.getString(2));
				rent.setRentarea(resultSet.getString(3));
				rent.setZipcode(resultSet.getInt(4));
				rent.setLandmark(resultSet.getString(5));
				rent.setHousetype(resultSet.getString(6));
				rent.setPrice(resultSet.getInt(7));
				rent.setPgSharing(resultSet.getString(8));
				rent.setGender(resultSet.getString(9));
				searchList.add(rent);
			}
			
		}catch(Exception e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
		return searchList;
	}
	/*
	 * This method allows to display rating and review
	 */
	public ArrayList<Rental>  displayReview(Rental rental)throws SQLException
	{
		ArrayList<Rental> reviewList = new ArrayList<>();
		try {
			connection = DBUtils.getConnection();
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_REVIEW_RATING);
			prepareStatement.setInt(1,rental.getHouseid());
			resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next())
			{
				Rental rent = new Rental();
				
				System.out.println(resultSet.getString(1));
				rent.setFname(resultSet.getString(1));
				rent.setRating(resultSet.getFloat(2));
				rent.setReview(resultSet.getString(3));
				reviewList.add(rent);
			}
			return reviewList;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	/*
	 * This method displays the facilities
	 */
	public ArrayList<Rental> displayFacility(Rental rental)throws SQLException
	{
		ArrayList<Rental> facilityList = new ArrayList<>();
		try {
			connection = DBUtils.getConnection();

			prepareStatement2 = connection.prepareStatement(SqlConstant.SELECT_FACILITY_ID);
			prepareStatement2.setInt(1,rental.getHouseid());
			resultSet2 = prepareStatement2.executeQuery();
			while(resultSet2.next())
			{
				prepareStatement3 = connection.prepareStatement(SqlConstant.RETRIEVE_FACILITY);
				prepareStatement3.setInt(1,resultSet2.getInt(1));
				resultSet3 = prepareStatement3.executeQuery();
				Rental rent = new Rental();
				while(resultSet3.next())
				{
				rent.setFacility(resultSet3.getString(1));
				facilityList.add(rent);
				}
			}
			return facilityList;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement2, resultSet2);
			DBUtils.closeJDBCConnection(prepareStatement3, resultSet3);
		}
	}
	/*
	 * This method displays the owner constant
	 */
	public long displayContact(Rental rental)throws SQLException
	{
		try {
			
			connection = DBUtils.getConnection();
			long phoneNo = 0;
			prepareStatement0 = connection.prepareStatement(SqlConstant.DISPLAY_CONTACT);
			prepareStatement0.setInt(1, rental.getHouseid());
			resultSet0 = prepareStatement0.executeQuery();
			
			while(resultSet0.next()) {
				
				System.out.println(1);
				phoneNo = resultSet0.getLong(1);
				
			}
			return phoneNo;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement0, resultSet0);
		}
		
	}
	/*
	 * This method allows user to
	 * mark interest on rental house
	 */
	public int checkMarkInterest(Rental rental)throws SQLException
	{
		try {
			connection = DBUtils.getConnection();
			int userId = 0;
			int houseId = 0;
			int flag = 0;
			prepareStatement = connection.prepareStatement(SqlConstant.CHECK_INTEREST);
			prepareStatement.setString(1, rental.getEmail());
			prepareStatement.setInt(2, rental.getHouseid());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
			{
				userId = resultSet.getInt(1);
				houseId = resultSet.getInt(2);
			}
			if(userId == 0 && houseId == 0)
			{
				flag = 1;
			}
			return flag;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	
	public int markInterest(Rental rental)throws SQLException
	{
		try {
			
			connection = DBUtils.getConnection();
			int counter = 0;
			int userId = 0;
			int statusId = 0;
			int flag = 0;
			String name="";
			
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_EMAIL);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
				if(resultSet.getString(2).equals(rental.getEmail()))
				{
					userId = resultSet.getInt(1);
					name = resultSet.getString(3);
				}
			
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_USER_INTEREST_STATUS);
			prepareStatement.setInt(1, rental.getHouseid());
			resultSet = prepareStatement.executeQuery();
			
			while(resultSet.next())
			{
				statusId = resultSet.getInt(1);
				if(statusId !=2)
					continue;
				else
					flag = 1;
			}
		
			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_USER_INTEREST);
			prepareStatement.setInt(1, rental.getHouseid());
			prepareStatement.setInt(2, userId);
			prepareStatement.setInt(3,rental.getReqpay());
			prepareStatement.setInt(4, 1);
			prepareStatement.setString(5, name);
			prepareStatement.setString(6, name);
			prepareStatement.executeUpdate();
			counter = 1;
				
			
			return counter;
			
		}catch(SQLException e)
		{
			throw e;
			
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
		
	}
	public ArrayList<Rental> displayAddress(Rental rental)throws SQLException
	{
		ArrayList<Rental> addressList = new ArrayList<>();
		try {
			
			connection = DBUtils.getConnection(); 
			prepareStatement = connection.prepareStatement(SqlConstant.DISPLAY_ADDRESS);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			while(resultSet.next())
			{
				Rental rent = new Rental();
				rent.setHouseid(resultSet.getInt(1));
				rent.setHousetype(resultSet.getString(2));
				rent.setRentaddress(resultSet.getString(3));
				rent.setRentarea(resultSet.getString(4));

				addressList.add(rent);
			}
			return addressList;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	/*
	 * This method allows to add review and rating
	 */
	public void addReview(Rental rental)throws SQLException
	{
		try {
			
			connection = DBUtils.getConnection();
			String name="";
			
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_EMAIL);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			int userId = 0;
			if(resultSet.next())
				if(resultSet.getString(2).equals(rental.getEmail()))
				{
					userId = resultSet.getInt(1);
					name = resultSet.getString(2);
				}
			
			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_REVIEW);
			prepareStatement.setInt(1, userId);
			prepareStatement.setInt(2, rental.getHouseid());
			prepareStatement.setDouble(3, rental.getRating());
			prepareStatement.setString(4, rental.getReview());
			prepareStatement.setString(5, name);
			prepareStatement.setString(6, name);
			prepareStatement.executeUpdate();
			
			
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	
	public int checkReviewRating(Rental rental)throws SQLException
	{
		try {
			connection = DBUtils.getConnection();
			int userId = 0;
			int houseId = 0;
			int flag = 0;
			prepareStatement = connection.prepareStatement(SqlConstant.CHECK_REVIEW_RATING);
			prepareStatement.setString(1, rental.getEmail());
			prepareStatement.setInt(2, rental.getHouseid());
			resultSet = prepareStatement.executeQuery();
			
			if(resultSet.next())
			{
				userId = resultSet.getInt(1);
				houseId = resultSet.getInt(2);
				
			}
			
			if(userId == 0 && houseId == 0)
				flag = 1;
			return flag;
		}catch(SQLException e)
		{
			throw e;
		}finally {
		
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	
	public ArrayList<Rental> viewApprovedHouse(Rental rental)throws SQLException
	{
		try {
			ArrayList<Rental> approvedHouseList = new ArrayList<Rental>();
			connection = DBUtils.getConnection();
			prepareStatement = connection.prepareStatement(SqlConstant.VIEW_APPROVED_HOUSE);
			prepareStatement.setString(1,rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
			{
				Rental rent = new Rental();
				rent.setHouseid(resultSet.getInt(1));
				rent.setRentaddress(resultSet.getString(2));
				rent.setRentarea(resultSet.getString(3));
				rent.setHousetype(resultSet.getString(4));
				rent.setPrice(resultSet.getInt(5));
				rent.setStatus(resultSet.getString(6));
				approvedHouseList.add(rent);
			}
			
			return approvedHouseList;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	
	public ArrayList<Rental> viewUserStatus(Rental rental)throws SQLException
	{
		ArrayList<Rental> userStatusList = new ArrayList<Rental>();
		try {
			connection = DBUtils.getConnection();
			prepareStatement = connection.prepareStatement(SqlConstant.VIEW_USER_STATUS);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			while(resultSet.next())
			{
				Rental rent = new Rental();
				rent.setHouseid(resultSet.getInt(1));
				rent.setRentaddress(resultSet.getString(2));
				rent.setRentarea(resultSet.getString(3));
				rent.setHousetype(resultSet.getString(4));
				rent.setPrice(resultSet.getInt(5));
				rent.setStatus(resultSet.getString(6));
				userStatusList.add(rent);
			}
			return userStatusList;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	public void conformHouse(Rental rental)throws SQLException
	{
		try {
			connection = DBUtils.getConnection();
			int userId = 0;
			int houseId = 0;
			
			prepareStatement = connection.prepareStatement(SqlConstant.SELECT_USERID);
			prepareStatement.setString(1,rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
			{
				userId = resultSet.getInt(1);
			}
			
			prepareStatement = connection.prepareStatement(SqlConstant.UPDATE_CONFIRMED_STATUS);
			prepareStatement.setInt(1,rental.getHouseid());
			prepareStatement.setInt(2, userId);
			prepareStatement.executeUpdate();
			
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIVE_USER_HOUSE_ID);
			prepareStatement.setInt(1,rental.getHouseid());
			prepareStatement.setString(2,rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
			{
				userId = resultSet.getInt(1);
				houseId = resultSet.getInt(2);
			}
			
			prepareStatement = connection.prepareStatement(SqlConstant.UPDATE_USER_REJECTED);
			prepareStatement.setInt(1, userId);
			prepareStatement.executeUpdate();
			
			prepareStatement = connection.prepareStatement(SqlConstant.UPDATE_HOUSE_REJECTED);
			prepareStatement.setInt(1, houseId);
			prepareStatement.executeUpdate();
		}catch(SQLException e)
		{
			throw e;
		}finally {
		
			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
	
}
