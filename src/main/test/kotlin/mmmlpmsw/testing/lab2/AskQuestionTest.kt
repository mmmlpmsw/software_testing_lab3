package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.AskQuestionPage
import mmmlpmsw.testing.lab2.pages.LoginPage
import mmmlpmsw.testing.lab2.pages.MainQuestionsPage
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import mmmlpmsw.testing.lab2.utilities.Utils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class AskQuestionTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun init() = DriversInitializer.initEverything()

        @JvmStatic
        fun provideWebDrivers() = DriversInitializer.provideWebDrivers()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun unauthorizedUserAskQuestionTest(driver: WebDriver) {
        driver.get("https://stackoverflow.com/questions")
        val questionsPage = MainQuestionsPage(driver)
        questionsPage.clickToAskQuestion()

        Assertions.assertTrue(driver.currentUrl.startsWith("https://stackoverflow.com/users/login?"))

        val path = "//p[@class='val-textemphasis']"
        Assertions.assertTrue(
            driver.findElement(By.xpath(path)).text == "You must be logged in to ask a question on Stack Overflow"
        )
        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionTest(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        val loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            driver.quit()
            return
        }
        driver.get("https://stackoverflow.com/questions")
        val questionsPage = MainQuestionsPage(driver)
        questionsPage.clickToAskQuestion()

        val askQuestionPage = AskQuestionPage(driver)



        driver.quit()
    }
}