package org.example.pages;

import org.example.models.ColorCheckbox;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.example.urils.Constants.*;
import static org.example.urils.Waiters.waitForVisibilityOfElementLocated;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TrackPage extends BasePage {

    private final String baseXpath = ".//div[@class='Track_Row__1sN1F']";

    private final By firstName = By.xpath(baseXpath + "[1]/div[2]");
    private final By lastName = By.xpath(baseXpath + "[2]/div[2]");
    private final By address = By.xpath(baseXpath + "[3]/div[2]");
    private final By metro = By.xpath(baseXpath + "[4]/div[2]");
    private final By telephone = By.xpath(baseXpath + "[5]/div[2]");
    private final By deliveryDay = By.xpath(baseXpath + "[6]/div[2]");
    private final By rentalPeriod = By.xpath(baseXpath + "[7]/div[2]");
    private final By color = By.xpath(baseXpath + "[8]/div[2]");
    private final By comment = By.xpath(baseXpath + "[9]/div[2]");
    private final By orderNumber = By.xpath(".//input[@class='Input_Input__1iN_Z " +
            "Track_Input__1g7lq Input_Filled__1rDxs Input_Responsible__1jDKN']");
    private final By orderCancelButton = By.xpath(".//button[@class='Button_Button__ra12g " +
            "Button_Middle__1CSJM Button_Inverted__3IF-i']");
    private final By confirmOrderCancellButton = By.xpath(".//div[@class='Order_Modal__YZ-d3']" +
            "//button[text()='Отменить']");
    private final By OKButton = By.xpath(".//div[@class='Order_Modal__YZ-d3 " +
            "Order_Inverted__2y3AM']//button");
    private final By notFoundImg = By.xpath(".//img[@alt='Not found']");

    public TrackPage(WebDriver driver) {
        super(driver);
    }

    public String getFirstName() {
        return driver.findElement(firstName).getText();
    }

    public String getLastName() {
        return driver.findElement(lastName).getText();
    }

    public String getAddress() {
        return driver.findElement(address).getText();
    }

    public String getMetro() {
        return driver.findElement(metro).getText();
    }

    public String getTelephone() {
        return driver.findElement(telephone).getText();
    }

    public String getDeliveryDay() {
        return driver.findElement(deliveryDay).getText();
    }

    public String getRentalPeriod() {
        return driver.findElement(rentalPeriod).getText();
    }

    public String getColor() {
        return driver.findElement(color).getText();
    }

    public String getComment() {
        List<WebElement> elements = driver.findElements(comment);

        // если комментарий не заполнен при создании заказа
        if (elements.isEmpty()) {
            return "";
        } else {
            return elements.get(0).getText();
        }
    }

    public String getOrderNumber() {
        return driver.findElement(orderNumber).getAttribute("value");
    }

    public void checkOrderNumber(String number) {
        assertEquals(number, getOrderNumber());
    }

    public void clickOrderCancel() {
        driver.findElement(orderCancelButton).click();
    }

    public void clickConfirmOrderCancel() {
        driver.findElement(confirmOrderCancellButton).click();
    }

    public void clickOkButton() {
        driver.findElement(OKButton).click();
    }

    public void isNotFoundOrder() {
        // ожидание, так как сначала появлся сообщение, что заказ не найден, а затем заказ открывается
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(driver.findElement(notFoundImg).isDisplayed());
    }

    public void checkOrderData(String firstName, String lastName, String address, String metro,
                               String telephone, String deliveryDay, String rentalPeriod,
                               ColorCheckbox color, String comment) {
        waitForVisibilityOfElementLocated(driver, this.firstName, BASE_WAIT);

        assertEquals(getFirstName(), firstName);
        assertEquals(getLastName(), lastName);
        assertEquals(getAddress(), address);
        assertEquals(getMetro(), metro);
        assertEquals(getTelephone(), telephone);
        assertEquals(getDeliveryDay(), deliveryDay);
        assertEquals(getRentalPeriod(), rentalPeriod);
        assertEquals(getColor(), color.getValue());

        // если комменатрий не заполнялся
        if (comment.isEmpty()) {
            assertTrue(getComment().isEmpty());
        } else {
            assertEquals(getComment(), comment);
        }
    }

    public void cancelOrder() {
        clickOrderCancel();

        waitForVisibilityOfElementLocated(driver, confirmOrderCancellButton, BASE_WAIT);
        clickConfirmOrderCancel();

        waitForVisibilityOfElementLocated(driver, OKButton, BASE_WAIT);
        clickOkButton();

        String currentURL = driver.getCurrentUrl();
        assertEquals(BASE_URL, currentURL);
    }

}
