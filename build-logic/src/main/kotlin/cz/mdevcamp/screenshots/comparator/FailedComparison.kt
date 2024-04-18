package cz.mdevcamp.screenshots.comparator

import com.dropbox.differ.Mask
import java.io.File

/**
 * Representation of a failed screenshot comparison
 */
class FailedComparison(
    val message: String,
    val screenshot: File,
    val goldenScreenshot: File?,
    val mask: Mask?
)
