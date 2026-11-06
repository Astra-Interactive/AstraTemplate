plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("ru.astrainteractive.klibs.rules.gradle.linter")
    id("ru.astrainteractive.gradleplugin.java.version")
}

linter {
    unit(ru.astrainteractive.klibs.rules.core.unit.model.UnitRole.Api())
    rootPackage.set("ru.astrainteractive.astratemplate.gui.api")
}

dependencies {
    implementation(libs.minecraft.astralibs.core)
}
