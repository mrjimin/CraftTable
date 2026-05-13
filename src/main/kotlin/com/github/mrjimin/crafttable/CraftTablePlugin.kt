package com.github.mrjimin.crafttable

import com.github.mrjimin.crafttable.command.CommandHandler
import com.github.mrjimin.crafttable.command.register
import com.github.mrjimin.crafttable.inventory.CraftTableGUIListener
import com.github.mrjimin.crafttable.item.ItemManager
import org.bukkit.plugin.java.JavaPlugin

class CraftTablePlugin : JavaPlugin() {

    override fun onEnable() {
        ItemManager.init()
        server.pluginManager.registerEvents(CraftTableGUIListener(), this)
        CommandHandler().register("crafttable")
    }
}