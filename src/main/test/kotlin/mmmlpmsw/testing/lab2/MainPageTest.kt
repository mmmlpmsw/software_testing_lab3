package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.MainQuestionsPage
import mmmlpmsw.testing.lab2.pages.UsersPage
import mmmlpmsw.testing.lab2.utilities.ProvideWebDrivers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver


class MainPageTest {
    private lateinit var drivers: List<WebDriver>

    @ParameterizedTest
    @ProvideWebDrivers
    fun testSearchUser(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach {  driver ->
        driver.get("https://stackoverflow.com/questions")
        val mainPage = MainQuestionsPage(driver)
        mainPage.clickUsersPageLink()

        val usersPage = UsersPage(driver)
        usersPage.searchUser("josliber")

        Assertions.assertTrue(usersPage.isUserPresented("josliber"))

        }
    }

    @AfterEach
    fun tearDown() {
        drivers.forEach(WebDriver::quit)
    }
}