package github.data;

public enum BrochureText {

    SENT("We’ve sent the ‘ %s ’ brochure to %s. Check your inbox!"),
    HEADER_INFO("Brochure successfully sent!"),
    RATING_TITLE("How easy has it been using TourRadar so far?"),
    RATING_TITLE_FEEDBACK("Do you have any feedback for the rating you provided?"),
    PLACEHOLDER_FEEDBACK("Your feedback goes here"),
    SUBMIT_FEEDBACK("Thank you for providing your feedback, we appreciate it.");

    private String textFormat;

    BrochureText(String textFormat) {
        this.textFormat = textFormat;
    }

    public String getText() {
        return textFormat;
    }

    public String getText(String tourName, String email) {
        return String.format(textFormat, tourName, email);
    }
}
