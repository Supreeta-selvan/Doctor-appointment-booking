package appointment;

import java.sql.SQLException;

public class Appointment {
	public static void main(String[] args) throws SQLException {
		System.out.println("Welcome to Appointment Booking Portal");
		selectOption();
	}

	public static void selectOption() throws SQLException {
		int option;
		System.out.println("1.If you are a new user press \"1\" to Signup");
		System.out.println("2.If you are an existing user press \"2\" to Login ");
		System.out.println("3.To exit press \"3\"");
		option = UserDetails.getValidIntegerInput();
		switch(option) {
		case 1:
			UserDetails.addUser();
			break;
		case 2:
			UserLogin.getUserDetails();
			break;
		case 3:
			System.out.print("\n**********EXIT**********");
			break;
		public:
			System.out.println("Enter valid number.");	
			selectOption();
		}
	}
}
