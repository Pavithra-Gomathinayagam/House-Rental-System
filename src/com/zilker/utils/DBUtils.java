package com.zilker.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.constant.SqlConstant;


public class DBUtils {
	private static final Logger logger = Logger.getLogger(DBUtils.class.getName());
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(SqlConstant.CONNECTION_STRING, SqlConstant.USERNAME, SqlConstant.PASSWORD);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error connecting with SQL Driver");
		}
		return conn;
	}
	
	public static void closeConnection (Connection conn, PreparedStatement pst, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(pst != null) {
				pst.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error closing the connection variables");
		}
	}
	
	public static void closeJDBCConnection (PreparedStatement pst, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(pst != null) {
				pst.close();
			}
			}catch (SQLException e) {
				logger.log(Level.SEVERE, "Error closing the connection variables");
			}
		}
}
