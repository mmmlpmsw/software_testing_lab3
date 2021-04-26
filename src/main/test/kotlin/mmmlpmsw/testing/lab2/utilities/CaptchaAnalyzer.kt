package mmmlpmsw.testing.lab2.utilities

import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

class CaptchaAnalyzer {
    companion object {
        fun isCaptchaSolved(driver: WebDriver, captchaElement: WebElement): Boolean {
            val bytes = (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES) // Taking full screenshot so browser does not flicker
            val image = ImageIO.read(ByteArrayInputStream(bytes))
            val rawRgba = image.data.getPixels(
                captchaElement.location.x,
                captchaElement.location.y,
                captchaElement.size.width,
                captchaElement.size.height,
                null as IntArray?
            )
            val greenBytes = rawRgba.withIndex().groupBy { it.index/4 }.filter {
                val r = it.value[0].value
                val g = it.value[1].value
                val b = it.value[2].value
                r < 10 && g > 150 && b < 90
            }.count()
            return greenBytes > 50
        }
    }
}