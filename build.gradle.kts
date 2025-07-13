plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.Steveice10:MCProtocolLib:1.19-1")
}

application {
    mainClass.set("AFKBot")
}
