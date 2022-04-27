package com.epam.lab.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends BasePage{

    @FindBy(xpath = "//div[@data-filter-name='producer']//input[@name='searchline']")
    private WebElement searchBrandField;

    @FindBy(xpath = "//a[@data-id='HP']")
    private WebElement hpCheckBox;

    @FindBy(xpath = "//select[@_ngcontent-rz-client-c184]")
    private WebElement filterDropDown;

    @FindBy(xpath = "//select[@_ngcontent-rz-client-c184]//option")
    private List<WebElement> fromExpensiveToCheap;

    @FindBy(xpath = "//button[@class='buy-button goods-tile__buy-button ng-star-inserted']")
    private List<WebElement> listOfCartIcons;

    @FindBy(xpath = "//button[@class='header__button ng-star-inserted header__button--active']")
    private WebElement cartButton;

    @FindBy(xpath = "//div[@class='cart-receipt__sum-price']//span")
    private WebElement price;

    public boolean isSearchBrandFieldVisible() { return searchBrandField.isDisplayed(); }
    public void enterTextToSearchBrandField(final String searchText) {
        searchBrandField.clear();
        searchBrandField.sendKeys(searchText);
        searchBrandField.sendKeys(Keys.ENTER);
    }

    public WebElement getHpCheckBox() { return hpCheckBox;}
    public boolean isHpCheckBoxVisible() { return hpCheckBox.isDisplayed(); }
    public boolean isHpCheckBoxEnabled() { return hpCheckBox.isEnabled(); }
    public void hpCheckBoxClick() { hpCheckBox.click(); }

    public WebElement getFilterDropDown() {return filterDropDown;}
    public void clickFilterDropDown() { filterDropDown.click();}

    public void clickFromExpensiveToCheap() { fromExpensiveToCheap.get(2).click();}
    public void clickListOfCartIcons() { listOfCartIcons.get(0).click(); }

    public void clickOnCartButton() {cartButton.click();}
    public WebElement getCartButton() { return cartButton; }

    public boolean isPriceVisible() { return price.isDisplayed(); }
    public WebElement getPrice() { return price; }
    public int getTextFromPrice() {return Integer.parseInt(price.getText());}

    public SearchResultsPage(WebDriver driver) { super(driver); }
}