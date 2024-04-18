package cz.mdevcamp.screenshots.comparator

import com.dropbox.differ.Color
import com.dropbox.differ.Image
import com.sksamuel.scrimage.ImmutableImage
import java.io.File

/**
 * Wrapper over a [File] that implements [Image] interface. This is used in Dropbox differ library
 */
class ImageWrapper(file: File) : Image {

    private val img = ImmutableImage.loader().fromFile(file)

    override val height: Int
        get() = img.height
    override val width: Int
        get() = img.width

    override fun getPixel(x: Int, y: Int): Color {
        val pixel = img.pixel(x, y)
        return Color(
            r = pixel.red(),
            g = pixel.green(),
            b = pixel.blue(),
            a = pixel.alpha()
        )
    }
}
