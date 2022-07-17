package commons;

import java.io.File;

public class GlobalConstants {
    public static final String DEV_CONSUMER_PAGE_URL = "https://demo.nopcommerce.com";
    public static final String DEV_ADMIN_PAGE_URL = "https://admin-demo.nopcommerce.com";
    public static final String DEV_ADMIN_EMAIL_ADDRESS = "admin@yourstore.com";
    public static final String DEV_ADMIN_PASSWORD = "admin";

    public static final String STAGING_CONSUMER_PAGE_URL = "https://demo.nopcommerce.com";
    public static final String STAGING_ADMIN_PAGE_URL = "https://admin-demo.nopcommerce.com";
    public static final String STAGING_ADMIN_EMAIL_ADDRESS = "admin@yourstore.com";
    public static final String STAGING_ADMIN_PASSWORD = "admin";

    public static final String PROD_CONSUMER_PAGE_URL = "https://demo.nopcommerce.com";
    public static final String PROD_ADMIN_PAGE_URL = "https://admin-demo.nopcommerce.com";
    public static final String PROD_ADMIN_EMAIL_ADDRESS = "admin@yourstore.com";
    public static final String PROD_ADMIN_PASSWORD = "admin";

    public static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String JAVA_VERSION = System.getProperty("java.version");
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String UPLOAD_FILE = PROJECT_PATH + File.separator + "uploadFiles";
    public static final String BROWSER_LOG = PROJECT_PATH + File.separator + "browserLogs";
    public static final String DRAG_DROP = PROJECT_PATH + File.separator + "dragAndDrop";
    public static final String AUTO_IT_SCRIPT = PROJECT_PATH + File.separator + "autoIT";
    public static final String REPORTNG_SCREENSHOT = PROJECT_PATH + File.separator + "reportNGImages" + File.separator;
    public static final long SHORT_TIMEOUT = 5;
    public static final long LONG_TIMEOUT = 30;
    public static final int RETRY_TEST_FAIL = 3;
}