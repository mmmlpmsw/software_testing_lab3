package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.AskQuestionPage
import mmmlpmsw.testing.lab2.pages.LoginPage
import mmmlpmsw.testing.lab2.pages.MainQuestionsPage
import mmmlpmsw.testing.lab2.utilities.DriversInitializer
import mmmlpmsw.testing.lab2.utilities.Utils
import mmmlpmsw.testing.lab2.utilities.find
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit

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
    fun authorizedUserAskQuestionEmptyFieldsTest(driver: WebDriver) {
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
        askQuestionPage.writeTitle("")
        askQuestionPage.writePost("")
        askQuestionPage.writeTag("")
        askQuestionPage.reviewQuestion()

        Assertions.assertFalse(askQuestionPage.isTitlePresented())
        Assertions.assertFalse(askQuestionPage.isPostPresented())
        Assertions.assertFalse(askQuestionPage.isTagPresented())

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionShortFieldsTest(driver: WebDriver) {
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

        askQuestionPage.writeTitle("a")
        askQuestionPage.writePost("a")
        askQuestionPage.writeTag("a")

        askQuestionPage.reviewQuestion()
        askQuestionPage.postQuestion()

        Assertions.assertTrue(askQuestionPage.isTitlePresented())
        Assertions.assertTrue(askQuestionPage.isPostPresented())
        Assertions.assertTrue(askQuestionPage.isTagPresented())

        Assertions.assertTrue(askQuestionPage.isTitleShort())
        Assertions.assertTrue(askQuestionPage.isBodyShort())
        Assertions.assertFalse(askQuestionPage.isTagAllowed())

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionLongTitleTest(driver: WebDriver) {
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

        askQuestionPage.writeTitle("jfhueias`cnhaiaisudhrfiuawhgiuszawshaiuhdisuyhbfiusdfhegpHGAUYFSGjfhueias`cnhaiaisudhrfiuawhgiuszawshaiuhdisuyhbfiusdfhegpHGAUYFSGjfhueias`cnhaiaisudhrfiuawhgiuszawshaiuhdisuyhbfiusdfhegpHGAUYFSGjfhueias`cnhaiaisudhrfiuawhgiuszawshaiuhdisuyhbfiusdfhegpHGAUYFSGjfhueias`cnhaiaisudhrfiuawhgiuszawshaiuh")
        askQuestionPage.writeTagAndEnter("ojhilugyftdytufygiuhoijpokjihougiyfutdyrsydfgijnoidnvodufnvosdnvoaisnfcioncdjoiasjisohcfohihihvhvoudhvduofhoifhohfohfohfsodhfohfio")
        askQuestionPage.writePost("a")

        Assertions.assertTrue(askQuestionPage.isTitleHavingError())
        Assertions.assertTrue(askQuestionPage.isTagHavingError())

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionBoldButtonTest(driver: WebDriver) {
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

        askQuestionPage.writePost("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.")

        askQuestionPage.highlightText(0, 5)
        askQuestionPage.clickBold()
        Assertions.assertEquals(
            "<p><strong>Lorem</strong> ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.clickBold()
        Assertions.assertEquals(
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionItalicButtonTest(driver: WebDriver) {
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

        askQuestionPage.writePost("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.")

        askQuestionPage.highlightText(6, 11)
        askQuestionPage.clickItalic()
        Assertions.assertEquals(
            "<p>Lorem <em>ipsum</em> dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.clickItalic()
        Assertions.assertEquals(
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview())

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionQuoteButtonTest(driver: WebDriver) {
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

        askQuestionPage.writePost("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.")

        askQuestionPage.highlightText(18, 24)
        askQuestionPage.clickQuote()
        Assertions.assertEquals(
            "<p>Lorem ipsum dolor</p>\n" +
                    "<blockquote>\n" +
                    "<p>sit am</p>\n" +
                    "</blockquote>\n" +
                    "<p>et, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.clickQuote()
        Assertions.assertEquals(
            "<p>Lorem ipsum dolor</p>\n" +
                    "<p>sit am</p>\n" +
                    "<p>et, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionLinkButtonTest(driver: WebDriver) {
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

        askQuestionPage.writePost("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.")

        askQuestionPage.highlightText(18, 24)
        Thread.sleep(2000)
        askQuestionPage.addLink("google.com")
        Assertions.assertEquals(
            "<p>Lorem ipsum dolor <a href=\"http://google.com\">sit am</a>et, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.removeLink()
        Assertions.assertEquals(
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionCodeButtonTest(driver: WebDriver) {
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

        askQuestionPage.writePost("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.")

        askQuestionPage.highlightText(6, 11)
        askQuestionPage.clickCode()

        Assertions.assertEquals(
            "<p>Lorem <code>ipsum</code> dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.clickCode()
        Assertions.assertEquals(
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview())

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionImageButtonTest(driver: WebDriver) {
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

        askQuestionPage.writePost("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.")

        askQuestionPage.clickImage()
        Assertions.assertTrue(
            driver.find(By.xpath("//div[contains(@class,'s-notice__warning')]")) &&
                    driver.findElement(By.xpath("//div[contains(@class,'s-notice__warning')]")).getAttribute("innerHTML") == "Images are useful in a post, but <b>make sure the post is still clear without them</b>.  If you post images of code or error messages, copy and paste or type the actual code or message into the post directly."
        )
        Assertions.assertEquals(
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview())

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionUndoRedoTest(driver: WebDriver) {
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

        askQuestionPage.writePost("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.")

        askQuestionPage.highlightText(0, 5)
        askQuestionPage.clickBold()
        Assertions.assertEquals(
            "<p><strong>Lorem</strong> ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.clickUndo()
        Assertions.assertEquals(
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.clickRedo()
        Assertions.assertEquals(
            "<p><strong>Lorem</strong> ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
            askQuestionPage.getPreview()
        )

        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionNumberedListTest(driver: WebDriver) {
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

        askQuestionPage.writePost("a\nb")

        askQuestionPage.highlightText(0, 1)
        askQuestionPage.clickNumberedList()

        Assertions.assertEquals(
            "<ol>\n" +
                    "<li>a</li>\n" +
                    "</ol>\n" +
                    "<p>b</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.highlightText(4, 5)
        askQuestionPage.clickNumberedList()
        Assertions.assertEquals(
            "<p>a</p>\n" +
                    "<p>b</p>\n",
            askQuestionPage.getPreview()
        )


        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionBulletedListTest(driver: WebDriver) {
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

        askQuestionPage.writePost("a\nb")

        askQuestionPage.highlightText(0, 1)
        askQuestionPage.clickBulletedList()
        Assertions.assertEquals(
            "<ul>\n" +
                    "<li>a</li>\n" +
                    "</ul>\n" +
                    "<p>b</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.highlightText(3, 4)
        askQuestionPage.clickBulletedList()
        Assertions.assertEquals(
            "<p>a</p>\n" +
                    "<p>b</p>\n",
            askQuestionPage.getPreview()
        )


        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionHeadingTest(driver: WebDriver) {
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

        askQuestionPage.writePost("Lorem\nipsum")

        askQuestionPage.highlightText(0, 5)
        askQuestionPage.clickHeading()
        Assertions.assertEquals(
            "<h2>Lorem</h2>\n" +
                    "<p>ipsum</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.highlightText(0, 5)
        askQuestionPage.clickHeading()
        Assertions.assertEquals(
            "<h1>Lorem</h1>\n" +
                    "<p>ipsum</p>\n",
            askQuestionPage.getPreview()
        )

        askQuestionPage.highlightText(0, 5)
        askQuestionPage.clickHeading()
        Assertions.assertEquals(
            "<p>Lorem</p>\n" +
                    "<p>ipsum</p>\n",
            askQuestionPage.getPreview()
        )


        driver.quit()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun authorizedUserAskQuestionHorizontalTest(driver: WebDriver) {
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

        askQuestionPage.writePost("Lorem\nipsum")

        askQuestionPage.highlightText(6, 6)
        askQuestionPage.clickHorizontal()
        Assertions.assertEquals(
            "<p>Lorem</p>\n" +
                    "<hr>\n" +
                    "<p>ipsum</p>\n",
            askQuestionPage.getPreview()
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

        askQuestionPage.writePost("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                " ut labore et dolore magna aliqua.")









        driver.quit()

    }
}