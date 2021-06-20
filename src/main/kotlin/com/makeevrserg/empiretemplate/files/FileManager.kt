import com.makeevrserg.empiretemplate.EmpireTemplate.Companion.plugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.lang.IllegalArgumentException

public class FileManager(var configName: String) {

    private var configFiles: File? = null
    private var dataConfig: FileConfiguration? = null

    init {
        saveDefaultConfig()
    }


    private fun reloadConfig() {
        if (this.configFiles == null) this.configFiles = File(plugin.dataFolder, configName)
        dataConfig = YamlConfiguration.loadConfiguration(configFiles!!)
        val defaultStream = plugin.getResource(configName)
        if (defaultStream != null) {
            val defaultConfig = YamlConfiguration.loadConfiguration(InputStreamReader(defaultStream))
            this.dataConfig?.setDefaults(defaultConfig)
        }
    }

    fun getName(): String? {
        return configName
    }


    fun getConfig(): FileConfiguration? {
        if (this.dataConfig == null) reloadConfig()
        return this.dataConfig
    }

    fun LoadFiles() {
        configFiles = File(plugin.dataFolder, configName)
    }

    fun updateConfig(conf: FileConfiguration) {
        this.dataConfig = conf
    }

    fun saveConfig() {
        if (this.configFiles == null || this.dataConfig == null) return
        try {
            getConfig()?.save(this.configFiles!!)
        } catch (e: IOException) {
            //println("${plugin.empireTranslations.SAVE_ERROR} $configName")
        }
    }

    private fun saveDefaultConfig() {
        if (this.configFiles == null) this.configFiles = File(plugin.dataFolder, configName)
        try {
            if (!this.configFiles!!.exists()) plugin.saveResource(configName, false)
        } catch (e: IllegalArgumentException) {
            //println(plugin.empireTranslations.NONSTANDART_FILE)
        }
    }
}