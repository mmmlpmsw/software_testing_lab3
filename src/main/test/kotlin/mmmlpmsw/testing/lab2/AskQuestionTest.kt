package mmmlpmsw.testing.lab2

import mmmlpmsw.testing.lab2.pages.AskQuestionPage
import mmmlpmsw.testing.lab2.pages.LoginPage
import mmmlpmsw.testing.lab2.pages.MainQuestionsPage
import mmmlpmsw.testing.lab2.utilities.ProvideWebDrivers
import mmmlpmsw.testing.lab2.utilities.Utils
import mmmlpmsw.testing.lab2.utilities.find
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


class AskQuestionTest {
    private lateinit var drivers: List<WebDriver>

    @ParameterizedTest
    @ProvideWebDrivers
    fun unauthorizedUserAskQuestionTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()

            Assertions.assertTrue(driver.currentUrl.startsWith("https://stackoverflow.com/users/login?"))

            val path = "//p[@class='val-textemphasis']"
            Assertions.assertTrue(
                driver.findElement(By.xpath(path)).text == "You must be logged in to ask a question on Stack Overflow"
            )
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionEmptyFieldsTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
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
        }

    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionShortFieldsTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
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

            Assertions.assertTrue(askQuestionPage.isTitleShort())
            Assertions.assertTrue(askQuestionPage.isTagHavingError())

            askQuestionPage.postQuestion()
            Assertions.assertTrue(askQuestionPage.isBodyShort())
        }

    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionLongTitleTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
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
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionBoldButtonTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        " ut labore et dolore magna aliqua."
            )

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
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionItalicButtonTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        " ut labore et dolore magna aliqua."
            )

            askQuestionPage.highlightText(6, 11)
            askQuestionPage.clickItalic()
            Assertions.assertEquals(
                "<p>Lorem <em>ipsum</em> dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
                askQuestionPage.getPreview()
            )

            askQuestionPage.clickItalic()
            Assertions.assertEquals(
                "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
                askQuestionPage.getPreview()
            )
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionQuoteButtonTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        " ut labore et dolore magna aliqua."
            )

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
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionLinkButtonTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        " ut labore et dolore magna aliqua."
            )

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
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionCodeButtonTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        " ut labore et dolore magna aliqua."
            )

            askQuestionPage.highlightText(6, 11)
            askQuestionPage.clickCode()

            Assertions.assertEquals(
                "<p>Lorem <code>ipsum</code> dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
                askQuestionPage.getPreview()
            )

            askQuestionPage.clickCode()
            Assertions.assertEquals(
                "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
                askQuestionPage.getPreview()
            )
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionImageButtonTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        " ut labore et dolore magna aliqua."
            )

            askQuestionPage.clickImage()
            Assertions.assertTrue(
                driver.find(By.xpath("//div[contains(@class,'s-notice__warning')]")) &&
                        driver.findElement(By.xpath("//div[contains(@class,'s-notice__warning')]"))
                            .getAttribute("innerHTML") == "Images are useful in a post, but <b>make sure the post is still clear without them</b>.  If you post images of code or error messages, copy and paste or type the actual code or message into the post directly."
            )
            Assertions.assertEquals(
                "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>\n",
                askQuestionPage.getPreview()
            )
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionUndoRedoTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" +
                        " ut labore et dolore magna aliqua."
            )

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
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionNumberedListTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost("a\nb")

            askQuestionPage.highlightText(0, 1)

            val currentHTML = askQuestionPage.getPreview()

            askQuestionPage.clickNumberedList()

            WebDriverWait(driver, 10).pollingEvery(Duration.ofMillis(100))
                .until { currentHTML != askQuestionPage.getPreview() }

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
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionBulletedListTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost("a\nb")

            askQuestionPage.highlightText(0, 1)
            val currentHTML = askQuestionPage.getPreview()

            askQuestionPage.clickBulletedList()

            WebDriverWait(driver, 10).pollingEvery(Duration.ofMillis(100))
                .until { currentHTML != askQuestionPage.getPreview() }

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
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionHeadingTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost("Lorem\nipsum")

            askQuestionPage.highlightText(0, 5)
            var currentHTML = askQuestionPage.getPreview()

            askQuestionPage.clickHeading()

            WebDriverWait(driver, 10).pollingEvery(Duration.ofMillis(100))
                .until { currentHTML != askQuestionPage.getPreview() }

            Assertions.assertEquals(
                "<h2>Lorem</h2>\n" +
                        "<p>ipsum</p>\n",
                askQuestionPage.getPreview()
            )

            askQuestionPage.highlightText(0, 5)
            currentHTML = askQuestionPage.getPreview()

            askQuestionPage.clickHeading()

            WebDriverWait(driver, 10).pollingEvery(Duration.ofMillis(100))
                .until { currentHTML != askQuestionPage.getPreview() }

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
        }
    }

    @ParameterizedTest
    @ProvideWebDrivers
    fun authorizedUserAskQuestionHorizontalTest(drivers: List<WebDriver>) {
        this.drivers = drivers
        drivers.forEach { driver ->
            driver.get("https://stackoverflow.com/users/login")
            val loginPage = LoginPage(driver)
            loginPage.login()
            if (Utils.waitForCaptchaIfExists(driver)) {
                return
            }
            driver.get("https://stackoverflow.com/questions")
            val questionsPage = MainQuestionsPage(driver)
            questionsPage.clickToAskQuestion()
            val askQuestionPage = AskQuestionPage(driver)

            askQuestionPage.writePost("Lorem\nipsum")

            askQuestionPage.highlightText(6, 6)
            var currentHTML = askQuestionPage.getPreview()

            askQuestionPage.clickHorizontal()

            WebDriverWait(driver, 10).pollingEvery(Duration.ofMillis(100))
                .until { currentHTML != askQuestionPage.getPreview() }


            Assertions.assertEquals(
                "<p>Lorem</p>\n" +
                        "<hr>\n" +
                        "<p>ipsum</p>\n",
                askQuestionPage.getPreview()
            )
        }
    }

    @AfterEach
    fun tearDown() {
        drivers.forEach(WebDriver::quit)
    }
}