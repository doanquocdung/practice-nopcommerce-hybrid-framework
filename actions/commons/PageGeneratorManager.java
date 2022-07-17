package commons;

import org.openqa.selenium.WebDriver;
import pageObjects.admin.DashboardPageObject;
import pageObjects.consumer.*;

public class PageGeneratorManager {

    public static HomePageObject getHomePage(WebDriver driver) {
        return new HomePageObject(driver);
    }

    public static RegisterPageObject getRegisterPage(WebDriver driver) {
        return new RegisterPageObject(driver);
    }

    public static LoginPageObject getConsumerLoginPage(WebDriver driver) {
        return new LoginPageObject(driver);
    }

    public static CustomerInfoPageObject getCustomerInfoPage(WebDriver driver) {
        return new CustomerInfoPageObject(driver);
    }

    public static AddressesPageObject getAddressesPage(WebDriver driver) {
        return new AddressesPageObject(driver);
    }

    public static OrdersPageObject getOrdersPage(WebDriver driver) {
        return new OrdersPageObject(driver);
    }

    public static DownloadableProductsPageObject getDownloadableProductsPage(WebDriver driver) {
        return new DownloadableProductsPageObject(driver);
    }

    public static BackInStockSubscriptionsPageObject getBackInStockSubscriptionsPage(WebDriver driver) {
        return new BackInStockSubscriptionsPageObject(driver);
    }

    public static RewardPointsPageObject getRewardPointsPage(WebDriver driver) {
        return new RewardPointsPageObject(driver);
    }

    public static ChangePasswordPageObject getChangePasswordPage(WebDriver driver) {
        return new ChangePasswordPageObject(driver);
    }

    public static MyProductReviewsPageObject getMyProductReviewsPage(WebDriver driver) {
        return new MyProductReviewsPageObject(driver);
    }

    public static pageObjects.admin.LoginPageObject getAdminLoginPage(WebDriver driver) {
        return new pageObjects.admin.LoginPageObject(driver);
    }

    public static DashboardPageObject getDashboardPage(WebDriver driver) {
        return new DashboardPageObject(driver);
    }
}
