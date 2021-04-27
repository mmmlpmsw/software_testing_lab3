package mmmlpmsw.testing.lab2.pages

import mmmlpmsw.testing.lab2.utilities.Utils
import mmmlpmsw.testing.lab2.utilities.find
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit

class AskQuestionPage(private val driver: WebDriver) {
    private val EXPECTED_PAGE_URL = "https://stackoverflow.com/questions/ask"
    init {
        WebDriverWait(driver, 1000).until {
            (driver as JavascriptExecutor).executeScript("return document.readyState") == "complete" &&
                    driver.currentUrl.startsWith(EXPECTED_PAGE_URL)
        }
        if (driver.find(By.xpath("//button[@class='grid--cell s-btn s-btn__primary js-modal-primary-btn js-modal-close js-first-tabbable js-modal-initial-focus js-gps-track']")))
            driver.findElement(By.xpath("//button[@class='grid--cell s-btn s-btn__primary js-modal-primary-btn js-modal-close js-first-tabbable js-modal-initial-focus js-gps-track']")).click()

        Utils.clickAcceptCookiesIfPresent(driver)
    }

    private val inputTitlePath = "//input[@name='title' and @type='text']"
    private val inputPostPath = "//textarea[@name='post-text' and contains(@class, 'js-post-body-field')]"
    private val inputTagPath = "//input[@class='s-input js-tageditor-replacing' and @type='text']"
    private val inputErrorMsgPath = "//div[contains(@class, 'js-stacks-validation-message')]"
    private val previewPath = "//div[contains(@class, 'wmd-preview')]"

    private val strongBtnPath = "//li[@class='wmd-button' and @title='Strong <strong> Ctrl+B']"
    private val italicBtnPath = "//li[@class='wmd-button' and @title='Emphasis <em> Ctrl+I']"
    private val linkBtnPath = "//li[@class='wmd-button' and @title='Hyperlink <a> Ctrl+L']"
    private val quoteBtnPath = "//li[@class='wmd-button' and @title='Blockquote <blockquote> Ctrl+Q']"
    private val codeBtnPath = "//li[@class='wmd-button' and @title='Code Sample <pre><code> Ctrl+K']"
    private val imageBtnPath = "//li[@class='wmd-button' and @title='Image <img> Ctrl+G']"
    private val snippetBtnPath = "//li[contains(@class, 'wmd-button') and @title='JavaScript/HTML/CSS snippet Ctrl-M']"
    private val numberedListBtnPath = "//li[@class='wmd-button' and @title='Numbered List <ol> Ctrl+O']"
    private val bulletedListBtnPath = "//li[@class='wmd-button' and @title='Bulleted List <ul> Ctrl+U']"
    private val headingBtnPath = "//li[@class='wmd-button' and @title='Heading <h1>/<h2> Ctrl+H']"
    private val hrBtnPath = "//li[@class='wmd-button' and @title='Horizontal Rule <hr> Ctrl+R']"
    private val undoBtnPath = "//li[@class='wmd-button' and @title='Undo - Ctrl+Z']"
    private val redoBtnPath = "//li[@class='wmd-button' and @title='Redo - Ctrl+Shift+Z']"

