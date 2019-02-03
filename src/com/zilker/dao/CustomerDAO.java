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
			 * ROLE
			 
			int roleId = 0;
			prepareStatement = connection.prepareStatement(SqlConstant.SELECT_ROLE);
			prepareStatement.setString(1, user.getRole());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
				if(resultSet.getString(2).equalsIgnoreCase(user.getRole()))
				{
					String id = resultSet.getString(1);
					roleId = Integer.parseInt(id);
				}
			
			
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
			System.out.println(areaId);
			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_USER_ADDRESS);
			prepareStatement.setString(1, user.getAddress());
			prepareStatement.setInt(2, areaId);
			prepareStatement.setString(3, user.getFname());
			prepareStatement.setString(4, user.getFname());
			prepareStatement.executeUpdate();
			//logger.log(Level.INFO, "Number of rows affected: " + counter);
			
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
			//logger.log(Level.INFO, "Number of rows affected: " + count);
			
			
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
			
			prepareStatement = connection.prepareStatement("SELECT DISTINCT RENTAL_DETAILS.HOUSE_ID,ADDRESS.ADDRESS,AREA.AREA,AREA.ZIPCODE,HOUSE_TYPE.TYPE,RENTAL_DETAILS.PRICE FROM USER,HOUSE_TYPE,RENTAL_DETAILS,ADDRESS,AREA,RENTAL_OPTION,RENTAL_STATUS WHERE AREA = ? AND AREA.AREA_ID = ADDRESS.AREA_ID AND ADDRESS.ADDRESS_ID = RENTAL_DETAILS.ADDRESS_ID AND RENTAL_DETAILS.TYPE_ID=HOUSE_TYPE.TYPE_ID AND RENTAL_OPTION.RENTAL_CHOICE = ? AND RENTAL_OPTION.CHOICE_ID = RENTAL_DETAILS.CHOICE_ID AND RENTAL_DETAILS.STATUS_ID = 2 AND USER.EMAIL = ? AND USER.USER_ID != RENTAL_DETAILS.USER_ID");
			prepareStatement.setString(1, rental.getRentarea());
			prepareStatement.setString(2, rental.getRentchoice());
			prepareStatement.setString(3, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			int houseId = 0;
			
			while(resultSet.next())
			{
				int flag = 0;
				//System.out.println(resultSet.getInt(1));
				houseId = resultSet.getInt(1);
				prepareStatement0 = connection.prepareStatement("SELECT HOUSE_ID FROM USER_RENTAL_INTEREST WHERE USER_RENTAL_INTEREST.HOUSE_ID = ?");
				prepareStatement0.setInt(1, houseId);
				resultSet0 = prepareStatement0.executeQuery();
				int id = 0;
				//System.out.println(resultSet.getInt(1));
				while(resultSet0.next())
				{
					id = resultSet0.getInt(1);
					//System.out.println(resultSet0.getInt(1));
				}
				if(id !=0)
				{
					//System.out.println(resultSet.getInt(1));
					prepareStatement2 = connection.prepareStatement("SELECT HOUSE_ID FROM USER_RENTAL_INTEREST WHERE HOUSE_ID = ? AND USER_RENTAL_INTEREST.STATUS_ID =4");
					prepareStatement2.setInt(1, houseId);
					resultSet2 = prepareStatement2.executeQuery();
					while(resultSet2.next())
					{
						int rentHouseId = resultSet2.getInt(1);
						if(rentHouseId == id)
							flag = 1;
					}
				}
//				System.out.println(resultSet.getInt(1));
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
				//logger.log(Level.INFO,"\nHOUSE_ID:"+resultSet.getInt(1)+"\nADDRESS:"+resultSet.getString(2)+"\nAREA:"+resultSet.getString(3)+"\nZIPCODE:"+resultSet.getInt(4)+"\nTYPE:"+resultSet.getString(5)+"\nPRICE:"+resultSet.getInt(6));
				
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
				//logger.log(Level.INFO,"\nRating:"+resultSet.getInt(1)+"\nReview:"+resultSet.getString(2));
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
			prepareStatement = connection.prepareStatement("SELECT USER_RENTAL_INTEREST.USER_ID,USER_RENTAL_INTEREST.HOUSE_ID FROM USER_RENTAL_INTEREST,USER WHERE USER.EMAIL = ? AND USER.USER_ID = USER_RENTAL_INTEREST.USER_ID AND USER_RENTAL_INTEREST.HOUSE_ID = ?");
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
			System.out.println(userId);
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
			/*if(flag!=1)
			{*/
				System.out.println(userId);
				prepareStatement = connection.prepareStatement(SqlConstant.INSERT_USER_INTEREST);
				prepareStatement.setInt(1, rental.getHouseid());
				prepareStatement.setInt(2, userId);
				prepareStatement.setInt(3,rental.getReqpay());
				prepareStatement.setInt(4, 1);
				prepareStatement.setString(5, name);
				prepareStatement.setString(6, name);
				prepareStatement.executeUpdate();
				counter = 1;
				//logger.log(Level.INFO, "Number of rows affected: " + cnt);
			
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
			/*int addressId = 0;
			int houseId = 0;
			int statusId = 0;*/
			/*
			prepareStatement = connection.prepareStatement(SqlConstant.SELECT_RENT_ADDRESS);
			prepareStatement.setString(1, rental.getRentaddress());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
				if(resultSet.getString(2).equalsIgnoreCase(rental.getRentaddress()))
				{
					String id = resultSet.getString(1);
					addressId = Integer.parseInt(id);
				}
			
			
			prepareStatement = connection.prepareStatement(SqlConstant.SELECT_RENTALADD_ID);
			prepareStatement.setInt(1,addressId);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
				if(resultSet.getString(5).equals(String.valueOf(addressId)))
				{
					houseId = resultSet.getInt(1);
					statusId = resultSet.getInt(6);
					
				}
		
			if(statusId==2)
			{
			*/
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
			prepareStatement.setFloat(3, rental.getRating());
			prepareStatement.setString(4, rental.getReview());
			prepareStatement.setString(5, name);
			prepareStatement.setString(6, name);
			prepareStatement.executeUpdate();
			//logger.log(Level.INFO, "Number of rows affected: " + cnt);
			
			
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
			prepareStatement = connection.prepareStatement("SELECT DISTINCT USER_REVIEW.USER_ID,USER_REVIEW.HOUSE_ID FROM USER_REVIEW,USER WHERE USER.EMAIL = ? AND HOUSE_ID = ? AND USER_REVIEW.USER_ID = USER.USER_ID");
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
			prepareStatement = connection.prepareStatement("SELECT distinct USER_RENTAL_INTEREST.HOUSE_ID,ADDRESS,AREA,TYPE,PRICE,STATUS FROM USER_RENTAL_INTEREST,USER,RENTAL_DETAILS,ADDRESS,AREA,HOUSE_TYPE,RENTAL_STATUS WHERE USER.EMAIL = ? AND USER.USER_ID = USER_RENTAL_INTEREST.USER_ID AND USER_RENTAL_INTEREST.HOUSE_ID = RENTAL_DETAILS.HOUSE_ID AND RENTAL_DETAILS.ADDRESS_ID = ADDRESS.ADDRESS_ID AND ADDRESS.AREA_ID = AREA.AREA_ID AND HOUSE_TYPE.TYPE_ID = RENTAL_DETAILS.TYPE_ID AND USER_RENTAL_INTEREST.STATUS_ID = 2 AND USER_RENTAL_INTEREST.STATUS_ID = RENTAL_STATUS.STATUS_ID");
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
			prepareStatement = connection.prepareStatement("SELECT distinct USER_RENTAL_INTEREST.HOUSE_ID,ADDRESS,AREA,TYPE,PRICE,STATUS FROM USER_RENTAL_INTEREST,USER,RENTAL_DETAILS,ADDRESS,AREA,HOUSE_TYPE,RENTAL_STATUS WHERE USER.EMAIL = ? AND USER.USER_ID = USER_RENTAL_INTEREST.USER_ID AND USER_RENTAL_INTEREST.HOUSE_ID = RENTAL_DETAILS.HOUSE_ID AND RENTAL_DETAILS.ADDRESS_ID = ADDRESS.ADDRESS_ID AND ADDRESS.AREA_ID = AREA.AREA_ID AND HOUSE_TYPE.TYPE_ID = RENTAL_DETAILS.TYPE_ID AND RENTAL_STATUS.STATUS_ID = USER_RENTAL_INTEREST.STATUS_ID");
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
			
			prepareStatement = connection.prepareStatement("SELECT USER_ID FROM USER WHERE EMAIL = ?");
			prepareStatement.setString(1,rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
			{
				userId = resultSet.getInt(1);
			}
			System.out.println(userId);
			prepareStatement = connection.prepareStatement("UPDATE USER_RENTAL_INTEREST SET STATUS_ID = 4 WHERE HOUSE_ID = ? AND USER_ID = ?");
			prepareStatement.setInt(1,rental.getHouseid());
			prepareStatement.setInt(2, userId);
			prepareStatement.executeUpdate();
			System.out.println(rental.getHouseid());
			prepareStatement = connection.prepareStatement("SELECT USER_RENTAL_INTEREST.USER_ID,USER_RENTAL_INTEREST.HOUSE_ID FROM USER_RENTAL_INTEREST,USER WHERE HOUSE_ID = ? AND USER.EMAIL = ? AND USER.USER_ID = USER_RENTAL_INTEREST.USER_ID");
			prepareStatement.setInt(1,rental.getHouseid());
			prepareStatement.setString(2,rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next())
			{
				userId = resultSet.getInt(1);
				houseId = resultSet.getInt(2);
			}
			System.out.println(userId);
			System.out.println(houseId);
			prepareStatement = connection.prepareStatement("UPDATE USER_RENTAL_INTEREST SET STATUS_ID = 3 WHERE USER_ID = ? AND STATUS_ID != 4");
			prepareStatement.setInt(1, userId);
			prepareStatement.executeUpdate();
			
			prepareStatement = connection.prepareStatement("UPDATE USER_RENTAL_INTEREST SET STATUS_ID = 3 WHERE HOUSE_ID = ? AND STATUS_ID != 4");
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
