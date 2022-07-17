package pageObjects.admin;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.admin.DashboardPageUI;

public class DashboardPageObject extends BasePage {
    private WebDriver driver;

    public DashboardPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isDashboardHeaderDisplayed() {
        waitForElementVisible(driver, DashboardPageUI.DASHBOARD_HEADER);
        return isElementDisplayed(driver, DashboardPageUI.DASHBOARD_HEADER);
    }
}
