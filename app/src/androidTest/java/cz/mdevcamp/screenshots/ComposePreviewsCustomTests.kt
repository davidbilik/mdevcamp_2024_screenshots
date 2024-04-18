package cz.mdevcamp.screenshots

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.test.core.graphics.writeToTestStorage
import com.airbnb.android.showkase.models.ShowkaseBrowserComponent
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import cz.mdevcamp.screenshots.ui.theme.MDevCamp24ShowcaseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class ComposePreviewsCustomTests {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testPreview(
        @TestParameter(valuesProvider = ComposePreviewsDropshotsTests.PreviewProvider::class) component: ShowkaseBrowserComponent,
    ) {
        rule.mainClock.autoAdvance = false
        rule.setContent {
            MDevCamp24ShowcaseTheme {
                component.component()
            }
        }
        rule.waitForIdle()
        rule.onAllNodes(isRoot())
            .onFirst()
            .captureToImage()
            .asAndroidBitmap()
            .writeToTestStorage(component.componentName)
    }
}
