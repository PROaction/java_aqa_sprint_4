package org.example;

import static org.example.urils.Constants.BASE_URL;
import static org.junit.Assert.assertTrue;

import org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import org.example.pages.HomePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


@RunWith(Parameterized.class)
public class AnswersOnQuestionsTest {
    private WebDriver driver;
    private int index;
    private String answer;

    public AnswersOnQuestionsTest(int index, String answer) {
        this.index = index;
        this.answer = answer;
    }

    @Parameterized.Parameters
    public static Object[][] testDatas() {
        return new Object[][] {
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с " +
                        "друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение " +
                        "дня. Отсчёт времени аренды начинается с момента, когда вы оплатите " +
                        "заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная " +
                        "аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно " +
                        "позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает " +
                        "на восемь суток — даже если будете кататься без передышек " +
                        "и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, " +
                        "объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void checkAnswers() {
        driver.get(BASE_URL);

        HomePage objHomePage = new HomePage(driver);
        objHomePage.checkAnswer(index, answer);
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
