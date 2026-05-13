package com.github.mrjimin.crafttable.item

import org.bukkit.inventory.ItemStack

interface ItemProvider {
    fun build(id: String): ItemStack?
    fun id(itemStack: ItemStack): String
    fun matches(itemStack: ItemStack): Boolean

    fun identifier(): String
}