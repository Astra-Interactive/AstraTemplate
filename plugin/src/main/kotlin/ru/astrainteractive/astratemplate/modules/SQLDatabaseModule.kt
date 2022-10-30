package ru.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.domain.local.SQLDatabase
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.di.IModule
import ru.astrainteractive.astralibs.di.module
import java.io.File

/**
 * There's two different ways to instantiate a module
 */
//val SQLDatabaseModule = module {
//    SQLDatabase("${AstraLibs.instance.dataFolder}${File.separator}data.db")
//}
object SQLDatabaseModule : IModule<SQLDatabase>() {
    override fun initializer(): SQLDatabase = SQLDatabase("${AstraLibs.instance.dataFolder}${File.separator}data.db")
}

