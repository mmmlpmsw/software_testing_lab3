package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.*
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import mmmlpmsw.testing.lab2.utilities.Utils
import mmmlpmsw.testing.lab2.utilities.find
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver


class AuthUserTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun init() = DriversInitializer.initEverything()

        @JvmStatic
        fun provideWebDrivers() = DriversInitializer.provideWebDrivers()
    }

    private lateinit var loginPage: LoginPage
    private lateinit var mainQuestionsPage: MainQuestionsPage
    private lateinit var questionPage: QuestionPage
    private lateinit var userPage: CurrentUserPage
    private lateinit var editProfilePage: EditProfilePage

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun searchQuestionTest(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            driver.quit()
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        mainQuestionsPage.search("answers:518")

        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/184618/what-is-the-best-comment-in-source-code-you-have-ever-encountered')]")

        questionPage = QuestionPage(driver)
        val path = "//div[@itemprop='mainEntity']//a[contains(@class, 'question-hyperlink')]"
        Assertions.assertTrue(driver.findElement(By.xpath(path)).text == "What is the best comment in source code you have ever encountered? [closed]")

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun leaveCommentTest(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            driver.quit()
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        Utils.waitForCaptchaIfExists(driver)

        mainQuestionsPage.search("answers:518")
        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/184618/what-is-the-best-comment-in-source-code-you-have-ever-encountered')]")

        questionPage = QuestionPage(driver)
        questionPage.comment()

        Assertions.assertTrue(driver.find(By.xpath("//div[@class='message-text' and .//a[@href='/help/privileges/comment']]")))

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun bookmarksTest(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            driver.quit()
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        Utils.waitForCaptchaIfExists(driver)

        mainQuestionsPage.search("answers:518")
        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/184618/what-is-the-best-comment-in-source-code-you-have-ever-encountered')]")

        questionPage = QuestionPage(driver)

        questionPage.addToBookmarks()
        questionPage.openMyProfile()

        userPage = CurrentUserPage(driver)
        userPage.openMyBookmarks()

        Assertions.assertTrue(driver.findElement(By.xpath(
            "//div[@class='user-questions']/div[contains(@class, 'question-summary')]//a[@class='question-hyperlink']"
        )).text == "What is the best comment in source code you have ever encountered? [closed]")

        userPage.removeFromBookmarks("//div[@class='user-questions']/div[contains(@class, 'question-summary')]")
        driver.navigate().refresh()

        Assertions.assertFalse(driver.find(By.xpath(
            "//div[@class='user-questions']/div[contains(@class, 'question-summary')]//a[@class='question-hyperlink']"
        )))

    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun followQuestionTest(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            driver.quit()
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        Utils.waitForCaptchaIfExists(driver)

        mainQuestionsPage.search("answers:107")
        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/5767325/how-can-i-remove-a-specific-item-from-an-array')]")

        questionPage = QuestionPage(driver)

        questionPage.addToFollowingQuestions()
        questionPage.openMyProfile()

        userPage = CurrentUserPage(driver)
        userPage.openMyFollowingQuestions()

        Assertions.assertTrue(driver.findElement(By.xpath(
            "//div[@class='user-questions']/div[contains(@class, 'js-followed-post')]//a[@class='question-hyperlink']"
        )).text == "How can I remove a specific item from an array?")

        userPage.unfollow()
        driver.navigate().refresh()

        Assertions.assertFalse(driver.find(By.xpath(
            "//div[@class='user-questions']/div[contains(@class, 'js-followed-post')]//a[@class='question-hyperlink']"
        )))

        driver.quit()

    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun editProfileTest(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)

        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            driver.quit()
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        if (Utils.waitForCaptchaIfExists(driver)) {
            driver.quit()
            return
        }

        mainQuestionsPage.openUserProfile()

        userPage = CurrentUserPage(driver)

        userPage.openEditProfile()
        editProfilePage = EditProfilePage(driver)

        editProfilePage.changeLocation("Saint Petersburg, Russia")
        editProfilePage.changeTitle("aaaaa")

        Thread.sleep(2000) // todo wait
        editProfilePage.saveChanges()
        Thread.sleep(2000) // todo wait

        Assertions.assertTrue(editProfilePage.isSuccess())

        Assertions.assertTrue(driver.findElement(By.xpath(editProfilePage.locationInputPath)).getAttribute("value") == "Saint Petersburg, Russia")
        Assertions.assertTrue(driver.findElement(By.xpath(editProfilePage.titleInputPath)).getAttribute("value") == "aaaaa")

    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun shareQuestionTest(driver: WebDriver) {
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            driver.quit()
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        Utils.waitForCaptchaIfExists(driver)

        mainQuestionsPage.search("answers:518")
        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/184618/what-is-the-best-comment-in-source-code-you-have-ever-encountered')]")

        questionPage = QuestionPage(driver)

        Assertions.assertTrue(questionPage.clickShareQuestion())

        driver.quit()
    }


    //todo logout
}