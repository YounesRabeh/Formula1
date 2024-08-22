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

jlink {
  launcher {
    name = "Formula 1"
  }
  imageZip.set(layout.buildDirectory.file("image-zip/Formula1.zip"))
  options.set(listOf(
    "--strip-debug",
    "--no-man-pages",
    "--no-header-files",
    "--add-modules=java.base,javafx.controls,javafx.fxml,jdk.jdwp.agent"  // Adding the required modules here
  ))
  imageDir.set(layout.buildDirectory.dir("jlink"))
  imageName.set("Formula1")
  moduleName.set("it.unicam.cs")

  launcher {
    name = "app"
  }

  jpackage {
    imageOptions.addAll(listOf("--resource-dir", "src/main/resources"))
    installerOptions.addAll(listOf("--resource-dir", "src/main/resources"))
    installerType = "app-image"
  }
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



