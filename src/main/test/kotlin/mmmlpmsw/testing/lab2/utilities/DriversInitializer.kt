package mmmlpmsw.testing.lab2.utilities

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.fail
import org.openqa.selenium.Cookie
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import java.io.FileInputStream
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.Stream

class DriversInitializer {
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

        @BeforeAll
        @JvmStatic
        fun provideWebDrivers(): Stream<WebDriver> {
            return Stream.of(
                    makeChromeDriver(),
//                    makeFirefoxDriver()
            )
        }

        private fun makeChromeDriver(): ChromeDriver {
            val opts = ChromeOptions()
            opts.addArguments("user-agent=Chrome/89.0.4389.128")

            return prepareDriver(ChromeDriver(opts))
        }

        private fun makeFirefoxDriver(): FirefoxDriver {
            return prepareDriver(FirefoxDriver())
        }

        fun <T : WebDriver> prepareDriver(driver: T): T {
            driver.get("https://stackoverflow.com")
            driver.manage().addCookie(Cookie("OptanonAlertBoxClosed", Instant.now().toString()))
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS)
            return driver
        }

//        Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:87.0) Gecko/20100101 Firefox/87.0
    }
}