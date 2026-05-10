package com.github.mrjimin.crafttable.inventory

import com.github.mrjimin.crafttable.util.toMMComponent
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

class CraftTableGUI : GUI {
    override fun title(): Component = "<blue>Test".toMMComponent()
    override fun type(): InventoryType = InventoryType.CHEST
    override fun rows(): Int = 6

    override fun setup(holder: CraftTableGUIHolder) {
        val inventory = holder.inventory
        inventory.setItem(0, ItemStack(Material.DIAMOND))
    }

    override fun onClick(event: InventoryClickEvent, player: Player) {
        event.isCancelled = true

        val slot = event.rawSlot
        when (slot) {
            0 -> player.sendMessage("You clicked the Diamond!")
        }
    }

    override fun onPlayerInventoryClick(event: InventoryClickEvent, player: Player) {
        event.isCancelled = true

        val slot = event.slot
        when (slot) {
            0 -> player.sendMessage("You clicked player inventory!")
        }
    }

    override fun onClose(event: InventoryCloseEvent, player: Player) {
        player.sendMessage("You close")
    }

    override fun onOpen(event: InventoryOpenEvent, player: Player) {
        player.sendMessage("You open")
    }
}