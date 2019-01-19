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

public class RentalDAO {

	private Connection conn = null;
	private PreparedStatement pst,pst0,pst1,pst2,pst3 = null;
	private ResultSet rs,rs0,rs1,rs2,rs3 = null;
	
	/*
	 * This method registers the 
	 * user details
	 */
	public void registerUser(User user)throws SQLException
	{
		try {
		
			conn = DBUtils.getConnection();
			/*
			 * ROLE
			 */
			pst = conn.prepareStatement(SqlConstant.SELECT_ROLE);
			pst.setString(1, user.getRole());
			rs = pst.executeQuery();
			int roleid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(user.getRole()))
				{
					String id = rs.getString(1);
					roleid = Integer.parseInt(id);
				}
			
			/*
			 * AREA
			 */
			pst = conn.prepareStatement(SqlConstant.SELECT_AREA_ZIP);
			pst.setString(1, user.getArea());
			pst.setInt(2, user.getZipcode());
			rs = pst.executeQuery();
			int areaid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(user.getArea())&&rs.getString(3).equals(String.valueOf(user.getZipcode())))
				{
					String id = rs.getString(1);
					areaid = Integer.parseInt(id);
				}
			
			
			pst = conn.prepareStatement(SqlConstant.INSERT_USER_ADDRESS);
			pst.setString(1, user.getAddress());
			pst.setInt(2, areaid);
			pst.executeUpdate();
			//logger.log(Level.INFO, "Number of rows affected: " + counter);
			
