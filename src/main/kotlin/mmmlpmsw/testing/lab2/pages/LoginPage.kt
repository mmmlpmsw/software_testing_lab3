package mmmlpmsw.testing.lab2.pages

import mmmlpmsw.testing.lab2.utilities.find
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait


class LoginPage(private val driver: WebDriver) {
    val EXPECTED_PAGE_URL = "https://stackoverflow.com/users/login"
    init {
        if (!driver.currentUrl.startsWith(EXPECTED_PAGE_URL))
            throw IllegalArgumentException(driver.currentUrl)
    }
    private val emailInputPath = "//input[@type='email']"
    private val passwordInputPath = "//input[@type='password']"
    private val acceptAllCookiesElementPath = "//button[contains(@class, 'js-accept-cookies')]"
    private val loginButtonPath = "//button[@name='submit-button']"

    private val errorMsgPath = "//p[contains(@class, 'js-error-message')]"

    private val loginWithGoogleButtonPath = "//button[@data-provider='google' and @data-oauthserver='https://accounts.google.com/o/oauth2/auth']"
    private val loginWithGithubButtonPath = "//button[@data-provider='github' and @data-oauthserver='https://github.com/login/oauth/authorize']"
    private val loginWithFacebookButtonPath = "//button[@data-provider='facebook' and @data-oauthserver='https://www.facebook.com/v2.0/dialog/oauth']"

    fun enterEmail(email: String) = driver.findElement(By.xpath(emailInputPath)).sendKeys(email)
    fun enterPassword(password: String) = driver.findElement(By.xpath(passwordInputPath)).sendKeys(password)
    fun clickAcceptCookies() = 0 // do nothing // todo remove
    fun waitForUrl(url: String, timeout: Long) = WebDriverWait(driver, timeout).until { driver.currentUrl == url }

    fun clickLoginButton() = driver.findElement(By.xpath(loginButtonPath)).click()
    fun isErrorAppear(): Boolean = driver.find(By.xpath(errorMsgPath))

    fun clickLoginViaGoogleButton() = driver.findElement(By.xpath(loginWithGoogleButtonPath)).click()
    fun clickLoginViaGithubButton() = driver.findElement(By.xpath(loginWithGithubButtonPath)).click()
    fun clickLoginViaFacebookButton() = driver.findElement(By.xpath(loginWithFacebookButtonPath)).click()

    fun login(email: String = "eevjaqmrffdlulceoi@niwghx.com", password: String = "qwerty123") {
        clickAcceptCookies()
        enterEmail(email)
        enterPassword(password)
        clickLoginButton()
    }
}