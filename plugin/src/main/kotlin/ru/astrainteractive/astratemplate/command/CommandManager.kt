import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.command.additem.AddItemCommand
import ru.astrainteractive.astratemplate.command.damage.DamageCommand
import ru.astrainteractive.astratemplate.command.di.CommandManagerDependencies
import ru.astrainteractive.astratemplate.command.reload.ReloadCommand
import ru.astrainteractive.astratemplate.command.rickmorty.RandomRickAndMortyCommand
import ru.astrainteractive.astratemplate.command.tabCompleter
import ru.astrainteractive.astratemplate.command.tempGUI
import ru.astrainteractive.astratemplate.command.translation

/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 */
class CommandManager(
    module: CommandManagerDependencies
) : CommandManagerDependencies by module,
    BukkitTranslationContext by module.bukkitTranslationContext {

    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        AddItemCommand().register(plugin)
        DamageCommand(
            translation = translation,
            permissionManager = permissionManager,
            translationContext = this
        ).register(plugin)
        RandomRickAndMortyCommand(
            scope = pluginScope,
            dispatchers = dispatchers,
            rmApi = rmApi,
            randomIntProvider = randomIntProvider
        ).register(plugin)
        ReloadCommand(
            permissionManager = permissionManager,
            translation = translation,
            translationContext = this
        ).register(plugin)
        ReloadCommand(
            permissionManager = permissionManager,
            translation = translation,
            translationContext = this
        ).register(plugin)
        tabCompleter()
        translation()
        tempGUI()
    }
}
