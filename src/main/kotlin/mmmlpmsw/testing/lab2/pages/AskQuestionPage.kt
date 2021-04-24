package mmmlpmsw.testing.lab2.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

class AskQuestionPage(private val driver: WebDriver) {
    private val EXPECTED_PAGE_URL = "https://stackoverflow.com/questions/ask"
    init {
        WebDriverWait(driver, 1000).until {
            (driver as JavascriptExecutor).executeScript("return document.readyState") == "complete" &&
                    driver.currentUrl.startsWith(EXPECTED_PAGE_URL)
        }
        driver.findElement(By.xpath("//button[@class='grid--cell s-btn s-btn__primary js-modal-primary-btn js-modal-close js-first-tabbable js-modal-initial-focus js-gps-track']")).click()
    }




}