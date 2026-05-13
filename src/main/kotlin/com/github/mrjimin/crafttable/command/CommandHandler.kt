package com.github.mrjimin.crafttable.command

import com.github.mrjimin.crafttable.inventory.CraftTableGUI
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.Item
import xyz.xenondevs.invui.item.ItemBuilder
import xyz.xenondevs.invui.window.Window

class CommandHandler : Command("crafttable") {

    init {
        aliases = listOf("ct")
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) return true

//        val item = sender.inventory.itemInMainHand
//
//        val id = ItemHandler.id(item)
//        val isItem = ItemHandler.build(id)
//
//        sender.sendMessage("Item ID: $id")
//        sender.give(isItem)
//        sender.sendMessage("Item is: $isItem")
        CraftTableGUI().open(sender)

        return true
    }

}