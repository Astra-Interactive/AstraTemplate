package ru.astrainteractive.astratemplate.command.additem.command

import ru.astrainteractive.astralibs.command.api.argumenttype.ArgumentConverter
import ru.astrainteractive.astratemplate.command.additem.model.AddItemError

internal data object ItemNameArgumentConverter : ArgumentConverter<String> {
    override fun transform(argument: String): String {
        return argument.takeIf(String::isNotBlank) ?: throw AddItemError.ItemNotFound()
    }
}
