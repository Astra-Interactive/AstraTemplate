plugins {
    id("org.jetbrains.kotlin.jvm")
    id("ru.astrainteractive.klibs.rules.gradle.linter")
    id("ru.astrainteractive.gradleplugin.java.version")
}

linter {
    unit(ru.astrainteractive.klibs.rules.core.unit.model.UnitRole.StorageContract())
    rootPackage.set("ru.astrainteractive.astratemplate.data.dao")
}

dependencies {
    implementation(libs.kotlin.coroutines.core)

    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.tests.kotlin.test)
}
