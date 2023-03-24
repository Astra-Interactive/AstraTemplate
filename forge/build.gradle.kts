plugins {
//    id("net.minecraftforge.gradle")
    id("forge-resource-processor")
    id("basic-java")
    id("forge-shadow")
    id("com.github.johnrengelman.shadow")
}
minecraft {
    mappings("official", "1.19")
}
dependencies {
    minecraft("net.minecraftforge:forge:${libs.versions.forge.version.get()}")
}
