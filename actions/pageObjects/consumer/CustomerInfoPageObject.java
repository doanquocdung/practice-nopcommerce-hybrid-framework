package pageObjects.consumer;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.consumer.CustomerInfoPageUI;

public class CustomerInfoPageObject extends BasePage {
    private WebDriver driver;

    public CustomerInfoPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isCustomerInfoPageDisplayed() {
        waitForElementVisible(driver, CustomerInfoPageUI.CUSTOMER_INFO_HEADER);
        return isElementDisplayed(driver, CustomerInfoPageUI.CUSTOMER_INFO_HEADER);
    }
}
