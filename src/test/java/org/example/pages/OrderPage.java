package org.example.pages;

import org.example.models.ColorCheckbox;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.urils.Constants.BASE_WAIT;
import static org.example.urils.Waiters.*;
import static org.junit.Assert.assertTrue;

public class OrderPage {
    private WebDriver driver;

    private final By firstName = By.xpath(".//input[contains(@placeholder, 'Имя')]");
    private final By lastName = By.xpath(".//input[contains(@placeholder, 'Фамилия')]");
    private final By address = By.xpath(".//input[contains(@placeholder, 'Адрес')]");
    private final By metro = By.xpath(".//input[contains(@placeholder, 'метро')]");
    private final By telephone = By.xpath(".//input[contains(@placeholder, 'Телефон')]");
    private final By next = By.xpath(".//button[text()='Далее']");
    private final By whenDelivery = By.xpath(".//input[contains(@placeholder, 'Когда')]");
    private final By deliveryDay = By.xpath(".//div[contains(@class, 'selected')]/following-sibling::div");
    private final By rentalPeriodButton = By.xpath(".//div[@class='Dropdown-placeholder']");
    private final By dayPeriod = By.xpath(".//div[text()='сутки']");
    private final By blackColor = By.id("black");
    private final By greyColor = By.id("grey");
    private final By comment = By.xpath(".//input[contains(@placeholder, 'Комментарий')]");
    private final By orderingButton =
            By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    private final By orderConfirmButton = By.xpath(".//div[@class='Order_Modal__YZ-d3']//button[text()='Да']");
    private final By processedTitle = By.xpath(".//div[text()='Заказ оформлен']");
    private final By processedDescription = By.className("Order_Text__2broi");
    private final By viewStatusButton = By.xpath(".//button[text()='Посмотреть статус']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setFirstName(String firstName) {
        driver.findElement(this.firstName).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        driver.findElement(this.lastName).sendKeys(lastName);
    }

    public void setAddress(String address) {
        driver.findElement(this.address).sendKeys(address);
    }

    public void setMetro(String metro) {
        By metroStation = By.xpath(".//div[text()='" + metro + "']");
        WebElement element = driver.findElement(this.metro);
        element.click();

        waitForVisibilityOfElementLocated(driver, By.xpath(".//div[@class='select-search__select']"), BASE_WAIT);

        driver.findElement(By.xpath(".//div[text()='" + metro + "']")).click();
    }

    public void setTelephone(String telephone) {
        driver.findElement(this.telephone).sendKeys(telephone);
    }

    public void clickWhenDelivery() {
        driver.findElement(whenDelivery).click();
    }

    /**
     * @return строка состоящая из дня и месяца (например, 5 июня)
     */
    public String getDeliveryDay() {

        String deliveryDate = driver.findElement(deliveryDay).getAttribute("aria-label");

        Pattern pattern = Pattern.compile("(\\d+)-е ([а-я]+)");
        Matcher matcher = pattern.matcher(deliveryDate);
        
        String result = null;
        if (matcher.find()) {
            String day = matcher.group(1);
            String month = matcher.group(2);
            result = day + " " + month;
        }

        return result;
    }

    public void clickDeliveryDay() {
        driver.findElement(deliveryDay).click();
    }

    public void clickRentalPeriod() {
        driver.findElement(rentalPeriodButton).click();
    }

    public void clickDayPeriod() {
        driver.findElement(dayPeriod).click();
    }

    public void setColor(ColorCheckbox color) {
        driver.findElement(By.id(color.getId())).click();
    }

    public void setComment(String comment) {
        driver.findElement(this.comment).sendKeys(comment);
    }

    public void clickOrderingButton() {
        driver.findElement(orderingButton).click();
    }

    public void clickViewStatus() {
        waitForElementToBeClickable(driver, viewStatusButton, BASE_WAIT);
        driver.findElement(viewStatusButton).click();
    }

    public void checkProcessedDescription() {
        Pattern orderNumberPattern = Pattern.compile("Номер заказа: (\\d+)");

        waitForElementToBeClickable(driver, processedTitle, BASE_WAIT);
        waitByTextMatches(driver, processedDescription, BASE_WAIT, orderNumberPattern);

        String description = driver.findElement(processedDescription).getText();
        Matcher matcher = orderNumberPattern.matcher(description);

        assertTrue("Заказ не создан, так как не отображен в описании.", matcher.find());
    }

    public void clickOrderConfirmButton() {
        waitForElementToBeClickable(driver, this.orderConfirmButton, BASE_WAIT);
        driver.findElement(orderConfirmButton).click();
    }

    public void setFirstPage(String firstName, String lastName, String address, String metro, String telephone) {
        waitForElementToBeClickable(driver, this.firstName, BASE_WAIT);
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setMetro(metro);
        setTelephone(telephone);
    }

    public String setSecondPage(ColorCheckbox color, String comment) {
        waitForElementToBeClickable(driver, this.whenDelivery, BASE_WAIT);
        clickWhenDelivery();
        String deliveryDay = getDeliveryDay();
        clickDeliveryDay();
        clickRentalPeriod();
        clickDayPeriod();
        setColor(color);
        setComment(comment);

        return deliveryDay;
    }

    public void clickNext() {
        driver.findElement(next).click();
    }

}
