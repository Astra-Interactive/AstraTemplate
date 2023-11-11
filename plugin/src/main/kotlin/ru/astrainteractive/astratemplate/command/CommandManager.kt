import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.command.additem.AddItemCommandFactory
import ru.astrainteractive.astratemplate.command.damage.DamageCommandFactory
import ru.astrainteractive.astratemplate.command.di.CommandManagerDependencies
import ru.astrainteractive.astratemplate.command.reload.ReloadCommandFactory
import ru.astrainteractive.astratemplate.command.rickmorty.RandomRickAndMortyCommandFactory
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
        AddItemCommandFactory(
            plugin = plugin
        ).create()
        DamageCommandFactory(
            plugin = plugin,
            translation = translation,
            translationContext = this
        ).create()
        RandomRickAndMortyCommandFactory(
            plugin = plugin,
            scope = pluginScope,
            dispatchers = dispatchers,
            rmApi = rmApi,
            randomIntProvider = randomIntProvider
        ).create()
        ReloadCommandFactory(
            plugin = plugin,
            translation = translation,
            translationContext = this
        ).create()
        ReloadCommandFactory(
            plugin = plugin,
            translation = translation,
            translationContext = this
        ).create()
        tabCompleter()
        translation()
        tempGUI()
    }
}
