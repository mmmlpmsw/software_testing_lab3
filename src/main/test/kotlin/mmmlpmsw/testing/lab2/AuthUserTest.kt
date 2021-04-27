package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.*
import mmmlpmsw.testing.lab2.utilities.ProvideWebDrivers
import mmmlpmsw.testing.lab2.utilities.Utils
import mmmlpmsw.testing.lab2.utilities.find
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit


class AuthUserTest {
    private lateinit var driver: WebDriver

    private lateinit var loginPage: LoginPage
    private lateinit var mainQuestionsPage: MainQuestionsPage
    private lateinit var questionPage: QuestionPage
    private lateinit var userPage: CurrentUserPage
    private lateinit var editProfilePage: EditProfilePage

    @ParameterizedTest
    @ProvideWebDrivers
    fun searchQuestionTest(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        mainQuestionsPage.search("answers:518")
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/184618/what-is-the-best-comment-in-source-code-you-have-ever-encountered')]")

        questionPage = QuestionPage(driver)
        val path = "//div[@itemprop='mainEntity']//a[contains(@class, 'question-hyperlink')]"
        Assertions.assertTrue(driver.findElement(By.xpath(path)).text == "What is the best comment in source code you have ever encountered? [closed]")

    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun leaveCommentTest(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        Utils.waitForCaptchaIfExists(driver)

        mainQuestionsPage.search("answers:518")
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/184618/what-is-the-best-comment-in-source-code-you-have-ever-encountered')]")

        questionPage = QuestionPage(driver)
        questionPage.comment()

        Assertions.assertTrue(driver.find(By.xpath("//div[@class='message-text' and .//a[@href='/help/privileges/comment']]")))

    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun bookmarksTest(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        Utils.waitForCaptchaIfExists(driver)

        mainQuestionsPage.search("answers:518")
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/184618/what-is-the-best-comment-in-source-code-you-have-ever-encountered')]")

        questionPage = QuestionPage(driver)

        questionPage.addToBookmarks()
        questionPage.openMyProfile()

        userPage = CurrentUserPage(driver)
        userPage.openMyBookmarks()
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)

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
    @ProvideWebDrivers
    fun followQuestionTest(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        Utils.waitForCaptchaIfExists(driver)

        mainQuestionsPage.search("answers:107")
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
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


    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun editProfileTest(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)

        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        mainQuestionsPage.openUserProfile()

        userPage = CurrentUserPage(driver)

        userPage.openEditProfile()
        editProfilePage = EditProfilePage(driver)

        editProfilePage.changeLocation("Saint Petersburg, Russia")
        editProfilePage.changeTitle("aaaaa")

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        editProfilePage.saveChanges()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

        Assertions.assertTrue(editProfilePage.isDone())

        Assertions.assertTrue(driver.findElement(By.xpath(editProfilePage.locationInputPath)).getAttribute("value") == "Saint Petersburg, Russia")
        Assertions.assertTrue(driver.findElement(By.xpath(editProfilePage.titleInputPath)).getAttribute("value") == "aaaaa")

    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun cancelEditProfileTest(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)

        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        mainQuestionsPage.openUserProfile()

        userPage = CurrentUserPage(driver)

        userPage.openEditProfile()
        editProfilePage = EditProfilePage(driver)

        Assertions.assertTrue(driver.findElement(By.xpath(editProfilePage.locationInputPath)).getAttribute("value") == "Saint Petersburg, Russia")
        Assertions.assertTrue(driver.findElement(By.xpath(editProfilePage.titleInputPath)).getAttribute("value") == "aaaaa")

//        editProfilePage.changeLocation("Saint Petersburg, Russia")
//        editProfilePage.changeTitle("aaaaa")

        editProfilePage.changeLocation("")
        editProfilePage.changeTitle("")

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        editProfilePage.cancelChanges()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

        userPage.openEditProfile()

        Assertions.assertTrue(driver.findElement(By.xpath(editProfilePage.locationInputPath)).getAttribute("value") == "Saint Petersburg, Russia")
        Assertions.assertTrue(driver.findElement(By.xpath(editProfilePage.titleInputPath)).getAttribute("value") == "aaaaa")

    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun shareQuestionTest(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        mainQuestionsPage = MainQuestionsPage(driver)

        Utils.waitForCaptchaIfExists(driver)

        mainQuestionsPage.search("answers:518")
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        mainQuestionsPage.clickToLink("//a[contains(@href, '/questions/184618/what-is-the-best-comment-in-source-code-you-have-ever-encountered')]")

        questionPage = QuestionPage(driver)
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        Assertions.assertTrue(questionPage.clickShareQuestion())

    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun logoutTest(driver: WebDriver) {
        this.driver = driver
        driver.get("https://stackoverflow.com/users/login")
        loginPage = LoginPage(driver)
        loginPage.login()

        if (Utils.waitForCaptchaIfExists(driver)) {
            return
        }

        val mainPage = MainQuestionsPage(driver)
        mainPage.logout()

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

        Assertions.assertEquals("https://stackoverflow.com/", driver.currentUrl)

    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}