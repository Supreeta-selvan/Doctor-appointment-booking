package appointment;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import dataBase.AppointmentDbRepository;

public class UserDetails {

	// method to add a new user
	public static void addUser() throws SQLException {
		String userName, name, password, city, contactNum;
		int age, roleIndicator, specialityCode;
		System.out.print("Enter username : ");
		userName = getValidUserName();
		AppointmentDbRepository dbObj = AppointmentDbRepository.getObj();
		List<String> userNames = dbObj.getUserNames();
		if (userNames.contains(userName)) {
			System.out.println("Username already exists. Try with new name.");
			addUser();
		} 
		else {
			System.out.print("Enter password : ");
			password = getValidStringInput();
			password = UserLogin.encryptPassword(password);
			System.out.print("Enter your personal details : \n");
			System.out.print("Name : ");
			name = getValidStringInput();
			System.out.print("City : ");
			city = getValidStringInput();
			System.out.print("Contact Number : ");
			contactNum = getValidContactNumber();
			System.out.print("Age : ");
			age = getValidIntegerInput();
			System.out.print("Choose Role (1.Doctor/ 2.User) : ");
			roleIndicator = getValidRole();
			dbObj.addUserCredential(userName, password);
			dbObj.addUserDetails(name, age, city, contactNum, roleIndicator, userName);
			if(roleIndicator == 1) {
				System.out.print("Please choose your speciality : ");
				System.out.print("\n 1. ENT Specialist\n 2. EYE Specialist\n 3. Ortho Specialist\n 4. General Physician\n");
				specialityCode = getValidSpecialityCode();
				dbObj.addDoctorSpeciality(userName, specialityCode, name);
			}
			System.out.println("User added successfully. You can now login and use the portal.\n");
			UserLogin.getUserDetails();
		}
	}

	public static int getValidRole() {
		int roleIndicator = getValidIntegerInput();
		while(roleIndicator > 2 || roleIndicator <= 0) {
			System.out.println("Enter a valid number.");
			roleIndicator = getValidIntegerInput();
		}
		return roleIndicator;
	}
	public static int  getValidSpecialityCode() {
		int specialityCode = getValidIntegerInput();
		while(specialityCode > 4 || specialityCode <= 0) {
			System.out.println("Enter a valid number.");
			specialityCode = getValidIntegerInput();
		}
		return specialityCode;
	}

	public static String getValidStringInput() {
		Scanner sc = new Scanner(System.in);
		String value = "";
		while(value.equals("")){
			value = sc.nextLine();
		}
		return value;
	}
	public static String getValidUserName() {
		String userName = getValidStringInput();
		while(! checkValidUserName(userName)) {
			System.out.println("Make sure that the entered username satisfies the below constraints:");
			System.out.println("1. Should contain ateast 6 characters");
			System.out.println("2. Should start with an alphabte and is followed by alphabtes / numbers / underscore");
			System.out.println("Kindly enter a valid username");
			userName = getValidStringInput();
		}
		return userName;
	}

	public static boolean checkValidUserName(String userName) {
		String regex = "^[A-Za-z]\\w{5,29}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(userName);
		if( m.matches()) {
			return true;
		}
		return false;
	}

	public static String getValidContactNumber() {
		String contactNumber = getValidStringInput();
		while(!checkValidContactNumber(contactNumber)) {
			System.out.println("Enter a valid number");
			contactNumber = getValidStringInput();
		}
		return contactNumber;
	}

	public static boolean checkValidContactNumber(String contactNumber ) {;
	Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");  
	//the matcher() method creates a matcher that will match the given input against this pattern  
	Matcher match = ptrn.matcher(contactNumber);  
	//returns a boolean value  
	return (match.find() && match.group().equals(contactNumber));  
	}

	public static int getValidIntegerInput() {
		int value = 0;
		while(value <= 0) {
			value = checkValidIntegerInput();
		}
		return value;
	}
	public static int checkValidIntegerInput() {
		Scanner sc = new Scanner(System.in);
		int value = 0;
		try {
			value = sc.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Enter a valid number.");
		}
		return value;
	}
}
