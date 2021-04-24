package mmmlpmsw.testing.lab2.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class MainQuestionsPage(private val driver: WebDriver) {
    init {
        WebDriverWait(driver, 10).until {
            (driver as JavascriptExecutor).executeScript("return document.readyState") == "complete" &&
                    (driver.currentUrl == "https://stackoverflow.com/" || driver.currentUrl == "https://stackoverflow.com/questions")
        }
    }

    private val searchPath = "//input[@aria-label='Search' and @type='text' and @placeholder='Search…']"
    private val usersPageLinkPath = "//a[@href='/users']"
    private val askQuestionButtonPath = "//a[@href='/questions/ask']"
    private val questionPath = "//div[@class='question-summary']/div[@class='summary']//a"
    private val profileLinkPath = "//a[@class='my-profile js-gps-track' and @data-gps-track='profile_summary.click()']"


    fun search(value:String) {
        WebDriverWait(driver, 10).until { ExpectedConditions.elementToBeSelected(By.xpath(searchPath)) }
        driver.findElement(By.xpath(searchPath)).sendKeys(value)
        driver.findElement(By.xpath(searchPath)).submit()
    }

    fun clickUsersPageLink() = driver.findElement(By.xpath(usersPageLinkPath)).click()

    fun clickToLink(linkPath: String) = driver.findElement(By.xpath(linkPath)).click()

    fun clickToAskQuestion() = driver.findElement(By.xpath(askQuestionButtonPath)).click()

    fun openQuestion() = driver.findElement(By.xpath(questionPath)).click()

    fun openUserProfile() = driver.findElement(By.xpath(profileLinkPath)).click()

    fun logout() {
        driver.findElement(By.xpath("//a[@href='https://stackexchange.com' and @data-gps-track='site_switcher.show']")).click()
        driver.findElement(By.xpath("//a[@href='https://stackoverflow.com/users/logout']")).click()
        driver.findElement(By.xpath("//form[@action='/users/logout']//button")).click()
    }

}