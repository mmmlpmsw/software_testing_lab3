package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.MainQuestionsPage
import mmmlpmsw.testing.lab2.pages.QuestionPage
import mmmlpmsw.testing.lab2.utilities.ProvideWebDrivers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit

class UnauthUserTest {
    private lateinit var driver: WebDriver

    @ParameterizedTest
    @ProvideWebDrivers
    fun testAnswerQuestion1(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/questions")
        val questionsPage = MainQuestionsPage(driver)
        questionsPage.openQuestion()

        val questionPage = QuestionPage(driver)
        questionPage.writeAnswerToQuestion("aaaaaa")
        questionPage.clickToPostAnswer()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        Assertions.assertFalse(questionPage.isAnswerValid())
        Assertions.assertTrue(questionPage.areErrorsPresented())
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun testAnswerQuestion2(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/questions")
        val questionsPage = MainQuestionsPage(driver)
        questionsPage.openQuestion()

        val questionPage = QuestionPage(driver)

        questionPage.writeAnswerToQuestion("test test help me please aaaaaaaaaaaaaaaaaaaaaaa")
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

        questionPage.dismiss()
        questionPage.clickToPostAnswer()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

        Assertions.assertTrue(questionPage.areErrorsPresented() || questionPage.isEmailNotPresentedToPost())
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}