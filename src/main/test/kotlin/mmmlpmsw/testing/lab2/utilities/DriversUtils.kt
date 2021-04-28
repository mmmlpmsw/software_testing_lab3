package mmmlpmsw.testing.lab2.utilities

import org.junit.jupiter.api.fail
import org.junit.jupiter.params.provider.MethodSource
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

const val DRIVERS_PROVIDER_NAME = "mmmlpmsw.testing.lab2.utilities.DriversUtils#provideWebDrivers"

@MethodSource(DRIVERS_PROVIDER_NAME)
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ProvideWebDrivers

class DriversUtils {
    companion object {
        private const val PROPERTIES_FILE = "src/main/test/resources/config.properties"
        var props: Properties = Properties()

        init {
            try {
                props.load(FileInputStream(PROPERTIES_FILE))
            } catch (e: NullPointerException) {
                fail("Settings file '${PROPERTIES_FILE}' not found")
            }
            System.setProperty("webdriver.gecko.driver", props.getProperty("webdriver.firefox.driver"))
            System.setProperty("webdriver.chrome.driver", props.getProperty("webdriver.chrome.driver"))
        }

        @JvmStatic
        @Suppress("unused")
        fun provideWebDrivers(): Stream<List<WebDriver>> {
            val browsers = props.getProperty("browsers").split(" ").map { it.strip() }
            val list = mutableListOf<WebDriver>()
            val builder = Stream.builder<List<WebDriver>>()
            if (browsers.contains("chrome"))
                list.add(makeChromeDriver())
//
            if (browsers.contains("firefox"))
                list.add(makeFirefoxDriver())
            builder.add(list)
            return builder.build()
        }

        private fun makeChromeDriver(): ChromeDriver {
            val opts = ChromeOptions()
            opts.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:87.0) Gecko/20100101 Firefox/87.0")

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
    }
}