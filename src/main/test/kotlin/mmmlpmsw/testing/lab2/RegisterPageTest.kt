package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.GithubAuthPage
import mmmlpmsw.testing.lab2.pages.RegisterPage
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import mmmlpmsw.testing.lab2.utilities.Utils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver

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
        Utils.clickAcceptCookies(driver)
        registerPage.clickRegisterViaGoogleButton()

        Assertions.assertNotEquals("https://stackoverflow.com/users/signup", driver.currentUrl)
        Assertions.assertTrue(driver.currentUrl.startsWith("https://accounts.google.com/o/oauth2/auth/identifier?client_id"))

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testRegisterViaGithub(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/signup")
        registerPage = RegisterPage(driver)
        Utils.clickAcceptCookies(driver)
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
        Utils.clickAcceptCookies(driver)
        registerPage.clickRegisterViaFacebookButton()

        Assertions.assertTrue(driver.currentUrl.startsWith("https://www.facebook.com/login.php?"))
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testRegister(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/signup")
        registerPage = RegisterPage(driver)
        Utils.clickAcceptCookies(driver)

        registerPage.agree()
        registerPage.enterName("new_user_test")
        registerPage.enterEmail("new_user_email@register.com")
        registerPage.enterPassword("passworD123")

        Thread.sleep(90000) //todo captcha

        registerPage.clickRegister()
        Thread.sleep(2000) //todo wait
        Assertions.assertTrue(registerPage.isRegistrationSuccedded())

        driver.quit()

    }
}