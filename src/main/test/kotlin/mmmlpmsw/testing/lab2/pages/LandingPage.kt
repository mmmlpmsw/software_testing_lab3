package mmmlpmsw.testing.lab2.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

class LandingPage(private val driver: WebDriver) {
    private val EXPECTED_PAGE_URL = "https://stackoverflow.com/"
    init {
        if (!driver.currentUrl.equals(EXPECTED_PAGE_URL))
            throw IllegalArgumentException(driver.currentUrl)
    }
    private val searchContentLink = "//p/a[@href='/questions']"
    private val discoverTeamsContentLink = "//p/a[@href='https://stackoverflow.com/teams']"
    private val joinCommunityButton = "//a[@href='/users/signup']"
    private val createTeamLink = "//a[@href='https://stackoverflow.com/teams/create/free']"

    fun clickSearchContentLink() = driver.findElement(By.xpath(searchContentLink)).click()
    fun clickDiscoverTeamsContentLink() = driver.findElement(By.xpath(discoverTeamsContentLink)).click()
    fun clickJoinCommunity() = driver.findElement(By.xpath(joinCommunityButton)).click()
    fun clickCreateTeam() = driver.findElement(By.xpath(createTeamLink)).click()
}