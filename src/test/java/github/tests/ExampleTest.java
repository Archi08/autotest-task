package github.tests;

import com.codeborne.selenide.WebDriverRunner;
import github.data.SortingInputData;
import github.common.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;
import static github.utils.Utils.openTabWithUrl;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Epic("Example Task")
@Feature("Client UI")
@Severity(SeverityLevel.NORMAL)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExampleTest extends BaseTest {

    @Test
    @Order(1)
    @Description("Dowbload broucher and check file")
    void downloadBroucher() {
        open(EMAIL_URI);
        String email = emailPage.getEmail();
        String tourName = "Europe Escape - 12 Days";
        openTabWithUrl(BASE_URI);
        searchPage.selectPopularityFilter(SortingInputData.POPULARITY_FIRST.getText());
        searchPage.acceptCookies();
        searchPage.clickDownloadBrochure(tourName);
        downloadBrochureForm.downloadBrochure(email);
        downloadBrochureForm.checkBrochureText(tourName, email);
        downloadBrochureForm.checkRatingForm();
        downloadBrochureForm.clickToFiveScore();
        downloadBrochureForm.checkFeedbackArea();
        downloadBrochureForm.submitFeedback("test data");
        downloadBrochureForm.checkFeedbackSubmissionMessage();
        switchTo().window(0);
        emailPage.checkInfoMessage(tourName);
        emailPage.clickFirstMessage();
        emailPage.checkTourName(tourName);
        emailPage.checkAvailability(tourName);
        switchTo().window(0);
        emailPage.clickDownloadBrochure();
        utils.waitUntilUrlDoesNotContain("tourradar", webdriver().driver().url());
        boolean actualState = emailPage.checkPdf(
                PDF_PATH, WebDriverRunner.url());
        assertTrue(actualState, "PDF files are different.");
    }

    @Test
    @Order(2)
    @Description("Check days filter and price sorting")
    public void checkFilterandSorting() {
        String days = "12";
        open("https://www.tourradar.com/d/europe");
        searchPage.acceptCookies();
        searchPage.selectPopularityFilter(SortingInputData.PRICE_HIGHEST_FIRST.getText());
        searchPage.setUpLengthDays(Integer.parseInt(days), Integer.parseInt(days));
        searchPage.checkDaysAllTours(days);
        boolean isSortedDescending = searchPage.checkSortlistbyDesc();
        assertTrue(isSortedDescending, "List sorted incorrectly.");
    }

}

