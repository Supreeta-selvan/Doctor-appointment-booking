package slot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import appointment.UserDetails;
import appointment.UserLogin;
import dataBase.AppointmentDbRepository;

public class SlotDetails  {
	static public int slotCount = 0;
	static public int docId;
	static public boolean isSlotAvailabe = true;
	static public List<Integer> doctorIdList = new ArrayList<Integer>();
	static AppointmentDbRepository dbObj = AppointmentDbRepository.getObj();

	public static void viewSlot(String userName) throws SQLException {
		slotCount = dbObj.getSlotCounts(userName);
		System.out.println("\nNumber of booked appointments = " + slotCount + "\n");
	}

	public static void  cancelSlot(String userName) throws SQLException {
		dbObj.cancelAllSlots(userName);
		int id = dbObj.getDoctorID(userName);
		dbObj.setAppointmentStatus(id);
	}

	public static void bookSlot() throws SQLException {
		System.out.println("To book appointment enter the \"ID\" of the respective doctor: ");
		docId = UserDetails.getValidIntegerInput();
		while(!doctorIdList.contains(docId)) {
			System.out.println("Enter a valid doctor id.");
			docId = UserDetails.getValidIntegerInput();
		}
		int slot = dbObj.getSlot(docId);
		if(slot == 0) {
			System.out.println("\nSorry the doctor is not availabe.");
		}
		else {
			dbObj.bookSlot(docId, slot - 1);
			System.out.println("\nYou have successfully booked your appointment.");
		}
	}

	public static void checkSlot() throws SQLException {
		System.out.print("Please choose your specialist : ");
		System.out.print("\n 1. ENT Specialist\n 2. EYE Specialist\n 3. Ortho Specialist\n 4. General Physician\n");
		int specialityCode = UserDetails.getValidSpecialityCode();
		List<String> slotDetailsList = dbObj.checkAvailabeSlot(specialityCode);

		for(int i = 0; i < slotDetailsList.size(); i+=3) {
			doctorIdList.add(Integer.parseInt(slotDetailsList.get(i)));
		}
		int count = 0;
		for(int i = 1; i < slotDetailsList.size(); i += 3) {
			if(Integer.parseInt(slotDetailsList.get(i)) == 0) {
				count++;
			}
		}
		isSlotAvailabe = (count == slotDetailsList.size()/3) ? false : true;
		if(slotDetailsList.size() == 0 || !isSlotAvailabe) {
			System.out.println("\nSorry no doctors available");
		}
		else {
			System.out.println("List of doctors availabe:");
			System.out.println("ID" + " " + "Slot" + " " + "Name");
			for(int i = 0; i < slotDetailsList.size(); ) {
				for(int j = 0; j < 3; j++) {
					System.out.print(slotDetailsList.get(i) + " ");
					i++;
				}
				System.out.println();
			}
		}
	}

	public static void openSlot() throws SQLException {
		System.out.println("Enter the number of appointments: ");
		int slotCount = UserDetails.getValidIntegerInput();
		dbObj.setSlotCount(slotCount , UserLogin.userName);
	}	

}
