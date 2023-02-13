package appointment;

import java.sql.SQLException;
import java.util.Map;
import dataBase.AppointmentDbRepository;
import slot.SlotDetails;

public class PatientDetails {
	private static String name, city, userName = UserLogin.userName;
	private static int age;
	private static String phoneNumber;
	static AppointmentDbRepository dbObj = AppointmentDbRepository.getObj();
	static boolean isSlotAvailable = false;

	// method to get patient details
	public static void getPatientDetails() throws SQLException {
		System.out.println("To whom do you want to book an appointment? (1)You (2)Others");
		int option = UserDetails.getValidIntegerInput();
		if(option == 1) {			
			Map<String, Object> userDetailsMap = dbObj.getUserDetails(userName);
			name = userDetailsMap.get("name").toString();
			city = userDetailsMap.get("city").toString();
			phoneNumber = userDetailsMap.get("contactNumber").toString();
			age = (int) userDetailsMap.get("age");
		}
		else {
			System.out.println("\nEnter the patient details: ");
			System.out.print("Name        :");
			name = UserDetails.getValidStringInput();
			System.out.print("City        :");
			city = UserDetails.getValidStringInput();
			System.out.print("Age         :");
			age = UserDetails.getValidIntegerInput();
			System.out.print("PhoneNumber :");
			phoneNumber = UserDetails.getValidContactNumber();
		}
		SlotDetails.checkSlot();
		if(SlotDetails.isSlotAvailabe) {
			SlotDetails.bookSlot();
			dbObj. addPatientDetails(name, age, city, phoneNumber, SlotDetails.docId, UserLogin.userName);
			printDetails();
		}
		UserModule.selectOption();
	}

	// method to print details of the patient 
	public static void printDetails() throws SQLException {
		System.out.println("\nAppointment details: ");
		System.out.println("Name              : " + name);
		System.out.println("Age               : " + age);
		System.out.println("City              : " + city);
		System.out.println("Phone             : " + phoneNumber);
		int appId = dbObj.getAppointmentId();
		System.out.println("AppointmentId     : " + appId);
	}

	public static void checkAppointmentStatus() throws SQLException {
		System.out.println("Enter the appointment id: " );
		int id = UserDetails.getValidIntegerInput();
		if(dbObj.checkValidAppointmentId(id, userName)) {
			boolean status = dbObj.checkAppointmentStatus(id);
			System.out.print("The appointment has ");
			if(status) {
				System.out.println("not been canceled");
			}
			else
				System.out.println("been canceled");
		}
		else {
			System.out.println("Invalid appointment id.");
		}
	}
}
