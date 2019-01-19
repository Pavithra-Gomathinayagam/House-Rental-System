package com.zilker.jdbcimpl;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * This class displays the set of operations 
 * to be performed by the owner or tenant
 * for the rental system.
 */
public class RentalImpl {
	
private static final Logger logger = Logger.getLogger(RentalImpl.class.getName());
	
	public static void main (String[] args) {
		Scanner scn = new Scanner(System.in);
		try {
			
			do {
			logger.log(Level.INFO,"Enter the choice");
			logger.log(Level.INFO,"1.New User?Register");
			logger.log(Level.INFO,"2.Existing User?Login");
			logger.log(Level.INFO,"3.Exit");
			
			int choice=scn.nextInt();
			RentalUI rentalui = new RentalUI();
		
			
				switch(choice)
				{
					case 1:
						rentalui.register();
						break;
				
					case 2:
						String role = rentalui.login();
						if(role.equals("TENANT"))
						{
							String value = "true";
							do {
								
								logger.log(Level.INFO,"Enter the choice");
								logger.log(Level.INFO,"1.Search");
								logger.log(Level.INFO,"2.Add reviews");
								logger.log(Level.INFO,"3.Exit");
								
								int tenant_choice = scn.nextInt();
								
								switch(tenant_choice)
								{
								case 1:
									rentalui.search();
									break;
									
								case 2:
									rentalui.addReviews();
									break;
									
								case 3:
									value = "false";
									break;
								}
							}while(value.equals("true"));
						}
						if(role.equals("OWNER"))
						{
							String value = "true";
							do {
								
								logger.log(Level.INFO,"Enter the choice");
								logger.log(Level.INFO,"1.Add rental house details");
								logger.log(Level.INFO,"2.Update rental house details");
								logger.log(Level.INFO,"3.View status");
								logger.log(Level.INFO,"4.View interested tenants");
								logger.log(Level.INFO,"5.Exit");
								
								int owner_choice = scn.nextInt();
								
								switch(owner_choice)
								{
								case 1:
									rentalui.addRentalDetails();
									break;
									
								case 2:
									rentalui.updateRentalDetails();
									break;
									
								case 3:
									rentalui.viewStatus();
									break;
								
								case 4:
									rentalui.viewInterestedTenant();
									break;
								case 5:
									value = "false";
									break;
								}
								
							}while(value.equals("true"));
						}
						break;
				
					case 3:
						System.exit(0);
						break;
				
				
				}
			}while(true);
			}catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			} finally {
			scn.close();
			}
	}

}


