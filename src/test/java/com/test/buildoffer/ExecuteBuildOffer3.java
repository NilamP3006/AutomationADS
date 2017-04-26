package com.test.buildoffer;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.LogStatus;
import com.test.java.ADSConstants;
import com.test.java.BaseTest;
import com.test.java.TestDataProvider;



public class ExecuteBuildOffer3 extends BaseTest {

	@BeforeSuite
	public void beforeTest() {
		test = extentReports.startTest("******Build Offer 3******");
		openBrowserInLoginTest(prop.getProperty(ADSConstants.BROWSER_TYPE),ADSConstants.BUILDORFFER_URL);

	}

	@BeforeMethod
	public void resettingValues() {
		actualResult = ADSConstants.DEFAULT_FAILURE_MESSAGE;
		testFailed = false;
		waitInSecond(1);
		if (extentReports != null) {
			// extentReports.endTest(test);
			extentReports.flush();
		}
	}

	/**
	 * To Validate the test case of Login feature.
	 * 
	 * @param data
	 * @throws TestLinkAPIException
	 */
	@Test(dataProvider = "getBuildOffer3Data", dataProviderClass = TestDataProvider.class)
	public void getBuildOffer3Test(Hashtable<String, String> data) throws Exception {
		expResult = data.get(ADSConstants.EXPECTED_RESULT);
		testCaseName = data.get(ADSConstants.DESCRIPTION);
		System.out.println("***************" + expResult + "------------>" + testCaseName);

		s_assert = new SoftAssert();
		test.log(LogStatus.INFO, data.toString());
		test = extentReports.startTest(data.get(ADSConstants.DESCRIPTION));
		String testCaseRunMode = data.get(ADSConstants.RUNMODE);
		checkTestRunMode("BuildOffer3", testCaseRunMode);

		test.log(LogStatus.INFO, ADSConstants.EXECUTING_TEST_CASE_MESSAGE + testCaseName);

		/*
		 * Login Details
		 */

		type("loginid_xpath", "admin", "admin");
		type("password_xpath", "admin", "admin");
		click("signinButton_xpath");
		waitInSecond(2);
		// waitInSecond(2);

		/*
		 * Dashboard details
		 */

		click("BuildOffer_xpath");

		/*
		 * Create New Offer
		 */
		waitInSecond(2);
		click("CreateNewOfferButton_xpath");

		/*
		 * Build Offer 1 Details --->Select Goal
		 */

		waitInSecond(2);

		selectElement("", "customerName_xpath", data.get(ADSConstants.CUSTOMER_NAME), ADSConstants.CUSTOMER_NAME);
		waitInSecond(2);
		selectElement("", "origincountry_xpath", data.get(ADSConstants.ORIGIN_COUNTRY), ADSConstants.ORIGIN_COUNTRY);
		waitInSecond(2);
		type("projectednumberofmsg_xpath", data.get(ADSConstants.PROJECTED_NUMBER_OF_MESSAGES),
				ADSConstants.PROJECTED_NUMBER_OF_MESSAGES);
		waitInSecond(2);
		selectElement("", "customertype_xpath", data.get(ADSConstants.CUSTOMER_TYPE), ADSConstants.CUSTOMER_TYPE);
		waitInSecond(2);

		// Search Qualifiers Selectors
		selectElement("", "directclassic_xpath", data.get(ADSConstants.DIRECTCLASSIC), ADSConstants.DIRECTCLASSIC);
		waitInSecond(2);
		selectElement("", "customertype_xpath", data.get(ADSConstants.CUSTOMER_TYPE), ADSConstants.CUSTOMER_TYPE);
		selectElement("", "region_xpath", data.get(ADSConstants.REGION), ADSConstants.REGION);
		waitInSecond(2);
		selectElement("", "destinationcountry_xpath", data.get(ADSConstants.DESTINATION_COUNTRY),
				ADSConstants.DESTINATION_COUNTRY);
		waitInSecond(2);
		selectElement("", "destionationoperator_xpath", data.get(ADSConstants.DESTINATION_OPERATOR),
				ADSConstants.DESTINATION_OPERATOR);
		waitInSecond(2);
		click("buildoffer1addbutton_xpath");
		click("savebutton_xpath");
		waitInSecond(2);
		click("findRoutes_xpath");
		waitInSecond(2);

		// Build Offer 2 Execution

		click("buildoffer2checkbox_xpath");

		if (data.get(ADSConstants.DESTINATION_OPERATOR) == "Y") {
			click("saveworkbutton_xpath");
		}
		waitInSecond(2);
		click("assignroutestoofferbutton_xpath");
		waitInSecond(2);
		/*
		 * type("projectedmonthlymessagevolume_xpath",
		 * data.get(EmdbConstants.PROJECTED_MONTHLY_MESSAGE_VOLUME),
		 * EmdbConstants.PROJECTED_MONTHLY_MESSAGE_VOLUME);
		 */ waitInSecond(2);
		String totalmonthly_Cost = data.get(ADSConstants.TOTAL_MONTHLY_COST);
		Boolean isSuccess = false;
		Boolean isError = false;
		if (isNumeric(totalmonthly_Cost)) {
			String totalmonthly_cost = totalmonthly_Cost.replaceAll("[0]+$", "").replaceAll("(\\.)(?!.*?[1-9]+)", "");
			type("totalmontlhycost_xpath", totalmonthly_cost, totalmonthly_cost);
			click("pricemarginbutton_xpath");
			isSuccess = checkLoginSuccessful(getElements("forecastmargin_xpath"));
		} else {
			waitInSecond(4);
			type("totalmontlhycost_xpath", data.get(ADSConstants.TOTAL_MONTHLY_COST), ADSConstants.TOTAL_MONTHLY_COST);
			waitInSecond(4);
			click("pricemarginbutton_xpath");
			waitInSecond(4);
			isError = checkLoginSuccessful(getElements("validatemsg_xpath"));
		}

		expectedResult = data.get(ADSConstants.EXPECTTED_RESULT);

		// checkTestRunMode("Buildoffer1", testCaseRunMode);

		if (isSuccess) {
			actualResult = ADSConstants.FORECASTMARGIN_SUCCESS;

		}
		if (isError) {
			actualResult = "Enter Numbers Only";

		}
		try {
			if (actualResult.equalsIgnoreCase(expectedResult)) {
				reportPass(testCaseName + ADSConstants.DEFAULT_TEST_PASSED_MESSAGE);
				notes = actualResult;
			} else {
				s_assert.assertEquals(actualResult, expResult, actualResult + " And " + expResult + " Not Match");
				testFailed = true;
				notes = actualResult;
				takeScreenShot();

			}
		} catch (Exception e) {
			notes = actualResult;

		} finally {

			if (testFailed) {
				reportPass(testCaseName + ADSConstants.DEFAULT_TEST_PASSED_MESSAGE);
				notes = actualResult;
				takeScreenShot();
			}
		}
		s_assert.assertAll();

	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(.\\d+)?");
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@AfterMethod
	public void afterMethod() throws Exception {

		if (isLoggedIn) {
			extentReports.flush();
			driver.navigate().back();
			driver.navigate().back();
			driver.navigate().back();
		}
	}

	@AfterSuite
	public void aftersuite() throws IOException {

		if (driver != null) {
			driver.quit();
			Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
			//deleteFolder(ADSConstants.LOCAL_TEMP_DIR_PATH);

		}
	}

}
