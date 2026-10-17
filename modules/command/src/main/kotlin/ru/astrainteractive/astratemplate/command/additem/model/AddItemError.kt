package ru.astrainteractive.astratemplate.command.additem.model

import ru.astrainteractive.astralibs.command.api.exception.CommandException

internal sealed class AddItemError(message: String) : CommandException(message) {
    class SenderNotPlayer : AddItemError("SenderNotPlayer")
    class ItemNotFound : AddItemError("ItemNotFound")
}
