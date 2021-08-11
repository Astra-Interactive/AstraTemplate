package empirelibs

import com.google.gson.Gson
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import java.lang.reflect.Type


/**
 * Converting yaml file to .json format
 *
 * It allows you to use GSON with yaml
 */
class EmpireYamlParser{
    companion object{
        private fun getConfSection(cs: ConfigurationSection?): MutableMap<String, Any>? {
            cs ?: return null
            val map = mutableMapOf<String, Any>()
            for (key in cs.getKeys(false)) {
                if (cs.isConfigurationSection(key))
                    map[key] = getConfSection(cs.getConfigurationSection(key)) as Any
                else
                    map[key] = cs.get(key) as Any
            }
            return map
        }

        private fun getMap(fc: FileConfiguration?): MutableMap<String, Any>? {
            fc ?: return null
            val map = mutableMapOf<String, Any>()
            for (key in fc.getKeys(false)) {
                if (fc.isConfigurationSection(key)) {
                    map[key] = getConfSection(fc.getConfigurationSection(key)) as Any
                } else {
                    map[key] = fc.get(key) as Any
                }
            }

            return map
        }
        public fun <T> parseYamlConfig(file: FileConfiguration?, type: Type): T? {
            file?:return null
            val map = getMap(file)
            val json = Gson().toJson(map, LinkedHashMap::class.java)
            return Gson().fromJson(json, type)
        }
        public fun <T> parseYamlConfig(section: ConfigurationSection?, type: Type): T? {
            section?:return null
            val map = getConfSection(section)
            val json = Gson().toJson(map, LinkedHashMap::class.java)
            return Gson().fromJson(json, type)
        }

    }
}
