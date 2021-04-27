package mmmlpmsw.testing.lab2.utilities

import org.openqa.selenium.*
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO

class CaptchaAnalyzer {
    companion object {
        fun isCaptchaSolved(driver: WebDriver, captchaElement: WebElement): Boolean {
            println("Checking captcha state")
            val bytes = (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES) // Taking full screenshot so browser does not flicker
            val image = ImageIO.read(ByteArrayInputStream(bytes))
            val pageOffset = (driver as JavascriptExecutor).executeScript("return window.pageYOffset").toString().toDouble()
            val scale = (driver as JavascriptExecutor).executeScript("return window.devicePixelRatio").toString().toDouble()
            val x = (captchaElement.location.x*scale).toInt()
            val y = ((captchaElement.location.y - pageOffset)*scale).toInt()
            val w = (captchaElement.size.width*scale).toInt()
            val h = (captchaElement.size.height*scale).toInt()

            if (x + w >= image.width || y + h >= image.height) {
                println("Element out of screen, screenshot no captured")
                return false
            }
            ImageIO.write(image.getSubimage(x, y, w, h), "png", File("test.png")) // For debugging purposes
            val rawRgba = image.data.getPixels(x, y, w, h, null as IntArray?)
            val greenPixels = rawRgba.withIndex().groupBy { it.index / 4 }.filter {
                val r = it.value[0].value
                val g = it.value[1].value
                val b = it.value[2].value
                r < 100 && g > 150 && b < 95
            }.count()
            println("Found $greenPixels green pixels")
            return greenPixels > 0.0002*rawRgba.size
        }
    }
}