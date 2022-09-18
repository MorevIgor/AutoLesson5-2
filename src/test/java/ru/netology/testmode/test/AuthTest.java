package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $x("//*[@data-test-id=\"login\"]//self::input").setValue(registeredUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//self::input").setValue(registeredUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]").click();
        sleep(1000);
        String validText = $x("//*[@id=\"root\"]").getText();
        assertEquals("Личный кабинет",validText.trim());
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() throws InterruptedException {
        var notRegisteredUser = getUser("active");
        $x("//*[@data-test-id=\"login\"]//self::input").setValue(notRegisteredUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//self::input").setValue(notRegisteredUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]").click();
        sleep(1000);
        String validText = $x("//*[@data-test-id=\"error-notification\"]").getText();
        assertEquals("Ошибка\n" +
                "Ошибка! Неверно указан логин или пароль",validText.trim());
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $x("//*[@data-test-id=\"login\"]//self::input").setValue(blockedUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//self::input").setValue(blockedUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]").click();
        sleep(1000);
        String validText = $x("//*[@data-test-id=\"error-notification\"]").getText();
        assertEquals("Ошибка\n" +
                "Ошибка! Пользователь заблокирован",validText.trim());
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $x("//*[@data-test-id=\"login\"]//self::input").setValue(wrongLogin);
        $x("//*[@data-test-id=\"password\"]//self::input").setValue(registeredUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]").click();
        sleep(1000);
        String validText = $x("//*[@data-test-id=\"error-notification\"]").getText();
        assertEquals("Ошибка\n" +
                "Ошибка! Неверно указан логин или пароль",validText.trim());
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $x("//*[@data-test-id=\"login\"]//self::input").setValue(registeredUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//self::input").setValue(wrongPassword);
        $x("//*[@data-test-id=\"action-login\"]").click();
        sleep(1000);
        String validText = $x("//*[@data-test-id=\"error-notification\"]").getText();
        assertEquals("Ошибка\n" +
                "Ошибка! Неверно указан логин или пароль",validText.trim());
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
    }
}
