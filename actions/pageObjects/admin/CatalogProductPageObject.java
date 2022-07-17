package pageObjects.admin;

import commons.BasePage;
import org.openqa.selenium.WebDriver;

public class CatalogProductPageObject extends BasePage {
    private WebDriver driver;

    public CatalogProductPageObject(WebDriver driver) {
        this.driver = driver;
    }
}