import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CheckoutTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }


    private WebElement waitForClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    private WebElement waitForVisible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://www.saucedemo.com/");
    }
    //Positive test
    @Test
    public void verifyPageCheckout() {

        // STEP 1: LOGIN
        waitForVisible(By.id("user-name"))
                .sendKeys("standard_user");
        pause(1);

        waitForVisible(By.id("password"))
                .sendKeys("secret_sauce");
        pause(1);

        waitForClickable(By.id("login-button")).click();

        wait.until(
                ExpectedConditions.urlContains("inventory.html")
        );
        System.out.println("STEP 1 PASS: Login successful");
        pause(2);

        // STEP 2: ADD PRODUCT TO CART


        By addToCartButton = By.id("add-to-cart-sauce-labs-backpack");
        By removeButton = By.id("remove-sauce-labs-backpack");

        WebElement button = waitForClickable(addToCartButton);

        System.out.println("Before click: " + button.getText());

        button.click();

// Wait until the Remove button appears
        WebElement removeBtn = waitForVisible(removeButton);

        Assert.assertEquals(
                removeBtn.getText(),
                "Remove",
                "Button should switch to Remove"
        );

        System.out.println("After click: " + removeBtn.getText());

        WebElement cartBadge = waitForVisible(
                By.className("shopping_cart_badge")
        );

        Assert.assertEquals(
                cartBadge.getText(),
                "1",
                "Cart quantity should be 1"
        );

        System.out.println("STEP 2 PASS: Product added to cart");


        // STEP 3: OPEN CART
        waitForClickable(By.className("shopping_cart_link")).click();

        wait.until(
                ExpectedConditions.urlContains("cart.html")
        );
        waitForVisible(By.id("checkout"));
        Assert.assertTrue(
                driver.findElement(
                        By.className("inventory_item_name")
                ).getText().contains("Sauce Labs Backpack"),
                "Expected product was not found in the cart"
        );



        System.out.println("STEP 3 PASS: Cart opened and product verified");
        pause(2);

        // STEP 4: CLICK CHECKOUT
        waitForClickable(By.id("checkout")).click();

        wait.until(
                ExpectedConditions.urlContains("checkout-step-one.html")
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one.html"),
                "Checkout information page did not open"
        );

        System.out.println("STEP 4 PASS: Checkout information page opened");
        pause(2);

        // STEP 5: ENTER CUSTOMER INFORMATION
        waitForVisible(By.id("first-name")).sendKeys("Ahmad");
        pause(1);

        driver.findElement(By.id("last-name")).sendKeys("Khaled");
        pause(1);

        driver.findElement(By.id("postal-code")).sendKeys("11118");
        pause(2);

        waitForClickable(By.id("continue")).click();

        wait.until(
                ExpectedConditions.urlContains("checkout-step-two.html")
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-two.html"),
                "Checkout overview page did not open"
        );

        System.out.println("STEP 5 PASS: Customer information submitted");
        pause(3);

        // STEP 6: VERIFY ORDER SUMMARY

        String total = waitForVisible(
                By.className("summary_total_label")
        ).getText();

        System.out.println("Order Total: " + total);

        // Verify expected order total
        Assert.assertEquals(
                total,
                "Total: $32.39",
                "Order total is incorrect"
        );

        System.out.println("STEP 6 PASS: Order total verified");
        pause(2);

        // STEP 7: FINISH ORDER
        waitForClickable(By.id("finish")).click();

        wait.until(
                ExpectedConditions.urlContains("checkout-complete.html")
        );

        WebElement confirmation = waitForVisible(
                By.className("complete-header")
        );

        Assert.assertEquals(
                confirmation.getText(),
                "Thank you for your order!",
                "Order confirmation message was incorrect"
        );

        System.out.println("STEP 7 PASS: Order completed successfully!");
        System.out.println("FINAL RESULT: TEST PASSED");

        pause(3);
    }

    //  Negative / Functional test
    @Test
    public void checkoutWithMissingFirstName() {

        // STEP 1: LOGIN
        waitForVisible(By.id("user-name"))
                .sendKeys("standard_user");

        driver.findElement(By.id("password"))
                .sendKeys("secret_sauce");

        waitForClickable(By.id("login-button")).click();

        wait.until(
                ExpectedConditions.urlContains("inventory.html")
        );

        System.out.println("STEP 1 PASS: Login successful");
        pause(2);

        // STEP 2: ADD PRODUCT
        waitForClickable(
                By.id("add-to-cart-sauce-labs-backpack")
        ).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("shopping_cart_badge"), "1"
        ));

        System.out.println("STEP 2 PASS: Product added to cart");
        pause(2);

        // STEP 3: OPEN CART
        waitForClickable(
                By.className("shopping_cart_link")
        ).click();

        wait.until(
                ExpectedConditions.urlContains("cart.html")
        );

        System.out.println("STEP 3 PASS: Shopping cart opened");
        pause(2);

        // STEP 4: OPEN CHECKOUT
        waitForClickable(By.id("checkout")).click();

        wait.until(
                ExpectedConditions.urlContains("checkout-step-one.html")
        );

        System.out.println("STEP 4 PASS: Checkout information page opened");
        pause(2);

        // STEP 5: ENTER INVALID INFORMATION

        // First Name intentionally left empty

        waitForVisible(By.id("last-name"))
                .sendKeys("Khaled");

        waitForVisible(By.id("postal-code"))
                .sendKeys("11118");

        System.out.println("STEP 5 PASS: Customer information entered (First Name left empty)");
        pause(2);

        // STEP 6: CLICK CONTINUE
        waitForClickable(By.id("continue")).click();

        System.out.println("STEP 6 PASS: Continue button clicked");
        pause(1);

        // STEP 7: VERIFY ERROR
        WebElement errorMessage = waitForVisible(
                By.className("error-message-container")
        );

        String actualError = errorMessage.getText();

        System.out.println("Actual Error Message: [" + actualError + "]");

        Assert.assertTrue(
                actualError.contains("First Name is required"),
                "Expected validation error was not displayed"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one.html"),
                "User was incorrectly allowed to continue"
        );

        System.out.println("STEP 7 PASS: First Name validation verified");
        System.out.println("FINAL RESULT: NEGATIVE TEST PASSED");
    }

    //  Negative / Functional test
    @Test
    public void checkoutWithMissingLastName_standard_user() {

        // STEP 1: LOGIN
        waitForVisible(By.id("user-name"))
                .sendKeys("standard_user");

        driver.findElement(By.id("password"))
                .sendKeys("secret_sauce");

        waitForClickable(By.id("login-button")).click();

        wait.until(
                ExpectedConditions.urlContains("inventory.html")
        );

        System.out.println("STEP 1 PASS: Login successful");
        pause(2);

        // STEP 2: ADD PRODUCT
        waitForClickable(
                By.id("add-to-cart-sauce-labs-backpack")
        ).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("shopping_cart_badge"), "1"
        ));

        System.out.println("STEP 2 PASS: Product added to cart");
        pause(2);

        // STEP 3: OPEN CART
        waitForClickable(
                By.className("shopping_cart_link")
        ).click();

        wait.until(
                ExpectedConditions.urlContains("cart.html")
        );

        System.out.println("STEP 3 PASS: Shopping cart opened");
        pause(2);

        // STEP 4: OPEN CHECKOUT
        waitForClickable(By.id("checkout")).click();

        wait.until(
                ExpectedConditions.urlContains("checkout-step-one.html")
        );

        System.out.println("STEP 4 PASS: Checkout information page opened");
        pause(2);

        // STEP 5: ENTER INVALID INFORMATION

        waitForVisible(By.id("first-name"))
                .sendKeys("Ahmad");

        // Last Name intentionally left empty

        waitForVisible(By.id("postal-code"))
                .sendKeys("11118");

        System.out.println("STEP 5 PASS: Customer information entered (Last Name left empty)");
        pause(2);

        // STEP 6: CLICK CONTINUE
        waitForClickable(By.id("continue")).click();

        System.out.println("STEP 6 PASS: Continue button clicked");
        pause(1);

        // STEP 7: VERIFY ERROR
        WebElement errorMessage = waitForVisible(
                By.className("error-message-container")
        );

        String actualError = errorMessage.getText();

        System.out.println("Actual Error Message: [" + actualError + "]");

        Assert.assertTrue(
                actualError.contains("Last Name is required"),
                "Expected validation error was not displayed"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one.html"),
                "User was incorrectly allowed to continue"
        );

        System.out.println("STEP 7 PASS: Last Name validation verified");
        System.out.println("FINAL RESULT: NEGATIVE TEST PASSED");
    }

    //  Negative / Functional test
    @Test
    public void checkoutWithMissingPostalCode() {

        // STEP 1: LOGIN
        waitForVisible(By.id("user-name"))
                .sendKeys("standard_user");

        driver.findElement(By.id("password"))
                .sendKeys("secret_sauce");

        waitForClickable(By.id("login-button")).click();

        wait.until(
                ExpectedConditions.urlContains("inventory.html")
        );

        System.out.println("STEP 1 PASS: Login successful");
        pause(2);

        // STEP 2: ADD PRODUCT
        waitForClickable(
                By.id("add-to-cart-sauce-labs-backpack")
        ).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.className("shopping_cart_badge"), "1"
        ));

        System.out.println("STEP 2 PASS: Product added to cart");
        pause(2);

        // STEP 3: OPEN CART
        waitForClickable(
                By.className("shopping_cart_link")
        ).click();

        wait.until(
                ExpectedConditions.urlContains("cart.html")
        );

        System.out.println("STEP 3 PASS: Shopping cart opened");
        pause(2);

        // STEP 4: OPEN CHECKOUT
        waitForClickable(By.id("checkout")).click();

        wait.until(
                ExpectedConditions.urlContains("checkout-step-one.html")
        );

        System.out.println("STEP 4 PASS: Checkout information page opened");
        pause(2);

        // STEP 5: ENTER INVALID INFORMATION

        waitForVisible(By.id("first-name"))
                .sendKeys("Ahmad");

        waitForVisible(By.id("last-name"))
                .sendKeys("Khaled");

        // Postal Code intentionally left empty

        System.out.println("STEP 5 PASS: Customer information entered (Postal Code left empty)");
        pause(2);

        // STEP 6: CLICK CONTINUE
        waitForClickable(By.id("continue")).click();

        System.out.println("STEP 6 PASS: Continue button clicked");
        pause(1);

        // STEP 7: VERIFY ERROR
        WebElement errorMessage = waitForVisible(
                By.className("error-message-container")
        );

        String actualError = errorMessage.getText();

        System.out.println("Actual Error Message: [" + actualError + "]");

        Assert.assertTrue(
                actualError.contains("Postal Code is required"),
                "Expected validation error was not displayed"
        );

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one.html"),
                "User was incorrectly allowed to continue"
        );

        System.out.println("STEP 7 PASS: Postal Code validation verified");
        System.out.println("FINAL RESULT: NEGATIVE TEST PASSED");
    }

    //  Negative / Functional test
    @Test
    public void checkoutWithMissingLastName_ErrorUser() {

        // STEP 1: LOGIN
        waitForVisible(By.id("user-name"))
                .sendKeys("error_user");

        waitForVisible(By.id("password"))
                .sendKeys("secret_sauce");

        waitForClickable(By.id("login-button")).click();

        wait.until(
                ExpectedConditions.urlContains("inventory.html")
        );

        System.out.println("STEP 1 PASS: Login successful");
        pause(2);

        // STEP 2: ADD PRODUCT
        waitForClickable(
                By.id("add-to-cart-sauce-labs-backpack")
        ).click();

        wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.className("shopping_cart_badge"), "1"
                )
        );

        System.out.println("STEP 2 PASS: Product added to cart");
        pause(2);

        // STEP 3: OPEN CART
        waitForClickable(
                By.className("shopping_cart_link")
        ).click();

        wait.until(
                ExpectedConditions.urlContains("cart.html")
        );

        Assert.assertTrue(
                waitForVisible(By.className("inventory_item_name"))
                        .getText().contains("Sauce Labs Backpack"),
                "Backpack was not found in the cart"
        );

        System.out.println("STEP 3 PASS: Shopping cart opened");
        pause(2);

        // STEP 4: OPEN CHECKOUT
        waitForClickable(By.id("checkout")).click();

        wait.until(
                ExpectedConditions.urlContains("checkout-step-one.html")
        );

        System.out.println("STEP 4 PASS: Checkout information page opened");
        pause(2);

        // STEP 5: ENTER CUSTOMER INFORMATION

        waitForVisible(By.id("first-name"))
                .sendKeys("Ahmad");

        // Last Name intentionally left empty

        waitForVisible(By.id("postal-code"))
                .sendKeys("11118");

        System.out.println(
                "STEP 5 PASS: Information entered (Last Name left empty)"
        );

        pause(2);

        // STEP 6: CLICK CONTINUE
        waitForClickable(By.id("continue")).click();

        System.out.println("STEP 6: Continue button clicked");

        // STEP 7: VERIFY ACTUAL APPLICATION BEHAVIOR

        By errorLocator = By.cssSelector("[data-test='error']");

        String errorText = "";

        try {
            WebElement errorMessage = waitForVisible(errorLocator);
            errorText = errorMessage.getText();

        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("No validation error message appeared.");
        }

        String currentUrl = driver.getCurrentUrl();

        System.out.println("Actual Error Message: [" + errorText + "]");
        System.out.println("Current URL: " + currentUrl);

        // STEP 8: VERIFY EXPECTED RESULT

        boolean errorDisplayed =
                errorText.contains("Last Name is required");

        boolean remainedOnInformationPage =
                currentUrl.contains("checkout-step-one.html");

        Assert.assertTrue(
                errorDisplayed,
                "BUG-CHK-002: Expected Last Name validation error was not displayed"
        );

        Assert.assertTrue(
                remainedOnInformationPage,
                "BUG-CHK-002: User was allowed to proceed without Last Name"
        );

        System.out.println(
                "STEP 8 PASS: Last Name validation verified"
        );

        System.out.println("FINAL RESULT: NEGATIVE TEST PASSED");
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            pause(3);
            driver.quit();
        }
    }
}
