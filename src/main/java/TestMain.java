package main.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestMain {
    public static void main(String[] args) {
        // Nastavení cesty k ChromeDriveru
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Konfigurace Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // Spustí Chrome bez GUI (nutné pro CI/CD)
        options.addArguments("--disable-gpu");  // Zakáže GPU akceleraci (někdy nutné v CI/CD)
        options.addArguments("--no-sandbox");  // Obchází sandboxing
        options.addArguments("--disable-dev-shm-usage");  // Oprava pro omezení sdílené paměti
        options.addArguments("--remote-allow-origins=*");  // Povolení vzdálených připojení
        options.addArguments("--user-data-dir=/tmp/chrome-profile");  // Unikátní profil

        // Inicializace WebDriveru s upravenými parametry
        WebDriver driver = new ChromeDriver(options);

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
                WebElement menuItem = driver.findElement(By.xpath(xpath));
                System.out.println("Klikám na: " + menuItem.getText());
                menuItem.click();
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
