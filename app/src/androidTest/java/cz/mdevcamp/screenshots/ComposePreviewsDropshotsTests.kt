package cz.mdevcamp.screenshots

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.unit.Density
import com.airbnb.android.showkase.models.Showkase
import com.airbnb.android.showkase.models.ShowkaseBrowserComponent
import com.dropbox.dropshots.Dropshots
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import cz.mdevcamp.screenshots.ui.theme.MDevCamp24ShowcaseTheme
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ComposePreviewsDropshotsTests {

    object PreviewProvider : TestParameter.TestParameterValuesProvider {

        override fun provideValues(): List<ShowkaseBrowserComponent> {
            return Showkase.getMetadata().componentList
        }
    }

    @get:Rule
    val dropshots = Dropshots()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    @Ignore
    fun testPreview(
        @TestParameter(valuesProvider = PreviewProvider::class) component: ShowkaseBrowserComponent,
        @TestParameter(value = ["0.75f", "1f", "1.5f", "2f"]) fontScale: Float,
        @TestParameter(value = ["light", "dark"]) theme: String,
    ) {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.setContent {
            MDevCamp24ShowcaseTheme(darkTheme = theme == "dark") {
                val currentDensity = LocalDensity.current
                CompositionLocalProvider(
                    LocalDensity provides Density(
                        currentDensity.density,
                        currentDensity.fontScale * fontScale
                    )
                ) {
                    component.component()
                }
            }
        }
        composeTestRule.waitForIdle()
        val bitmap = composeTestRule.onAllNodes(isRoot())
            .onFirst()
            .captureToImage()
            .asAndroidBitmap()
        dropshots.assertSnapshot(
            bitmap = bitmap,
            name = "${component.componentName}_fs=${fontScale}_$theme"
        )
    }
}
