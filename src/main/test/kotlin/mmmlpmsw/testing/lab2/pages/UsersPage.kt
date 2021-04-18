package mmmlpmsw.testing.lab2.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import java.lang.IllegalArgumentException

class UsersPage(private val driver: WebDriver) {
    val EXPECTED_PAGE_URL = "https://stackoverflow.com/users"
    init {
        if (!(driver.currentUrl.startsWith(EXPECTED_PAGE_URL)) )
            throw IllegalArgumentException(driver.currentUrl)
    }

    private val acceptAllCookiesElement = "//button[contains(@class, 'js-accept-cookies')]"
    private val searchUserPath = "//input[@type='text' and @name='userfilter' and @placeholder='Filter by user']"
    private val userInfoPath = "//div[@id='user-browser']//div[@class='user-details']/a"



    fun clickAcceptCookies() = driver.findElement(By.xpath(acceptAllCookiesElement)).click()

    fun searchUser(value:String) {
        driver.findElement(By.xpath(searchUserPath)).sendKeys(value)
        driver.findElement(By.xpath(searchUserPath)).sendKeys(Keys.RETURN)
    }

    fun isUserPresented(name: String):Boolean {
        println(driver.findElement(By.xpath(userInfoPath)).text)
        return driver.findElement(By.xpath(userInfoPath)).text == name
    }
}