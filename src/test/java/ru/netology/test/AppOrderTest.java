package ru.netology.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSubmitFormSuccessfully() {
        // 1. Открываем локально запущенный SUT
        driver.get("http://localhost:9999");

        // 2. Заполняем поле "Фамилия и имя" по data-test-id
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иван Иванов-Смирнов");

        // 3. Заполняем поле "Телефон" по data-test-id
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79990000000");

        // 4. Отмечаем чекбокс согласия (кликаем по label внутри data-test-id)
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();

        // 5. Нажимаем кнопку отправки
        driver.findElement(By.className("button")).click();

        // 6. Получаем и проверяем текст успешного отправления
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text);
    }
}