plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)

    implementation(libs.image.differ)
    implementation(libs.scr.image)
}

gradlePlugin {
    plugins {
        register("screenshot-tests-plugin") {
            id = "screenshots-plugin"
            implementationClass = "cz.mdevcamp.screenshots.build.ScreenshotsPlugin"
        }
    }
}
