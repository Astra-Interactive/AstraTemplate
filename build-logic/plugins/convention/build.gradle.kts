plugins {
    java
    `maven-publish`
    `java-library`
    `kotlin-dsl`
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.shadow) apply false
}
dependencies {
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.detekt.gradle)
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.kotlin.serialization)
    implementation(libs.shadow)
}
