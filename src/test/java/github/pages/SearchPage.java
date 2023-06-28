package github.pages;

import com.codeborne.selenide.*;
import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SearchPage {

    private ElementsCollection listOfTour = $$("[data-cy='serp-tour']");
    private SelenideElement popularityFilter = $("[data-cy='serp-filters--sort");
    private SelenideElement acceptCoockiesButton = $("[data-cy='cookie-notification--accept-all-cookies");
    private SelenideElement lowerSlider = $("[data-cy='serp-filters--duration-lower-sliderpoint']");
    private SelenideElement upperSlider = $("[data-cy='serp-filters--duration-upper-sliderpoint']");
    private SelenideElement sliderForm = $(".b.op.b_dur");


    public void selectPopularityFilter(String optionValue) {
        popularityFilter.selectOptionByValue(optionValue);
    }

    public SelenideElement findTourByName(String tourName) {
        return $(By.xpath("//h4[contains(text(), '" + tourName + "')]/ancestor::li"));
    }

    public void clickDownloadBrochure(String tourName) {
        SelenideElement tour = findTourByName(tourName);
        tour.$("[data-cy='serp-tour--download-brochure']").click();
    }

    public void acceptCookies() {
        acceptCoockiesButton.click();
    }

    public boolean checkDaysAllTours(String days) {
        for (SelenideElement selenideElement : listOfTour) {
            if (!selenideElement.$("dd.br__price-wrapper-info-description")
                    .shouldHave(exactText(String.format("%s days", days))).exists()) {
                return false;
            }
        }
        return true;
    }

    public void performSlider(int value, SelenideElement slider, boolean isNegativeOffset) {

        // Get the size of the slider element
        Dimension size = slider.getSize();

        // Calculate the offset based on the desired value
        int xOffset = calculateOffset(value, size.getWidth());

        if (isNegativeOffset) {
            xOffset *= -1;
        }

        // Use Actions API to perform the sliding action
        new Actions(getWebDriver())
                .dragAndDropBy(slider.toWebElement(), xOffset, 0)
                .build()
                .perform();
    }

    public void setUpLengthDays(int lowerValue, int upperValue) {
        sliderForm.scrollIntoView(true);
        performSlider(lowerValue, lowerSlider, false);
        Selenide.sleep(2000);
        performSlider(upperValue, upperSlider, true);
    }


    private int calculateOffset(double value, int sliderWidth) {
        // Calculate the offset based on the value and the width of the slider
        // Adjust this calculation based on the specific slider implementation

        double minValue = 1.0;  // Minimum value of the slider
        double maxValue = 22.0; // Maximum value of the slider

        double ratio = (value - minValue) / (maxValue - minValue);
        return (int) (ratio * sliderWidth) * 10;
    }

    public boolean checkSortlistbyDesc() {
        List<String> tourPrices = new ArrayList<>();
        for (SelenideElement element : listOfTour.filterBy(Condition.cssClass("dd.br__price-wrapper-info-description"))) {
            tourPrices.add(element.getText());
        }
        boolean isSortedDescending = Ordering.natural().reverse().isOrdered(tourPrices);
        return isSortedDescending;
    }


}
