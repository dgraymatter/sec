package me.andriefc.secj

import me.andriefc.secj.command.DecryptValueCommand
import me.andriefc.secj.command.EncryptConfigCommand
import me.andriefc.secj.command.EncryptValueCommand
import me.andriefc.secj.command.GenerateKeyPairCommand
import me.andriefc.secj.common.cli.service.CommandFactory
import me.andriefc.secj.common.cli.service.registerCommonConverters
import me.andriefc.secj.common.cli.service.registerExceptionHandlers
import picocli.CommandLine
import picocli.CommandLine.*
import kotlin.system.exitProcess

@Command(
    name = "sec",
    description = ["Sec configuration companion to the excellent Palantir library."],
    mixinStandardHelpOptions = true,
    subcommands = [
        HelpCommand::class,
        GenerateKeyPairCommand::class,
        EncryptValueCommand::class,
        DecryptValueCommand::class,
        EncryptConfigCommand::class,
    ]
)
object App {

    @JvmStatic
    fun main(args: Array<String>) {
        exitProcess(
            CommandLine(App, CommandFactory())
                .setExpandAtFiles(true)
                .setCaseInsensitiveEnumValuesAllowed(true)
                .setInterpolateVariables(true)
                .registerCommonConverters()
                .registerExceptionHandlers()
                .execute(* args)
        )
    }
}
