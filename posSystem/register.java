package posSystem;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

//							 Kyle Blackmore --- S1368557 --- CSIS 222

/* number of items, price in dollars
 * enter the number “00” to indicate the end of the process
 * The next part is to indicate any coupons to enter
 * display the item total, coupon total, sub-total, tax percentage, tax amount, and total.
 * cashier will enter the received money amount from the customer. 
 * the change and the breakdown of each monetary value.
 * only list change bills/coins needed, not the ones not needed 

// - Do not prompt the user to answer any questions that require any input other than numbers
// 
// Output the screen the end of the program with following message:
//		----------------------
//		*** END OF PROGRAM ***
*/

public class register {

	static Scanner input = new Scanner(System.in);
	static List<Double> items = new ArrayList<Double>();
	static List<Double> coupons = new ArrayList<Double>();
	static double itemTotal = 0;
	static double couponTotal = 0;
	static double subTotal = 0;
	static double finalTotal = 0;
	static double taxesTotal = 0;
	static final double TAXPERCENTAGE = 0.07;
	
	public static void main(String[] args) {
				
		getItems();
		getCoupons();
		displayTotals();
		payment();
		lottery();
		
		System.out.println("\n\n----------------------");
		System.out.println("*** END OF PROGRAM ***");
	}
	
	public static void getItems() {
		// putting all amounts into a list array
		// since the exit condition has to be "00" i had to do this workaround
		// where input is in a string, then cast the string into a double. 
		int counter = 0;
		String item;
		
		System.out.println("Enter price (usd) of each item. Enter \"00\" when finished");
		do {
			item = input.nextLine();
			if (!item.equals("00"))
				items.add(counter, Double.parseDouble(item));
			counter++;
		} while (!item.equals("00"));	
	}
	
	public static void getCoupons() {
		// same as get items, wasn't sure if to go with
		// coupon for a percentage or raw dollar  amount. i chose dollar amount

		int counter = 0;
		String coupon;
		
		System.out.println("Enter dollar amount of coupon(s). Enter \"00\" when finished");
		do {
			coupon = input.nextLine();
			if (!coupon.equals("00"))
				coupons.add(counter, Double.parseDouble(coupon));
			counter++;
		} while (!coupon.equals("00"));
	}

	public static void displayTotals() {
		// iterate through lists, output totals, and math out the final totals/taxes
		
		System.out.println("\nReceipt of item(s):");
		for (double item:items) {
			System.out.printf("%c%15.2f%n", '$',item);
			itemTotal += item;
		}
		System.out.println("------------------");
		System.out.println("Item total: $" + itemTotal + "\n");
		
		if (coupons.size() > 0) {
			System.out.println("Coupon(s) Entered:");
			for (double coupon:coupons) {
				System.out.printf("%c%15.2f%n", '$',coupon);
				couponTotal += coupon;
			}
			System.out.println("------------------");
			System.out.println("Discount: $" + couponTotal + "\n");
		}
		
		subTotal = (itemTotal - couponTotal);
		taxesTotal = (subTotal * TAXPERCENTAGE);
		finalTotal = (subTotal + taxesTotal);
		
		System.out.println("Subtotal: $" + subTotal);
		System.out.print("Taxes are: %");
		System.out.printf("%.2f%n", (TAXPERCENTAGE * 100));
		System.out.print("Tax amount: $");
		System.out.printf("%.2f%n", taxesTotal);
		System.out.println("------------------");
		System.out.print("Final total: $");
		System.out.printf("%.2f%n%n", finalTotal);
		//item total, coupon total, sub-total, tax percentage, tax amount, and total.
	}
	
