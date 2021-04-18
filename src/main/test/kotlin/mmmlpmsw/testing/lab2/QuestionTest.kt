package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.LoginPage
import mmmlpmsw.testing.lab2.pages.MainQuestionsPage
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import java.io.FileInputStream
import java.util.*
import java.util.stream.Stream

class QuestionTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun init() = DriversInitializer.initEverything()
        @JvmStatic
        fun provideWebDrivers() = DriversInitializer.provideWebDrivers()
    }

    private lateinit var loginPage: LoginPage
    private lateinit var mainQuestionsPage: MainQuestionsPage

//    @ParameterizedTest
//    @MethodSource("mmmlpmsw.testing.lab2.utilities.DriversInitializer#provideWebDrivers")
//    fun searchQuestionTest(driver: WebDriver) {
//        driver.get("https://stackoverflow.com/users/login")
//        loginPage = LoginPage(driver)
//        loginPage.login()
//
//        mainQuestionsPage = MainQuestionsPage(driver)
//        mainQuestionsPage.search("answers:518")
//
//        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/184618/what-is-the-best-comment-in-source-code-you-have-ever-encountered')]")
//    }


}