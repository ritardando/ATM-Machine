package atm_machine;

import java.util.Scanner;
/**
 * 
 * @author Jose Gomez
 *
 */
public class ATM {
	private Bank myBank = new Bank();
	private Scanner cin = new Scanner(System.in);
	
	public ATM() {}
	/**
	 * This is the ATM welcome Screen<br>
	 * This screen is displayed until there is user input.
	 */
	public void welcomeScreen() {
		Screen.displayWelcome();
		String choice = cin.nextLine();
		if(choice.equalsIgnoreCase("Q")) {}
		else {
			//This should not be commented out unless testing
			//insertCard();
			
			//For Testing purposes
			myBank.accessAccount("7433", "00000");
			mainMenu();
		}
	}
	/**
	 * This method prompts user to input pin card number (simulating inserting card and entering pin number).<br>
	 * If the user get it wrong 3 time, the ATM machine will return to the welcome screen.
	 */
	public void insertCard() {
		int tries = 0;
		String pin, cardNumber;
		boolean accountOpen = false;
		boolean cancel = false; 
		
		while(tries < 3 && !accountOpen && !cancel) {
			if(tries > 0) {
				System.out.print("Try Again? [y/n]: ");
				String choice = cin.nextLine();
				if(choice.equalsIgnoreCase("n")) {
					cancel = true;
				}
			}
			if(!cancel) {
				System.out.println("Insert card and pin number:");
				System.out.print("Card #: ");
				cardNumber = cin.nextLine();
				System.out.print("Pin #: ");
				pin = cin.nextLine();
				accountOpen = myBank.accessAccount(pin, cardNumber);
				if(!accountOpen) {
					System.out.println("Account not found");
				}
				tries++;
			}
		}
		if(accountOpen) {
			mainMenu();
		}else {
			welcomeScreen();
		}
	}
	/**
	 * This is the main menu in the ATM Machine. It allow user to select from withdraw funds, deposit funds, transferFunds
	 * and view balance. The ATM will come back here after each action until the user decides to end their usage of the atm
	 * machine. When the user finishes it goes back to welcome screen.<br><br>
	 * There is also a quit option. Should it be hidden? ATM machines wouldn't normally turn off or end the program.
	 */
	public void mainMenu() {
		boolean quit = false;
		boolean cancel = false;
		
		while(!quit && !cancel) {
			Screen.displayMainMenu();
			String choice = cin.nextLine();
			switch(choice) {
			case "1": depositFunds(); break;
			case "2": withdrawFunds(); break;
			case "3": transferFunds(); break;
			case "4": viewBalance(); break;
			case "5": cancel = true; welcomeScreen(); break;
			case "6": quit = true; break;
			default: System.out.println("Invalid Input");
			}
		}
	}
	
	public void depositFunds() {
		System.out.println("Function Coming Soon");
	}
	/**
	 * Withdraws money from bank account. 
	 */
	public void withdrawFunds() {
		Screen.displayWithdrawMenu();
		int choice = getChoice();
		
		if(choice == 2) {}
		else if(choice == 3) {
			System.out.println("Invalid Choice");
		}
		else {
			System.out.print("How much money would you like to withdraw?: ");
			String amtstr = cin.nextLine();
			try {
				double amount = Double.parseDouble(amtstr);
				if(myBank.withdraw(choice, amount)) {
					giveMoney(amount);
				}else {
					System.out.println("Could Not withdraw Amount");
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		pressEnterToContinue();
	}
	
	public void transferFunds() {
		System.out.println("Function coming soon");
	}
	/**
	 * Shows the balance in a specific account
	 */
	public void viewBalance() {
		Screen.checkBalanceMoney();
		int choice = getChoice();
		if(choice == 1 || choice == 0) {
			System.out.printf("%nAccount: %s%nAmount: $%.2f%n", 
					  (choice == 0) ? "Checking" : "Savings", myBank.viewBalance(choice));
		}else if(choice == 2){}
		else{
			System.out.println("Invalid Choice");
		}
		pressEnterToContinue();
	}
	/**
	 * Gets the choice for the account. 0 is checking, 1 is savings, 2 corresponds to c/C which cancels the activity, 3 is an invalid option
	 * @return integer value. Each integer value corresponds to something different
	 */
	public int getChoice() {
		String choice = cin.nextLine();
		switch(choice) {
		case "1": return 0;
		case "2": return 1;
		case "c":
		case "C": return 2;
		default: return 3;
		}
	}
	/**
	 * Model of expelling the money through the slot in the atm machine
	 * @param amount the money value being withdrawn
	 */
	private void giveMoney(double amount) {
		int value[] = {5,10,20,50,100};
		int remainder = (int) amount;
		Money cash = new Money();
		
		if (remainder < 5) {
			cash.updateMoney(1, remainder);
			remainder = 0;
		}else {
			System.out.println("How would your money: ");
			billsMenu(value, amount);
			String choicestr = cin.nextLine();
			try {
				int choice = Integer.parseInt(choicestr) - 1;
				if(value[choice] <= amount) {
					int tempamount = remainder / value[choice];
					remainder = remainder - (value[choice] * tempamount);
					cash.valueToMoney(remainder);
					cash.updateMoney(value[choice], tempamount);
					System.out.println("\nYou withdrew: " + String.format("$%.2f", amount) +
										"\nYour cash is: ");
					System.out.println(cash.toString());
				}else {
					System.out.println("Invalid Choice");
				}
			} catch(Exception e) {
				System.out.println("Invalid Input");
			}
		}
	}
	/**
	 * Displays what bills you can choose from
	 * @param value
	 * @param amount
	 */
	private void billsMenu(int value[], double amount) {
		for(int i = 1; i-1 < 5; i++) {
			if(value[i-1] <= amount) {
				System.out.println(i + ") $" + value[i-1]);
			}
		}
	}
	/**
	 * Prompts user to press enter to continue
	 */
	private void pressEnterToContinue() {
		System.out.println("press return key to continue...");
		String choice = cin.nextLine();
	}
}
