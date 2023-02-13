package appointment;

import java.sql.SQLException;

import slot.SlotDetails;

public class DoctorModule {
	public static void selectOption() throws SQLException {
		int option;
		System.out.println("1.To cancel the appointments press \"1\"");
		System.out.println("2.To view the number of booked appointments press \"2\"");
		System.out.println("3.To accept appointments press \"3\"");
		System.out.println("4.To logout press \"4\"");
		System.out.println("5.To exit press \"5\"");
		option = UserDetails.getValidIntegerInput();
		String userName = UserLogin.userName;
		if(option == 1) {
			SlotDetails.cancelSlot(userName);
			System.out.println("\nAll appointments are cancelled.");
			selectOption();	
		}
		else if(option == 2) {
			SlotDetails.viewSlot(userName);
			selectOption();
		}
		else if(option == 4){
			System.out.print("\n**********LOGOUT**********\n");
			Appointment.selectOption();
		}
		else if(option == 5){
			System.out.print("\n**********EXIT**********");
		}
		else if(option == 3) {
			SlotDetails.openSlot();
			selectOption();
		}
		else {
			System.out.println("Enter a valid number.");
			selectOption();
		}
	}
}
