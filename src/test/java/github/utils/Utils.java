package github.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Utils {

    private static final long TIMEOUT = 10000; // Timeout in milliseconds
    private static final long POLLING_INTERVAL = 500; // Polling interval in milliseconds

    public void waitUntilUrlDoesNotContain(String value, String currentUrl) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + TIMEOUT;

        while (System.currentTimeMillis() < endTime) {
            Selenide.sleep(POLLING_INTERVAL);
            if (!currentUrl.contains(value)) {
                return;
            }
        }
        throw new RuntimeException("Timeout: URL still contains the value after waiting");
    }

    public static boolean comparePDFs(String localFilePath, String remoteFileUrl) {
        try {
            // Load local PDF
            PDDocument localDocument = PDDocument.load(new File(localFilePath));
            PDFTextStripper localTextStripper = new PDFTextStripper();
            String localText = localTextStripper.getText(localDocument);

            // Load remote PDF
            URL remoteURL = new URL(remoteFileUrl);
            InputStream remoteInputStream = new BufferedInputStream(remoteURL.openStream());
            RandomAccessBufferedFileInputStream remoteFileInputStream =
                    new RandomAccessBufferedFileInputStream(remoteInputStream);
            PDFParser remoteParser = new PDFParser(remoteFileInputStream);
            remoteParser.parse();
            COSDocument remoteCosDoc = remoteParser.getDocument();
            PDDocument remoteDocument = new PDDocument(remoteCosDoc);
            PDFTextStripper remoteTextStripper = new PDFTextStripper();
            String remoteText = remoteTextStripper.getText(remoteDocument);

            // Compare the text content of the two PDFs
            boolean areEqual = localText.equals(remoteText);

            // Clean up resources
            localDocument.close();
            remoteDocument.close();

            return areEqual;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void openTabWithUrl(String url) {
        executeJavaScript("window.open(arguments[0], '_blank');",
                String.format("%s", url));
        switchTo().window(1);
    }


}
