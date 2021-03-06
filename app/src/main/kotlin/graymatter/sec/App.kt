package graymatter.sec

import graymatter.sec.command.*
import graymatter.sec.common.cli.CommandFactory
import graymatter.sec.common.cli.ToolVersionProvider
import graymatter.sec.common.crypto.BinaryEncoding
import graymatter.sec.common.document.DocumentFormat
import graymatter.sec.common.exception.CommandFailedException
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.HelpCommand
import kotlin.system.exitProcess

@Command(
    name = "sec",
    description = [
        "SEC is a configuration companion to Palantir library for handling encrypting/decryption",
        "of various configuration file formats such as YAML, JSON and Java Properties."],
    versionProvider = ToolVersionProvider::class,
    mixinStandardHelpOptions = true,
    subcommands = [
        HelpCommand::class,
        GenerateKey::class,
        EncryptValue::class,
        DecryptValue::class,
        EncryptConfig::class,
        DecryptConfig::class,
        ConvertConfig::class,
        GenerateRandomBytes::class,
    ]
)
object App {

    @JvmStatic
    fun main(args: Array<String>) {
        exitProcess(createCommandLine().execute(* args))
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun createCommandLine(cmd: Any = this): CommandLine {
        return CommandLine(cmd, CommandFactory)
            .registerExceptionHandlers()
            .setExpandAtFiles(true)
            .setCaseInsensitiveEnumValuesAllowed(true)
            .setInterpolateVariables(true)
            .registerCommonConverters()
            .setUsageHelpWidth(150)
    }   

    private fun CommandLine.registerCommonConverters(): CommandLine = apply {
        registerConverter(BinaryEncoding::class.java) { BinaryEncoding.named(it) }
        registerConverter(DocumentFormat::class.java) { DocumentFormat.named(it) }
    }

    private fun CommandLine.registerExceptionHandlers(): CommandLine {
        exitCodeExceptionMapper = CommandLine.IExitCodeExceptionMapper {
            (it as? CommandFailedException)?.exitCode ?: CommandLine.ExitCode.SOFTWARE
        }
        return this
    }

}
