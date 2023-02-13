package appointment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import dataBase.AppointmentDbRepository;

public class UserLogin {
	public static String userName;
	static String password;
	static void getUserDetails() throws SQLException {
		System.out.print("Enter username :");
		userName = UserDetails.getValidUserName();
		System.out.print("Enter password :");
		password = UserDetails.getValidStringInput();
		password = encryptPassword(password);
		AppointmentDbRepository dbObj = AppointmentDbRepository.getObj();
		if(dbObj.checkUserCredentials(userName, password)) {
			System.out.println("\nLogin successful");
			chooseRole(dbObj.getUserRole(userName));
		}
		else {
			System.out.println("\nIncorrect details.Try again.\n");
			getUserDetails();
		}
	}

	static void chooseRole(int roleIndicator) throws SQLException {
		if(roleIndicator == 2)
			UserModule.selectOption();
		else
			DoctorModule.selectOption();
	}

	static String encryptPassword(String password) {
		// Plain-text password initialization.  
		String encryptedpassword = null;  
		try   
		{  
			// MessageDigest instance for MD5.  
			MessageDigest m = MessageDigest.getInstance("MD5");                
			// Add plain-text password bytes to digest using MD5 update() method.  
			m.update(password.getBytes());   
			// Convert the hash value into bytes  
			byte[] bytes = m.digest();  
			// The bytes array has bytes in decimal form. Converting it into hexadecimal format.  
			StringBuilder s = new StringBuilder();  
			for(int i=0; i < bytes.length; i++)  
			{  
				s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
			}  
			// Complete hashed password in hexadecimal format 
			encryptedpassword = s.toString();  
		}   
		catch (NoSuchAlgorithmException e)   
		{  
			e.printStackTrace();  
		}   
		return encryptedpassword;
	}  

}