    fun clickBold() {
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS)
        driver.findElement(By.xpath(strongBtnPath)).click()
    }
    fun clickItalic() = driver.findElement(By.xpath(italicBtnPath)).click()
    fun addLink(link: String) {
        WebDriverWait(driver, 10).until (ExpectedConditions.presenceOfElementLocated(By.xpath(linkBtnPath)))
        driver.findElement(By.xpath(linkBtnPath)).click()
        WebDriverWait(driver, 10).until (ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='wmd-inline-dialog js-wmd-inline-dialog-']//input")))
        driver.findElement(By.xpath("//div[@class='wmd-inline-dialog js-wmd-inline-dialog-']//input")).clear()
        driver.findElement(By.xpath("//div[@class='wmd-inline-dialog js-wmd-inline-dialog-']//input")).sendKeys(link)
        driver.findElement(By.xpath("//div[@class='wmd-inline-dialog js-wmd-inline-dialog-']//button[contains(@class, 'js-insert-link-button')]")).click()
    }
    fun removeLink() = driver.findElement(By.xpath(linkBtnPath)).click()

    fun clickQuote() {
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS)
        driver.findElement(By.xpath(quoteBtnPath)).click()
    }

    fun clickCode() = driver.findElement(By.xpath(codeBtnPath)).click()
    fun clickImage() = driver.findElement(By.xpath(imageBtnPath)).click()
    fun clickSnippet() = driver.findElement(By.xpath(snippetBtnPath)).click()
    fun clickNumberedList() = driver.findElement(By.xpath(numberedListBtnPath)).click()
    fun clickBulletedList() = driver.findElement(By.xpath(bulletedListBtnPath)).click()
    fun clickHeading() = driver.findElement(By.xpath(headingBtnPath)).click()
    fun clickHorizontal() = driver.findElement(By.xpath(hrBtnPath)).click()
    fun clickUndo() {
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS)
        driver.findElement(By.xpath(undoBtnPath)).click()
    }
    fun clickRedo() {
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS)
        driver.findElement(By.xpath(redoBtnPath)).click()
    }

    fun writeTitle(title: String) {
        driver.findElement(By.xpath(inputTitlePath)).clear()
        driver.findElement(By.xpath(inputTitlePath)).sendKeys(title)
    }

    fun writePost(text: String) {
        driver.findElement(By.xpath(inputPostPath)).clear()
        driver.findElement(By.xpath(inputPostPath)).sendKeys(text)
    }

    fun writeTag(tag: String) {
        driver.findElement(By.xpath(inputTagPath)).sendKeys(Keys.BACK_SPACE)
        driver.findElement(By.xpath(inputTagPath)).sendKeys(Keys.CONTROL)
        driver.findElement(By.xpath(inputTagPath)).sendKeys("a")
        driver.findElement(By.xpath(inputTagPath)).sendKeys(Keys.BACK_SPACE)

        driver.findElement(By.xpath(inputTagPath)).clear()
        driver.findElement(By.xpath(inputTagPath)).sendKeys(tag)
    }

    fun getPreview(): String = driver.findElement(By.xpath(previewPath)).getAttribute("innerHTML")

    fun isTitlePresented() = driver.findElement(By.xpath("//div[contains(@class, 'js-stacks-validation-message') and text() = 'Title is missing.']")) == null
    fun isPostPresented() = driver.findElement(By.xpath("//div[contains(@class, 'js-stacks-validation-message') and text() = 'Body is missing.']")) == null
    fun isTagPresented() = driver.findElement(By.xpath("//div[contains(@class, 'js-stacks-validation-message') and contains(text(), 'Please enter at least one tag;')]")) == null

    fun isTitleShort() = driver.findElement(By.xpath("//div[contains(@class, 'js-stacks-validation-message') and contains(text(), 'Title must be at least 15 characters.')]")) != null
    fun isBodyShort() = driver.findElement(By.xpath("//div[contains(@class, 'js-stacks-validation-message') and contains(text(), 'Body must be at least 30 characters;')]")) != null

    fun isTitleHavingError() = driver.findElements(By.xpath("//*[@id='post-title' and contains(@class, 'has-error')]")).size > 0

    fun isTagHavingError() = driver.findElements(By.xpath("//*[@id='tag-editor']//*[contains(@class, 'js-tag-editor-container') and contains(@class, 'has-error')]")).size > 0

    fun writeTagAndEnter(tag: String) {
        driver.findElement(By.xpath(inputTagPath)).clear()
        driver.findElement(By.xpath(inputTagPath)).sendKeys(tag + Keys.ENTER)
    }

    fun highlightText(from: Int, to: Int) {
        Actions(driver).moveToElement(driver.findElement(By.xpath(inputPostPath))).perform()
        (driver as JavascriptExecutor).executeScript("arguments[0].setSelectionRange($from,$to)", driver.findElement(By.xpath(inputPostPath)))
    }

    fun postQuestion() = driver.findElement(By.xpath("//button[@type='submit' and contains(@class, 'js-submit-button')]")).click()
    fun reviewQuestion() = driver.findElement(By.xpath("//button[@type='button' and @data-gps-track='askpage.review_click']")).click()


    fun aaa() {
        println(driver.findElement(By.xpath(previewPath)).getAttribute("innerHTML"))
    }

}