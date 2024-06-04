package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;

    By homeLink = By.className("Header_LogoScooter__3lsAR");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickHomePage() {
        driver.findElement(homeLink).click();
    }
}
