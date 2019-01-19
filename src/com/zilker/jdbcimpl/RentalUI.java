package com.zilker.jdbcimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.beans.Rental;
import com.zilker.beans.User;
import com.zilker.delegate.RentalRedirect;
import com.zilker.utils.RentalValidation;


public class RentalUI {

	private static final Logger logger = Logger.getLogger(RentalRedirect.class.getName());
	Scanner sc = new Scanner(System.in);
	String email;
	/*
	 * This method to get the user details
	 * and to register in database
	 */
	public void register()
	{
		try {
			
			RentalValidation rentalvalidation = new RentalValidation();
			User user = new User();
			RentalRedirect redirect = new RentalRedirect();
			
			logger.log(Level.INFO,"Enter the Name");
			String fname = sc.next();
			int counter =0;
			do {
				
				logger.log(Level.INFO,"Enter the Email");
				email = sc.next();
				counter = rentalvalidation.validEmail(email);
			}while(counter!=1);
			
			logger.log(Level.INFO,"Enter the Password");
			String password = sc.next();
			int count = 0;
			long phoneno;
			do {
				logger.log(Level.INFO,"Enter the Phone number");
				phoneno = sc.nextLong();
				count = rentalvalidation.validPhoneno(phoneno);
			}while(count!=1);
			
			logger.log(Level.INFO,"Enter the Occupation");
			String occupation = sc.next();
			sc.nextLine();
			
			logger.log(Level.INFO,"Enter the Address");
			String address = sc.nextLine();
			
			logger.log(Level.INFO,"Enter the Area");
			String area = sc.next();
			
			logger.log(Level.INFO,"Enter the Zipcode");
			int zipcode = sc.nextInt();
			
			logger.log(Level.INFO,"wanna sell or rent?(owner or tenant)");
			String role = sc.next();
			
			user.setFname(fname);
			user.setEmail(email);
			user.setPassword(password);
			user.setPhoneno(phoneno);
			user.setOccupation(occupation);
			user.setAddress(address);
			user.setArea(area);
			user.setZipcode(zipcode);
			user.setRole(role);
			redirect.register(user);
		
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
			RentalRedirect redirect = new RentalRedirect();
			RentalValidation rentalvalidation = new RentalValidation();
			int counter = 0;
			
			do {
			logger.log(Level.INFO,"Enter the Email");
			email = sc.next();
			counter = rentalvalidation.validEmail(email);
			}while(counter!=1);
			
			logger.log(Level.INFO,"Enter the Password");
			String password = sc.next();
			
			user.setEmail(email);
			user.setPassword(password);
			 
			role = redirect.login(user);
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
			ArrayList<Rental> al = new ArrayList<>();
			ArrayList<Rental> ar = new ArrayList<>();
			ArrayList<Rental> as = new ArrayList<>();
			
			Rental rental = new Rental();
			RentalRedirect redirect = new RentalRedirect();
			
			logger.log(Level.INFO,"Enter the Rental choice(buy|rent|pg)");
			String rentchoice = sc.next();
			if(rentchoice.equals("buy"))
			{
				rentchoice = "sell";
			}
			
			logger.log(Level.INFO,"Enter the Location");
			String rentarea = sc.next();
			
			rental.setRentchoice(rentchoice);
			rental.setRentarea(rentarea);
			
			int houseid = 0;
			long phoneno = 0;
			
			al = redirect.search(rental);
			for(Rental i : al)
			{
				logger.log(Level.INFO,"\nHOUSE_ID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nTYPE:"+i.getHousetype()+"\nPRICE:"+i.getPrice());
				houseid = i.getHouseid();
				rental.setHouseid(houseid);
				phoneno = redirect.displayContact(rental);
				logger.log(Level.INFO,"\nPHONENO:"+phoneno);
				ar = redirect.displayFacility(rental);
				for(Rental j : ar)
					logger.log(Level.INFO,"\nAmenities:"+j.getFacility());
				as = redirect.displayReview(rental);
				for(Rental k : as)
					logger.log(Level.INFO,"\nRating:"+k.getRating()+"\nReview:"+k.getReview());
			}
			
			int reqpay = 0;
			logger.log(Level.INFO,"Enter the number of interested house");
			int count = sc.nextInt();
			int counter = 0;
			for(int i=0;i<count;i++)
			{
				logger.log(Level.INFO,"Enter the house ID");
				houseid = sc.nextInt();
				rental.setHouseid(houseid);
				rental.setEmail(email);
				logger.log(Level.INFO,"Enter the Request amount");
				reqpay = sc.nextInt();
				rental.setReqpay(reqpay);
				counter = redirect.markInterest(rental);
			}
			if(counter == 1)
				logger.log(Level.INFO,"Marked as Interested:)");
			else
				logger.log(Level.INFO,"House is taken you cannot mark interest");
			
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
			RentalRedirect redirect = new RentalRedirect();

			logger.log(Level.INFO,"Enter the Rental choice(sell|rent|pg)");
			String rentchoice = sc.next();
			
			logger.log(Level.INFO,"Enter the House Type(1BHK|2BHK|3BHK|1RK|4+BHK)");
			String housetype = sc.next();
			
			sc.nextLine();
			logger.log(Level.INFO,"Enter the Rental address");
			String rentaddress = sc.nextLine();
			
			logger.log(Level.INFO,"Enter the Rental area");
			String rentarea = sc.next();
			
			logger.log(Level.INFO,"Enter the Rental area zipcode");
			int zipcode = sc.nextInt();
			
			int flag = 0;
			
			/*
			logger.log(Level.INFO,"Enter the Rental address latitude");
			double latitude = sc.nextDouble();
			
			logger.log(Level.INFO,"Enter the Rental address longitude");
			double longitude = sc.nextDouble();
			*/
			
			logger.log(Level.INFO,"Enter the Rental house price");
			int price = sc.nextInt();
			
			rental.setRentchoice(rentchoice);
			rental.setHousetype(housetype);
			rental.setRentaddress(rentaddress);
			rental.setRentarea(rentarea);
			rental.setZipcode(zipcode);
			rental.setPrice(price);
			rental.setEmail(email);
			/*
			rental.setLatitude(latitude);
			rental.setLongitude(longitude);
			*/
			
			flag = redirect.addRentalDetails(rental);
			if(flag != 1)
				logger.log(Level.INFO, "Enter the proper area  and zipcode");
			
			logger.log(Level.INFO,"Enter the number of facilities");
			int numbr = sc.nextInt();
			sc.nextLine();
			String facility;
			
			logger.log(Level.INFO,"Enter the Rental Facilities");
			for(int i=0;i<numbr;i++)
			{
				facility = sc.nextLine();
				rental.setEmail(email);
				rental.setFacility(facility);
				redirect.addFacility(rental);
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
			ArrayList<Rental> al = new ArrayList<>();
			Rental rental = new Rental();
			RentalRedirect redirect = new RentalRedirect();
			
			String value = "true";
			do {
				logger.log(Level.INFO,"Enter the choice which you want to update");
				logger.log(Level.INFO,"1.Rental Type(1BHK|2BHK|3BHK|1RK|4+BHK)");
				logger.log(Level.INFO,"2.Rental Choice(sell|rent|pg)");
				logger.log(Level.INFO,"3.Rental Address");
				logger.log(Level.INFO,"4.Rental Price");
				logger.log(Level.INFO,"5.Exit.");
				int choice = sc.nextInt();
				int flag = 0;
				String rentaddress="";
				
				switch(choice)
				{
				case 1:
					rental.setEmail(email);
					al = redirect.viewHouse(rental);
					for(Rental i : al)
						logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nPRICE:"+i.getPrice());
					sc.nextLine();
					logger.log(Level.INFO,"Enter the Rental Address");
					rentaddress = sc.nextLine();
					logger.log(Level.INFO,"Enter the Rental Type(1BHK|2BHK|3BHK|1RK|4+BHK)");
					String renttype = sc.next();
					rental.setHousetype(renttype);
					rental.setRentaddress(rentaddress);
					rental.setEmail(email);
					flag = redirect.updateRentType(rental);
					if(flag != 1)
						logger.log(Level.INFO,"Your request is denied");
					break;
					
				case 2:
					rental.setEmail(email);
					al = redirect.viewHouse(rental);
					for(Rental i : al)
						logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nPRICE:"+i.getPrice());
					sc.nextLine();
					logger.log(Level.INFO,"Enter the Rental Address");
					rentaddress = sc.nextLine();
					logger.log(Level.INFO,"Enter the Rental choice(sell|rent|pg)");
					String rentchoice = sc.next();
					rental.setRentchoice(rentchoice);
					rental.setRentaddress(rentaddress);
					rental.setEmail(email);
					flag = redirect.updateRentChoice(rental);
					if(flag != 1)
						logger.log(Level.INFO,"Your request is denied");
					break;
					
				case 3:
					rental.setEmail(email);
					al = redirect.viewHouse(rental);
					for(Rental i : al)
						logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nPRICE:"+i.getPrice());
					sc.nextLine();
					logger.log(Level.INFO,"Enter the Rental Address");
					rentaddress = sc.nextLine();
					rental.setRentaddress(rentaddress);
					logger.log(Level.INFO,"Enter the new Rental Address");
					String newrentaddress = sc.nextLine();
					logger.log(Level.INFO,"Enter the Rental Area");
					String rentarea = sc.next();
					rental.setNewrentaddress(newrentaddress);
					rental.setRentarea(rentarea);
					flag = redirect.updateRentAddress(rental);
					if(flag != 1)
						logger.log(Level.INFO,"Your request is denied");
					break;
				
				case 4:
					rental.setEmail(email);
					al = redirect.viewHouse(rental);
					for(Rental i : al)
						logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nPRICE:"+i.getPrice());
					sc.nextLine();
					logger.log(Level.INFO,"Enter the Rental Address");
					rentaddress = sc.nextLine();
					logger.log(Level.INFO,"Enter the Rental Price");
					int price = sc.nextInt();
					rental.setPrice(price);
					rental.setRentaddress(rentaddress);
					flag = redirect.updateRentPrice(rental);
					if(flag != 1)
						logger.log(Level.INFO,"Your request is denied");
					break;
					
				case 5:
					value = "false";
					break;
				
				}
			}while(value.equals("true"));
			
		}catch(SQLException e)
		{
			logger.log(Level.INFO,"ERROR IN UPADTIN RENTAL DETAILS:"+e.getMessage());
		}	
	 }
	 /*
	  * This method is to add rating
	  * and reviews about the house
	  */
	 public void addReviews()
	 {
		 ArrayList<Rental> al = new ArrayList<>();
			try {
				
				Rental rental = new Rental();
				RentalRedirect redirect = new RentalRedirect();
			
				al = redirect.displayAddress(rental);
				for(Rental i : al)
					logger.log(Level.INFO,"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea());
				
				sc.nextLine();
				logger.log(Level.INFO,"Enter the Rental Address");
				String rentaddress = sc.nextLine();
				
				logger.log(Level.INFO,"Enter the Reviews");
				String review = sc.nextLine();
				
				logger.log(Level.INFO,"Enter the Ratings from 1 to 5");
				int rating = sc.nextInt();
				
				rental.setEmail(email);
				rental.setRentaddress(rentaddress);
				rental.setRating(rating);
				rental.setReview(review);
				redirect.addReview(rental);
				
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
				ArrayList<Rental> al = new ArrayList<>();
				ArrayList<User> list = new ArrayList<>();
				Rental rental = new Rental();
				User user = new User();
				RentalRedirect redirect = new RentalRedirect();
				
				rental.setEmail(email);
				al = redirect.viewHouse(rental);
				for(Rental i : al)
					logger.log(Level.INFO,"\nHOUSEID:"+i.getHouseid()+"\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode());
				
				logger.log(Level.INFO,"Enter the house id");
				int houseid = sc.nextInt();
				user.setId(houseid);
				
				list = redirect.viewInterestedTenant(user);
				for(User i : list)
					logger.log(Level.INFO, "\nUSER ID:"+i.getId()+"\nFNAME:"+i.getFname()+"\nEMAIL:"+i.getEmail()+"\nPHONE NO:"+i.getPhoneno()+"\nOCCUPATION:"+i.getOccupation()+ "\nADDRESS:"+i.getAddress()+"\nAREA:"+i.getArea()+"\nZIPCODE:"+i.getZipcode()+"\nREQUESTED AMOUNT:"+i.getRequestpay());
				
				logger.log(Level.INFO,"want to accept (Y|N)");
				char choice = sc.next().charAt(0); 
				if(choice == 'y'||choice == 'Y')
				{
					logger.log(Level.INFO,"Enter the user id");
					int userid = sc.nextInt();
					rental.setUserid(userid);
					redirect.acceptUserRequest(rental);
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
				ArrayList<Rental> al = new ArrayList<>();
				Rental rental = new Rental();
				RentalRedirect redirect = new RentalRedirect();
				
				rental.setEmail(email);
				al = redirect.viewStatus(rental);
				for(Rental i : al)
					logger.log(Level.INFO, "\nADDRESS:"+i.getRentaddress()+"\nAREA:"+i.getRentarea()+"\nZIPCODE:"+i.getZipcode()+"\nSTATUS:"+i.getStatus());
				
			}catch(SQLException e)
			{
				logger.log(Level.INFO,"ERROR IN VIEWING STATUS:"+e.getMessage());
			}
	 }
}
