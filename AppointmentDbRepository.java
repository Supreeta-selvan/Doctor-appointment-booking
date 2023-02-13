package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AppointmentDbRepository {

	private AppointmentDbRepository() {

	}

	String signUpSql = " INSERT INTO Login (username, password)"
			+ " VALUES(?, ?)";
	String checkUserCredentialSql = "SELECT * FROM login"
			+ " WHERE username = ? AND password = ?";
	String getUserNamesSql = "SELECT username FROM login";
	String addUserDetailsSql = " INSERT INTO userdetails (name, age, city, contactnumber, roleindicator, userName)"
			+ " VALUES(?, ?, ?, ?, ?, ?)";
	String getRoleIndSql = "SELECT roleindicator FROM UserDetails"
			+ " WHERE username = ?";
	String addDoctorDetailsSql = " INSERT INTO specialistdetails (username, specialitycode, slotCount, name)"
			+ " VALUES(?, ?, ?, ?)";
	String cancelSlotsSql = " UPDATE specialistdetails"
			+ " SET slotcount = 0, bookedslot = 0"
			+ " WHERE username = ?";
	String getbookedSlotSql = " SELECT bookedslot FROM specialistdetails"
			+ " WHERE username = ?";
	String getUsersSql = " SELECT username FROM specialistdetails " 
			+ "WHERE specialitycode = ?";
	String getSpecialistNameSql = " SELECT name FROM specialistdetails " 
			+ "WHERE username = ?";
	String getAvailableDoctorSql = "SELECT username FROM specialistdetails "
			+ "WHERE specialitycode = ?";
	String getAvailableSlotSql = "SELECT id, slotcount, name FROM specialistdetails "
			+ "WHERE specialitycode = ? And slotcount <> ?";
	String getSlotSql = " SELECT slotcount FROM specialistdetails"
			+ " WHERE id = ?";
	String bookSlotSql = " UPDATE specialistdetails"
			+ " SET slotcount = ?, bookedslot =? "
			+ " WHERE id = ?";
	String getSlotCountSql = " SELECT bookedslot FROM specialistdetails"
			+ " WHERE id = ?";
	String getUserDetailsSql = "SELECT name, city, contactnumber, age from userdetails "
			+ "WHERE username = ?";
	String addPatientDetailsSql = " INSERT INTO appointment (name, age, city, contactnumber, doctorid, username)"
			+ " VALUES(?, ?, ?, ?, ?, ?)";
	String getDoctorIDSql = "SELECT id FROM specialistdetails "
			+ "WHERE username = ?";
	String setAppointmentStatusSql = " UPDATE appointment"
			+ " SET status = false"
			+ " WHERE doctorid = ?";
	String getAppointmentStatusSql = "SELECT status FROM appointment "
			+ "WHERE id = ?";
	String getAppointmentIdSql = "SELECT id FROM appointment "
			+ "ORDER BY id DESC "
			+ "LIMIT 1";
	String setSlotCountSql = " UPDATE specialistdetails"
			+ " SET slotcount = ?"
			+ " WHERE username = ?";
	String checkValidAppointmentIdSql = "SELECT id,name FROM appointment "
			+ "WHERE id = ? AND username = ?";

	private static AppointmentDbRepository obj = null;
	public static AppointmentDbRepository getObj() {
		if(obj == null)
			obj = new AppointmentDbRepository();
		return obj;
	}

	// Open a connection
	private static Connection connection ;
	public Connection getConnection() {
		try	{
			connection = DBCPDataSource.getConnection();
			Class.forName("org.postgresql.Driver");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return connection ;
	}

	public void addUserCredential(String username, String password) throws SQLException {
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(signUpSql);
			prepStmt.setString(1, username);
			prepStmt.setString(2, password);
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if(connection != null)
				connection.close();
		}
	}
	public List<String> getUserNames() throws SQLException {
		List<String> userNamesList = new ArrayList<String>();
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getUserNamesSql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				userNamesList.add(rs.getString("username"));
			}
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if(connection != null)
				connection.close();
		}		
		return userNamesList;
	}

	public boolean checkUserCredentials(String userName, String password) throws SQLException {
		try {	
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(checkUserCredentialSql);
			prepStmt.setString(1, userName);
			prepStmt.setString(2, password);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				return true;
			}
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if(connection != null)
				connection.close();
		}
		return false;
	}

	public void addUserDetails(String name, int age, String city, String contactNum, int roleIndicator, String userName) throws SQLException {
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(addUserDetailsSql);
			prepStmt.setString(1, name);
			prepStmt.setLong(2, age);
			prepStmt.setString(3, city);
			prepStmt.setString(4, contactNum);
			prepStmt.setLong(5, roleIndicator);
			prepStmt.setString(6, userName);
			prepStmt.executeUpdate();
			prepStmt.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
	}

	public int getUserRole(String userName) throws SQLException {
		int roleIndicator = 0;
		try {	
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getRoleIndSql);
			prepStmt.setString(1, userName);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				roleIndicator = rs.getInt("roleindicator");
			}
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if(connection != null)
				connection.close();
		}
		return roleIndicator;
	}

	public void addDoctorSpeciality(String userName, int specialityCode, String name) throws SQLException {
		try {	
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(addDoctorDetailsSql);
			prepStmt.setString(1, userName);
			prepStmt.setLong(2, specialityCode);
			prepStmt.setLong(3, 0);
			prepStmt.setString(4, name);
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if(connection != null)
				connection.close();
		}
	}

	public void cancelAllSlots(String userName) throws SQLException {
		try {	
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(cancelSlotsSql);
			prepStmt.setString(1, userName);
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
	}
	public int getDoctorID(String userName) throws SQLException {
		int id = 0;
		try {	
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getDoctorIDSql);
			prepStmt.setString(1, userName);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				id = Integer.parseInt(rs.getString("id"));
			}
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
		return id;
	}

	public void setAppointmentStatus(int id) throws SQLException {
		try {	
			Connection connection = getConnection(); 
			PreparedStatement prepStmt = connection.prepareStatement(setAppointmentStatusSql);
			prepStmt.setInt(1, id);
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
	}

	public int getSlotCounts(String userName) throws SQLException {
		int slotCount = 0;
		try {	
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getbookedSlotSql);
			prepStmt.setString(1, userName);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				slotCount = rs.getInt("bookedslot");
			}
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if(connection != null)
				connection.close();
		}
		return slotCount;
	}

	public List<String> checkAvailabeDoctor(int code) throws SQLException {
		List<String> userNamesList = new ArrayList<String>();
		try {	
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getAvailableDoctorSql);
			prepStmt.setLong(1, code);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				userNamesList.add(rs.getString("username"));
			}
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		finally {
			if(connection != null)
				connection.close();
		}
		return userNamesList;
	}

	public List<String> checkAvailabeSlot(int code) throws SQLException {
		List<String> slotDetailsList = new ArrayList<String>();
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getAvailableSlotSql);
			prepStmt.setLong(1, code);
			prepStmt.setLong(2, 0);
			ResultSet rs = prepStmt.executeQuery();
			while(rs.next())
			{
				slotDetailsList.add(rs.getString("id"));
				slotDetailsList.add(rs.getString("slotcount"));
				slotDetailsList.add(rs.getString("name"));
			}
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		finally {
			if(connection != null)
				connection.close();;
		}
		return slotDetailsList;
	}

	public int getSlot(int id) throws SQLException {
		int slotCount = 0;
		try {	
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getSlotSql);
			prepStmt.setLong(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				slotCount = rs.getInt("slotcount");
			}
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if(connection != null)
				connection.close();
		}
		return slotCount;
	}

	public void bookSlot(int id, int slot) throws SQLException {
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(bookSlotSql);
			prepStmt.setLong(1, slot);
			int bookedSlot = new AppointmentDbRepository().getBookedSlot(id) + 1;
			prepStmt.setLong(2, bookedSlot);
			prepStmt.setLong(3, id);
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if(connection != null)
				connection.close();
		}
	}

	public int getBookedSlot(int id) throws SQLException {
		int bookedSlot = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getSlotCountSql);
			prepStmt.setLong(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				bookedSlot = rs.getInt("bookedslot");
			}
			prepStmt.close();
			connection.close();
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			if(connection != null)
				connection.close();
		}
		return bookedSlot;
	}

	public Map<String, Object> getUserDetails(String userName) throws SQLException {
		Map<String, Object> userDetailsMap = new HashMap<>();
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getUserDetailsSql);
			prepStmt.setString(1, userName);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				userDetailsMap.put("name", rs.getString("name"));
				userDetailsMap.put("city", rs.getString("city"));
				userDetailsMap.put("contactNumber", rs.getString("contactnumber"));
				userDetailsMap.put("age", rs.getInt("age"));
			}
			prepStmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
		return userDetailsMap;
	}

	public void addPatientDetails(String name, int age, String city, String contactNum, int doctorId, String userName) throws SQLException {
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(addPatientDetailsSql);
			prepStmt.setString(1, name);
			prepStmt.setLong(2, age);
			prepStmt.setString(3, city);
			prepStmt.setString(4, contactNum);
			prepStmt.setLong(5, doctorId);
			prepStmt.setString(6, userName);
			prepStmt.executeUpdate();
			prepStmt.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
	}

	public boolean checkAppointmentStatus(int id ) throws SQLException {
		boolean status = false;
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getAppointmentStatusSql);
			prepStmt.setInt(1, id);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				status = rs.getBoolean("status");
			}
			prepStmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
		return status;
	}

	public int getAppointmentId() throws SQLException {
		String id = "";
		try {	
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(getAppointmentIdSql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("id");
			}
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
		int id1 = Integer.parseInt(id);
		return id1;
	}

	public void setSlotCount(int slotCount, String userName) throws SQLException {
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(setSlotCountSql);
			prepStmt.setLong(1, slotCount);
			prepStmt.setString(2, userName);
			prepStmt.executeUpdate();
			prepStmt.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
	}

	public boolean checkValidAppointmentId(int id, String userName) throws SQLException {
		boolean valid = false;
		try {
			Connection connection = getConnection();
			PreparedStatement prepStmt = connection.prepareStatement(checkValidAppointmentIdSql);
			prepStmt.setInt(1, id);
			prepStmt.setString(2, userName);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				valid = true;
			}
			prepStmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connection != null)
				connection.close();
		}
		return valid;
	}

}

