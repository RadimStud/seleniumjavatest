import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestMain {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Konfigurace Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--user-data-dir=/tmp/chrome-profile");  

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));  

        try {
            driver.manage().window().maximize();
            driver.get("https://radimstudeny.cz");

            String[] menuXpaths = {
                "//*[@id='modal-1-content']/ul/li[1]/a/span",
                "//*[@id='modal-1-content']/ul/li[2]/a/span",
                "//*[@id='modal-1-content']/ul/li[3]/a/span",
                "//*[@id='modal-1-content']/ul/li[4]/a/span"
            };

            for (String xpath : menuXpaths) {
                WebElement menuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                System.out.println("Klikám na: " + menuItem.getText());
                menuItem.click();
                wait.until(ExpectedConditions.stalenessOf(menuItem)); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();  
            }

            // Ukončení všech běžících vláken
            shutdownThreads();
        }
    }

    private static void shutdownThreads() {
        try {
            ExecutorService executor = Executors.newCachedThreadPool();
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
            System.out.println("Všechna vlákna byla ukončena.");
        } catch (InterruptedException e) {
            System.err.println("Chyba při ukončování vláken.");
        }
    }
}
