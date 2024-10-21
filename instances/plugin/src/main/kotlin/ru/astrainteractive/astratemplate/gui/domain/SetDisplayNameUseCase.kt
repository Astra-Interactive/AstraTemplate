package ru.astrainteractive.astratemplate.gui.domain

import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astratemplate.gui.domain.SetDisplayNameUseCase.Input
import ru.astrainteractive.astratemplate.gui.domain.SetDisplayNameUseCase.Output

internal interface SetDisplayNameUseCase {
    class Input(val items: List<ItemStack>, val index: Int)
    class Output(val items: List<ItemStack>)

    fun invoke(input: Input): Output
}

internal class SetDisplayNameUseCaseImpl(
    private val getRandomColorUseCase: GetRandomColorUseCase
) : SetDisplayNameUseCase {
    override fun invoke(input: Input): Output {
        val list = input.items.toMutableList()
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
