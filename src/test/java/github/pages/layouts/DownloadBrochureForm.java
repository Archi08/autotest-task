package github.pages.layouts;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import github.data.BrochureText;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DownloadBrochureForm {

    private SelenideElement emailInput = $("[data-cy='common-download-brochure--email-input']");
    private SelenideElement downloadBrochureButton = $("[data-cy='common-download-brochure--submit']");
    private SelenideElement infoSent = $(By.id("callback_popup")).$(".scout-component__modal-heading");
    private SelenideElement brochureText = $(By.id("callback_popup")).$(".brochure-modal__text");
    private SelenideElement ratingTitle = $(".ces-rating__title");
    private SelenideElement feedbackTitle = $(".ces-rating__section")
            .find(byCssSelector(".ces-rating__feedback-wrapper p"));
    private SelenideElement feedbackInput = $(By.id("ces-ratting__feedback-text"));
    private SelenideElement submitButton = $("[data-cy='scout-button']");
    private SelenideElement submitFeedback = $(".ces-rating__success-text");
    private ElementsCollection ratingButtons = $$(".ces-rating__item");



    public void downloadBrochure(String email) {
        emailInput.setValue(email);
        downloadBrochureButton.click();
    }

    public void checkBrochureText(String expectedTourName, String expectedEmail) {
        infoSent.shouldHave(exactText(BrochureText.HEADER_INFO.getText()));
        brochureText.shouldHave(exactText(BrochureText.SENT.getText(expectedTourName, expectedEmail)));
    }

    public void checkRatingForm() {
        ratingTitle.shouldHave(exactText(BrochureText.RATING_TITLE.getText()));
        ratingButtons.filterBy(enabled).shouldHave(CollectionCondition.size(5));
    }

    public void clickToFiveScore() {
        ratingButtons.last().click();
    }

    public void checkFeedbackArea() {
        feedbackTitle.shouldHave(exactText(BrochureText.RATING_TITLE_FEEDBACK.getText()));
        feedbackInput.shouldHave(attribute("placeholder", BrochureText.PLACEHOLDER_FEEDBACK.getText()));
    }

    public void submitFeedback(String feedback) {
        feedbackInput.setValue(feedback);
        submitButton.click();
    }

    public void checkFeedbackSubmissionMessage() {
        submitFeedback.shouldHave(exactText(BrochureText.SUBMIT_FEEDBACK.getText()));
    }
}
