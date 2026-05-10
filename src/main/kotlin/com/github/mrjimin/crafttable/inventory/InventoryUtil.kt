package com.github.mrjimin.crafttable.inventory

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

object InventoryUtil {

    fun createInventory(holder: InventoryHolder, type: InventoryType, title: Component, rows: Int = 3): Inventory {
        return if (type == InventoryType.CHEST) {
            Bukkit.createInventory(holder, rows * 9, title)
        } else {
            Bukkit.createInventory(holder, type, title)
        }
    }

}