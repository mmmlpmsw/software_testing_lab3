package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.LandingPage
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import kotlin.test.assertEquals

class LandingPageTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun init() = DriversInitializer.initEverything()
        @JvmStatic
        fun provideWebDrivers() = DriversInitializer.provideWebDrivers()
    }

    private lateinit var landingPage: LandingPage

    @ParameterizedTest
    @MethodSource("mmmlpmsw.testing.lab2.utilities.DriversInitializer#provideWebDrivers")
    fun testOpenSearch(driver: WebDriver) {
        driver.get("https://stackoverflow.com")
        landingPage = LandingPage(driver)
        landingPage.clickAcceptCookies()
        landingPage.clickSearchContentLink()

        assertEquals("https://stackoverflow.com/questions", driver.currentUrl)
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("mmmlpmsw.testing.lab2.utilities.DriversInitializer#provideWebDrivers")
    fun testOpenDiscoverTeams(driver: WebDriver) {
        driver.get("https://stackoverflow.com")
        landingPage = LandingPage(driver)
        landingPage.clickAcceptCookies()
        landingPage.clickDiscoverTeamsContentLink()

        assertEquals("https://stackoverflow.com/teams", driver.currentUrl)
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("mmmlpmsw.testing.lab2.utilities.DriversInitializer#provideWebDrivers")
    fun testPressJoinCommunity(driver: WebDriver) {
        driver.get("https://stackoverflow.com")
        landingPage = LandingPage(driver)
        landingPage.clickAcceptCookies()
        landingPage.clickJoinCommunity()

        assertEquals("https://stackoverflow.com/users/signup", driver.currentUrl)
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("mmmlpmsw.testing.lab2.utilities.DriversInitializer#provideWebDrivers")
    fun testPressCreateTeam(driver: WebDriver) {
        driver.get("https://stackoverflow.com")
        landingPage = LandingPage(driver)
        landingPage.clickAcceptCookies()
        landingPage.clickCreateTeam()

        assertEquals("https://stackoverflow.com/teams/create/free", driver.currentUrl)
        driver.quit()
    }
}