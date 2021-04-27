package mmmlpmsw.testing.lab2.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.lang.IllegalArgumentException
import java.time.Duration

class RegisterPage(private val driver: WebDriver) {
    private val EXPECTED_PAGE_URL = "https://stackoverflow.com/users/signup"
    init {
        if (!driver.currentUrl.startsWith(EXPECTED_PAGE_URL))
            throw IllegalArgumentException(driver.currentUrl)
    }

    private val registerWithGoogleButtonPath = "//button[@data-provider='google' and @data-oauthserver='https://accounts.google.com/o/oauth2/auth']"
    private val registerWithGithubButtonPath = "//button[@data-provider='github' and @data-oauthserver='https://github.com/login/oauth/authorize']"
    private val registerWithFacebookButtonPath = "//button[@data-provider='facebook' and @data-oauthserver='https://www.facebook.com/v2.0/dialog/oauth']"

    private val namePath = "//input[@name='display-name']"
    private val emailPath = "//input[@name='email' and @type='email']"
    private val passwordPath = "//input[@name='password' and @type='password']"
    private val registerButtonPath = "//button[@name='submit-button']"
    private val captchaWindowPath = "//div[contains(@style, 'visibility: visible;') and .//div[@class='g-recaptcha-bubble-arrow']]"
    private val captchaPath = "//input[@type='checkbox' and @name='EmailOptIn']"

    fun clickRegisterViaGoogleButton() = driver.findElement(By.xpath(registerWithGoogleButtonPath)).click()
    fun clickRegisterViaGithubButton() = driver.findElement(By.xpath(registerWithGithubButtonPath)).click()
    fun clickRegisterViaFacebookButton() = driver.findElement(By.xpath(registerWithFacebookButtonPath)).click()

    fun enterName(password: String) = driver.findElement(By.xpath(namePath)).sendKeys(password)
    fun enterEmail(email: String) = driver.findElement(By.xpath(emailPath)).sendKeys(email)
    fun enterPassword(password: String) = driver.findElement(By.xpath(passwordPath)).sendKeys(password)
    fun getCaptchaElement() = driver.findElement(By.xpath("//div[@id='no-captcha-here']//iframe"))!!
    fun isCaptchaWindowShowed() = driver.findElements(By.xpath(captchaWindowPath)).size > 0

    fun agree() = driver.findElement(By.xpath(captchaPath)).click()
    fun clickRegister() {
        driver.findElement(By.xpath(registerButtonPath)).click()
        WebDriverWait(driver, 10).pollingEvery(Duration.ofSeconds(1)).until {
            (driver as JavascriptExecutor).executeScript("return document.readyState") == "complete"
        }
    }

    fun isRegistrationSucceeded(): Boolean = driver.findElements(By.xpath("//*[@id='content']/div[contains(@class, 's-notice__success')]")).size > 0

}