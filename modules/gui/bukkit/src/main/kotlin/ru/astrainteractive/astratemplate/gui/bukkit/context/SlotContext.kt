package ru.astrainteractive.astratemplate.gui.bukkit.context

import ru.astrainteractive.astratemplate.core.plugin.model.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue
import java.util.Locale

internal class SlotContext(
    translationKrate: CachedKrate<PluginTranslation>,
    val locale: Locale
) {
    val translation by translationKrate
}
