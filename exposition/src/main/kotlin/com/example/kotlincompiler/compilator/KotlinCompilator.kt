package com.example.kotlincompiler.compilator

import com.example.kotlincompiler.mappers.randomValue
import com.example.kotlincompiler.mappers.runCommand
import com.example.kotlincompiler.runner.Compilator
import com.example.kotlincompiler.runner.CompilatorPaths
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Paths

@Component
class KotlinCompilator: Compilator {
    private val appRoot = Paths.get("").toAbsolutePath().toString()
    private val kotlinCompilatorPath = "compilator"

    private val pdkTemplate = File("$appRoot/$kotlinCompilatorPath/empty-pdk")
    private val gradleSettingsTemplate = File("$appRoot/$kotlinCompilatorPath/example.gradle")

    private val defaultBodyMobBehavior = "return MobAction(ActionType.WALK, Direction.UP)"
    private val mobBehaviorPath = "src/main/kotlin/com/esgi/kotlin_game/pdk_test/MobBehavior.kt"

    @Value("\${HOME_PLUGINS:/Users/simonhalimi/Development/2021/projets/jee/kotlin-compiler/plugins}")
    lateinit var HOME_PLUGINS: String

    override fun compileAndExecute(compilatorPaths: CompilatorPaths, turnObjective: Int, userId: String) {
        println("approot=$appRoot")
        ("gradle compilator:${compilatorPaths.moduleName}:build -c ${compilatorPaths.settingsGradleFileName} --no-daemon")
            .runCommand(File(appRoot))

        ("mkdir -p $appRoot/plugins/${compilatorPaths.moduleName}")
            .runCommand(File(appRoot))

        println(HOME_PLUGINS)

        ("ls $appRoot/$kotlinCompilatorPath/${compilatorPaths.moduleName}/build/libs/")
            .runCommand(File(appRoot))

        ("mv $appRoot/$kotlinCompilatorPath/${compilatorPaths.moduleName}/build/libs/plugin.jar $appRoot/plugins/${compilatorPaths.moduleName}")
            .runCommand(File(appRoot))

        ("docker run --rm --env-file .env --network ${System.getenv("RABBIT_NETWORK") ?: "setted-network"} " +
                "--env TURN_OBJECTIVE=$turnObjective --env USER_ID=$userId " +
                "--mount type=bind,source=$HOME_PLUGINS/${compilatorPaths.moduleName},target=/app/plugins" +
                " --name ${compilatorPaths.moduleName} kotlin-game/api").runCommand(File(appRoot))
    }

    override fun buildEntrypoint(): CompilatorPaths {
        val newModuleName = createDirectory()
        val newSettingGradleName = createSettingsFile(newModuleName)

        return CompilatorPaths(newSettingGradleName, newModuleName)
    }

    override fun clean(compilatorPaths: CompilatorPaths) {
//        val directoryPathToDelete = File("$appRoot/$kotlinCompilatorPath/${compilatorPaths.moduleName}")
//        directoryPathToDelete.deleteRecursively()
//
//        val gradleFileToDelete = File("$appRoot/${compilatorPaths.settingsGradleFileName}")
//        gradleFileToDelete.delete()
//
//        val pluginToDelete = File("$appRoot/plugins/${compilatorPaths.moduleName}")
//        pluginToDelete.delete(
    }

    private fun createSettingsFile(newModuleName: String): String {
        val newGradleName = "$newModuleName.gradle.kts"
        val directoryPath = "$appRoot/$newGradleName"
        val settingsFileToCreate = File(directoryPath)

        gradleSettingsTemplate.copyTo(
            target = settingsFileToCreate,
            overwrite = true
        )

        val newGradleFile = File(directoryPath)
        newGradleFile.appendText("include(\":$kotlinCompilatorPath:$newModuleName\")")

        return newGradleName
    }

    private fun createDirectory(): String {
        val directoryName = "".randomValue(10)
        val path = "$appRoot/$kotlinCompilatorPath/$directoryName"
        val directoryToCreateIn = File(path)

        pdkTemplate.copyRecursively(
            target = directoryToCreateIn,
            overwrite = true,
        )

        return directoryName
    }

    override fun addUserCode(userCode: String, compilatorPaths: CompilatorPaths) {
        val fileToModify = File("$appRoot/$kotlinCompilatorPath/${compilatorPaths.moduleName}/$mobBehaviorPath")
        println(userCode)
        var mobBehaviorFileContent = fileToModify.readText()
        var fileContent = mobBehaviorFileContent.replace(defaultBodyMobBehavior, userCode)

        fileToModify.writeText(fileContent)
    }
}