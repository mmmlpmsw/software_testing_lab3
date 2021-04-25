package mmmlpmsw.testing.lab2.pages

import mmmlpmsw.testing.lab2.utilities.find
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.lang.IllegalArgumentException

class GithubAuthPage(private val driver: WebDriver) {

    private val EXPECTED_PAGE_URL = "https://github.com/login?client_id"
    init {
        if (!driver.currentUrl.startsWith(EXPECTED_PAGE_URL))
            throw IllegalArgumentException(driver.currentUrl)
    }

    private val githubIdentifierPath = "/html/body/div[3]/main/div/p"
    private val emailPath = "//input[@name='login' and @autocomplete='username']"
    private val passwordPath = "//input[@name='password' and @autocomplete='current-password']"
    private val signInButtonPath = "//input[@name='commit' and @value='Sign in']"

    fun isOnGithubAuth(): Boolean = driver.find(By.xpath(githubIdentifierPath))
    fun enterEmail(email: String) = driver.findElement(By.xpath(emailPath)).sendKeys(email)
    fun enterPassword(password: String) = driver.findElement(By.xpath(passwordPath)).sendKeys(password)
    fun pressSignIn() = driver.findElement(By.xpath(signInButtonPath)).click()

}