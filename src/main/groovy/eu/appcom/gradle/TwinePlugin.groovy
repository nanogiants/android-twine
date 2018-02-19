package eu.appcom.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class TwinePlugin implements Plugin < Project > {

    static String sourcePath = ""
    static String outputPath = ""

    @Override
    void apply(Project project) {

        sourcePath = project.getRootDir().path + "/localisation/localisation.txt"
        outputPath = project.getRootDir().path + "/app/src/main/res"

        def generateStrings = project.tasks.create("generateStrings") {
            doLast {
                runTwineScript()
            }
        }
        generateStrings.group = "appcom"
        generateStrings.description = "Generates string values from localisation.txt file."

        def printTwineInfo = project.tasks.create("printTwineVersion") {
            doLast {
                println "Twine Version: " + getTwineVersion()
            }
        }
        printTwineInfo.group = "appcom"
        printTwineInfo.description = "Prints twine information."
    }

    static def getTwineVersion() {
        def script = ["bash", "-c", "gem list"].execute()
        String version = ""
        script.text.eachLine {
            line ->
                if (line.contains("twine ")) {
                    version = line.substring(line.indexOf("(") + 1, line.indexOf(")"))
                }
        }
        if (version.isEmpty()) {
            throw new AssertionError("Twine not found!")
        }
        return version
    }

    static def runTwineScript() {
        String script
        if (greaterOrSame(getTwineVersion(), "0.10.0")) {
            if (greaterOrSame(getTwineVersion(), "1.0.0")) {
                println "Use twine version newer than 1.0.0 to generate Strings"
                script =
                    "if hash twine 2>/dev/null; then twine generate-all-localization-files" + sourcePath + " " + outputPath + "; fi"
            } else {
                println "Use twine version newer than 0.10.0 to generate Strings"
                script =
                    "if hash twine 2>/dev/null; then twine generate-all-localization-files" + sourcePath + " " + outputPath + "; fi"
            }
        } else {
            println "Use twine version older than 0.10.0 to generate Strings"
            script =
                "if hash twine 2>/dev/null; then twine generate-all-string-files" + sourcePath + " " + outputPath + "; fi"
        }
        ["sh", "-c", script].execute()
    }

    static boolean greaterOrSame(String verA, String verB) {
        String[] verTokenA = verA.tokenize(".")
        String[] verTokenB = verB.tokenize('.')
        int commonIndices = Math.min(verTokenA.size(), verTokenB.size())

        for (int i = 0; i < commonIndices; ++i) {
            if (verTokenA[i].toInteger() > verTokenB[i].toInteger()) {
                return true
            }
        }
        return verTokenA.size() >= verTokenB.size()
    }
}