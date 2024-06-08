package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.models.ColorCheckbox;
import org.example.pages.HomePage;
import org.example.pages.OrderPage;
import org.example.pages.TrackPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import static org.example.urils.Constants.BASE_URL;
import static org.example.urils.Constants.COMMENT;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;

    private String firstName;
    private String lastName;
    private String street;
    private String metro;
    private String phoneNumber;
    private ColorCheckbox color;
    private String comment;
    private String orderingButton;

    public OrderTest(String firstName, String lastName, String street, String metro,
                     String phoneNumber, ColorCheckbox color, String comment, String orderingButton) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.metro = metro;
        this.phoneNumber = phoneNumber;
        this.color = color;
        this.comment = comment;
        this.orderingButton = orderingButton;
    }

    @Parameterized.Parameters(name = "{index}: testWithFirstName={0}, lastName={1}, street={2}, metro={3}, " +
            "phoneNumber={4}, color={5}, comment={6}, orderingButton={7}")
    public static Object[][] testData() {
        return new Object[][]{
                {
                        "Ян",                                                   // самое короткое имя в РФ
                        "Иванов",
                        "Ленивка",
                        "Лубянка",
                        "89999999999",
                        new ColorCheckbox("black", "чёрный жемчуг"),
                        "",                                                     // пустой комментарий
                        "botOrderButton"                                        // нижняя кнопка создания заказа
                },
                {
                        "Иван",
                        "Ё",                                                    // самое короткая фамилия в РФ
                        "Ленивка",
                        "Чистые пруды",
                        "89999999999",
                        new ColorCheckbox("black", "чёрный жемчуг"),
                        "Т",
                        "topOrderButton"
                },
                {
                        "Абдурахмангаджи",                                      // самое длиное имя в РФ
                        "Христорождественский",                                 // самая длиная фамилия
                        "Сокольники",
                        "Воробьевы горы",
                        "80000000000",
                        new ColorCheckbox("grey", "серая безысходность"),
                        COMMENT,                                                // коментарий больше 255 символов
                        "topOrderButton"},
        };
    }

    @Before
    public void setUp() {
       WebDriverManager.chromedriver().setup();
       driver = new ChromeDriver();
//         WebDriverManager.edgedriver().setup();
//         driver = new EdgeDriver();

    }

    @Test
    public void checkCreateOrderThroughTopButton () {
        driver.get(BASE_URL);

        HomePage objHomePage = new HomePage(driver);
        TrackPage objTrackPage = new TrackPage(driver);
        OrderPage objOrderPage = new OrderPage(driver);

        // выбираем какую кнопку нажать для перехода на страницу создания заказа
        if (orderingButton.equals("topOrderButton")) {
            objHomePage.clickTopOrder();
        } else if (orderingButton.equals("botOrderButton")) {
            objHomePage.clickBotOrder();
        }

        objOrderPage.setFirstPage(firstName, lastName, street, metro, phoneNumber);
        objOrderPage.clickNext();
        String deliveryDay = objOrderPage.setSecondPage(color, comment);
        objOrderPage.clickOrderingButton();
        objOrderPage.clickOrderConfirmButton();
        objOrderPage.checkProcessedDescription();
        objOrderPage.clickViewStatus();

        objTrackPage.checkOrderData(firstName, lastName, street, metro, phoneNumber,
                deliveryDay, "сутки", color, comment);
        String orderNumber = objTrackPage.getOrderNumber();

        objTrackPage.cancelOrder();

        objHomePage.goToOrderPage(orderNumber);
        objTrackPage.isNotFoundOrder();
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
