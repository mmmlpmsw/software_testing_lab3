package mmmlpmsw.testing.lab2.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.lang.IllegalArgumentException

class LandingPage(private val driver: WebDriver) {
    private val EXPECTED_PAGE_URL = "https://stackoverflow.com/"
    init {
        if (!driver.currentUrl.equals(EXPECTED_PAGE_URL))
            throw IllegalArgumentException(driver.currentUrl)
    }
    private val searchContentLinkPath = "//p/a[@href='/questions']"
    private val discoverTeamsContentLinkPath = "//p/a[@href='https://stackoverflow.com/teams']"
    private val joinCommunityBtnPath = "//a[@href='/users/signup']"
    private val createTeamLinkPath = "//a[@href='https://stackoverflow.com/teams/create/free']"

    fun clickSearchContentLink() = driver.findElement(By.xpath(searchContentLinkPath)).click()
    fun clickDiscoverTeamsContentLink() = driver.findElement(By.xpath(discoverTeamsContentLinkPath)).click()
    fun clickJoinCommunity() = driver.findElement(By.xpath(joinCommunityBtnPath)).click()
    fun clickCreateTeam() = driver.findElement(By.xpath(createTeamLinkPath)).click()
}