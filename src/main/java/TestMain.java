package main.java;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestMain {
    public static void main(String[] args) {
        // Nastavení cesty k ChromeDriveru
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");

        // Inicializace WebDriveru
        WebDriver driver = new ChromeDriver();

        try {
            // Maximalizuje okno prohlížeče
                  // Maximalizuje okno prohlížeče
            driver.manage().window().maximize();

            // Otevře hlavní stránku
            driver.get("https://radimstudeny.cz");

            // XPath selektory pro menu
            String[] menuXpaths = {
                    "//*[@id='modal-1-content']/ul/li[1]/a/span",
                    "//*[@id='modal-1-content']/ul/li[2]/a/span",
                    "//*[@id='modal-1-content']/ul/li[3]/a/span",
                    "//*[@id='modal-1-content']/ul/li[4]/a/span"
            };

            // Klikání na položky menu
            for (String xpath : menuXpaths) {
                WebElement menuItem = driver.findElement(By.xpath(xpath));
                System.out.println("Klikám na: " + menuItem.getText());
                menuItem.click();
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Zavře prohlížeč
            driver.quit();
        }
    }
}
