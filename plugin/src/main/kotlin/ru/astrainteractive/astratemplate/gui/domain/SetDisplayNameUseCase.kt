package ru.astrainteractive.astratemplate.gui.domain

import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astratemplate.gui.domain.SetDisplayNameUseCase.Input
import ru.astrainteractive.astratemplate.gui.domain.SetDisplayNameUseCase.Output
import ru.astrainteractive.klibs.mikro.core.domain.UseCase

interface SetDisplayNameUseCase : UseCase.Blocking<Input, Output> {
    class Input(val items: List<ItemStack>, val index: Int)
    class Output(val items: List<ItemStack>)
}

class SetDisplayNameUseCaseImpl(
    private val getRandomColorUseCase: GetRandomColorUseCase
) : SetDisplayNameUseCase {
    override fun invoke(input: Input): Output {
        val list = input.items.toMutableList()
        println("Index: ${input.index}")
        val item = list.getOrNull(input.index)?.clone()?.apply {
            editMeta {
                val color = getRandomColorUseCase.invoke().color.toString()
                it.setDisplayName(color + this.type.name)
            }
        } ?: return list.let(::Output)
        list[input.index] = item
        return list.let(::Output)
    }
}
