package eu.appcom.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class TwinePlugin implements Plugin<Project> {

    final static String VERSION_0_10 = "0.10.0"

    static String twineVersion
    static String sourcePath
    static String outputPath
    static boolean validate

    void apply(Project project) {
        project.extensions.add("twineplugin", TwinePluginExtension)

        Task generateStrings = project.tasks.create("generateStrings") {
            doLast {
                setTwineVersion()
                setVariables(project)
                runTwineScript()
            }
        }
        generateStrings.group = "appcom"
        generateStrings.description = "Generates string values from localisation file."

        Task printTwineVersion = project.tasks.create("printTwineVersion") {
            doLast {
                setTwineVersion()
            }
        }
        printTwineVersion.group = "appcom"
        printTwineVersion.description = "Prints installed twine version."
    }

    static void setTwineVersion() {
        twineVersion = null
        ["bash", "-c", "gem list"].execute().text.eachLine {
            line ->
                if (line.toLowerCase().startsWith("twine (")) {
                    twineVersion = line.substring(line.indexOf("(") + 1, line.indexOf(")"))
                }
        }
        if (twineVersion == null) {
            throw new AssertionError("Twine not found!")
        } else {
            println 'twine version "' + twineVersion + '"'
        }
    }

    static void setVariables(Project project) {
        if (project.extensions.twineplugin.inputFilePath != null) {
            sourcePath =
                project.rootDir.getAbsolutePath() + (project.extensions.twineplugin.inputFilePath.startsWith("/") ? "" :
                    "/") + project.extensions.twineplugin.inputFilePath
        } else {
            sourcePath = project.rootDir.getAbsolutePath() + "/localisation/localisation.txt"
        }
        if (project.extensions.twineplugin.outputResPath != null) {
            outputPath =
                project.rootDir.getAbsolutePath() + (project.extensions.twineplugin.outputResPath.startsWith("/") ? "" :
                    "/") + project.extensions.twineplugin.outputResPath
        } else {
            outputPath = project.rootDir.getAbsolutePath() + "/app/src/main/res"
        }
        if (project.extensions.twineplugin.validate != null) {
            validate = project.extensions.twineplugin.validate
        } else {
            validate = true
        }
    }

    static void runTwineScript() {
        String script
        String command
        if (isAtLeast(twineVersion, VERSION_0_10)) {
            command = "generate-all-localization-files"
        } else {
            command = "generate-all-string-files"
        }
        script = command + " " + sourcePath + " " + outputPath + (validate ? " --validate" : "") + " --format android"
        println script
        script = "if hash twine 2>/dev/null; then twine " + script + "; fi"

        Process process = ["sh", "-c", script].execute()
        process.waitFor()
        String error = process.errorStream.text

        if (process.exitValue() != 0 || !error.isEmpty()) {
            throw new AssertionError(error)
        }
    }

    static boolean isAtLeast(String verA, String verB) {
        String[] verTokenA = verA.tokenize(".")
        String[] verTokenB = verB.tokenize(".")
        int commonIndices = Math.min(verTokenA.size(), verTokenB.size())

        for (int i = 0; i < commonIndices; ++i) {
            if (verTokenA[i].toInteger() > verTokenB[i].toInteger()) {
                return true
            }
        }
        return verTokenA.size() >= verTokenB.size()
    }
}