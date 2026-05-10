package com.github.mrjimin.crafttable.inventory

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType

interface GUI {
    fun title(): Component
    fun rows(): Int = 3
    fun type(): InventoryType = InventoryType.CHEST

    fun setup(holder: CraftTableGUIHolder) {}

    fun onClick(event: InventoryClickEvent, player: Player) {}
    fun onPlayerInventoryClick(event: InventoryClickEvent, player: Player) {}

    fun onDrag(event: InventoryDragEvent, player: Player) {}

    fun onOpen(event: InventoryOpenEvent, player: Player) {}
    fun onClose(event: InventoryCloseEvent, player: Player) {}

    fun open(player: Player) {
        val holder = CraftTableGUIHolder(this)
        setup(holder)
        player.openInventory(holder.inventory)
    }
}