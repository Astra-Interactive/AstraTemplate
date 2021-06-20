package com.makeevrserg.empiretemplate.menumanager

import com.makeevrserg.empiretemplate.EmpireTemplate.Companion.plugin
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack

class MenuConfig {


    public val itemNavigationMap = mutableMapOf<String, ItemStack>()
    private fun initItemNavigation(confSection: ConfigurationSection): Boolean {
        for (itemKey in confSection.getKeys(false)) {
            val itemSection = confSection.getConfigurationSection(itemKey)!!
            val itemStack =
                ItemStack(Material.getMaterial(itemSection.getString("material") ?: return false) ?: return false)
            val itemMeta = itemStack.itemMeta
            itemMeta.setDisplayName(itemSection.getString("name"))
            itemMeta.lore = itemSection.getStringList("lore")
            itemMeta.setCustomModelData(itemSection.getInt("custom_model_data"))
            itemStack.setItemMeta(itemMeta)
            itemNavigationMap[itemKey] = itemStack
        }
        return true
    }

    private fun initMenuConfig() {
        val configFile = plugin.empireFiles.menuConfigFile.getConfig()?.defaultSection ?: return
        initItemNavigation(configFile.getConfigurationSection("item_navigation") ?: return)
    }

    init {
        initMenuConfig()
    }

    public fun onDisable() {

    }
}