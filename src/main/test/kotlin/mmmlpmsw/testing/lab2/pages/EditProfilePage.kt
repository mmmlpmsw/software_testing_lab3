package mmmlpmsw.testing.lab2.pages

import mmmlpmsw.testing.lab2.utilities.find
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait

class EditProfilePage(private val driver: WebDriver) {
    private val EXPECTED_PAGE_URL = "https://stackoverflow.com/users/edit/"
    init {
        WebDriverWait(driver, 10).until {
            (driver as JavascriptExecutor).executeScript("return document.readyState") == "complete" &&
                    driver.currentUrl.startsWith(EXPECTED_PAGE_URL)
        }
    }

    private val displayNameInputPath = "//input[@class='s-input' and @name='DisplayName' and @type='text']"
    val locationInputPath = "//input[@class='s-input pac-target-input' and @name='Location' and @type='text']"
    val titleInputPath = "//input[@class='s-input' and @name='Title' and @type='text']"
    private val saveProfileBtnPath = "//button[@class='s-btn s-btn__primary js-save-button' and @data-push='true' and @type='button']"
    private val cancelEditProfileBtnPath = "//a[@class='s-btn' and @name='cancel']"
    private val successFieldPath = "//div[@class='val-message val-success']"

    fun changeDisplayName(value: String) {
        driver.findElement(By.xpath(displayNameInputPath)).clear()
        driver.findElement(By.xpath(displayNameInputPath)).sendKeys(value)
    }
    fun changeLocation(value: String) {
        driver.findElement(By.xpath(locationInputPath)).clear()
        driver.findElement(By.xpath(locationInputPath)).sendKeys(value)
    }
    fun changeTitle(value: String) {
        driver.findElement(By.xpath(titleInputPath)).clear()
        driver.findElement(By.xpath(titleInputPath)).sendKeys(value)
    }
    fun saveChanges() = driver.findElement(By.xpath(saveProfileBtnPath)).click()

    fun cancelChanges() = driver.findElement(By.xpath(cancelEditProfileBtnPath)).click()

    fun isSuccess(): Boolean = driver.find(By.xpath(successFieldPath))

}