package com.github.mrjimin.crafttable.inventory

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class CraftTableGUIHolder(val gui: GUI) : InventoryHolder {
    private val inventory: Inventory = InventoryUtil.createInventory(this, gui.type(), gui.title(), gui.rows())

    override fun getInventory(): Inventory = inventory
}