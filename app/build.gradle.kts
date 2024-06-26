plugins {
  java
  application
  id("org.javamodularity.moduleplugin") version "1.8.12"
  id("org.openjfx.javafxplugin") version "0.0.13"
  id("org.beryx.jlink") version "2.25.0"
}


repositories {
  mavenCentral()
}

val junitVersion = "5.10.0"

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}

application {
  mainModule.set("it.unicam.cs.app")
  mainClass.set("it.unicam.cs.app.HelloApplication")
}

javafx {
  version = "21"
  modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
  implementation("org.controlsfx:controlsfx:11.1.2")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

}

tasks.test {
  useJUnitPlatform()
}