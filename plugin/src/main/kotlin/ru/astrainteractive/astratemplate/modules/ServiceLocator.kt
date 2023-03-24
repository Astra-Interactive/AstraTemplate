package ru.astrainteractive.astratemplate.modules

import CommandManager
import com.astrainteractive.astratemplate.api.local.DatabaseFactory
import com.astrainteractive.astratemplate.api.local.LocalApiFactory
import com.astrainteractive.astratemplate.api.remote.RickMortyApiFactory
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.EmpireSerializer
import ru.astrainteractive.astralibs.di.factory
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.di.module
import ru.astrainteractive.astralibs.di.reloadable
import ru.astrainteractive.astralibs.utils.toClass
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.events.EventManager
import ru.astrainteractive.astratemplate.gui.SampleGUI
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import ru.astrainteractive.astratemplate.modules.factories.CustomConfigurationFactory
import ru.astrainteractive.astratemplate.plugin.Files
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation
import java.io.File

object ServiceLocator {
    val configurationModule = reloadable {
        EmpireSerializer.toClass<MainConfiguration>(Files.configFile) ?: MainConfiguration()
    }
    val translationModule = reloadable {
        Translation()
    }

    val rmApiModule = module {
        RickMortyApiFactory().value
    }

    val databaseModule = module {
        DatabaseFactory("${AstraLibs.instance.dataFolder}${File.separator}data.db").value
    }

    val localApiModule = module {
        val database by databaseModule
        LocalApiFactory(database).value
    }

    val eventHandlerModule = module {
        EventManager(translationModule = translationModule)
    }

    val commandManager = module {
        CommandManager(
            translationModule = translationModule,
            rmApiModule = rmApiModule,
            guiFactories = Guis
        )
    }
    val customConfiguration = reloadable {
        CustomConfigurationFactory.value
    }

    object ViewModels {
        val SampleGuiViewModelFactory = factory {
            val localApi by localApiModule
            SampleGUIViewModel(localApi, ItemStackSpigotAPI)
        }
    }

    object Guis {
        fun sampleGuiFactory(player: Player) = factory {
            SampleGUI(
                player = player,
                translationModule = translationModule,
                viewModel = ViewModels.SampleGuiViewModelFactory.value
            )
        }
    }
}
