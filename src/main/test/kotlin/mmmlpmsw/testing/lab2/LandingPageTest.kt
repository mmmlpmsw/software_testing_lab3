package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.LandingPage
import mmmlpmsw.testing.lab2.utilities.ProvideWebDrivers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver

class LandingPageTest {
    private lateinit var driver: WebDriver

    private lateinit var landingPage: LandingPage

    @ParameterizedTest
    @ProvideWebDrivers
    fun testOpenSearch(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com")
        landingPage = LandingPage(driver)
        landingPage.clickSearchContentLink()

        assertEquals("https://stackoverflow.com/questions", driver.currentUrl)
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun testOpenDiscoverTeams(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com")
        landingPage = LandingPage(driver)
        landingPage.clickDiscoverTeamsContentLink()

        assertEquals("https://stackoverflow.com/teams", driver.currentUrl)
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun testPressJoinCommunity(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com")
        landingPage = LandingPage(driver)
        landingPage.clickJoinCommunity()

        assertEquals("https://stackoverflow.com/users/signup", driver.currentUrl)
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun testPressCreateTeam(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com")
        landingPage = LandingPage(driver)
        landingPage.clickCreateTeam()

        assertEquals("https://stackoverflow.com/teams/create/free", driver.currentUrl)
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}