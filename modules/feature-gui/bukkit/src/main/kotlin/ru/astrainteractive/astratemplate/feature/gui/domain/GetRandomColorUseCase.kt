package ru.astrainteractive.astratemplate.feature.gui.domain

import org.bukkit.ChatColor
import ru.astrainteractive.astratemplate.feature.gui.domain.GetRandomColorUseCase.Output
import kotlin.random.Random

internal interface GetRandomColorUseCase {
    class Output(val color: ChatColor)

    fun invoke(): Output
}

internal class GetRandomColorUseCaseImpl : GetRandomColorUseCase {
    override fun invoke(): Output {
        return ChatColor.entries[Random.nextInt(ChatColor.entries.size)].let(::Output)
    }
}
