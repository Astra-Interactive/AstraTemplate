package com.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.domain.local.SQLDatabase
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.di.IModule
import java.io.File

object SQLDatabaseModule : IModule<SQLDatabase>() {
    override fun initializer(): SQLDatabase = SQLDatabase("${AstraLibs.instance.dataFolder}${File.separator}data.db")
}