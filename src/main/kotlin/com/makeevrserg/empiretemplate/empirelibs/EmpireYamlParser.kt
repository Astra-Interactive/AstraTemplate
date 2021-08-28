package empirelibs

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.makeevrserg.empiretemplate.EmpireTemplate
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import java.lang.Exception
import java.lang.reflect.Type


/**
 * Converting yaml file to .json format
 *
 * It allows you to use GSON with yaml
 */
class EmpireYamlParser {
    companion object {
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


        private fun <T> fromJson(map: Map<String, Any>?, type: Type, paths: List<String>?): T? {

            var json = JsonParser().parse(Gson().toJson(map, LinkedHashMap::class.java))
            val gson = GsonBuilder().serializeNulls().create()
            return if (paths != null) {
                for (path in paths)
                    if (json.isJsonObject)
                        json = json.asJsonObject.get(path)

                gson.fromJson(json, type)
            } else
                gson.fromJson(json, type)

        }

        private fun <T> returnTry(
            map: MutableMap<String, Any>?,
            type: Type,
            paths: List<String>?,
            file: String
        ): T? {
            return try {
                fromJson(map, type, paths)
            } catch (e: Exception) {
                println("${EmpireTemplate.translations.FILE_WRONG_PARSE} ?${file}")
                println(e.stackTraceToString())
                return null
            }
        }

        public fun <T> fromYAML(file: FileConfiguration?, type: Type, paths: List<String>? = null): T? {
            file ?: return null
            val map = getMap(file)
            return returnTry<T>(map, type, paths, file.name)
        }


        public fun <T> fromYAML(section: ConfigurationSection?, type: Type, paths: List<String>? = null): T? {
            section ?: return null
            val map = getConfSection(section)
            println(map)
            return returnTry<T>(map, type, paths, section.name)
        }

    }
}
