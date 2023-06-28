package github.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import github.data.EmailMessage;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static github.utils.Utils.comparePDFs;

public class EmailPage {

    private SelenideElement emailInfo = $("[data-original-title='Your Temporary Email Address']");
    private SelenideElement refreshButton = $(By.id("click-to-refresh"));
    private SelenideElement firstMessage = $x(("(//a[@class='viewLink link'])[2]"));
    private SelenideElement inboxEmail = $(".inbox-dataList ul li").find("div.m-link-view");
    private SelenideElement loadingElement = $(".inbox-empty");
    private SelenideElement tourName = $(".tbody tr a b:nth-child(1)");
    private SelenideElement downloadButton = $(".a[title='Download brochure'] span");
    private SelenideElement availabilityButton = $(".a[title='Check Availability'] span");


    public String getEmail() {
        while (emailInfo.has(cssClass("disabledText"))) {
            emailInfo.shouldHave(Condition.visible, Duration.ofSeconds(5));
        }
        return emailInfo.getValue();
    }

    public void refreshEmailBox() {
        refreshButton.click();
        while (!loadingElement.has(cssClass("hide"))) {
            loadingElement.shouldHave(Condition.visible, Duration.ofSeconds(5));
        }
    }

    public void checkInfoMessage( String tourName) {
        $(String.format("span[title='%s ']", EmailMessage.SENDER_NAME.getText())).shouldHave(visible);
        $(String.format("span[title='%s']", EmailMessage.SENDER_EMAIL.getText())).shouldHave(visible);
        $x(String.format("//a[contains(text(),'%s \uD83D\uDE0E')]", tourName)).shouldHave(visible);
    }

    public void clickFirstMessage() {
        firstMessage.click();
    }

    public void checkTourName(String tourName) {
        this.tourName.shouldHave(Condition.exactText(tourName));
    }

    public void checkAvailability(String tourName) {
        availabilityButton.click();
        $x(String.format("//h1[normalize-space()='%s']", tourName)).shouldHave(Condition.visible);
    }

    public void clickDownloadBrochure() {
        downloadButton.click();

    }

    public boolean checkPdf(String localFilePath, String remoteFileUrl) {
        return comparePDFs(localFilePath, remoteFileUrl);
    }
}
