package cz.balt03.rukovoditel.selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import cz.balt03.rukovoditel.selenium.LoginTestSuite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Common {

    protected ChromeDriver driver;
    private String baseURL = "https://digitalnizena.cz/rukovoditel/";
    UUID uuid = UUID.randomUUID();
    String projectName = "balt03" + uuid;

    String wrongUsername = "rukovoditell";
    String wrongPassword = "vse456ru!";
    String username = "rukovoditel";
    String password = "vse456ru";

    @Before
    public void init() {
        //windows setup
                ChromeOptions cho = new ChromeOptions();

                boolean runOnTravis = false;
                if (runOnTravis) {
                    cho.addArguments("headless");
                } else {
                    System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriverwin.exe");
                }

                driver = new ChromeDriver(cho);

        // mac setup
//        ChromeOptions cho = new ChromeOptions();
//        driver = new ChromeDriver(cho);

        // common setup
        driver.manage().window().maximize();
        driver.get(baseURL);
    }

    @After
    public void tearDown() {
        driver.close();
    }


    public void login(String username, String password) {

        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(password);
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login_form\"]/div[3]/button"));
        loginButton.click();

    }

    public void goToProjectForm() {
        WebElement projects = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/ul/li[4]/a/span"));
        projects.click();
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"entitly-listing-buttons-left\"]/button")));
        //   /html/body/div[3]/div[2]/div/div/div[2]/div/div[4]/div[1]/div/button
        // click create
        WebElement createProjectButton = driver.findElement(By.xpath("//*[@class=\"entitly-listing-buttons-left\"]/button"));
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOf(createProjectButton));
        createProjectButton.click();

        WebDriverWait wait2 = new WebDriverWait(driver, 5);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("fields_156")));

    }
    public void createProject(String priorityValue, String statusValue){
        goToProjectForm();

        //Priority
        Select priority = new Select(driver.findElement(By.id("fields_156")));
        priority.selectByVisibleText("Urgent");
        priority.selectByValue(priorityValue);

        //Status
        Select status = new Select(driver.findElement(By.id("fields_157")));
        status.selectByVisibleText("New");
        status.selectByValue(statusValue);

        //Project name
        WebElement projectNameInput = driver.findElement(By.cssSelector("#fields_158"));
        projectNameInput.sendKeys(projectName);

        //Date
        Date cur_dt = new Date();
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String strTodaysDate = dateFormat.format(cur_dt);
        WebElement dateInput = driver.findElement(By.id("fields_159"));
        dateInput.sendKeys(strTodaysDate);

        WebElement saveButton = driver.findElementByXPath("//*[@id=\"items_form\"]/div[2]/button[1]");
        saveButton.click();

        WebDriverWait projectNameWait = new WebDriverWait(driver, 1);
        projectNameWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"page-breadcrumb breadcrumb noprint\"]/li[2]/a")));
        ////*[@class="page-breadcrumb breadcrumb noprint"]/li[2]/a
        // /html/body/div[3]/div[2]/div/div/div[2]/div/div[1]/div/ul/li[2]/a
    }
}