			/*
			 * ADDRESS
			 */
			pst = conn.prepareStatement(SqlConstant.SELECT_USER_ADDRESS);
			pst.setString(1, user.getAddress());
			rs = pst.executeQuery();
			int addressid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(user.getAddress()))
				{
					String id = rs.getString(1);
					addressid = Integer.parseInt(id);
				}
			
			
			pst = conn.prepareStatement(SqlConstant.INSERT_USER);
			pst.setString(1, user.getFname());
			pst.setString(2, user.getEmail());
			pst.setString(3, user.getPassword());
			pst.setLong(4, user.getPhoneno());
			pst.setString(5, user.getOccupation());
			pst.setInt(6, addressid);
			pst.setInt(7, roleid);
			
			
			pst.executeUpdate();
			//logger.log(Level.INFO, "Number of rows affected: " + count);
			
			
		}catch(SQLException e)
		{
			throw e;
		}
		finally {
			
			DBUtils.closeConnection(conn, pst, rs);
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
			
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.SELECT_USER);
			pst.setString(1, user.getEmail());
			pst.setString(2, user.getPassword());
			int roleid = 0;
		
			rs = pst.executeQuery();
			if(rs.next())
			{
				if((rs.getString(1).equals(user.getEmail())) && (rs.getString(2).equals(user.getPassword())))
				{
					roleid = rs.getInt(3);					
				}
			}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_ROLE_ID);
			pst.setInt(1, roleid);
			rs = pst.executeQuery();
			if(rs.next())
			{
				if(rs.getString(1).equals(String.valueOf(roleid)))
				{
					role = rs.getString(2);
				}
			}
		}
		catch(SQLException e){
			
			throw e;	
		}
		finally{
			
			DBUtils.closeConnection(conn, pst, rs);
		}
		return role;
	}
	/*
	 * This method allows to add Rental details
	 */
	public int addRentalDetails(Rental rental)throws SQLException
	{
		try {
			
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.SELECT_TYPE);
			pst.setString(1, rental.getHousetype());
			rs = pst.executeQuery();
			int typeid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getHousetype()))
				{
					String id = rs.getString(1);
					typeid = Integer.parseInt(id);
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_AREA_ZIP);
			pst.setString(1, rental.getRentarea());
			pst.setInt(2, rental.getZipcode());
			rs = pst.executeQuery();
			int areaid = 0;
			int flag = 0;
			if(rs.next())
			{
				if(rs.getString(2).equalsIgnoreCase(rental.getRentarea())&&rs.getString(3).equals(String.valueOf(rental.getZipcode())))
				{
					String id = rs.getString(1);
					areaid = Integer.parseInt(id);
					flag = 1;
				}
			}
			
			pst = conn.prepareStatement(SqlConstant.INSERT_RENTAL_ADDRESS);
			pst.setString(1, rental.getRentaddress());
			pst.setInt(2, areaid);
			pst.setDouble(3, 0.00);
			pst.setDouble(4, 0.00);
			pst.executeUpdate();
		
			pst = conn.prepareStatement(SqlConstant.SELECT_RENT_ADDID);
			pst.setString(1, rental.getRentaddress());
			rs = pst.executeQuery();
			int addressid = 0;
			if(rs.next())
				if(rs.getString(1).equals(String.valueOf(areaid)))
				{
					String id = rs.getString(2);
					addressid = Integer.parseInt(id);
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_EMAIL);
			pst.setString(1, rental.getEmail());
			rs = pst.executeQuery();
			int userid = 0;
			if(rs.next())
				if(rs.getString(2).equals(rental.getEmail()))
				{
					userid = rs.getInt(1);		
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_RENTAL_CHOICE);
			pst.setString(1, rental.getRentchoice());
			rs = pst.executeQuery();
			int choiceid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getRentchoice()))
				{
					choiceid = rs.getInt(1);
				}
			
			//LocalDateTime now = LocalDateTime.now();  
			//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
			//String formatDateTime = now.format(format);  
			pst = conn.prepareStatement(SqlConstant.INSERT_RENTAL_DETAILS);
			pst.setInt(1, userid);
			pst.setInt(2, typeid);
			pst.setInt(3, choiceid);
			pst.setInt(4, addressid);
			pst.setInt(5, 1);
			pst.setInt(6, rental.getPrice());
			//pst.setString(7, formatDateTime);
			pst.executeUpdate();
			//logger.log(Level.INFO, "Number of rows affected: " + cnt);
			
			return flag;
			
		}catch(SQLException e)
		{
			throw e;
		}
		finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
	}
	/*
	 * This method allows to add facilities
	 */
	public void addFacility(Rental rental)throws SQLException
	{
		try {
			
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.SELECT_EMAIL);
			pst.setString(1, rental.getEmail());
			rs = pst.executeQuery();
			int userid = 0;
			if(rs.next())
				if(rs.getString(2).equals(rental.getEmail()))
				{
					userid = rs.getInt(1);
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_RENT_ADDRESS);
			pst.setString(1, rental.getRentaddress());
			rs = pst.executeQuery();
			int addressid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getRentaddress()))
				{
					String id = rs.getString(1);
					addressid = Integer.parseInt(id);
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_RENTALADD_ID);
			pst.setInt(1,addressid);
			rs = pst.executeQuery();
			int houseid = 0;
			if(rs.next())
				if(rs.getString(2).equals(String.valueOf(userid)))
				{
					houseid = rs.getInt(1);		
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_FACILITY);
			pst.setString(1, rental.getFacility());
			rs = pst.executeQuery();
			int facilityid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getFacility()))
				{
					facilityid = rs.getInt(1);
				}
			
			pst = conn.prepareStatement(SqlConstant.INSERT_FACILITY);
			pst.setInt(1,facilityid);
			pst.setInt(2,houseid);
			pst.executeUpdate();
			//logger.log(Level.INFO, "Number of rows affected: " + cnt);
			
		}catch(SQLException e)
		{
			throw e;	
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
	}
	/*
	 * This method allows to update rent type
	 */
	public int updateRentType(Rental rental)throws SQLException
	{
		int flag = 0;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.SELECT_RENT_ADDRESS);
			pst.setString(1, rental.getRentaddress());
			rs = pst.executeQuery();
			int addressid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getRentaddress()))
				{
					String id = rs.getString(1);
					addressid = Integer.parseInt(id);
				}
	
			pst = conn.prepareStatement(SqlConstant.SELECT_RENTALADD_ID);
			pst.setInt(1, addressid);
			rs = pst.executeQuery();
			int statusid = 0;
			if(rs.next())
				if(rs.getString(5).equals(String.valueOf(addressid)))
				{
					statusid = rs.getInt(6);
				}
			
			if(statusid == 2)
			{
				pst = conn.prepareStatement(SqlConstant.SELECT_TYPE);
				pst.setString(1, rental.getHousetype());
				rs = pst.executeQuery();
				int typeid = 0;
				if(rs.next())
					if(rs.getString(2).equalsIgnoreCase(rental.getHousetype()))
					{
						String id = rs.getString(1);
						typeid = Integer.parseInt(id);
					}
				
				pst = conn.prepareStatement(SqlConstant.UPDATE_TYPE);
				pst.setInt(1, typeid);
				pst.setInt(2, addressid);
				pst.executeUpdate();
				//logger.log(Level.INFO, "Number of rows affected: " + cnt);
				flag = 1;
			}			
			
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
		return flag;
	}
	/*
	 * This method allows to update rent choice
	 */
	public int updateRentChoice(Rental rental)throws SQLException
	{
		int flag = 0;
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.SELECT_RENT_ADDRESS);
			pst.setString(1, rental.getRentaddress());
			rs = pst.executeQuery();
			int addressid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getRentaddress()))
				{
					String id = rs.getString(1);
					addressid = Integer.parseInt(id);
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_RENTALADD_ID);
			pst.setInt(1, addressid);
			rs = pst.executeQuery();
			int statusid = 0;
			if(rs.next())
				if(rs.getString(5).equals(String.valueOf(addressid)))
				{
					statusid = rs.getInt(6);
				}
			
			if(statusid == 2)
			{
				pst = conn.prepareStatement(SqlConstant.SELECT_RENTAL_CHOICE);
				pst.setString(1, rental.getRentchoice());
				rs = pst.executeQuery();
				int choiceid = 0;
				if(rs.next())
					if(rs.getString(2).equalsIgnoreCase(rental.getRentchoice()))
					{
						choiceid = rs.getInt(1);
					}
				
				pst = conn.prepareStatement(SqlConstant.UPDATE_CHOICE);
				pst.setInt(1, choiceid);
				pst.setInt(2, addressid);
				pst.executeUpdate();
				//logger.log(Level.INFO, "Number of rows affected: " + cnt);
				flag = 1;
			}
			
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
		return flag;
	}
	/*
	 * This method allows to update rent address
	 */
	public int updateRentAddress(Rental rental)throws SQLException
	{
		int flag = 0;
		try {
			
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.SELECT_RENT_ADDRESS);
			pst.setString(1, rental.getRentaddress());
			rs = pst.executeQuery();
			int addressid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getRentaddress()))
				{
					String id = rs.getString(1);
					addressid = Integer.parseInt(id);
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_RENTALADD_ID);
			pst.setInt(1, addressid);
			rs = pst.executeQuery();
			int statusid = 0;
			if(rs.next())
				if(rs.getString(5).equals(String.valueOf(addressid)))
				{
					statusid = rs.getInt(6);
				}
			
			if(statusid == 2)
			{
				pst = conn.prepareStatement(SqlConstant.SELECT_AREA);
				pst.setString(1, rental.getRentarea());
				rs = pst.executeQuery();
				int areaid = 0;
				if(rs.next())
				{
					if(rs.getString(2).equalsIgnoreCase(rental.getRentarea()))
					{
						String id = rs.getString(1);
						areaid = Integer.parseInt(id);
					}
				}
				pst = conn.prepareStatement(SqlConstant.UPDATE_AREA );
				pst.setInt(1, areaid);
				pst.setInt(2, addressid);
				pst.executeUpdate();
				//logger.log(Level.INFO, "Number of rows affected: " + count);
				
				pst = conn.prepareStatement(SqlConstant.UPDATE_RENT_ADDRESS);
				pst.setString(1, rental.getNewrentaddress());
				pst.setInt(2, addressid);
				pst.executeUpdate();
				//logger.log(Level.INFO, "Number of rows affected: " + cnt);
				flag = 1;
			}
			
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
		return flag;
	}
	/*
	 * This method allows to update rent price
	 */
	public int updateRentPrice(Rental rental)throws SQLException
	{
		int flag = 0;
		try {
			
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.SELECT_RENT_ADDRESS);
			pst.setString(1, rental.getRentaddress());
			rs = pst.executeQuery();
			int addressid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getRentaddress()))
				{
					String id = rs.getString(1);
					addressid = Integer.parseInt(id);
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_RENTALADD_ID);
			pst.setInt(1, addressid);
			rs = pst.executeQuery();
			int statusid = 0;
			if(rs.next())
				if(rs.getString(5).equals(String.valueOf(addressid)))
				{
					statusid = rs.getInt(6);
				}
			
			if(statusid == 2)
			{
			
				pst = conn.prepareStatement(SqlConstant.UPDATE_PRICE);
				pst.setInt(1, rental.getPrice());
				pst.setInt(2, addressid);
				pst.executeUpdate();
				//logger.log(Level.INFO, "Number of rows affected: " + cnt);
				flag = 1;
			}
			
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
		return flag;
	}
	public ArrayList<Rental> displayAddress(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			
			conn = DBUtils.getConnection(); 
			pst = conn.prepareStatement(SqlConstant.DISPLAY_ADDRESS);
			rs = pst.executeQuery();
			while(rs.next())
			{
				Rental rent = new Rental();
				rent.setRentaddress(rs.getString(1));
				rent.setRentarea(rs.getString(2));

				al.add(rent);
			}
			return al;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			DBUtils.closeConnection(conn, pst, rs);
		}
	}
	/*
	 * This method allows to add review and rating
	 */
	public void addReview(Rental rental)throws SQLException
	{
		try {
			
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.SELECT_RENT_ADDRESS);
			pst.setString(1, rental.getRentaddress());
			rs = pst.executeQuery();
			int addressid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getRentaddress()))
				{
					String id = rs.getString(1);
					addressid = Integer.parseInt(id);
				}
			
			
			pst = conn.prepareStatement(SqlConstant.SELECT_RENTALADD_ID);
			pst.setInt(1,addressid);
			rs = pst.executeQuery();
			int houseid = 0;
			int statusid = 0;
			if(rs.next())
				if(rs.getString(5).equals(String.valueOf(addressid)))
				{
					houseid = rs.getInt(1);
					statusid = rs.getInt(6);
					
				}
		
			if(statusid==2)
			{
			pst = conn.prepareStatement(SqlConstant.SELECT_EMAIL);
			pst.setString(1, rental.getEmail());
			rs = pst.executeQuery();
			int userid = 0;
			if(rs.next())
				if(rs.getString(2).equals(rental.getEmail()))
				{
					userid = rs.getInt(1);
					
				}
			
			pst = conn.prepareStatement(SqlConstant.INSERT_REVIEW);
			pst.setInt(1, userid);
			pst.setInt(2, houseid);
			pst.setInt(3, rental.getRating());
			pst.setString(4, rental.getReview());
			pst.executeUpdate();
			//logger.log(Level.INFO, "Number of rows affected: " + cnt);
			}
			
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
	}
	/*
	 * This method allows to display rating and review
	 */
	public ArrayList<Rental>  displayReview(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.SELECT_REVIEW_ID);
			pst.setInt(1,rental.getHouseid());
			rs = pst.executeQuery();
			
			while(rs.next())
			{
				Rental rent = new Rental();
				//logger.log(Level.INFO,"\nRating:"+rs.getInt(1)+"\nReview:"+rs.getString(2));
				rent.setRating(rs.getInt(1));
				rent.setReview(rs.getString(2));
				al.add(rent);
			}
			return al;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
	}
	/*
	 * This method displays the facilities
	 */
	public ArrayList<Rental> displayFacility(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();

			pst2 = conn.prepareStatement(SqlConstant.SELECT_HOUSE_ID);
			pst2.setInt(1,rental.getHouseid());
			rs2 = pst2.executeQuery();
			while(rs2.next())
			{
				pst3 = conn.prepareStatement(SqlConstant.SELECT_FACILITY_ID);
				pst3.setInt(1,rs2.getInt(1));
				rs3 = pst3.executeQuery();
				Rental rent = new Rental();
				while(rs3.next())
				{
				rent.setFacility(rs3.getString(2));
				al.add(rent);
				}
			}
			return al;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst2, rs2);
			DBUtils.closeJDBCConnection(pst3, rs3);
		}
	}
	/*
	 * This method displays the owner constant
	 */
	public long displayContact(Rental rental)throws SQLException
	{
		try {
			
			conn = DBUtils.getConnection();
			pst0 = conn.prepareStatement(SqlConstant.DISPLAY_CONTACT);
			pst0.setInt(1, rental.getHouseid());
			rs0 = pst0.executeQuery();
			long phoneno = 0;
			
			while(rs0.next()) {
				
				System.out.println(1);
				phoneno = rs0.getLong(1);
				
			}
			return phoneno;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst0, rs0);
		}
		
	}
	/*
	 * This method search the house by location
	 */
	public ArrayList<Rental> search(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			
			pst = conn.prepareStatement(SqlConstant.SEARCH_LOCATION);
			pst.setString(1,rental.getRentarea());
			pst.setString(2, rental.getRentchoice());
			pst.setInt(3, 2);
			rs=pst.executeQuery();
			while(rs.next())
			{
				Rental rent = new Rental();
				//logger.log(Level.INFO,"\nHOUSE_ID:"+rs.getInt(1)+"\nADDRESS:"+rs.getString(2)+"\nAREA:"+rs.getString(3)+"\nZIPCODE:"+rs.getInt(4)+"\nTYPE:"+rs.getString(5)+"\nPRICE:"+rs.getInt(6));
				
				rent.setHouseid(rs.getInt(1));
				rent.setRentaddress(rs.getString(2));
				rent.setRentarea(rs.getString(3));
				rent.setZipcode(rs.getInt(4));
				rent.setHousetype(rs.getString(5));
				rent.setPrice(rs.getInt(6));
				
				al.add(rent);
				
			}	
			return al;
			
		}catch(SQLException e)
		{
			throw e;
		
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
	}
	/*
	 * This method allows user to
	 * mark interest on rental house
	 */
	public int markInterest(Rental rental)throws SQLException
	{
		try {
			
			conn = DBUtils.getConnection();
			int counter = 0;
			pst = conn.prepareStatement(SqlConstant.SELECT_EMAIL);
			pst.setString(1, rental.getEmail());
			rs = pst.executeQuery();
			int userid = 0;
			if(rs.next())
				if(rs.getString(2).equals(rental.getEmail()))
				{
					userid = rs.getInt(1);
					
				}
			pst = conn.prepareStatement(SqlConstant.SELECT_USER_INTEREST);
			pst.setInt(1, rental.getHouseid());
			rs = pst.executeQuery();
			int statusid = 0;
			int flag = 0;
			while(rs.next())
			{
				statusid = rs.getInt(4);
				if(statusid !=2)
					continue;
				else
					flag = 1;
			}
			if(flag!=1)
			{
				pst = conn.prepareStatement(SqlConstant.INSERT_USER_INTEREST);
				pst.setInt(1, rental.getHouseid());
				pst.setInt(2, userid);
				pst.setInt(3,rental.getReqpay());
				pst.setInt(4, 1);
				pst.executeUpdate();
				counter = 1;
				//logger.log(Level.INFO, "Number of rows affected: " + cnt);
			}
			return counter;
			
		}catch(SQLException e)
		{
			throw e;
			
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
		
	}
	/*
	 * This method allows owner to view interested tenant
	 * for the added rental house.
	 */
	public ArrayList<User> viewInterestedTenant(User user)throws SQLException
	{
		ArrayList<User> al = new ArrayList<>();
		try {
			
			conn = DBUtils.getConnection();
			
			pst = conn.prepareStatement(SqlConstant.SELECT_USER_INTEREST);
			pst.setInt(1, user.getId());
			rs = pst.executeQuery();
			int tuserid = 0;
			int useraddid = 0;
			int areaid = 0;
			int reqpay = 0;
			while(rs.next())
			{
				User rentuser = new User();
				tuserid = rs.getInt(2);
				reqpay = rs.getInt(3);
				pst1 = conn.prepareStatement(SqlConstant.SELECT_USER_ID);
				pst1.setInt(1, tuserid);
				rs1 = pst1.executeQuery();
				if(rs1.next())
				{
					//logger.log(Level.INFO, "\nUSER ID:"+rs1.getInt(1)+"\nFNAME:"+rs1.getString(2)+"\nEMAIL:"+rs1.getString(3)+"\nPHONE NO:"+rs1.getLong(5)+"\nOCCUPATION:"+rs1.getString(6));
					useraddid = rs1.getInt(7);
					rentuser.setId(rs1.getInt(1));
					rentuser.setFname(rs1.getString(2));
					rentuser.setEmail(rs1.getString(3));
					rentuser.setPhoneno(rs1.getLong(5));
					rentuser.setOccupation(rs1.getString(6));
					
				}
				pst2 = conn.prepareStatement(SqlConstant.SELECT_ADDRESS_ID);
				pst2.setInt(1, useraddid);
				rs2 = pst2.executeQuery();
				if(rs2.next())
				{
					//logger.log(Level.INFO, "\nADDRESS:"+rs2.getString(2));
					areaid = rs2.getInt(3);
					rentuser.setAddress(rs2.getString(2));
					
				}
				pst3 = conn.prepareStatement(SqlConstant.SELECT_AREA_ID);
				pst3.setInt(1, areaid);
				rs3 = pst3.executeQuery();
				if(rs3.next())
				{
					//logger.log(Level.INFO, "\nAREA:"+rs3.getString(2)+"\nZIPCODE:"+rs3.getString(3));
					rentuser.setArea(rs3.getString(2));
					rentuser.setZipcode(rs3.getInt(3));
					rentuser.setRequestpay( rs.getInt(3));
				}
				al.add(rentuser);
			}
			return al;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
			DBUtils.closeJDBCConnection(pst1, rs1);
			DBUtils.closeJDBCConnection(pst2, rs2);
			DBUtils.closeJDBCConnection(pst3, rs3);
		}
	}
	/*
	 * This method allows to accept user request.
	 */
	public void acceptUserRequest(Rental rental)throws SQLException
	{
		try {
			conn = DBUtils.getConnection();
			pst = conn.prepareStatement(SqlConstant.UPDATE_STATUS_ID);
			pst.setInt(1, 2);
			pst.setInt(2, rental.getUserid());
			pst.executeUpdate();
			
			pst = conn.prepareStatement(SqlConstant.SELECT_RENT_ADDRESS);
			pst.setString(1, rental.getRentaddress());
			rs = pst.executeQuery();
			int addressid = 0;
			if(rs.next())
				if(rs.getString(2).equalsIgnoreCase(rental.getRentaddress()))
				{
					String id = rs.getString(1);
					addressid = Integer.parseInt(id);
				}
			
			pst = conn.prepareStatement(SqlConstant.SELECT_RENTALADD_ID);
			pst.setInt(1,addressid);
			rs = pst.executeQuery();
			int houseid = 0;
			if(rs.next())
				if(rs.getString(5).equals(String.valueOf(addressid)))
				{
					houseid = rs.getInt(1);
				}
			
			pst = conn.prepareStatement(SqlConstant.UPDATE_STATUS);
			pst.setInt(1, 3);
			pst.setInt(2, rental.getUserid());
			pst.setInt(3, houseid);
			pst.executeUpdate();
			//logger.log(Level.INFO, "Number of rows affected: " + cnt);
			
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
	}
	/*
	 * This method allows to view house details
	 */
	public ArrayList<Rental> viewHouse(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			conn = DBUtils.getConnection();
			
			pst = conn.prepareStatement(SqlConstant.VIEW_HOUSE);
			pst.setString(1, rental.getEmail());
			rs = pst.executeQuery();
			while(rs.next())
			{
				Rental rent = new Rental();
				rent.setHouseid(rs.getInt(1));
				rent.setRentaddress(rs.getString(2));
				rent.setRentarea(rs.getString(3));
				rent.setZipcode(rs.getInt(4));
				rent.setPrice(rs.getInt(5));
				al.add(rent);
			}
			return al;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
		}
	}
	/*
	 * This method allows owner to view the status 
	 * for the requested house.
	 */
	public ArrayList<Rental> viewStatus(Rental rental)throws SQLException
	{
		ArrayList<Rental> al = new ArrayList<>();
		try {
			
			conn = DBUtils.getConnection();
			
			pst1 = conn.prepareStatement(SqlConstant.SELECT_RENTAL);
			pst1.setString(1,rental.getEmail());
			rs1 = pst1.executeQuery();
			int addressid = 0;
			int areaid = 0;
			int statusid = 0;
			while(rs1.next())
			{
				Rental rent = new Rental();
				addressid = rs1.getInt(2);
				statusid = rs1.getInt(3);
				
				pst2 = conn.prepareStatement(SqlConstant. SELECT_ADDRESS_AREA);
				pst2.setInt(1, addressid);
				rs2 = pst2.executeQuery();
				if(rs2.next())
				{
					//logger.log(Level.INFO, "\nADDRESS:"+rs2.getString(1));
					rent.setRentaddress(rs2.getString(1));
					areaid = rs2.getInt(2);
					
				}
				pst3 = conn.prepareStatement(SqlConstant.SELECT_AREA_ID);
				pst3.setInt(1, areaid);
				rs3 = pst3.executeQuery();
				if(rs3.next())
				{
					//logger.log(Level.INFO, "\nAREA:"+rs3.getString(2)+"\nZIPCODE:"+rs3.getString(3));
					rent.setRentarea(rs3.getString(2));
					rent.setZipcode(rs3.getInt(3));
				}
				pst = conn.prepareStatement(SqlConstant.SELECT_STATUS_ID);
				pst.setInt(1, statusid);
				rs = pst.executeQuery();
				if(rs.next())
				{
					//logger.log(Level.INFO, "\nSTATUS:"+rs3.getString(2));
					rent.setStatus(rs.getString(1));
				}
				al.add(rent);
			}	
			
			return al;
		}catch(SQLException e)
		{
			throw e;
		}finally {
			
			DBUtils.closeConnection(conn, pst, rs);
			DBUtils.closeJDBCConnection(pst1, rs1);
			DBUtils.closeJDBCConnection(pst2, rs2);
			DBUtils.closeJDBCConnection(pst3, rs3);
		}
	}
}
