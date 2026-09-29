package ru.astrainteractive.astratemplate.feature.command.additem

import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue

internal class AddItemExecutor(
    translationKrate: CachedKrate<PluginTranslation>
) {
    private val translation by translationKrate

    fun execute(input: AddItemCommand.Result) {
        input.player.sendMessage(translation.addItem.success(input.amount, input.itemName))
    }
}