	public static void payment() {
		// only display the bills/coins needed
		double change, amountGiven;
		int changeBills, changeCoins, currentValue, amountEach;
		Map<String,Integer> bills = new LinkedHashMap<>();
		Map<String,Integer> coins = new LinkedHashMap<>();
		
		//just a check to make sure the buyer gives enough $$
		do {
			System.out.println("Enter amount of money received: ");
			amountGiven = input.nextDouble();
			if (amountGiven < finalTotal)
				System.out.println("$" + amountGiven + " was not enough to cover " + finalTotal);
		} while (amountGiven < finalTotal );
		
		change = amountGiven - finalTotal;
		System.out.print("\nChange: $");
		System.out.printf("%.2f", change);
		System.out.println("\nGive the following denominations:\n");
		// Due to weird double (float) rounding issues i split the
		// dollar amount and coin amount into two integers
		changeBills = (int) change;
	    change -= changeBills;
	    change *= 100;
	    changeCoins = (int) Math.round(change);
	    
	    // inserting key value pairs for each bill/coin
	    // again, due to the weird float issues i made two separate containers
	    // for bills/coins rather than one container.
	    bills.put("hundreds", 100);
	    bills.put("fifties", 50);
	    bills.put("twenties", 20);
	    bills.put("tens", 10);
	    bills.put("fives", 5);
	    bills.put("ones", 1);
	    coins.put("quarters", 25);
	    coins.put("dimes", 10);
	    coins.put("nickels", 5);
	    coins.put("pennies", 1);
	    
	    // if payment is divisible, get amount it does so, and put that number
	    // into the value of that key (bill/coin), until there is no change remaining.
	    for(String key : bills.keySet()) {    	
	    	if (changeBills >= bills.get(key)) {
	    		currentValue = bills.get(key);
	    		amountEach = (int) changeBills / bills.get(key);
	    		bills.put(key, amountEach);
	    		changeBills -= (amountEach * currentValue);
	    	} else {
	    		bills.put(key, 0);
	    	}
	    }
	    
	    for(String key : coins.keySet()) {	    	
	    	if (changeCoins > coins.get(key)) {
	    		currentValue = coins.get(key);
	    		amountEach = (int) changeCoins / coins.get(key);
	    		coins.put(key, amountEach);
	    		changeCoins -= (amountEach * currentValue);
	    	} else {
	    		coins.put(key, 0);
	    	}
	    }

	    for(String key : bills.keySet()) {
	    	if(bills.get(key) != 0) {
	    		System.out.println(key.toUpperCase() + ": " + bills.get(key));
	    		//System.out.println(WordUtils.) + ": " + bills.get(key));
	    	}
	    }
	    for(String key : coins.keySet()) {
	    	if(coins.get(key) != 0) {
	    		System.out.println(key.toUpperCase() + ": " + coins.get(key));
	    	}
	    }
	}
	
	public static void lottery() {
		// Extra credit portion - pick rdm # 1-20
		// 3 guesses with higher/lower response.
		// "You Won" message for a $25 gift card for a future shopping trip in your store
		// "Sorry. Not a winner this time" message and then print out both their three guesses and the random number.
		
		Random lottery = new Random();
		int lotteryNumber = 1 + lottery.nextInt(20);
		int[] lotteryGuess = new int[3];

		System.out.println("\nA super secret number has been chosen at random between 1-20." +
						   "\nYou will have 3 attempts to guess it correctly for a gift card." +
						   "\nEnter your first guess:");
		// Get 3 guesses, simple input validation, and respond with if they're too high/low
		for (int i = 0; i <= 2; i++ ) {
			do {
				lotteryGuess[i] = input.nextInt();
				if (lotteryGuess[i] < 1 || lotteryGuess[i] > 20)
					System.out.println("That number was invalid. Please enter a whole number between 1 and 25");
			} while (lotteryGuess[i] < 1 || lotteryGuess[i] > 20);
			
			if (lotteryGuess[i] == lotteryNumber) {
				System.out.println("You Won!! Congratulations, you receive a $25 gift card for a future shopping trip in our store!");
				break;
			} else if (lotteryGuess[i] > lotteryNumber) {
				System.out.print("Your number of " + lotteryGuess[i] + " was too high. ");
			} else if (lotteryGuess[i] < lotteryNumber) {
				System.out.print("Your number of " + lotteryGuess[i] + " was too low. ");
			}
			
			if (i < 2) {
				System.out.println("Try again. You have " + (2 - i) + " attempt(s) left");
			} else {
				System.out.println("Sorry. Not a winner this time.");
			}
		}
		
		System.out.println("\nThe secret number was: " + lotteryNumber);
		System.out.print("\nYour guesses were: ");
		for (int i = 0; i <= 2; i++) {
			if (lotteryGuess[i] != 0)
				System.out.printf("%6s", lotteryGuess[i]);
		}
	}
}


/*
 * So below is how i originally had written my payment() method, mainly just to get the logic and 
 * math to work. However it was immediately apparent that is was quite clunky so a changed it so
 * that each value was put into a map container. specifically a "LinkedHashMap" since it will
 * keep the order in which they are placed. And by dynamically iterating through the above solution
 * is by far cleaner, and scalable. 
 * 
 * 		//int hundreds, fifties, twenties, tens, fives, singles;
		//int quarters, dimes, nickels, pennies;
 
		if (change > 100) {
			hundreds = (int) change / 100;
			change -= hundreds * 100;
		}
		if (change > 50) {
			fifties = (int) change / 50;
			change -= fifties * 50;
		}
		if (change > 20) {
			twenties = (int) change / 20;
			change -= twenties * 20;
		}
		if (change > 10) {
			tens = (int) change / 10;
			change -= tens * 10;
		}
		if (change > 5) {
			fives = (int) change / 5;
			change -= fives * 5;
		}
		if (change > 1) {
			singles = (int) change / 1;
			change -= singles;
		}
		// now change will be less than a dollar. multiply by 100 to turn 0.75 to 75. makes it easier.
		change = change * 100;
		if (change > 25) {
			quarters = (int) change / 25;
			change -= quarters * 25;
		}
		if (change > 10) {
			dimes = (int) change / 10;
			change -= dimes * 10;
		}
		if (change > 5) {
			nickels = (int) change / 5;
			change -= nickels * 5;
		}
		if (change > 1) {
			pennies = (int) change;
			change -= pennies;
		}
		
 */
