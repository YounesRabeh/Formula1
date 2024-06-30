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
  mainModule.set("it.unicam.cs")
  mainClass.set("it.unicam.cs.App")
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

sourceSets {
  main {
    java {
      setSrcDirs(listOf("src/main/java"))
    }
    resources {
      setSrcDirs(listOf("src/main/resources"))
    }
  }
}



// TASKS:
tasks.test {
  useJUnitPlatform()
}

tasks.named<Jar>("jar") {
  manifest {
    attributes["Main-Class"] = "it.unicam.cs.App"
  }
}

tasks.register<JavaExec>("runR") {
  group = "application"
  description = "Runs the application with javafx"
  classpath = sourceSets["main"].runtimeClasspath
  mainClass.set(application.mainClass)
  jvmArgs = listOf("--module-path", classpath.asPath, "--add-modules", "javafx.controls,javafx.fxml")
  workingDir = projectDir // Ensures the working directory is set to the project directory
  args = listOf() // If you need to pass arguments, add them here
}