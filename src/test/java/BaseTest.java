import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.ITestResult;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {

    public WebDriver driver;
    public WebDriverWait wait;
    public ExtentReports extent;
    public ExtentSparkReporter reporter;
    public ExtentTest  logger;

    @BeforeSuite
    public void beforeSuite() {
        extent = new ExtentReports();
        reporter = new ExtentSparkReporter("./reports/"+ System.currentTimeMillis() +".html");
        extent.attachReporter(reporter);
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        logger = extent.createTest(method.getName());
        logger.info("Driver has been initialized and the test has started.");
    }


    @AfterMethod
    public void afterTest() {
        if (driver!=null) {
            try {
                driver.quit();
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64)).build());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            logger.info("Test succeeded.");
        }
    }

    @AfterSuite
    public void afterSuite() {
        extent.flush();
    }
}
