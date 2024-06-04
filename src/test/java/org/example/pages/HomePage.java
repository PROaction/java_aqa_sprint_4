package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.example.urils.Constants.BASE_WAIT;
import static org.example.urils.Waiters.waitForElementToBeClickable;
import static org.junit.Assert.assertEquals;


public class HomePage {
    private final WebDriver driver;

    private final By answers = By.xpath(".//div[@class='accordion__panel']/p");
    private final By questionButtons = By.className("accordion__button");
    private final By topOrderButton =
            By.xpath(".//div[@class='Header_Nav__AGCXC']/button[text()='Заказать']");
    private final By botOrderButton = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button");
    private final By orderStatus = By.xpath(".//button[text()='Статус заказа'");
    private final By orderNumberField = By.xpath(".//input[@placeholder='Введите номер заказа']");
    private final By goButton = By.xpath(".//button[text()='Go!']");


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickTopOrder() {
        driver.findElement(topOrderButton).click();
    }

    public void clickBotOrder() {
        WebElement element = driver.findElement(botOrderButton);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);

        element.click();
    }

    public WebElement getAnswer(int index) {
        return driver.findElements(answers).get(index);
    }

    public WebElement getQuestion(int index) {
        return driver.findElements(questionButtons).get(index);
    }

    public void clickQuestion(int index) {
        getQuestion(index).click();
    }

    public void clickOrderStatus() {
        driver.findElement(orderStatus).click();
    }

    public void setOrderNumberField(String orderNumber) {
        driver.findElement(orderNumberField).sendKeys(orderNumber);
    }

    public void clickGo() {
        driver.findElement(goButton).click();
    }


    public void checkAnswer(int index, String answer) {
//        new WebDriverWait(driver, 5)
//                .until(ExpectedConditions.elementToBeClickable(By.id("accordion__heading-" + index)));
        By answerElement = By.id("accordion__heading-" + index);
        waitForElementToBeClickable(driver, answerElement, BASE_WAIT);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", getQuestion(index));

        clickQuestion(index);

        String actualAnswer = getAnswer(index).getText();
        assertEquals(
                "Ответ " + index + " не равен ожидаемому.",
                answer,
                actualAnswer
        );
    }

    public void goToOrderPage(String orderNumber) {
        clickOrderStatus();
        setOrderNumberField(orderNumber);
        clickGo();
    }
}
