package com.zilker.jdbcimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.beans.Rental;
import com.zilker.beans.User;
import com.zilker.delegate.AdminDelegate;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.OwnerDelegate;
import com.zilker.utils.RentalValidation;


public class RentalUI {

	private static final Logger logger = Logger.getLogger(RentalUI.class.getName());
	Scanner scanner = new Scanner(System.in);
	String email;
	/*
	 * This method to get the user details
	 * and to register in database
	 */
	public void register()
	{
		try {
			
			RentalValidation rentalValidation = new RentalValidation();
			User user = new User();
			
			CustomerDelegate customerDelegate = new CustomerDelegate();
			
			logger.log(Level.INFO,"Enter the First name");
			String fname = scanner.next();
			
			logger.log(Level.INFO,"Enter the Last name");
			String lname = scanner.next();
			
			boolean counter = false;
			do {
				
				logger.log(Level.INFO,"Enter the Email");
				email = scanner.next();
				counter = rentalValidation.validateEmail(email);
			}while(counter!=true);
			
			logger.log(Level.INFO,"Enter the Password");
			String password = scanner.next();
			
			logger.log(Level.INFO,"Retype the password");
			String retypePassword = scanner.next();
			
			boolean count = false;
			long phoneNo;
			do {
				logger.log(Level.INFO,"Enter the Phone number");
				phoneNo = scanner.nextLong();
				count = rentalValidation.validatePhoneno(phoneNo);
			}while(count!=true);
			
			logger.log(Level.INFO,"Enter the Occupation");
			String occupation = scanner.next();
			scanner.nextLine();
			
			logger.log(Level.INFO,"Enter the Address");
			String address = scanner.nextLine();
			
			logger.log(Level.INFO,"Enter the Zipcode");
			int zipcode = scanner.nextInt();
			
			user.setFname(fname);
			user.setLname(lname);
			user.setEmail(email);
			user.setPassword(password);
			user.setPhoneno(phoneNo);
			user.setOccupation(occupation);
			user.setAddress(address);
			user.setZipcode(zipcode);
			customerDelegate.register(user);
		
		}catch(SQLException e)
		{
			logger.log(Level.INFO,"ERROR IN REGISTERING:"+e.getMessage());
		}
	}
	/*
	 * This method verifies
	 *  email and password
	 */
	public String login()
	{
		String role = "";
		try {
			
			User user = new User();
			CustomerDelegate customerDelegate = new CustomerDelegate();

			RentalValidation rentalValidation = new RentalValidation();
			boolean counter = false;
			
			do {
			logger.log(Level.INFO,"Enter the Email");
			email = scanner.next();
			counter = rentalValidation.validateEmail(email);
			}while(counter!=true);
			
			logger.log(Level.INFO,"Enter the Password");
			String password = scanner.next();
			
			user.setEmail(email);
			user.setPassword(password);
			 
			role = customerDelegate.login(user);
			
			if(role.equals(""))
				logger.log(Level.INFO,"Login Failed");
			
		}catch(SQLException e)
		{
			logger.log(Level.INFO,"ERROR IN LOGIN PROCESS:"+e.getMessage());
		}
		return role;
	}
	/*
	 * This method is used to search the
	 * rental house
	 */
	public void search()
	{
		try {
			ArrayList<Rental> rentalList = new ArrayList<>();
			ArrayList<Rental> facilityList = new ArrayList<>();
			ArrayList<Rental> reviewList= new ArrayList<>();
			ArrayList<Rental> searchList = new ArrayList<>();
			
			Rental rental = new Rental();
			CustomerDelegate customerDelegate = new CustomerDelegate();
			
			logger.log(Level.INFO,"Enter the Rental choice(rent|pg)");
			String rentChoice = scanner.next();
			
			logger.log(Level.INFO,"Enter the Location");
			String rentArea = scanner.next();
			
			rental.setRentchoice(rentChoice);
			rental.setRentarea(rentArea);
			rental.setEmail(email);
			
			int houseId = 0;
			long phoneNo = 0;
			char loopVariable = 'y';
			int flag = 0;
			
			searchList = customerDelegate.search(rental);
			if(searchList.isEmpty())
				flag = 1;
			
			if(flag == 1)
			{
				logger.log(Level.INFO,"No house to display");
			}
			else {
			do {
			searchList = customerDelegate.search(rental);
			for(Rental i : searchList)
			{
				logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nHOUSE TYPE:"+i.getHousetype()+"\nPRICE:"+i.getPrice());
			}
			
			logger.log(Level.INFO,"Enter the house to view in detail");
			houseId = scanner.nextInt();
			rental.setHouseid(houseId);
			
			
			if(rentChoice.equalsIgnoreCase("rent"))
			{
				rentalList = customerDelegate.searchRent(rental);
				for(Rental i : rentalList)
				{
					logger.log(Level.INFO,"\nHOUSE_ID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nLANDMARK:"+i.getLandmark()+"\nTYPE:"+i.getHousetype()+"\nPRICE:"+i.getPrice()+"\nDEPOSIT:"+i.getDeposit()+"\nBUILT SQUARE FEET:"+i.getBuiltSqFeet()+"\nTOTAL FLOORS:"+i.getTotalFloor()+"\nFLOOR NUMBER:"+i.getFloorNo());
					houseId = i.getHouseid();
					rental.setHouseid(houseId);
					phoneNo = customerDelegate.displayContact(rental);
					logger.log(Level.INFO,"\nPHONENO:"+phoneNo);
					facilityList = customerDelegate.displayFacility(rental);
					for(Rental j : facilityList)
						logger.log(Level.INFO,"\nAmenities:"+j.getFacility());
					reviewList = customerDelegate.displayReview(rental);
					for(Rental k : reviewList)
						logger.log(Level.INFO,"\nName:"+k.getFname()+"\nRating:"+k.getRating()+"\nReview:"+k.getReview());
					
					logger.log(Level.INFO, "To go back type yes(y) else No (n)");
					loopVariable = scanner.next().charAt(0);
				}
			}
			else
			{
				rentalList = customerDelegate.searchPg(rental);
				for(Rental i : rentalList)
				{
					logger.log(Level.INFO,"\nHOUSE_ID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nLANDMARK:"+i.getLandmark()+"\nTYPE:"+i.getHousetype()+"\nPRICE:"+i.getPrice()+"\nSHARING:"+i.getPgSharing()+"\nGENDER:"+i.getGender());
					reviewList = customerDelegate.displayReview(rental);
					for(Rental k : reviewList)
						logger.log(Level.INFO,"\nName:"+k.getFname()+"\nRating:"+k.getRating()+"\nReview:"+k.getReview());
					logger.log(Level.INFO, "To go back type yes(y) else No (n)");
					loopVariable = scanner.next().charAt(0);
				}
			}
			}while(loopVariable == 'y' || loopVariable == 'Y');
			logger.log(Level.INFO,"Do you want to mark interest?(Y|N)");
			char markChoice = scanner.next().charAt(0);
			if(markChoice == 'y'||markChoice == 'Y')
			{
			int reqpay = 0;
			logger.log(Level.INFO,"Enter the number of interested house");
			int count = scanner.nextInt();
			int counter = 0;
			for(int i=0;i<count;i++)
			{
				logger.log(Level.INFO,"Enter the house ID");
				houseId = scanner.nextInt();
				rental.setHouseid(houseId);
				rental.setEmail(email);
				flag = customerDelegate.checkMarkInterest(rental);
				if(flag == 1)
				{
				logger.log(Level.INFO,"Enter the Request amount");
				reqpay = scanner.nextInt();
				rental.setReqpay(reqpay);
				counter = customerDelegate.markInterest(rental);
				}
				else{
					logger.log(Level.INFO,"Already marked as interested!");
				}
			}
			if(counter == 1)
				logger.log(Level.INFO,"Marked as Interested:)");
			
			}
			}
		}catch(SQLException e)
		{
			logger.log(Level.INFO,"ERROR IN SEARCHING THE HOUSE:"+e.getMessage());
		}
	}
	/*
	 * This method is add the rental details
	 */
	public void addRentalDetails()
	{
		try {
			Rental rental = new Rental();
			OwnerDelegate ownerDelegate = new OwnerDelegate();

			logger.log(Level.INFO,"Enter the Rental choice(rent|pg)");
			String rentChoice = scanner.next();
			
			scanner.nextLine();
			logger.log(Level.INFO,"Enter the Rental address");
			String rentAddress = scanner.nextLine();
			
			logger.log(Level.INFO,"Enter the Rental area zipcode");
			int zipcode = scanner.nextInt();
			
			logger.log(Level.INFO,"Enter the Rental house price");
			int price = scanner.nextInt();
			
			logger.log(Level.INFO,"Enter the House Type(1BHK|2BHK|3BHK|1RK|4+BHK)");
			String houseType = scanner.next();
			
			scanner.nextLine();
			logger.log(Level.INFO,"Enter the Landmark");
			String landmark = scanner.nextLine();
			
			rental.setRentchoice(rentChoice);
			rental.setRentaddress(rentAddress);
			rental.setZipcode(zipcode);
			rental.setPrice(price);
			rental.setHousetype(houseType);
			rental.setEmail(email);
			rental.setLandmark(landmark);
			/*
			logger.log(Level.INFO,"Enter the Rental area");
			String rentArea = scanner.next();
			*/
			int flag = 0;
			flag = ownerDelegate.addRentalDetails(rental);
			if(flag != 1)
				logger.log(Level.INFO, "Enter the proper area  and zipcode");
			
			if(rentChoice.equals("rent"))
			{
				/*
				logger.log(Level.INFO,"Enter the Rental address latitude");
				double latitude = scanner.nextDouble();
			
				logger.log(Level.INFO,"Enter the Rental address longitude");
				double longitude = scanner.nextDouble();
				 */
			
				logger.log(Level.INFO,"Enter the built-up area");
				int builtSqFeet = scanner.nextInt();
			
				logger.log(Level.INFO,"Enter the Total number of floors");
				int totalFloor = scanner.nextInt();
			
				logger.log(Level.INFO,"Enter the Floor number");
				int floorNo = scanner.nextInt();
			
				logger.log(Level.INFO,"Enter the Deposit amount");
				int deposit = scanner.nextInt();
			
				
			
				rental.setRentaddress(rentAddress);
				rental.setBuiltSqFeet(builtSqFeet);
				rental.setTotalFloor(totalFloor);
				rental.setFloorNo(floorNo);
				rental.setDeposit(deposit);
				ownerDelegate.addRentDetails(rental);
				
				/*
				rental.setLatitude(latitude);
				rental.setLongitude(longitude);
				 */
				
				logger.log(Level.INFO,"Enter the number of facilities");
				int numbr = scanner.nextInt();
				scanner.nextLine();
				String facility;
				
				logger.log(Level.INFO,"Enter the Rental Facilities");
				for(int i=0;i<numbr;i++)
				{
					facility = scanner.nextLine();
					rental.setEmail(email);
					rental.setFacility(facility);
					ownerDelegate.addFacility(rental);
				}
			}
			else
			{
				logger.log(Level.INFO,"Enter the sharing(Double sharing|Triple sharing|Four sharing)");
				String pgSharing = scanner.nextLine();
				
				logger.log(Level.INFO,"Enter the Gender(Male or Female) sharing");
				String gender = scanner.next();
				
				rental.setPgSharing(pgSharing);
				rental.setGender(gender);
				ownerDelegate.addPgDetails(rental);
			}
			
			
			System.out.print(email);
		}catch(SQLException e)
		{
			logger.log(Level.INFO,"ERROR IN ADDING RENTAL DETAILS:"+e.getMessage());
		}
	 }
	/*
	 * This method is update rental details
	 * such as rental type , rental choice
	 * rental price and address
	 */
	 public void updateRentalDetails()
	 {
		try {
			ArrayList<Rental> rentalList = new ArrayList<>();
			Rental rental = new Rental();
			OwnerDelegate ownerDelegate = new OwnerDelegate();
			
			boolean loopVariable = true;
			do {
				logger.log(Level.INFO,"Enter the choice which you want to update");
				logger.log(Level.INFO,"1.Rental Type(1BHK|2BHK|3BHK|1RK|4+BHK)");
				logger.log(Level.INFO,"2.Rental Address");
				logger.log(Level.INFO,"3.Rental Price");
				logger.log(Level.INFO,"4.Exit.");
				int choice = scanner.nextInt();
				int flag = 0;
				
				int houseId;
				
				switch(choice)
				{
				case 1:
					rental.setEmail(email);
					rentalList = ownerDelegate.viewHouse(rental);
					if(rentalList.isEmpty())
						logger.log(Level.INFO,"House deal is finalised with the customer.No house to view");
					else {
					for(Rental i : rentalList)
						logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nHOUSE TYPE:"+i.getHousetype()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nPRICE:"+i.getPrice());
			
					logger.log(Level.INFO,"Enter the House id:");
					houseId = scanner.nextInt();
					logger.log(Level.INFO,"Enter the Rental Type(1BHK|2BHK|3BHK|1RK|4+BHK)");
					String rentType = scanner.next();
					rental.setHousetype(rentType);
					rental.setHouseid(houseId);
					rental.setEmail(email);
					flag = ownerDelegate.updateRentType(rental);
					if(flag == 1)
						logger.log(Level.INFO,"House type is updated");
					}
					break;
					
				/*case 2:
					rental.setEmail(email);
					rentalList = redirect.viewHouse(rental);
					for(Rental i : rentalList)
						logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nPRICE:"+i.getPrice());
					scanner.nextLine();
					logger.log(Level.INFO,"Enter the Rental Address");
					rentAddress = scanner.nextLine();
					logger.log(Level.INFO,"Enter the Rental choice(sell|rent|pg)");
					String rentChoice = scanner.next();
					rental.setRentchoice(rentChoice);
					rental.setRentaddress(rentAddress);
					rental.setEmail(email);
					flag = redirect.updateRentChoice(rental);
					if(flag != 1)
						logger.log(Level.INFO,"Your request is denied");
					break;*/
					
				case 2:
					rental.setEmail(email);
					rentalList = ownerDelegate.viewHouse(rental);
					if(rentalList.isEmpty())
						logger.log(Level.INFO,"House deal is finalised with the customer.No house to view");
					else {
					for(Rental i : rentalList)
						logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nHOUSE TYPE:"+i.getHousetype()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nPRICE:"+i.getPrice());
					
					logger.log(Level.INFO,"Enter the House id:");
					houseId = scanner.nextInt();
					rental.setHouseid(houseId);
					logger.log(Level.INFO,"Enter the new Rental Address");
					String newRentAddress = scanner.nextLine();
					logger.log(Level.INFO,"Enter the Rental Area zipcode");
					int zipcode = scanner.nextInt();
					rental.setNewrentaddress(newRentAddress);
					rental.setZipcode(zipcode);
					flag = ownerDelegate.updateRentAddress(rental);
					if(flag == 1)
						logger.log(Level.INFO,"House address is updated");
					}
					break;
				
				case 3:
					rental.setEmail(email);
					rentalList = ownerDelegate.viewHouse(rental);
					if(rentalList.isEmpty())
						logger.log(Level.INFO,"House deal is finalised with the customer.No house to view");
					else {
					for(Rental i : rentalList)
						logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nHOUSE TYPE:"+i.getHousetype()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nPRICE:"+i.getPrice());
					
					logger.log(Level.INFO,"Enter the House id:");
					houseId = scanner.nextInt();
					logger.log(Level.INFO,"Enter the Rental Price");
					int price = scanner.nextInt();
					rental.setPrice(price);
					rental.setHouseid(houseId);
					flag = ownerDelegate.updateRentPrice(rental);
					if(flag == 1)
						logger.log(Level.INFO,"House price is updated");
					}
					break;
					
				case 4:
					loopVariable = false;
					break;
				
				}
			}while(loopVariable == true);
			
		}catch(SQLException e)
		{
			logger.log(Level.INFO,"ERROR IN UPDATING RENTAL DETAILS:"+e.getMessage());
		}	
	 }
	 /*
	  * This method is to add rating
	  * and reviews about the house
	  */
	 public void addReviews()
	 {
		 ArrayList<Rental>  rentalList = new ArrayList<>();
			try {
				
				Rental rental = new Rental();
				CustomerDelegate customerDelegate = new CustomerDelegate();
				
			
				rental.setEmail(email);
				rentalList = customerDelegate.displayAddress(rental);
				if(rentalList.isEmpty())
					logger.log(Level.INFO,"No house to display");
				else {
				for(Rental i : rentalList)
					logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nHOUSE TYPE:"+i.getHousetype()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea());
				
				logger.log(Level.INFO,"Enter the House ID");
				int houseId = scanner.nextInt();
				rental.setEmail(email);
				rental.setHouseid(houseId);
				
				int flag = customerDelegate.checkReviewRating(rental);
				if(flag == 0)
					logger.log(Level.INFO,"You have already added review and rating for this house!");
				
				else {
				scanner.nextLine();
				logger.log(Level.INFO,"Enter the Reviews");
				String review = scanner.nextLine();
				
				logger.log(Level.INFO,"Enter the Ratings from 1 to 5");
				float rating = scanner.nextFloat();
				
				rental.setRating(rating);
				rental.setReview(review);
				customerDelegate.addReview(rental);
				}
				}
			}catch(SQLException e)
			{
				logger.log(Level.INFO,"ERROR IN ADDING REVIEWS:"+e.getMessage());
			}
	 }
	 /*
	  * This method is view interested tenants
	  * for the house
	  */
	 public void viewInterestedTenant()
	 {
			try {
				ArrayList<Rental> rentalList = new ArrayList<>();
				ArrayList<User> userList = new ArrayList<>();
				Rental rental = new Rental();
				User user = new User();
				OwnerDelegate ownerDelegate = new OwnerDelegate();
				
				int flag = 1;
				rental.setEmail(email);
				rentalList = ownerDelegate.viewHouse(rental);
				if(rentalList.isEmpty())
					logger.log(Level.INFO,"House deal is finalised with the customer.No house to view");
				else
				{
				for(Rental i : rentalList)
					logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nHOUSE TYPE:"+i.getHousetype()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nPRICE:"+i.getPrice());
				
				logger.log(Level.INFO,"Enter the house id");
				int houseId = scanner.nextInt();
				user.setId(houseId);
				
				userList = ownerDelegate.viewInterestedTenant(user);
				if(userList.isEmpty())
				{	
					logger.log(Level.INFO,"No user has marked interest on this house!:/");
					flag = 0;
				}
				for(User i : userList)
					logger.log(Level.INFO, "\nUSER ID:"+i.getId()+"\nFNAME:"+i.getFname()+"\nEMAIL:"+i.getEmail()+"\nPHONE NO:"+i.getPhoneno()+"\nOCCUPATION:"+i.getOccupation()+ "\nADDRESS:"+i.getAddress()+"\nAREA:"+i.getArea()+"\nZIPCODE:"+i.getZipcode()+"\nREQUESTED AMOUNT:"+i.getRequestpay());
				
				if(flag == 1)
				{
					logger.log(Level.INFO,"want to accept (Y|N)");
					char choice = scanner.next().charAt(0); 
					if(choice == 'y'||choice == 'Y')
					{
						int checkPoint = 0;
						logger.log(Level.INFO,"Enter the user id");
						int userId = scanner.nextInt();
						rental.setHouseid(houseId);
						rental.setUserid(userId);
						checkPoint = ownerDelegate.checkAcceptedRequest(rental);
						if(checkPoint == 0)
							logger.log(Level.INFO,"Already marked as accepted");
						else
						{
							ownerDelegate.acceptUserRequest(rental);
							logger.log(Level.INFO,"Marked as accepted!");
						}
					}
				}
				}
			}catch(SQLException e)
			{
				logger.log(Level.INFO,"ERROR IN VIEWING INTERESTED TENATNT:"+e.getMessage());
			}
	 }
	 /*
	  * This method is for owner to view status of the
	  * requested house
	  */
	 public void viewStatus()
	 {
			try {
				ArrayList<Rental> rentalList = new ArrayList<>();
				Rental rental = new Rental();
				OwnerDelegate ownerDelegate = new OwnerDelegate();
				
				rental.setEmail(email);
				rentalList = ownerDelegate.viewStatus(rental);
				if(rentalList.isEmpty())
					logger.log(Level.INFO, "No house to display");
				else {
				for(Rental i : rentalList)
					logger.log(Level.INFO, "\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nSTATUS:"+i.getStatus());
				}
			}catch(SQLException e)
			{
				logger.log(Level.INFO,"ERROR IN VIEWING STATUS:"+e.getMessage());
			}
	 }
	 public void viewUserStatus()
	 {
		 try {
			 Rental rental = new Rental();
			 CustomerDelegate customerDelegate = new CustomerDelegate();
			 ArrayList<Rental> userList = new ArrayList<>();
			 
			 rental.setEmail(email);
			 userList = customerDelegate.viewUserStatus(rental);
			 if(userList.isEmpty())
					logger.log(Level.INFO, "No house has been marked as interested");
			else {
			 for(Rental i : userList)
			 {
				 logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nTYPE:"+i.getHousetype()+"\nPRICE:"+i.getPrice()+"\nSTATUS:"+i.getStatus());
				 //if(i.getStatus().equalsIgnoreCase("approved"))
					 //flag = 1;
			 }
			 /*if(flag == 1)
			 {
				 logger.log(Level.INFO,"Do you want to conform the house(Y|N)");
				 char choice = scanner.next().charAt(0);
				 if(choice == 'y'||choice == 'Y')
				 {
					 logger.log(Level.INFO,"Enter the house Id");
					 houseId = scanner.nextInt();
					 rental.setEmail(email);
					 rental.setHouseid(houseId);
					 redirect.checkHouse(rental);
					 redirect.conformHouse(rental);
				 }
				 
			 }*/
			}
		 }catch(SQLException e)
		 {
			 
		 }
	 }
	 public void viewApprovedHouse()
	 {
		 try {
			 Rental rental = new Rental();
			 CustomerDelegate customerDelegate = new CustomerDelegate();
			 ArrayList<Rental> approvedHouseList = new ArrayList<>();
			 
			 rental.setEmail(email);
			 approvedHouseList = customerDelegate.viewApprovedHouse(rental);
			 if(approvedHouseList.isEmpty())
				 logger.log(Level.INFO, "No approved house to display");
			 else
			 {
				 for(Rental i : approvedHouseList)
				 {
					 logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nTYPE:"+i.getHousetype()+"\nPRICE:"+i.getPrice()+"\nSTATUS:"+i.getStatus());
				 }
				 logger.log(Level.INFO,"Do you want to conform the house(Y|N)");
				 char choice = scanner.next().charAt(0);
				 if(choice == 'y'||choice == 'Y')
				 {
					 logger.log(Level.INFO,"Enter the house Id");
					 int houseId = scanner.nextInt();
					 rental.setEmail(email);
					 rental.setHouseid(houseId);
					 customerDelegate.conformHouse(rental);
					 logger.log(Level.INFO,"Deal finalised with owner :D");
				 }
			 }
			 
		 }catch(SQLException e)
		 {
			 
		 }
	 }
	 public void viewRequestedHouse()
	 {
		 try {
			 ArrayList<Rental> requestedList = new ArrayList<>();
			 Rental rental = new Rental();
			 int houseId = 0;
			 AdminDelegate adminDelegate = new AdminDelegate();
			 
			 requestedList = adminDelegate.displayRequestedHouse(rental);
			 if(requestedList.isEmpty())
				 logger.log(Level.INFO, "No house to display");
			 else {
			 for(Rental i : requestedList)
				 logger.log(Level.INFO, "\nHOUSE ID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE"+i.getZipcode());
			 
			 logger.log(Level.INFO,"Enter the Total number of house id to approve or reject");
			 int counter = scanner.nextInt();
			 int statusId = 0;
			 for(int i=0;i<counter;i++)
			 {
				 logger.log(Level.INFO,"Enter the house id:");
				 houseId = scanner.nextInt();
				 logger.log(Level.INFO,"You want to approve or reject");
				 String status = scanner.next();
				 if(status.equalsIgnoreCase("approved"))
					 statusId = 2;
				 else
					 statusId = 3;
				 rental.setHouseid(houseId);
				 rental.setStatusId(statusId);
				 adminDelegate.acceptHouse(rental);
				 logger.log(Level.INFO,"House is "+status);
				 
			 }
			 }
			 
		 }catch(SQLException e)
		 {
				logger.log(Level.INFO,"ERROR IN VIEWING STATUS:"+e.getMessage());
		 }
	 }
	 public void viewConformedCustomer()
	 {
		 try {
			
			 OwnerDelegate ownerDelegate = new OwnerDelegate();
			 ArrayList<Rental> customerList = new ArrayList<>();
			 Rental rental = new Rental();
			 rental.setEmail(email);
			 customerList = ownerDelegate.viewConformedCustomer(rental);
			 if(customerList.isEmpty())
				 logger.log(Level.INFO, "No house to display");
			 else {
			 for(Rental i : customerList)
				 logger.log(Level.INFO, "\nHOUSE ID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nCHOICE:"+i.getRentchoice()+"\nPRICE:"+i.getPrice()+"\nSTATUS:"+i.getStatus());
			 }
		 }catch(SQLException e)
		 {
			 
		 }
	 }
	 public void viewRejected()
	 {
		 try {
			
			 OwnerDelegate ownerDelegate = new OwnerDelegate();
			 ArrayList<Rental> customerList = new ArrayList<>();
			 Rental rental = new Rental();
			 rental.setEmail(email);
			 customerList = ownerDelegate.viewRejected(rental);
			 if(customerList.isEmpty())
				 logger.log(Level.INFO, "No house to display");
			 else {
			 for(Rental i : customerList)
				 logger.log(Level.INFO, "\nHOUSE ID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nCHOICE:"+i.getRentchoice()+"\nPRICE:"+i.getPrice()+"\nSTATUS:"+i.getStatus());
			 }
		 }catch(SQLException e)
		 {
			 
		 }
	 }
	 
}
