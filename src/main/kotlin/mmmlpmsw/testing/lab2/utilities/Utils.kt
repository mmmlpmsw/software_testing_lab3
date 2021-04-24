package mmmlpmsw.testing.lab2.utilities

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class Utils {
    companion object {
        fun waitForCaptchaIfExists(driver: WebDriver): Boolean {
            println("captcha check executed")
            WebDriverWait(driver, 10).until {
                (driver as JavascriptExecutor).executeScript("return document.readyState") == "complete"
            }
            try {
                WebDriverWait(driver, 3).until(ExpectedConditions.titleIs("Human verification - Stack Overflow"))
            } catch (ignored: TimeoutException) {}
            if (driver.findElements(By.xpath("//iframe[@title='reCAPTCHA']")).size > 0) {
                println("Waiting for captcha solution")
                WebDriverWait(driver, 99999).until { driver.findElements(By.xpath("//iframe[@title='reCAPTCHA']")).size == 0 }
                return true
            }
            return false
        }

        fun isCaptchaShown(driver: WebDriver): Boolean = driver.findElements(By.xpath("//iframe[@title='reCAPTCHA']")).size > 0

        private const val acceptAllCookiesElementPath = "//button[contains(@class, 'js-accept-cookies')]"
        fun clickAcceptCookies(driver: WebDriver) = driver.findElement(By.xpath(acceptAllCookiesElementPath)).click()
    }
}

fun WebDriver.find(by: By) = this.findElements(by).size > 0




//eevjaqmrffdlulceoi@niwghx.com
//qwerty123



// xattr -d com.apple.quarantine /Users/mmmlpmsw/Documents/sem/testing/software_testing_lab2/driver/chromedriver
// xattr -d com.apple.quarantine /Users/mmmlpmsw/Documents/sem/testing/software_testing_lab2/driver/geckodriver