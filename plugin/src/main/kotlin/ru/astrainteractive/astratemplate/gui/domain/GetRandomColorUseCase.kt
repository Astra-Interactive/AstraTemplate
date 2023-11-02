package ru.astrainteractive.astratemplate.gui.domain

import org.bukkit.ChatColor
import ru.astrainteractive.astratemplate.gui.domain.GetRandomColorUseCase.Output
import ru.astrainteractive.klibs.mikro.core.domain.UseCase
import kotlin.random.Random

interface GetRandomColorUseCase : UseCase.Blocking.Simple<Output> {
    class Output(val color: ChatColor)
}

class GetRandomColorUseCaseImpl : GetRandomColorUseCase {
    override fun invoke(input: Unit): Output {
        return ChatColor.entries[Random.nextInt(ChatColor.entries.size)].let(::Output)
    }
}
