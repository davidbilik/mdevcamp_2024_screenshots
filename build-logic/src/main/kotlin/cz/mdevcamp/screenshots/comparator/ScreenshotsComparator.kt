package cz.mdevcamp.screenshots.comparator

import com.dropbox.differ.Mask
import com.dropbox.differ.SimpleImageComparator
import java.io.File

class ScreenshotsComparator(
    private val screenshotsDir: File,
    private val goldenScreenshotsDir: File
) {

    private val imageComparator = SimpleImageComparator(maxDistance = 0.1f)

    fun compareScreenshots(): List<FailedComparison> {
        val failures = mutableListOf<FailedComparison>()
        screenshotsDir.listFiles()?.forEach { file ->
            val goldenFile = File(goldenScreenshotsDir, file.name)
            if (!goldenFile.exists()) {
                failures.add(
                    FailedComparison(
                        message = "Golden screenshot does not exist",
                        screenshot = file,
                        goldenScreenshot = null,
                        mask = null,
                    )
                )
                return@forEach
            }
            val newImage = ImageWrapper(file)
            val goldenImage = ImageWrapper(goldenFile)
            val mask = Mask(newImage.width, newImage.height)
            val result = try {
                imageComparator.compare(newImage, goldenImage, mask)
            } catch (e: Exception) {
                failures.add(
                    FailedComparison(
                        message = "Failed to compare screenshots",
                        screenshot = file,
                        goldenScreenshot = goldenFile,
                        mask = mask,
                    )
                )
                return@forEach
            }
            if (result.pixelDifferences > 0) {
                failures.add(
                    FailedComparison(
                        message = "Screenshots differ by ${result.pixelDifferences} pixels",
                        screenshot = file,
                        goldenScreenshot = goldenFile,
                        mask = mask,
                    )
                )
            }
        }
        return failures
    }
}
