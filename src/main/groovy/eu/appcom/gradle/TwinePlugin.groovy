package eu.appcom.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class TwinePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        def generateStrings = project.tasks.create("twine_generateStrings") {
            doLast {
                runTwineScript()
            }
        }
        generateStrings.group = "appcom"
        generateStrings.description = "Generates string values from twine.txt file."

        def printTwineInfo = project.tasks.create("twine_printVersion") {
            doLast {
                println "Twine Version: " + getTwineVersion()
            }
        }
        printTwineInfo.group = "appcom"
        printTwineInfo.description = "Prints twine information."
    }

    static def getTwineVersion() {
        def script = ["bash", "-c", "twine --version"].execute()
        String version = script.text
        Float v
        if (!version.isEmpty()) {
            v = new Float(version.replace("Twine version 0.", ""))
        } else {
            throw new AssertionError("Twine not found!")
        }
        return v
    }

    static def runTwineScript() {
        String script
        if (getTwineVersion() >= 10.0) {
            println "Use twine version newer than 0.9 to generate Strings"
            script =
                    "if hash twine 2>/dev/null; then twine generate-all-localization-files ../twine/localisation.txt ../app/src/main/res; fi"
        } else {
            println "Use twine version older than 0.10 to generate Strings"
            script =
                    "if hash twine 2>/dev/null; then twine generate-all-string-files ../twine/localisation.txt ../app/src/main/res; fi"
        }
        exec {
            executable "sh"
            args '-c', script
        }
    }
}


