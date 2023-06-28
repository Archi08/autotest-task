package github.data;

public enum EmailMessage {

    HEADER_MESSAGE("See What’s Waiting for You"),
    BODY_MESSAGE("To get you into adventure mode, we’ve included an exclusive promo code for you to use on\n" +
            " \nyour next booking. Simply enter the code at checkout and your discount will be applied!"),
    SENDER_NAME("TourRadar"),
    SENDER_EMAIL("no-reply@tourradar.com");


    EmailMessage(String textFormat) {
        this.textFormat = textFormat;
    }

    private String textFormat;

    public String getText() {
        return textFormat;
    }


}
