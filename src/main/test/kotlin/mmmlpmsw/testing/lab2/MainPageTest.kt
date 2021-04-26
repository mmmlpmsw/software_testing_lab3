package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.MainQuestionsPage
import mmmlpmsw.testing.lab2.pages.UsersPage
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit


class MainPageTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun init() = DriversInitializer.initEverything()
        @JvmStatic
        fun provideWebDrivers() = DriversInitializer.provideWebDrivers()
    }

    private lateinit var mainPage: MainQuestionsPage
    private lateinit var usersPage: UsersPage

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testSearchUser(driver: WebDriver) {
        driver.get("https://stackoverflow.com/questions")
        mainPage = MainQuestionsPage(driver)
        mainPage.clickUsersPageLink()

        usersPage = UsersPage(driver)
        usersPage.clickAcceptCookies()
        usersPage.searchUser("josliber")

        Assertions.assertTrue(usersPage.isUserPresented("josliber"))

        driver.quit()
    }
}