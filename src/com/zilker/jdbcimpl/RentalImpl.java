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

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {

			do {
				logger.log(Level.INFO, "\nENTER THE CHOICE \n1.NEW USER?REGISTER \n2.EXISTING USER?LOGIN \n3.EXIT");
				/*logger.log(Level.INFO, "1.New User?Register");
				logger.log(Level.INFO, "2.Existing User?Login");
				logger.log(Level.INFO, "3.Exit");*/

				int choice = scanner.nextInt();
				RentalUI rentalUi = new RentalUI();

				switch (choice) {
				case 1:
					rentalUi.register();
					break;

				case 2:
					String role = rentalUi.login();
					//if (role.equals("TENANT")) {
					if (role.equals("ADMIN")) {
						boolean loopVariable = true;
						do {
							logger.log(Level.INFO, "\nENTER THE CHOICE \n1.VIEW REQUESTED HOUSE \n2.EXIT");
							/*logger.log(Level.INFO, "Enter the choice");
							logger.log(Level.INFO, "1.View requested House");
							logger.log(Level.INFO, "2.Exit");*/
							
							int adminChoice = scanner.nextInt();

							switch (adminChoice) {
							case 1:
								rentalUi.viewRequestedHouse();
								break;
								
							case 2:
								loopVariable = false;
								break;
							}
						}while(loopVariable==true);
					}
					if((role.equals("CUSTOMER"))||(role.equals("OWNER"))) {
						boolean loopVariable = true;
						do {

							logger.log(Level.INFO, "ENTER THE CHOICE \n1.SEARCH \n2.ADD REVIEWS AND RATINGS  \n3.VIEW STATUS \n4.VIEW APPROVED HOUSE \n5.LIST YOUR PROPERTY \n6.Exit");

							int customerChoice = scanner.nextInt();

							switch (customerChoice) {
							case 1:
								rentalUi.search();
								break;

							case 2:
								rentalUi.addReviews();
								break;
								
							case 3:
								rentalUi.viewUserStatus();
								break;
								
							case 4:
								rentalUi.viewApprovedHouse();
								break;
								
							case 5:
								boolean exitCondition = true;
								
								do {
									logger.log(Level.INFO, "ENTER THE CHOICE \n1.ADD RENTAL DETAILS \n2.UPDATE RENTAL DETAILS  \n3.VIEW STATUS \n4.VIEW INTERESTED CUSTOMER \n5.VIEW CONFORMED HOUSE \n6.VIEW REJECTED HOUSE \n7.EXIT");
									
									/*logger.log(Level.INFO, "Enter the choice");
									logger.log(Level.INFO, "1.Add rental house details");
									logger.log(Level.INFO, "2.Update rental house details");
									logger.log(Level.INFO, "3.View status");
									logger.log(Level.INFO, "4.View interested customer");
									logger.log(Level.INFO, "5.View conformed customer");
									logger.log(Level.INFO, "6.Exit");*/

									int ownerChoice = scanner.nextInt();

									switch (ownerChoice) {
									case 1:
										rentalUi.addRentalDetails();
										break;

									case 2:
										rentalUi.updateRentalDetails();
										break;

									case 3:
										rentalUi.viewStatus();
										break;

									case 4:
										rentalUi.viewInterestedTenant();
										break;
										
									case 5:
										rentalUi.viewConformedCustomer();
										break;
										
									case 6:
										rentalUi.viewRejected();
										break;
										
									case 7:
										exitCondition = false;
										break;
									}

								} while (exitCondition == true);
								break;
								
							case 6:
								loopVariable = false;
								break;
							}
						} while (loopVariable == true);
						
					}
					break;
					
				case 3:
					System.exit(0);
					break;

				}
			} while (true);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		} finally {
			scanner.close();
		}
	}

}
