package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.MainQuestionsPage
import mmmlpmsw.testing.lab2.pages.QuestionPage
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import mmmlpmsw.testing.lab2.utilities.Utils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit

class UnauthUserTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun init() = DriversInitializer.initEverything()
        @JvmStatic
        fun provideWebDrivers() = DriversInitializer.provideWebDrivers()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun testAnswerQuestion1(driver: WebDriver) {
        driver.get("https://stackoverflow.com/questions")
        val questionsPage = MainQuestionsPage(driver)
        questionsPage.openQuestion()

        val questionPage = QuestionPage(driver)
        Utils.clickAcceptCookiesIfPresent(driver)
        questionPage.writeAnswerToQuestion("aaaaaa")
        questionPage.clickToPostAnswer()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        Assertions.assertFalse(questionPage.isAnswerValid())
        Assertions.assertTrue(questionPage.areErrorsPresented())
        driver.quit()
    }

//    @ParameterizedTest
//    @MethodSource("provideWebDrivers")
//    fun testAnswerQuestion2(driver: WebDriver) {
//        //fixme found element which appears after JS validation
//        driver.get("https://stackoverflow.com/questions")
//        val questionsPage = MainQuestionsPage(driver)
//        questionsPage.openQuestion()
//
//        val questionPage = QuestionPage(driver)
//        Utils.clickAcceptCookies(driver)
//        questionPage.writeAnswerToQuestion("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
//        questionPage.clickToPostAnswer()
//        Thread.sleep(2000)
//
//        Assertions.assertTrue(questionPage.isAnswerValid())
//        Assertions.assertTrue(questionPage.areErrorsPresented())
//        Assertions.assertTrue(questionPage.isEmailNotPresentedToPost())
//
//        driver.quit()
//    }
}