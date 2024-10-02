package Utilility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class ExtentReportManager implements ITestListener {
    /*public static void main(String[] args) {
        //blank html
        ExtentSparkReporter reporter = new ExtentSparkReporter("./ExtentReport.html");  // UI of the report
        reporter.config().setTheme(Theme.DARK);
        //create report
        ExtentReports extent = new ExtentReports(); // common infos like tester name , category
        extent.attachReporter(reporter);
        ExtentTest test = extent.createTest("Test1"); //create test case entrires in report and update status of the reports
        test.assignAuthor("Yogesh");
        test.assignCategory("regression");
        test.pass("Test passed1");
        test.pass("Test passed");
        test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath("/Users/yogesh-21540/Downloads/PetstoreProject/src/test/java/Utilility/Yogesh.jpeg")
                .build());
        extent.flush();
    }*/
    public static ExtentReports extent;
    public static ExtentTest test;
    @BeforeSuite
    public void startReport(){
        //blank html
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./ExtentReport.html");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Extent Report Demo");
        sparkReporter.config().setReportName("Test Report");
        //create report
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        test = extent.createTest("testName");
        test.assignAuthor("author");
        test.assignCategory("category");
    }

    @AfterSuite
    public void endReport(){
       extent.flush();
       System.out.println("Extent Report Path: " + System.getProperty("user.dir") + "/ExtentReport.html");
    }

    /*@BeforeClass
    public void testFormat(String testName,String author,String category, String status){
        test = extent.createTest(testName);
        test.assignAuthor(author);
        test.assignCategory(category);

    }*/


/*    public void reportStatus(String status,String description){
//        test.pass("Test passed1");
//        test.pass("Test passed");
//        test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath("/Users/yogesh-21540/Downloads/PetstoreProject/src/test/java/Utilility/Yogesh.jpeg")
//                .build());
        switch (status){
            case "pass":
                test.pass(description);
                break;
            case "fail":
                test.fail(description);
                break;
            case "info":
                test.info(description);
                break;
            case "warning":
                test.warning(description);
                break;
            default:
                System.err.println("status not found");
                break;
        }
    }*/

    @AfterMethod
    public void report(ITestResult result){
       if(result.getStatus() == ITestResult.FAILURE){
           ///test.fail(result.getThrowable());
           test = extent.createTest(result.getName());
           test.log(Status.FAIL, result.getThrowable());
       }
       else if(result.getStatus() == ITestResult.SUCCESS){
           test = extent.createTest(result.getName());
           test.pass("Test passed");
           test.log(Status.PASS, "Test case passed is " + result.getName());
       }
       else if(result.getStatus() == ITestResult.SKIP){
           test = extent.createTest(result.getName());
           test.skip("Test skipped");
           test.log(Status.SKIP, result.getThrowable());
       }

    }
}
