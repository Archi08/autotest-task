package github.data;

public enum SortingInputData {

    POPULARITY_FIRST("popularity"),
    PRICE_HIGHEST_FIRST("prdesc");

    SortingInputData(String textFormat) {
        this.textFormat = textFormat;
    }

    private String textFormat;

    public String getText() {
        return textFormat;
    }


}
