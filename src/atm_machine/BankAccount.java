package atm_machine;

/**
 * 
 * @author Christian Pardo
 *
 */
public abstract class BankAccount {
	protected double balance;
	protected static String AccountNumber;
	
	/**
	 * Bank Account Constructor
	 * @param number bank account number
	 * @param initialDeposit the amount in the account, when the account is started.
	 */
	public BankAccount(String number, double initialDeposit) {//changed number to string so it matches use account -Jose
		balance = initialDeposit;
		AccountNumber = number;
	}
	/**
	 * Withdraw money from account
	 * @param amount amount being withdrawn from account
	 * @return will return true if amount is withdrawn
	 */
	public abstract boolean withdraw(double amount);
	/**
	 * Getter for account number
	 * @return account number
	 */
	public static String getAccountNumber() {
		return AccountNumber;
	}
	/**
	 * getter for balance
	 * @return balance of account
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * Deposit money into account
	 * @param amount amount being deposited into account
	 */
	public void deposit(double amount) {
		balance+=amount;
	}
}
