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

public class OwnerDAO {

	private Connection connection = null;
	private PreparedStatement prepareStatement, prepareStatement1, prepareStatement2, prepareStatement3 = null;
	private ResultSet resultSet, resultSet1, resultSet2, resultSet3 = null;

	/*
	 * This method allows to add Rental details
	 */
	public int addRentalDetails(Rental rental) throws SQLException {
		try {

			int typeId = 0;
			int areaId = 0;
			int flag = 0;
			int addressId = 0;
			int userId = 0;
			int choiceId = 0;
			String name = "";
			connection = DBUtils.getConnection();

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_TYPE);
			prepareStatement.setString(1, rental.getHousetype());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				String id = resultSet.getString(1);
				typeId = Integer.parseInt(id);
			}

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_AREA_ID);
			prepareStatement.setInt(1, rental.getZipcode());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				String id = resultSet.getString(1);
				areaId = Integer.parseInt(id);
				flag = 1;
			}

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_EMAIL);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next())
				if (resultSet.getString(2).equals(rental.getEmail())) {
					userId = resultSet.getInt(1);
					name = resultSet.getString(3);

				}

			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_RENTAL_ADDRESS);
			prepareStatement.setString(1, rental.getRentaddress());
			prepareStatement.setInt(2, areaId);
			prepareStatement.setDouble(3, 0.00);
			prepareStatement.setDouble(4, 0.00);
			prepareStatement.setString(5, name);
			prepareStatement.setString(6, name);
			prepareStatement.executeUpdate();

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_RENT_ADDRESS_ID);
			prepareStatement.setString(1, rental.getRentaddress());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next())
				if (resultSet.getString(1).equals(String.valueOf(areaId))) {
					String id = resultSet.getString(2);
					addressId = Integer.parseInt(id);
				}

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_RENTAL_CHOICE);
			prepareStatement.setString(1, rental.getRentchoice());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next())
				if (resultSet.getString(2).equalsIgnoreCase(rental.getRentchoice())) {
					choiceId = resultSet.getInt(1);

				}

			// LocalDateTime now = LocalDateTime.now();
			// DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy
			// HH:mm:ss");
			// String formatDateTime = now.format(format);
			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_RENTAL_DETAILS);
			prepareStatement.setInt(1, userId);
			prepareStatement.setInt(2, typeId);
			prepareStatement.setInt(3, choiceId);
			prepareStatement.setInt(4, addressId);
			prepareStatement.setInt(5, 1);
			prepareStatement.setInt(6, rental.getPrice());
			prepareStatement.setString(7, name);
			prepareStatement.setString(8, name);
			prepareStatement.setString(9, rental.getLandmark());
			prepareStatement.executeUpdate();
			// logger.log(Level.INFO, "Number of rows affected: " + cnt);

			prepareStatement = connection.prepareStatement(SqlConstant.UPDATE_ROLE_ID);
			prepareStatement.setInt(1, userId);
			prepareStatement.executeUpdate();

			return flag;

		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}

	/*
	 * This method allows to add facilities
	 */
	public void addRentDetails(Rental rental) throws SQLException {
		try {

			connection = DBUtils.getConnection();
			int houseId = 0;
			int userId = 0;
			String name = "";

			prepareStatement = connection.prepareStatement(SqlConstant.GET_HOUSE_ID);
			prepareStatement.setString(1, rental.getRentaddress());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				houseId = resultSet.getInt(1);
				userId = resultSet.getInt(2);
			}
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_NAME);
			prepareStatement.setInt(1, userId);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next())
				name = resultSet.getString(1);

			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_RENTAL_DESCRIPTION);
			prepareStatement.setInt(1, houseId);
			prepareStatement.setInt(2, rental.getBuiltSqFeet());
			prepareStatement.setInt(3, rental.getDeposit());
			prepareStatement.setInt(4, rental.getTotalFloor());
			prepareStatement.setInt(5, rental.getFloorNo());
			prepareStatement.setString(6, name);
			prepareStatement.setString(7, name);
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}

	public void addFacility(Rental rental) throws SQLException {
		try {

			connection = DBUtils.getConnection();
			int userId = 0;
			int addressId = 0;
			int houseId = 0;
			int facilityId = 0;
			String name = "";

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_EMAIL);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next())
				if (resultSet.getString(2).equals(rental.getEmail())) {
					userId = resultSet.getInt(1);
					name = resultSet.getString(3);
				}
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_ADDRESS);
			prepareStatement.setString(1, rental.getRentaddress());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next())
				if (resultSet.getString(2).equalsIgnoreCase(rental.getRentaddress())) {
					String id = resultSet.getString(1);
					addressId = Integer.parseInt(id);
				}

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_HOUSE_ID);
			prepareStatement.setInt(1, addressId);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {// if(resultSet.getString(2).equals(String.valueOf(userId)))
				houseId = resultSet.getInt(1);
			}

			prepareStatement = connection.prepareStatement(SqlConstant.SELECT_FACILITY);
			prepareStatement.setString(1, rental.getFacility());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next())
				if (resultSet.getString(2).equalsIgnoreCase(rental.getFacility())) {
					facilityId = resultSet.getInt(1);
				}

			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_FACILITY);
			prepareStatement.setInt(1, facilityId);
			prepareStatement.setInt(2, houseId);
			prepareStatement.setString(3, name);
			prepareStatement.setString(4, name);
			prepareStatement.executeUpdate();
			// logger.log(Level.INFO, "Number of rows affected: " + cnt);

		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}

	public void addPgDetails(Rental rental) throws SQLException {
		try {

			connection = DBUtils.getConnection();
			int addressId = 0;
			int houseId = 0;
			int pgId = 0;
			int genderId = 0;
			int userId = 0;
			String name = "";

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_ADDRESS);
			prepareStatement.setString(1, rental.getRentaddress());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next())
				if (resultSet.getString(2).equalsIgnoreCase(rental.getRentaddress())) {
					String id = resultSet.getString(1);
					addressId = Integer.parseInt(id);
				}

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_HOUSE_ID);
			prepareStatement.setInt(1, addressId);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {// if(resultSet.getString(2).equals(String.valueOf(userId)))
				houseId = resultSet.getInt(1);
				userId = resultSet.getInt(2);
			}

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_NAME);
			prepareStatement.setInt(1, userId);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next())
				name = resultSet.getString(1);

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_PG_GENDER);
			prepareStatement.setString(1, rental.getPgSharing());
			prepareStatement.setString(2, rental.getGender());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				pgId = resultSet.getInt(1);
				genderId = resultSet.getInt(2);
			}

			prepareStatement = connection.prepareStatement(SqlConstant.INSERT_PG_DESCRIPTION);
			prepareStatement.setInt(1, houseId);
			prepareStatement.setInt(2, pgId);
			prepareStatement.setInt(3, genderId);
			prepareStatement.setString(4, name);
			prepareStatement.setString(5, name);
			prepareStatement.executeUpdate();

		} catch (Exception e) {
			throw e;

		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}

	/*
	 * This method allows to update rent type
	 */
	public int updateRentType(Rental rental) throws SQLException {
		int flag = 0;
		try {
			connection = DBUtils.getConnection();

			int typeId = 0;

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_TYPE);
			prepareStatement.setString(1, rental.getHousetype());
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next())
				if (resultSet.getString(2).equalsIgnoreCase(rental.getHousetype())) {
					String id = resultSet.getString(1);
					typeId = Integer.parseInt(id);
				}

			prepareStatement = connection.prepareStatement(SqlConstant.UPDATE_TYPE);
			prepareStatement.setInt(1, typeId);
			prepareStatement.setInt(2, rental.getHouseid());
			prepareStatement.executeUpdate();
			flag = 1;

		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
		return flag;
	}

	/*
	 * This method allows to update rent address
	 */
	public int updateRentAddress(Rental rental) throws SQLException {
		int flag = 0;
		try {

			connection = DBUtils.getConnection();
			int addressId = 0;
			int areaId = 0;

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_AREA_ID);
			prepareStatement.setInt(1, rental.getZipcode());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				if (resultSet.getString(2).equalsIgnoreCase(rental.getRentarea()))
					areaId = resultSet.getInt(1);
			}
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_ADDRESS_ID);
			prepareStatement.setInt(1, rental.getHouseid());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next())
				if (resultSet.getString(2).equalsIgnoreCase(rental.getRentaddress()))
					addressId = resultSet.getInt(1);

			prepareStatement = connection.prepareStatement(SqlConstant.UPDATE_RENT_ADDRESS);
			prepareStatement.setString(1, rental.getNewrentaddress());
			prepareStatement.setInt(2, addressId);
			prepareStatement.executeUpdate();
			// logger.log(Level.INFO, "Number of rows affected: " + cnt);

			prepareStatement = connection.prepareStatement(SqlConstant.UPDATE_AREA);
			prepareStatement.setInt(1, areaId);
			prepareStatement.setInt(2, addressId);
			prepareStatement.executeUpdate();
			// logger.log(Level.INFO, "Number of rows affected: " + count);
			flag = 1;

		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
		return flag;
	}

	/*
	 * This method allows to update rent price
	 */
	public int updateRentPrice(Rental rental) throws SQLException {
		int flag = 0;
		try {

			connection = DBUtils.getConnection();

			prepareStatement = connection.prepareStatement(SqlConstant.UPDATE_PRICE);
			prepareStatement.setInt(1, rental.getPrice());
			prepareStatement.setInt(2, rental.getHouseid());
			prepareStatement.executeUpdate();
			// logger.log(Level.INFO, "Number of rows affected: " + cnt);
			flag = 1;

		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
		return flag;
	}

	/*
	 * This method allows to view house details
	 */
	public ArrayList<Rental> viewHouse(Rental rental) throws SQLException {
		ArrayList<Rental> houseList = new ArrayList<>();
		try {
			connection = DBUtils.getConnection();

			prepareStatement = connection.prepareStatement(SqlConstant.VIEW_HOUSE);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			int houseId = 0;
			while (resultSet.next()) {
				int flag = 0;

				houseId = resultSet.getInt(1);
				prepareStatement1 = connection.prepareStatement(SqlConstant.INTEREST_HOUSE_ID);
				prepareStatement1.setInt(1, houseId);
				resultSet1 = prepareStatement1.executeQuery();
				int id = 0;
				
				while (resultSet1.next()) {
					id = resultSet1.getInt(1);
					
				}
				if (id != 0) {
					
					prepareStatement2 = connection.prepareStatement(SqlConstant.GET_INTEREST_HOUSE_ID);
					prepareStatement2.setInt(1, houseId);
					resultSet2 = prepareStatement2.executeQuery();
					while (resultSet2.next()) {
						int rentHouseId = resultSet2.getInt(1);
						if (rentHouseId == id)
							flag = 1;
					}
				}
//				
				if (flag != 1) {
					Rental rent = new Rental();
					rent.setHouseid(resultSet.getInt(1));
					rent.setHousetype(resultSet.getString(2));
					rent.setRentaddress(resultSet.getString(3));
					rent.setRentarea(resultSet.getString(4));
					rent.setZipcode(resultSet.getInt(5));
					rent.setPrice(resultSet.getInt(6));
					houseList.add(rent);
				}
			}
			return houseList;
		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}

	public ArrayList<User> viewInterestedTenant(User user) throws SQLException {
		ArrayList<User> customerList = new ArrayList<>();
		try {

			connection = DBUtils.getConnection();

			int custId = 0;
			int userAddressId = 0;
			int areaId = 0;
			int reqPay = 0;
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_USER_INTEREST);
			prepareStatement.setInt(1, user.getId());
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				User rentuser = new User();
				custId = resultSet.getInt(1);
				reqPay = resultSet.getInt(2);
				prepareStatement1 = connection.prepareStatement(SqlConstant.RETRIEVE_USER);
				prepareStatement1.setInt(1, custId);
				resultSet1 = prepareStatement1.executeQuery();
				if (resultSet1.next()) {
					
					userAddressId = resultSet1.getInt(7);
					rentuser.setId(resultSet1.getInt(1));
					rentuser.setFname(resultSet1.getString(2));
					rentuser.setEmail(resultSet1.getString(3));
					rentuser.setPhoneno(resultSet1.getLong(5));
					rentuser.setOccupation(resultSet1.getString(6));

				}
				prepareStatement2 = connection.prepareStatement(SqlConstant.RETRIEVE_ADDRESS_AREA);
				prepareStatement2.setInt(1, userAddressId);
				resultSet2 = prepareStatement2.executeQuery();
				if (resultSet2.next()) {
					
					areaId = resultSet2.getInt(2);
					rentuser.setAddress(resultSet2.getString(1));

				}
				prepareStatement3 = connection.prepareStatement(SqlConstant.RETRIEVE_AREA);
				prepareStatement3.setInt(1, areaId);
				resultSet3 = prepareStatement3.executeQuery();
				if (resultSet3.next()) {
					
					rentuser.setArea(resultSet3.getString(2));
					rentuser.setZipcode(resultSet3.getInt(3));
					rentuser.setRequestpay(resultSet.getInt(2));
				}
				customerList.add(rentuser);
			}
			return customerList;
		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
			DBUtils.closeJDBCConnection(prepareStatement1, resultSet1);
			DBUtils.closeJDBCConnection(prepareStatement2, resultSet2);
			DBUtils.closeJDBCConnection(prepareStatement3, resultSet3);
		}
	}

	/*
	 * This method allows to accept user request.
	 */
	public int checkAcceptedRequest(Rental rental) throws SQLException {
		try {

			connection = DBUtils.getConnection();
			int userId = 0;
			int houseId = 0;
			int flag = 0;
			prepareStatement = connection.prepareStatement(SqlConstant.CHECK_ACCEPTED_REQUEST);
			prepareStatement.setString(1, rental.getEmail());
			prepareStatement.setInt(2, rental.getHouseid());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				userId = resultSet.getInt(1);
				houseId = resultSet.getInt(2);
			}
			if (userId == 0 && houseId == 0) {
				flag = 1;
			}
			return flag;
		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}

	public void acceptUserRequest(Rental rental) throws SQLException {
		try {
			connection = DBUtils.getConnection();
			int addressId = 0;
			int houseId = 0;
			prepareStatement = connection.prepareStatement(SqlConstant.UPDATE_STATUS_ID);
			prepareStatement.setInt(1, 2);
			prepareStatement.setInt(2, rental.getUserid());
			prepareStatement.setInt(3, rental.getHouseid());
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}

	public ArrayList<Rental> viewStatus(Rental rental) throws SQLException {
		ArrayList<Rental> statusList = new ArrayList<>();
		try {

			connection = DBUtils.getConnection();

			int addressId = 0;
			int areaId = 0;
			int statusId = 0;
			prepareStatement = connection.prepareStatement(SqlConstant.VIEW_STATUS);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				Rental rent = new Rental();

				rent.setRentaddress(resultSet.getString(1));
				rent.setRentarea(resultSet.getString(2));
				rent.setZipcode(resultSet.getInt(3));
				rent.setStatus(resultSet.getString(4));

				statusList.add(rent);
			}

			return statusList;
		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}

	public ArrayList<Rental> viewConformedCustomer(Rental rental) throws SQLException {
		try {
			ArrayList<Rental> customerList = new ArrayList<>();
			connection = DBUtils.getConnection();
			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_RENTAL_DETAILS_HOUSE_ID);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			int houseId = 0;
			int userId = 0;
			while (resultSet.next()) {
				houseId = resultSet.getInt(1);

				prepareStatement2 = connection.prepareStatement(SqlConstant.GET_INTEREST_HOUSE_ID);
				prepareStatement2.setInt(1, houseId);
				resultSet2 = prepareStatement2.executeQuery();
				if (resultSet2.next()) {

					Rental rent = new Rental();
					prepareStatement1 = connection.prepareStatement(SqlConstant.VIEW_CONFIRMED_HOUSE);
					prepareStatement1.setInt(1, houseId);
					resultSet1 = prepareStatement1.executeQuery();

					if (resultSet1.next()) {

						rent.setHouseid(resultSet2.getInt(1));
						rent.setRentaddress(resultSet1.getString(1));
						rent.setRentarea(resultSet1.getString(2));
						rent.setRentchoice(resultSet1.getString(3));
						rent.setPrice(resultSet1.getInt(4));
						rent.setStatus(resultSet1.getString(5));
					}
					customerList.add(rent);
				}

			}

			return customerList;
		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}

	public ArrayList<Rental> viewRejected(Rental rental) throws SQLException {
		try {
			ArrayList<Rental> customerList = new ArrayList<>();
			connection = DBUtils.getConnection();

			prepareStatement = connection.prepareStatement(SqlConstant.RETRIEVE_RENTAL_DETAILS_HOUSE_ID);
			prepareStatement.setString(1, rental.getEmail());
			resultSet = prepareStatement.executeQuery();
			int house = 0;
			int id = 0;
			while (resultSet.next()) {
				house = resultSet.getInt(1);

				prepareStatement2 = connection.prepareStatement(SqlConstant.GET_INTEREST_HOUSE_ID);
				prepareStatement2.setInt(1, house);
				resultSet2 = prepareStatement2.executeQuery();
				if (resultSet2.next()) {
					id = resultSet2.getInt(1);
					if (house == id)
						continue;
				}
				prepareStatement1 = connection.prepareStatement(SqlConstant.VIEW_REJECTED_HOUSE);
				prepareStatement1.setInt(1, house);
				resultSet1 = prepareStatement1.executeQuery();

				if (resultSet1.next()) {
				
					Rental rent = new Rental();
					rent.setHouseid(resultSet.getInt(1));
					rent.setRentaddress(resultSet1.getString(1));
					rent.setRentarea(resultSet1.getString(2));
					rent.setRentchoice(resultSet1.getString(3));
					rent.setPrice(resultSet1.getInt(4));
					rent.setStatus(resultSet1.getString(5));
					customerList.add(rent);
				}

			}
			return customerList;
		} catch (SQLException e) {
			throw e;
		} finally {

			DBUtils.closeConnection(connection, prepareStatement, resultSet);
		}
	}
}
