package mmmlpmsw.testing.lab2

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

import java.io.FileInputStream
import java.util.*
import java.util.stream.Stream


@TestInstance(PER_METHOD)
class Test {
    companion object {
        private const val PROPERTIES_FILE = "src/main/test/resources/config.properties"
        lateinit var props: Properties

        @BeforeAll
        @JvmStatic
        fun initEverything() {
            props = Properties()
            try {
                props.load(FileInputStream(PROPERTIES_FILE))
            } catch (e: NullPointerException) {
                fail("Settings file '$PROPERTIES_FILE' not found")
            }
            System.setProperty("webdriver.gecko.driver", props.getProperty("webdriver.firefox.driver"))
            System.setProperty("webdriver.chrome.driver", props.getProperty("webdriver.chrome.driver"))
        }

        @JvmStatic
        fun provideWebDrivers(): Stream<WebDriver> {
            return Stream.of(
                    makeChromeDriver(),
                    makeFirefoxDriver()
            )
        }

        private fun makeChromeDriver()= ChromeDriver()

        private fun makeFirefoxDriver() = FirefoxDriver()
    }

    @ParameterizedTest
    @MethodSource("provideWebDrivers")
    fun dummyTest(driver: WebDriver) {
        driver.get("https://google.com")
        Thread.sleep(8000)
        driver.quit()
    }
}

// xattr -d com.apple.quarantine /Users/mmmlpmsw/Documents/sem/testing/software_testing_lab2/driver/chromedriver
// xattr -d com.apple.quarantine /Users/mmmlpmsw/Documents/sem/testing/software_testing_lab2/driver/geckodriver