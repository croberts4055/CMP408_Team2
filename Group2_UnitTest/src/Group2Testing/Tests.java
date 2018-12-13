package Group2Testing;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tests extends MasterHelper {

	// Runs before every test.
	@Before
	public void setUp() {

		// Location of the chromedriver on device
		String chromeDriverLocation = "/Users/chris/Desktop/chromedriver";

		// set up driver
		System.setProperty("webdriver.chrome.driver", chromeDriverLocation);

		// driver instance is of type Chrome driver. This opens webpage in chrome. (e.g.
		// if using firefox = FirefoxDriver())
		driver = new ChromeDriver();

		// Switches to chrome window.
		String currentWindow = driver.getWindowHandle();
		driver.switchTo().window(currentWindow);
	}

	// Runs after every test.
	@After
	public void tearDown() throws InterruptedException {
		// dispose of the chrome window and driver.
		
		Thread.sleep(2000);
		driver.quit();
	}

	
	@Test
	public void TitleTest() {
//		This test checks the title on the main home Page
System.out.println("Test 3: React App Title Test");

		// Go to the Group2 Website Homepage
		driver.get(homeURL);

		// Get the title of the page
		String title = driver.getTitle();

		// Return true if the title is EGF
		assertTrue(title.contains("React App"));
		System.out.println("Passed!");
	}

	@Test
	public void makeNewUser() throws InterruptedException {
		System.out.println("Test 2: Making a new user Test");
		createRandomUser();
		System.out.println("New random user has successfully been created\nPassed!");
	}
	
	@Test
	public void signInTest() {
		System.out.println("Test 1: Sign In Test");
		signInUser("random-22e0615d-b9f4-40cb-b620-9a2f8f3a1fc5@example.com", "qwerty123");
		System.out.println("Passed!");
		System.out.println("________________________________________");

	}

}
