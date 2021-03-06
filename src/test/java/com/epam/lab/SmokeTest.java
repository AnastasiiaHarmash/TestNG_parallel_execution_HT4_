package com.epam.lab;

import com.epam.lab.page.HomePageRozetka;
import com.epam.lab.page.SearchResultsPage;
import com.epam.lab.util.DriverFactoryManager;
import com.epam.lab.util.PropertiesReader;
import com.epam.lab.util.TestListener;
import com.epam.lab.util.XMLToObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.xml.sax.SAXException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Listeners({TestListener.class})
public class SmokeTest {

    private static final Logger logger = LogManager.getLogger(SmokeTest.class);
    private static final long DEFAULT_TIMEOUT = 60;
    private static XMLToObject xmlToObject;
    PropertiesReader propertiesReader = new PropertiesReader();

    static {
        try {
            xmlToObject = new XMLToObject();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeTest
    public void profileSetUp() {
        BasicConfigurator.configure();
        logger.info("BeforeTest in progress.");
        System.setProperty(propertiesReader.getDriverName(), propertiesReader.getDriverLocation());

    }

    @BeforeMethod
    public void testsSetUp() {
        logger.trace("BeforeMethod in progress.");
        DriverFactoryManager.setDriver();
        DriverFactoryManager.getDriver().manage().window().maximize();
        DriverFactoryManager.getDriver().get(propertiesReader.getUrl());
    }

   @AfterMethod
    public void tearDown() {
        logger.info("AfterMethod, driver close.");
        DriverFactoryManager.closeDriver();
    }

    @DataProvider
    public Object [][] simpleData() {
        return new Object[][] {{"someProduct", "someBrand", "someSum"}};
    }

    @DataProvider
    public Object [][] testData() {
        logger.info("Running dataProvider testData");
        return xmlToObject.testDataMassive();
    }

    @Test(dataProvider = "testData", invocationCount = 5, threadPoolSize = 3)
    public void smokeTest(String product, String brand, String sum) throws InterruptedException {
        logger.info("smokeTest is running");
        HomePageRozetka homePageRozetka = new HomePageRozetka();
        homePageRozetka.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        logger.info("Enter text to text field");
        homePageRozetka.enterTextToSearchField(product);
        homePageRozetka.clickSearchButton();
        SearchResultsPage searchResultsPage = new SearchResultsPage();
        searchResultsPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        searchResultsPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultsPage.getSearchBrandField());
        Assert.assertTrue(searchResultsPage.isSearchBrandFieldVisible());
        logger.info("Enter brand to search field");
        searchResultsPage.enterTextToSearchBrandField(brand);
        //redneck code
        Thread.sleep(3000);
        searchResultsPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultsPage.getListCheckBox());
        Assert.assertTrue(searchResultsPage.isListCheckBoxVisible());
        Assert.assertTrue(searchResultsPage.isListCheckBoxEnabled());
        logger.info("Click check box");
        searchResultsPage.clickListCheckBox();
        searchResultsPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultsPage.getFilterDropDown());
        logger.info("Click filter dropdown");
        searchResultsPage.clickFilterDropDown();
        searchResultsPage.clickFromExpensiveToCheap();
        searchResultsPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        //redneck code
        Thread.sleep(3000);
        searchResultsPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultsPage.isCartIconVisible());
        logger.info("Click add to cart");
        searchResultsPage.clickListOfCartIcons();
        searchResultsPage.waitForPageLoadComplete(DEFAULT_TIMEOUT);
        searchResultsPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultsPage.getCartButton());
        logger.info("Click on cart button");
        searchResultsPage.clickOnCartButton();
        searchResultsPage.waitVisibilityOfElement(DEFAULT_TIMEOUT, searchResultsPage.getPrice());
        Assert.assertTrue(searchResultsPage.isPriceVisible());
        Assert.assertTrue(Integer.parseInt(sum) < searchResultsPage.getTextFromPrice());
    }
}
