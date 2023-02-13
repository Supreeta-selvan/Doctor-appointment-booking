package appointment;
import java.sql.SQLException;
import java.util.InputMismatchException;
import slot.SlotDetails;

public class UserModule {
	// method to select the preferred task 
	public static void selectOption() throws SQLException {
		System.out.println("\n1. To check doctor's availability press \"1\"");
		System.out.println("2. To book doctor's appointment press \"2\"");
		System.out.println("3. To check the status of the already booked appointment press \"3\"");
		System.out.println("4. To logout press \"4\"");
		System.out.println("5. To exit press \"5\"");
		System.out.println();
		int option = 0;
		option = UserDetails.getValidIntegerInput();
		switch (option) {
		case 1:
			SlotDetails.checkSlot();
			UserModule.selectOption();
			break;
		case 2:
			PatientDetails.getPatientDetails();
			break;
		case 3:
			PatientDetails.checkAppointmentStatus();
			UserModule.selectOption();
			break;
		case 4:
			System.out.print("\n**********LOGOUT**********\n");
			Appointment.selectOption();
			break;
		case 5:
			System.out.print("\n**********EXIT**********");
			break;
		public:
			System.out.println("Enter a valid number.");
			UserModule.selectOption();
			break;
		}
	}

}
