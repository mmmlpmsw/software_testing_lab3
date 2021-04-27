package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.GithubAuthPage
import mmmlpmsw.testing.lab2.pages.RegisterPage
import mmmlpmsw.testing.lab2.utilities.CaptchaAnalyzer
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import mmmlpmsw.testing.lab2.utilities.Utils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.FluentWait
import java.time.Duration


class RegisterPageTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun init() = DriversInitializer.initEverything()
        @JvmStatic
        fun provideWebDrivers() = DriversInitializer.provideWebDrivers()
    }

    private lateinit var registerPage: RegisterPage
    private lateinit var githubAuthPage: GithubAuthPage

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testRegisterViaGoogle(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/signup")
        registerPage = RegisterPage(driver)
        registerPage.clickRegisterViaGoogleButton()

        Assertions.assertNotEquals("https://stackoverflow.com/users/signup", driver.currentUrl)
        Assertions.assertTrue(driver.currentUrl.startsWith("https://accounts.google.com/"))

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testRegisterViaGithub(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/signup")
        registerPage = RegisterPage(driver)
        registerPage.clickRegisterViaGithubButton()
        Utils.waitForCaptchaIfExists(driver)

        githubAuthPage = GithubAuthPage(driver)
        Assertions.assertNotEquals("https://stackoverflow.com/users/signup", driver.currentUrl)
        Assertions.assertTrue(driver.currentUrl.startsWith("https://github.com/login"))

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testRegisterViaFacebook(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/signup")
        registerPage = RegisterPage(driver)
        registerPage.clickRegisterViaFacebookButton()

        Assertions.assertTrue(driver.currentUrl.startsWith("https://www.facebook.com/login.php?"))
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testRegister(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/signup")
        registerPage = RegisterPage(driver)

        registerPage.agree()
        registerPage.enterName("new_user_test")
        registerPage.enterEmail("new_user_email${System.currentTimeMillis()}@register.com")
        registerPage.enterPassword("passworD123")

        FluentWait(driver)
            .pollingEvery(Duration.ofSeconds(2))
            .withTimeout(Duration.ofMinutes(10))
            .ignoring(StaleElementReferenceException::class.java)
            .until {
                !registerPage.isCaptchaWindowShowed()
                CaptchaAnalyzer.isCaptchaSolved(
                    driver, registerPage.getCaptchaElement()
                )
            }

        registerPage.clickRegister()
        Assertions.assertTrue(registerPage.isRegistrationSucceeded())

        driver.quit()

    }
}