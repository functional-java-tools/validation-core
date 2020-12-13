plugins {
    `java-library`
    id("com.github.spotbugs") version "4.6.0"
    jacoco
}

repositories {
    jcenter()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
}


tasks {

    spotbugsMain {
        reports {
            register("html") {
                isEnabled = true
            }
            register("xml") {
                isEnabled = false
            }
        }
    }

    spotbugsTest {
        reports {
            register("html") {
                isEnabled = true
            }
            register("xml") {
                isEnabled = false
            }
        }
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }
    }

    jacocoTestCoverageVerification {
        dependsOn(jacocoTestReport)

        violationRules {
            rule {
                limit {
                    counter = "INSTRUCTION"
                    minimum = "0.8".toBigDecimal()
                }
                limit {
                    counter = "BRANCH"
                    minimum = "0.8".toBigDecimal()
                }
            }
        }
    }

    check {
        dependsOn(jacocoTestCoverageVerification)
    }
}





