package Group2Testing;

import java.util.Random;
import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MasterHelper {

	// Webdriver instance
	public WebDriver driver;

	// Random instance
	Random random = new Random();

	// URLS for all pages on website
	String landingURL = "http://localhost:3000/";
	String homeURL = "http://localhost:3000/home";
	String accountURL = "http://localhost:3000/account";
	String signinURL = "http://localhost:3000/signin";
	String signupURL = "http://localhost:3000/signup";

	// xPaths for buttons and links.
	String signupButtonPath = "//*[@id=\"root\"]/div/div[2]/form/button";
	String signinButtonPath = "//*[@id=\"root\"]/div/div[2]/form/button";
	
	String signupNamePath = "//*[@id=\"root\"]/div/div[2]/form/input[1]";
	
	String signupEmailPath = "//*[@id=\"root\"]/div/div[2]/form/input[2]";
	String signinEmailPath = "//*[@id=\"root\"]/div/div[2]/form/input[1]";
	
	String signupPasswordPath = "//*[@id=\"root\"]/div/div[2]/form/input[3]";
	String signinPasswordPath = "//*[@id=\"root\"]/div/div[2]/form/input[2]";
	
	String signupConfirmpasswordPath = "//*[@id=\"root\"]/div/div[2]/form/input[4]";
	String signoutButtonPath = "//*[@id=\"root\"]/div/div[1]/div/ul[1]/li/a";


	String randomNames[] = {
			// Array of 50 random Names
			"Analisa Silber", "Armida Winland", "Beatris Christie", "Beckie Pasillas", "Brenda Banister",
			"Caroyln Feaster", "Chasity Bruns", "Deanne Tewell", "Demetria Mclelland", "Diane Morrissette",
			"Elizabeth Fetterolf", "Evangeline Dunmore", "Evelyne Sundquist", "Felipe Lambdin", "Garnet Albright",
			"Irish Quinto", "Jeraldine Salamanca", "Katlyn Alm", "Kisha Cather", "Latanya Castorena", "Leigha Lunde",
			"Lila Franceschi", "Lourdes Hynd", "Lulu Rivas", "Marcie Trogdon", "Marcy Mitten", "Margorie Linson",
			"Marva Narvaez", "Micki Mullens", "Monnie Tumlinson", "Monte Alcazar", "Novella Kirsch", "Peggy Kantner",
			"Phyllis Rozman", "Pierre Edelson", "Rocio Gibbs", "Rodrick Hoffer", "Ruthe Switzer", "Sabrina Kellog",
			"Santa Defrank", "Sherley Weiss", "Sonya Wolfgang", "Tamie Mattie", "Tanja Nunemaker", "Theodora Davison",
			"Theodore Mongold", "Tyisha Ruggiero", "Usha Favors", "Vanesa Woolley", "Wonda Despres" };

	public int randNum1() {
		// Returns a number between 0 (inclusive) and 50 (exclusive); Returns 0-49

		int high = 50;
		int low = 0;
		int randomNumber = random.nextInt(high - low) + low;
		return randomNumber;
	}

	public String randNum2() {
		// Returns a number between 0 (inclusive) and 1,000,000 (exclusive) and then
		// turns it into a String.

		int high = 1000000;
		int low = 0;
		int num = random.nextInt(high - low) + low;
		String randomNumber = Integer.toString(num);
		return randomNumber;
	}

	private String randomEmail() {
		// Returns a random email made from a Universally Unique Identifier (UUID)
		return "random-" + UUID.randomUUID().toString() + "@example.com";
	}

	public void signInUser(String email, String password) {
		
		driver.get(signinURL);

		// Web Element is created for the email box on the login page.
		WebElement emailBox = driver.findElement(By.xpath(signinEmailPath));

		// Name created above is typed into name box.
		emailBox.sendKeys(email);

		// Web Element is created for the password box on the login page.
		WebElement passwordBox = driver.findElement(By.xpath(signinPasswordPath));

		// Password created above is typed into name box.
		passwordBox.sendKeys(password);

		// Click the submit button		
		driver.findElement(By.xpath(signinButtonPath)).click();


	}

	public void createRandomUser() throws InterruptedException {
		// Automates the process of creating a new user.

		// Go to account creation page.
		driver.get(signupURL);

		// User's name comes from randomNames()
		String Name = randomNames[randNum1()];

		// User's email comes from randomEmail().
		String Email = randomEmail();

		String Password = "qwerty123";
		String ConfirmPassword = "qwerty123";

		// Web Element is created for the name box on the login page.
		WebElement nameBox = driver.findElement(By.xpath(signupNamePath));

		// Name created above is typed into name box.
		nameBox.sendKeys(Name);

		WebElement emailBox = driver.findElement(By.xpath(signupEmailPath));
		emailBox.sendKeys(Email);

		WebElement passwordBox = driver.findElement(By.xpath(signupPasswordPath));
		passwordBox.sendKeys(Password);

		WebElement confirmPasswordBox = driver.findElement(By.xpath(signupConfirmpasswordPath));
		confirmPasswordBox.sendKeys(ConfirmPassword);

		// Click the signup button
		driver.findElement(By.xpath(signupButtonPath)).click();
		
		System.out.println("_____________________________________\n");
		System.out.println("Name: " + Name);
		System.out.println("Email: " + Email);
		System.out.println("Password: " + Password);

		
		
//		String signout = driver.findElement(By.cssSelector("a[href*='signout']")).getText();
//		System.out.println("href text ------>>>" + signout);
//		System.out.println("signoutB text ------>>>" + signoutButtonPath);

				
//		assertTrue(signout.equals(signoutButtonPath));

	}

}
