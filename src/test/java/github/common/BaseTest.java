package github.common;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.automatedowl.tools.AllureEnvironmentWriter;
import github.utils.Utils;
import github.pages.EmailPage;
import com.google.common.collect.ImmutableMap;
import github.pages.SearchPage;
import github.pages.layouts.DownloadBrochureForm;
import github.utils.PropertiesLoader;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.invoke.MethodHandles.lookup;



public class BaseTest {

    protected final String BASE_URI = PropertiesLoader.loadProperties("urlSite");
    protected final String EMAIL_URI = PropertiesLoader.loadProperties("urlEmail");
    protected final String PDF_PATH = PropertiesLoader.loadProperties("pathToFile");

    private final static Logger logger = LoggerFactory.getLogger(lookup().lookupClass());
    private final static String selenideProperties = "selenide.properties";

    protected DownloadBrochureForm downloadBrochureForm = new DownloadBrochureForm();
    protected SearchPage searchPage = new SearchPage();
    protected EmailPage emailPage = new EmailPage();
    protected Utils utils = new Utils();


    @BeforeAll
    static void setupClass() throws IOException {
        /*
         * Add AllureSelenide listener
         */
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        /*
         * Load selenide.properties file in resources
         */
        Properties props = new Properties();
        InputStream inputStream = BaseTest.class
                .getClassLoader()
                .getResourceAsStream(selenideProperties);
        props.load(inputStream);

        if (!props.isEmpty()) {
            for (Object propObj : props.keySet()) {
                String prop = String.valueOf(propObj);

                if (!System.getProperties().containsKey(prop)) {
                    System.setProperty(prop, props.getProperty(prop));
                }
            }
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        logger.info("Loading selenide properties as {}", selenideProperties);
    }

    @AfterAll
    static void cleanupClass() {
        /*
         * Generate environment properties to Allure report
         * */
        ImmutableMap.Builder<String, String> environmentBuilder = ImmutableMap.builder();
        /*
         * From selenide.properties
         * */
        System.getProperties().forEach((key, val) -> {
            if (key.toString().startsWith("selenide.")) {
                environmentBuilder.put(key.toString(), val.toString());
            }
        });
        /*
         * From allure.properties
         * */
        System.getProperties().forEach((key, val) -> {
            if (key.toString().startsWith("allure.")) {
                environmentBuilder.put(key.toString(), val.toString());
            }
        });
        AllureEnvironmentWriter.allureEnvironmentWriter(
                environmentBuilder.build(),
                System.getProperty("allure.results.directory") + "/"
        );

        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.closeWebDriver();
        }
        SelenideLogger.removeListener("AllureSelenide");
    }

    @AfterEach
    protected void cleanupTest() {
        WebDriverRunner.closeWindow();
    }

}
