package org.example;

import com.google.common.annotations.VisibleForTesting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;



public class Test_Suite
{

    protected static WebDriver driver;
    static String ExpectedRegistrationMessage = "Your registration completed."; //String variable to store registration message.
    static String ExpectedSendEmailToFriendMessage = "Your email has send to your friend successfully."; //Variable to store email send message.
    static String ExpectedErrorMessageWhileVoting = "Only registered users can vote.";
    //Before method to start browser and open url before executing @test.
    @BeforeMethod
    public static void OpenBrowser()
    {
        driver = new ChromeDriver();
        driver.get("https://demo.nopcommerce.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    //Aftermethod to close browser after testing.
    @AfterMethod
    public static void closeBrowser()
    {
        driver.close();
    }


    //Method for clickonelement
    public static void clickOnElement(By by)
    {
        driver.findElement(by).click();
    }

    //Method for typetext to input string in placeholder
    public static void typeText(By by, String text)
    {
        driver.findElement(by).sendKeys(text);
    }

    //Method to get text
    public static String getTextFromElement(By by)
    {
        return driver.findElement(by).getText();
    }

    //Method for timestamp
    public static long timestamp()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    //Testing registration.
    @Test
    public static void VerifyUserShouldAbleToRegisterSuccessfully()
    {
        //Click on register button
        clickOnElement(By.className("ico-register"));
        //type first name
        typeText(By.id("FirstName"),"map");
        //type last name
        typeText(By.id("LastName"),"map11");
        //type email.
        typeText(By.name("Email"),"test+"+timestamp()+"@gmail.com");
        //type password
        typeText(By.name("Password"),"tEstPaSs321123");
        //retype password
        typeText(By.name("ConfirmPassword"),"tEstPaSs321123");
        //Click on register button
        clickOnElement(By.id("register-button"));
        //Capture actual message
        String actualmessage = getTextFromElement(By.className("result"));
        //System.out.println(actualmessage);
        Assert.assertEquals(actualmessage,ExpectedRegistrationMessage);

    }

    //Testing email a friend functionality
    @Test
    public static void UserShouldAbleToReferProductToFriendByEmail()
    {
        //click add to cart
        clickOnElement(By.xpath("(//button[@class='button-2 product-box-add-to-cart-button'])[2]"));
        //click on email to a friend
        clickOnElement(By.xpath("//button[@class='button-2 email-a-friend-button']"));
        //type friend's email
        typeText(By.className("friend-email") , "jplpatel+"+timestamp()+"@gmail.com");
        //Type your email
        typeText(By.className("your-email") , "jppatel+"+timestamp()+"@gmail.com");
        //Writing personal message
        typeText(By.name("PersonalMessage") , "Hello Hizenberg");
        //Click on send email
        clickOnElement(By.name("send-email"));
        //Capturing message
        String actual_message = getTextFromElement(By.xpath("//div[@class='message-error validation-summary-errors']/ul/li"));
        System.out.println(actual_message);
        Assert.assertEquals(actual_message , ExpectedSendEmailToFriendMessage);
    }

    //Testing vote
    @Test
    public static void UserShouldSeeErrorMessageIfUserTryToVoteWithoutRegistering()
    {
        //Click on good
        clickOnElement(By.name("(//input[@name='pollanswers-1'])[2]"));
        //click on vote button
        clickOnElement(By.xpath("//button[@class='button-2 vote-poll-button']"));
        //capture error message
        String actual_message_v = getTextFromElement(By.xpath("//div[@class='poll-vote-error']"));
        System.out.println(actual_message_v);
        Assert.assertEquals(actual_message_v , ExpectedErrorMessageWhileVoting , "Did not match");
    }


}
