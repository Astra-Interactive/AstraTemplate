plugins {
    kotlin("jvm")
    alias(libs.plugins.gradle.shadow)
//    alias(libs.plugins.gradle.buildconfig)
}

dependencies {
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.orm)
    // klibs
    implementation(klibs.klibs.kdi)
    // Velocity
    compileOnly(libs.minecraft.velocity.api)
    annotationProcessor(libs.minecraft.velocity.api)
}

//buildConfig {
//    className("BuildKonfig")
//    packageName(libs.versions.plugin.group.get())
//    fun buildConfigStringField(name: String, value: String) {
//        buildConfigField("String", name, "\"${value}\"")
//    }
////    buildConfigStringField("id", info.id)
////    buildConfigStringField("name", info.name)
////    buildConfigStringField("version", info.version)
////    buildConfigStringField("url", info.url)
////    buildConfigStringField("description", info.description)
////    info.authors.forEachIndexed { i, dev ->
////        buildConfigStringField("author_$i", dev)
////    }
//}
