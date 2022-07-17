package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.admin.LoginPageObject;
import pageObjects.consumer.*;
import pageUIs.consumer.BasePageUI;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BasePage {
    private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;
    private long longTimeout = GlobalConstants.LONG_TIMEOUT;
    protected static BasePage getBasePage() {
        return new BasePage();
    }
    public void openPageUrl(WebDriver driver, String pageUrl) {
        driver.get(pageUrl);
    }
    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }
    public String getPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }
    public String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }
    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }
    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }
    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }
    public Alert waitForAlertPresence(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }
    public void acceptAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
    }
    public void cancelAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }
    public String getAlertText(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }
    public void sendKeyToAlert(WebDriver driver, String textValue) {
        waitForAlertPresence(driver).sendKeys(textValue);
    }
    public void switchToWindowsByID(WebDriver driver, String windowsID) {
        Set<String> allWindowsIDs = driver.getWindowHandles();
        for (String id : allWindowsIDs) {
            if (!id.equals(windowsID)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }
    public void switchToWindowsByTitle(WebDriver driver, String expectedTitle) {
        Set<String> allWindowsIDs = driver.getWindowHandles();
        for (String id : allWindowsIDs) {
            driver.switchTo().window(id);
            String windowsTitle = driver.getTitle();
            if (windowsTitle.equals(expectedTitle)) {
                break;
            }
        }
    }
    public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
        Set<String> allWindowsIDs = driver.getWindowHandles();
        for (String id : allWindowsIDs) {
            if (!id.equals(parentID)) {
                driver.switchTo().window(id);
                driver.close();
            }
            driver.switchTo().window(parentID);
        }
    }
    private By getByLocator(String locator) {
        By by = null;
        if (locator.toLowerCase().startsWith("id")) {
            by = By.id(locator.substring(3));
        } else if (locator.toLowerCase().startsWith("class")) {
            by = By.className(locator.substring(6));
        } else if (locator.toLowerCase().startsWith("name")) {
            by = By.name(locator.substring(5));
        } else if (locator.toLowerCase().startsWith("css")) {
            by = By.cssSelector(locator.substring(4));
        } else if (locator.toLowerCase().startsWith("xpath")) {
            by = By.xpath(locator.substring(6));
        } else {
            throw new RuntimeException("Locator type is not supported");
        }
        return by;
    }
    private String getDynamicXpathLocator(String locator, String... dynamicValues) {
        if (locator.toLowerCase().startsWith("xpath")) {
            locator = String.format(locator, (Object[]) dynamicValues);
        }
        return locator;
    }
    private WebElement getWebElement(WebDriver driver, String locator) {
        return driver.findElement(getByLocator(locator));
    }
    public List<WebElement> getListWebElements(WebDriver driver, String locator) {
        return driver.findElements(getByLocator(locator));
    }
    public List<WebElement> getListWebElements(WebDriver driver, String locator, String... dynamicValues) {
        return driver.findElements(getByLocator(getDynamicXpathLocator(locator, dynamicValues)));
    }
    public void clickToElement(WebDriver driver, String locator) {
        getWebElement(driver, locator).click();
    }
    public void clickToElement(WebDriver driver, String locator, String... dynamicValues) {
        getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues)).click();
    }
    public void sendKeyToElement(WebDriver driver, String locator, String textValue) {
        WebElement element = getWebElement(driver, locator);
        element.clear();
        element.sendKeys(textValue);
    }
    public void sendKeyToElement(WebDriver driver, String locator, String textValue, String... dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues));
        element.clear();
        element.sendKeys(textValue);
    }
    public void clearValueInElementByPressKey(WebDriver driver, String locator) {
        WebElement element = getWebElement(driver, locator);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }
    public void clearValueInElementByPressKey(WebDriver driver, String locator, String... dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues));
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }
    public String getElementText(WebDriver driver, String locator) {
        return getWebElement(driver, locator).getText();
    }
    public String getElementText(WebDriver driver, String locator, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues)).getText();
    }
    public void selectItemInDefaultDropdown(WebDriver driver, String locator, String textItem) {
        Select select = new Select(getWebElement(driver, locator));
        select.selectByVisibleText(textItem);
    }
    public void selectItemInDefaultDropdown(WebDriver driver, String locator, String textItem, String... dynamicValues) {
        Select select = new Select(getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues)));
        select.selectByVisibleText(textItem);
    }
    public String getSelectedItemDefaultDropdown(WebDriver driver, String locator) {
        Select select = new Select(getWebElement(driver, locator));
        return select.getFirstSelectedOption().getText();
    }
    public String getSelectedItemDefaultDropdown(WebDriver driver, String locator, String... dynamicValues) {
        Select select = new Select(getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues)));
        return select.getFirstSelectedOption().getText();
    }
    public boolean isDropdownMultiple(WebDriver driver, String locator) {
        Select select = new Select(getWebElement(driver, locator));
        return select.isMultiple();
    }
    public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedItem) {
        getWebElement(driver, parentXpath).click();
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                getByLocator(childXpath)));

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedItem)) {
                if (!item.isDisplayed()) {
                    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                    sleepInSecond(1);
                }
                item.click();
                break;
            }
        }
    }
    public void sleepInSecond(long timeout) {
        try {
            Thread.sleep(timeout * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected String getElementAttribute(WebDriver driver, String locator, String attributeName) {
        return getWebElement(driver, locator).getAttribute(attributeName);
    }
    protected String getElementAttribute(WebDriver driver, String locator, String attributeName, String... dynamicValues) {
        return getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues)).getAttribute(attributeName);
    }
    protected String getElementCssValue(WebDriver driver, String locator, String propertyName) {
        return getWebElement(driver, locator).getCssValue(propertyName);
    }
    protected String getHexColorFromRGBA(String rgbaValue) {
        return Color.fromString(rgbaValue).asHex();
    }
    protected int getElementSize(WebDriver driver, String locator) {
        return getListWebElements(driver, locator).size();
    }
    protected int getElementSize(WebDriver driver, String locator, String... dynamicValues) {
        return getListWebElements(driver, getDynamicXpathLocator(locator, dynamicValues)).size();
    }
    protected void checkToDefaultCheckboxRadio(WebDriver driver, String locator) {
        WebElement element = getWebElement(driver, locator);
        if (!element.isSelected()) {
            element.click();
        }
    }
    protected void checkToDefaultCheckboxRadio(WebDriver driver, String locator, String... dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues));
        if (!element.isSelected()) {
            element.click();
        }
    }
    protected void uncheckToDefaultCheckbox(WebDriver driver, String locator) {
        WebElement element = getWebElement(driver, locator);
        if (element.isSelected()) {
            element.click();
        }
    }
    protected void uncheckToDefaultCheckbox(WebDriver driver, String locator, String... dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues));
        if (element.isSelected()) {
            element.click();
        }
    }
    protected boolean isElementDisplayed(WebDriver driver, String locator) {
        try {
            return getWebElement(driver, locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    protected boolean isElementDisplayed(WebDriver driver, String locator, String... dynamicValues) {
        try {
            return getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    protected boolean isElementUndisplayed(WebDriver driver, String locator) {
        overrideImplicitTimeout(driver, GlobalConstants.SHORT_TIMEOUT);
        List<WebElement> elements = getListWebElements(driver, locator);

        overrideImplicitTimeout(driver, GlobalConstants.LONG_TIMEOUT);

        if (elements.size() == 0) {
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }
    protected boolean isElementUndisplayed(WebDriver driver, String locator, String... dynamicValues) {
        overrideImplicitTimeout(driver, shortTimeout);
        List<WebElement> elements = getListWebElements(driver, getDynamicXpathLocator(locator, dynamicValues));

        overrideImplicitTimeout(driver, longTimeout);

        if (elements.size() == 0) {
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }
    protected void overrideImplicitTimeout(WebDriver driver, long timeOut) {
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }
    protected boolean isElementEnabled(WebDriver driver, String locator) {
        return getWebElement(driver, locator).isEnabled();
    }
    protected boolean isElementSelected(WebDriver driver, String locator) {
        return getWebElement(driver, locator).isSelected();
    }
    protected void switchToFrameIframe(WebDriver driver, String locator) {
        driver.switchTo().frame(getWebElement(driver, locator));
    }
    protected void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }
    protected void hoverMouseToElement(WebDriver driver, String locator) {
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(driver, locator)).perform();
    }
    public void pressKeyToElement(WebDriver driver, String locator, Keys key) {
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, locator), key).perform();
    }
    public void pressKeyToElement(WebDriver driver, String locator, Keys key, String... dynamicValues) {
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues)), key).perform();
    }
    protected void scrollToBottomPage(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }
    protected void highlightElement(WebDriver driver, String locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getWebElement(driver, locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element,
                "style", originalStyle);
    }
    protected void clickToElementByJS(WebDriver driver, String locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, locator));
    }
    protected void scrollToElement(WebDriver driver, String locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locator));
    }
    protected void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver,
                locator));
    }
    protected boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }
    protected String getElementValidationMessage(WebDriver driver, String locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return arguments[0].validationMessage;",
                getWebElement(driver, locator));
    }
    protected boolean isImageLoaded(WebDriver driver, String locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && " +
                        "typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(driver, locator));
        if (status) {
            return true;
        } else {
            return false;
        }
    }
    protected boolean isImageLoaded(WebDriver driver, String locator, String... dynamicValues) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && " +
                        "typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(driver, getDynamicXpathLocator(locator, dynamicValues)));
        if (status) {
            return true;
        } else {
            return false;
        }
    }
    protected WebElement getShadowDOM(WebDriver driver, String locator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        WebElement element = (WebElement) jsExecutor
                .executeScript("return arguments[0].shadowRoot;", getWebElement(driver, locator));
        return element;
    }
    protected String getElementValueByJSXpath(WebDriver driver, String xpathLocator) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        xpathLocator = xpathLocator.replace("xpath", "");
        return (String) jsExecutor.executeScript("return $(document.evaluate(\"" + xpathLocator + "\", document, null, XPathResult.FIRST_ORDER_NODE_TYPE, null).singleNodeValue).val()");
    }
    protected void waitForElementVisible(WebDriver driver, String locator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locator)));
    }
    protected void waitForElementVisible(WebDriver driver, String locator, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(getDynamicXpathLocator(locator, dynamicValues))));
    }
    protected void waitForAllElementsVisible(WebDriver driver, String locator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locator)));
    }
    protected void waitForAllElementsVisible(WebDriver driver, String locator, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicXpathLocator(locator, dynamicValues))));
    }
    protected void waitForElementInvisible(WebDriver driver, String locator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locator)));
    }
    protected void waitForElementInvisible(WebDriver driver, String locator, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicXpathLocator(locator, dynamicValues))));
    }

    /**
     * @param driver
     * @param locator
     * Wait for element is undisplayed in DOM or is not in DOM and override implicit timeout
     */
    protected void waitForElementUndisplayed(WebDriver driver, String locator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
        overrideImplicitTimeout(driver, shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locator)));
        overrideImplicitTimeout(driver, longTimeout);
    }
    protected void waitForElementUndisplayed(WebDriver driver, String locator, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
        overrideImplicitTimeout(driver, shortTimeout);
        explicitWait.until(ExpectedConditions
                .invisibilityOfElementLocated(getByLocator(getDynamicXpathLocator(locator, dynamicValues))));
        overrideImplicitTimeout(driver, longTimeout);
    }
    protected void waitForAllElementsInvisible(WebDriver driver, String locator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, locator)));
    }
    protected void waitForAllElementsInvisible(WebDriver driver, String locator, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(
                getListWebElements(driver, getDynamicXpathLocator(locator, dynamicValues))));
    }
    protected void waitForElementClickable(WebDriver driver, String locator) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locator)));
    }
    protected void waitForElementClickable(WebDriver driver, String locator, String... dynamicValues) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpathLocator(locator, dynamicValues))));
    }
    public Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }
    public void setCookies(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie:cookies) {
            driver.manage().addCookie(cookie);
        }
    }
 /*   public void uploadMultipleFiles(WebDriver driver, String... fileNames) {
        String filePath = System.getProperty("user.dir") + getDirectorySlash("uploadFiles");
        String fullFileName = "";

        for (String file : fileNames) {
            fullFileName = fullFileName + filePath + file + "\n";
        }

        fullFileName = fullFileName.trim();

        getWebElement(driver, DashboardPageUI.UPLOAD_FILE).sendKeys(fullFileName);
    }*/
    public String getDirectorySlash(String folderName) {
        String separator = System.getProperty("file.separator");
        return separator + folderName + separator;
    }
    public CustomerInfoPageObject openCustomerInfoPage(WebDriver driver) {
        waitForElementClickable(driver, BasePageUI.CUSTOMER_INFO_LINK);
        clickToElement(driver, BasePageUI.CUSTOMER_INFO_LINK);
        return PageGeneratorManager.getCustomerInfoPage(driver);
    }
    public AddressesPageObject openAddressesPage(WebDriver driver) {
        waitForElementClickable(driver, BasePageUI.ADDRESSES_LINK);
        clickToElement(driver, BasePageUI.ADDRESSES_LINK);
        return PageGeneratorManager.getAddressesPage(driver);
    }
    public OrdersPageObject openOrdersPage(WebDriver driver) {
        waitForElementClickable(driver, BasePageUI.ORDERS_LINK);
        clickToElement(driver, BasePageUI.ORDERS_LINK);
        return PageGeneratorManager.getOrdersPage(driver);
    }
    public DownloadableProductsPageObject openDownloadableProductsPage(WebDriver driver) {
        waitForElementClickable(driver, BasePageUI.DOWNLOADABLE_PRODUCTS_LINK);
        clickToElement(driver, BasePageUI.DOWNLOADABLE_PRODUCTS_LINK);
        return PageGeneratorManager.getDownloadableProductsPage(driver);
    }
    public BackInStockSubscriptionsPageObject openBackInStockSubscriptionsPage(WebDriver driver) {
        waitForElementClickable(driver, BasePageUI.BACK_IN_STOCK_SUBSCRIPTIONS_LINK);
        clickToElement(driver, BasePageUI.BACK_IN_STOCK_SUBSCRIPTIONS_LINK);
        return PageGeneratorManager.getBackInStockSubscriptionsPage(driver);
    }
    public RewardPointsPageObject openRewardPointsPage(WebDriver driver) {
        waitForElementClickable(driver, BasePageUI.REWARD_POINTS_LINK);
        clickToElement(driver, BasePageUI.REWARD_POINTS_LINK);
        return PageGeneratorManager.getRewardPointsPage(driver);
    }
    public ChangePasswordPageObject openChangePasswordPage(WebDriver driver) {
        waitForElementClickable(driver, BasePageUI.CHANGE_PASSWORD_LINK);
        clickToElement(driver, BasePageUI.CHANGE_PASSWORD_LINK);
        return PageGeneratorManager.getChangePasswordPage(driver);
    }
    public MyProductReviewsPageObject openMyProductReviewsPage(WebDriver driver) {
        waitForElementClickable(driver, BasePageUI.MY_PRODUCT_REVIEWS_LINK);
        clickToElement(driver, BasePageUI.MY_PRODUCT_REVIEWS_LINK);
        return PageGeneratorManager.getMyProductReviewsPage(driver);
    }
    public HomePageObject clickToLogoutLinkAtConsumerPage(WebDriver driver) {
        waitForElementVisible(driver, BasePageUI.LOGOUT_LINK);
        clickToElement(driver, BasePageUI.LOGOUT_LINK);
        return PageGeneratorManager.getHomePage(driver);
    }
    public LoginPageObject clickToLogoutLinkAtAdminPage(WebDriver driver) {
        waitForElementVisible(driver, pageUIs.admin.BasePageUI.LOGOUT_LINK);
        clickToElement(driver, pageUIs.admin.BasePageUI.LOGOUT_LINK);
        return PageGeneratorManager.getAdminLoginPage(driver);
    }
    public BasePage openPagesAtMyAccountSidebarByName(WebDriver driver, String pageName) {
        waitForElementClickable(driver, BasePageUI.DYNAMIC_PAGES_AT_MY_ACCOUNT_SIDEBAR, pageName);
        clickToElement(driver, BasePageUI.DYNAMIC_PAGES_AT_MY_ACCOUNT_SIDEBAR, pageName);
        switch (pageName) {
            case "Customer info":
                return PageGeneratorManager.getCustomerInfoPage(driver);
            case "Addresses":
                return PageGeneratorManager.getAddressesPage(driver);
            case "Orders":
                return PageGeneratorManager.getOrdersPage(driver);
            case "Downloadable products":
                return PageGeneratorManager.getDownloadableProductsPage(driver);
            case "Back in stock subscriptions":
                return PageGeneratorManager.getBackInStockSubscriptionsPage(driver);
            case "Reward points":
                return PageGeneratorManager.getRewardPointsPage(driver);
            case "Change password":
                return PageGeneratorManager.getChangePasswordPage(driver);
            case "My product reviews":
                return PageGeneratorManager.getMyProductReviewsPage(driver);
            default:
                throw new RuntimeException("Invalid page name at My Account sidebar");
        }
    }
    public void openPagesAtMyAccountSidebarByPageName(WebDriver driver, String pageName) {
        waitForElementClickable(driver, BasePageUI.DYNAMIC_PAGES_AT_MY_ACCOUNT_SIDEBAR, pageName);
        clickToElement(driver, BasePageUI.DYNAMIC_PAGES_AT_MY_ACCOUNT_SIDEBAR, pageName);
    }

    /** Input data into a dynamic textbox by ID
     * @param driver
     * @param textboxID
     * @param textboxValue
     */
    public void inputToTextboxByID(WebDriver driver, String textboxID, String textboxValue) {
        waitForElementVisible(driver, BasePageUI.DYNAMIC_TEXTBOX_BY_ID, textboxID);
        sendKeyToElement(driver, BasePageUI.DYNAMIC_TEXTBOX_BY_ID, textboxValue, textboxID);
    }

    /** Click to a dynamic button by Text
     * @param driver
     * @param buttonText
     */
    public void clickToButtonByText(WebDriver driver, String buttonText) {
        waitForElementClickable(driver, BasePageUI.DYNAMIC_BUTTON_BY_TEXT, buttonText);
        clickToElement(driver, BasePageUI.DYNAMIC_BUTTON_BY_TEXT, buttonText);
    }

    /** Select a dynamic item of Dropdown-list by label name
     * @param driver
     * @param dropdownAttributeName
     * @param itemValue
     */
    public void selectToDropdownByName(WebDriver driver, String dropdownAttributeName, String itemValue) {
        waitForElementVisible(driver, BasePageUI.DYNAMIC_DROPDOWN_BY_NAME_ATTRIBUTE, dropdownAttributeName);
        selectItemInDefaultDropdown(driver, BasePageUI.DYNAMIC_DROPDOWN_BY_NAME_ATTRIBUTE, itemValue, dropdownAttributeName);
    }

    /**
     * @param driver
     * @param genderLabel
     */
    public void checkToRadioButtonByLabel(WebDriver driver, String genderLabel) {
        waitForElementClickable(driver, BasePageUI.DYNAMIC_RADIO_BUTTON_BY_LABEL, genderLabel);
        checkToDefaultCheckboxRadio(driver, BasePageUI.DYNAMIC_RADIO_BUTTON_BY_LABEL, genderLabel);
    }

    /** Check to a dynamic checkbox by label name
     * @param driver
     * @param checkboxLabel
     */
    public void checkToCheckboxByLabelName(WebDriver driver, String checkboxLabel) {
        waitForElementClickable(driver, BasePageUI.DYNAMIC_CHECKBOX_BY_LABEL, checkboxLabel);
        checkToDefaultCheckboxRadio(driver, BasePageUI.DYNAMIC_CHECKBOX_BY_LABEL, checkboxLabel);
    }

    public String getTextboxAttributeValueByID(WebDriver driver, String textboxID) {
        waitForElementVisible(driver, BasePageUI.DYNAMIC_TEXTBOX_BY_ID, textboxID);
        return getElementAttribute(driver, BasePageUI.DYNAMIC_TEXTBOX_BY_ID, "value", textboxID);
    }

}