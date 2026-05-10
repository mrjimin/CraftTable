package com.github.mrjimin.crafttable.item

import org.bukkit.inventory.ItemStack

interface ItemSource {
    val plugin: String

    fun build(id: String): ItemStack?
    fun id(itemStack: ItemStack): String

    fun matches(itemStack: ItemStack): Boolean
}