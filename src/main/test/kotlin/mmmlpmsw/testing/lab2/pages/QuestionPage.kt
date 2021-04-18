package mmmlpmsw.testing.lab2.pages

import mmmlpmsw.testing.lab2.utilities.find
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.lang.IllegalArgumentException

class QuestionPage(private val driver: WebDriver) {
    private val EXPECTED_PAGE_URL = "https://stackoverflow.com/questions/"

    init {
        WebDriverWait(driver, 10).until {
            (driver as JavascriptExecutor).executeScript("return document.readyState") == "complete" &&
                    (driver.currentUrl.startsWith(EXPECTED_PAGE_URL))
        }
    }

    private val postAnswerTextareaPath = "//textarea[@name='post-text']"
    private val buttonPath = "//button[@type='submit']"
    private val emailErrorMsgPath = "//div[@class='message-text']"
    private val showMoreCommentsPath = "//div[@class='answer']//a[@title='Expand to show all comments on this post']"
    private val addCommentPath = "//div[@class='answer']//a[contains(.,'Add a comment')]"
    private val addToBookmarksBtnPath = "//button[contains(@class, 'js-bookmark-btn')]"
    private val addToFollowingBtnPath = "//button[contains(@class, 'js-follow-post js-follow-question')]"
    private val myProfileLinkPath = "//a[contains(@class, 'my-profile')]"

//    <a class="js-show-link comments-link  dno" title="Expand to show all comments on this post" href="#" onclick="" role="button"></a>
    fun writeAnswerToQuestion(text: String) = driver.findElement(By.xpath(postAnswerTextareaPath)).sendKeys(text)
    fun isAnswerValid() =
        !driver.find(
            By.xpath("//div[contains(@class, 'js-stacks-validation-message')]")
        )

    fun isEmailNotPresentedToPost(): Boolean {
        WebDriverWait(driver, 10).until {
            driver.findElement(By.xpath(emailErrorMsgPath)).text == "An email is required to post."
        }
        return driver.findElement(By.xpath(emailErrorMsgPath)).text == "An email is required to post."
    }

    fun clickToPostAnswer() {
        if (driver.findElement(By.xpath(buttonPath)).text == " Post Your Answer ")
            driver.findElement(By.xpath(buttonPath)).click()
    }

    fun areErrorsPresented(): Boolean {
        WebDriverWait(driver, 10).until {
            driver.find(By.xpath("//div[contains(@class, 'js-general-error general-error')]"))
        }
        return driver.find(By.xpath("//div[contains(@class, 'js-general-error general-error')]"))
    }

    fun comment() {
        driver.findElement(By.xpath(showMoreCommentsPath)).click()
        Thread.sleep(2000)
        driver.findElement(By.linkText("Add a comment")).click() //fixme linkText -> xpath
    }

    fun addToBookmarks() = driver.findElement(By.xpath(addToBookmarksBtnPath)).click()
    fun addToFollowingQuestions() = driver.findElement(By.xpath(addToFollowingBtnPath)).click()
    fun openMyProfile() = driver.findElement(By.xpath(myProfileLinkPath)).click()

}