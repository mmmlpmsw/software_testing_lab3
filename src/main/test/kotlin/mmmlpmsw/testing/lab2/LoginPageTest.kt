package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.GithubAuthPage
import mmmlpmsw.testing.lab2.pages.LoginPage
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import mmmlpmsw.testing.lab2.utilities.Utils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import java.io.FileInputStream
import java.util.*


class LoginPageTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun init() = DriversInitializer.initEverything()
        @JvmStatic
        fun provideWebDrivers() = DriversInitializer.provideWebDrivers()
    }

    private lateinit var loginPage: LoginPage
    private lateinit var githubAuthPage: GithubAuthPage

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testLoginWithWrongEmail(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.clickAcceptCookies()
        loginPage.enterEmail("aaaaaaaa")
        loginPage.enterPassword("aaaaaaaa")

        loginPage.clickLoginButton()

        Assertions.assertTrue(loginPage.isErrorAppear())
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testLoginWithWrongPassword(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.clickAcceptCookies()
        loginPage.enterEmail("eevjaqmrffdlulceoi@niwghx.com")
        loginPage.enterPassword("help me please aaaaaaaaa")

        loginPage.clickLoginButton()

        Assertions.assertTrue(loginPage.isErrorAppear())
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testLoginViaEmail(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.clickAcceptCookies()
        loginPage.enterEmail("eevjaqmrffdlulceoi@niwghx.com")
        loginPage.enterPassword("qwerty123")
        loginPage.clickLoginButton()

        if (Utils.waitForCaptchaIfExists(driver)) {
            Assertions.assertNotEquals("https://stackoverflow.com/", driver.currentUrl)
            driver.quit()
            return
        }

        loginPage.waitForUrl("https://stackoverflow.com/", 1)
        Assertions.assertEquals("https://stackoverflow.com/", driver.currentUrl)
        driver.quit()
    }


    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testLoginViaGoogle(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.clickAcceptCookies()
        loginPage.clickLoginViaGoogleButton()

        Assertions.assertNotEquals(loginPage.EXPECTED_PAGE_URL, driver.currentUrl)
        Assertions.assertTrue(driver.currentUrl.startsWith("https://accounts.google.com/o/oauth2/auth/identifier?client_id"))

        driver.quit()
    }


    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testLoginViaGithub(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.clickAcceptCookies()
        loginPage.clickLoginViaGithubButton()
        Utils.waitForCaptchaIfExists(driver)

        githubAuthPage = GithubAuthPage(driver)
        Assertions.assertNotEquals(loginPage.EXPECTED_PAGE_URL, driver.currentUrl)
        Assertions.assertTrue(githubAuthPage.isOnGithubAuth())

        val props = Properties()
        try {
            props.load(FileInputStream("src/main/test/resources/github.properties"))
        } catch (e: NullPointerException) {
            fail("Props file not found")
        }

        githubAuthPage.enterEmail(props.getProperty("github.email"))
        githubAuthPage.enterPassword(props.getProperty("github.password"))

        githubAuthPage.pressSignIn()
        if (Utils.isCaptchaShown(driver)) {
            Utils.waitForCaptchaIfExists(driver)
            Assertions.assertTrue(driver.currentUrl.startsWith("https://stackoverflow.com/users/login"))
            driver.quit()
            return
        }
        Assertions.assertEquals("https://stackoverflow.com/", driver.currentUrl)
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testLoginViaFacebook(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.clickAcceptCookies()
        loginPage.clickLoginViaFacebookButton()

        Assertions.assertTrue(driver.currentUrl.startsWith("https://www.facebook.com/login.php?"))
        driver.quit()
    }
}