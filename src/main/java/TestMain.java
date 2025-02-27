package main.java;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class TestMain {
    public static void main(String[] args) {
        // Nastavení cesty k ChromeDriveru
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Konfigurace Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Spustí Chrome bez GUI (nutné pro CI/CD)
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--user-data-dir=/tmp/chrome-profile");  // Unikátní profil

        // Inicializace WebDriveru
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Čekání max 10 sekund

        try {
            driver.manage().window().maximize();
            driver.get("https://radimstudeny.cz");

            String[] menuXpaths = {
                "//*[@id='modal-1-content']/ul/li[1]/a/span",
                "//*[@id='modal-1-content']/ul/li[2]/a/span",
                "//*[@id='modal-1-content']/ul/li[3]/a/span",
                "//*[@id='modal-1-content']/ul/li[4]/a/span"
            };

            for (int i = 0; i < menuXpaths.length; i++) {
                WebElement menuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(menuXpaths[i])));
                System.out.println("Klikám na: " + menuItem.getText());
                menuItem.click();
                wait.until(ExpectedConditions.stalenessOf(menuItem)); // Počkej, než zmizí starý element

                // Pořiď screenshot
                takeScreenshot(driver, "screenshot_" + (i + 1) + ".png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();  // Ukončení WebDriveru
            }
        }
    }

    // Metoda pro pořízení screenshotu
    private static void takeScreenshot(WebDriver driver, String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Path destination = Path.of("screenshots", fileName);
            Files.createDirectories(destination.getParent()); // Vytvoří složku, pokud neexistuje
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot uložen: " + destination.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
