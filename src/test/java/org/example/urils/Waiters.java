package org.example.urils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Pattern;

public class Waiters {
    public static void waitForElementToBeClickable(WebDriver driver, By locator, int timeout) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForVisibilityOfElementLocated(WebDriver driver, By locator, int timeout) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitByTextMatches(WebDriver driver, By locator, int timeout, Pattern pattern) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.textMatches(locator, pattern));
    }

    public static void waitForURLMatches(WebDriver driver, String urlRegex, int timeout) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.urlMatches(urlRegex));
    }
}
