package com.github.mrjimin.crafttable.inventory

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class CraftTableGUIListener : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onInventoryClick(event: InventoryClickEvent) {
        val holder = event.inventory.holder as? CraftTableGUIHolder ?: return
        val player = event.whoClicked as? Player ?: return
        val gui = holder.gui

        if (event.action == InventoryAction.COLLECT_TO_CURSOR) {
            event.isCancelled = true
            return
        }

        val clickedInv = event.clickedInventory ?: return

        if (clickedInv.holder is CraftTableGUIHolder) {
            event.isCancelled = true
            gui.onClick(event, player)
        } else {
            if (event.isShiftClick) {
                event.isCancelled = true
            }
            gui.onPlayerInventoryClick(event, player)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInventoryDrag(event: InventoryDragEvent) {
        val holder = event.inventory.holder as? CraftTableGUIHolder ?: return
        val player = event.whoClicked as? Player ?: return

        val topSize = event.view.topInventory.size
        val isGuiIncluded = event.rawSlots.any { it < topSize }

        if (isGuiIncluded) {
            event.isCancelled = true
            player.updateInventory()
            holder.gui.onDrag(event, player)
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onInventoryOpen(event: InventoryOpenEvent) {
        val holder = event.inventory.holder as? CraftTableGUIHolder ?: return
        val player = event.player as? Player ?: return
        holder.gui.onOpen(event, player)
    }

    @EventHandler(ignoreCancelled = true)
    fun onInventoryClose(event: InventoryCloseEvent) {
        val holder = event.inventory.holder as? CraftTableGUIHolder ?: return
        val player = event.player as? Player ?: return
        holder.gui.onClose(event, player)
    }

    @EventHandler(ignoreCancelled = true)
    fun onInventoryMove(event: InventoryMoveItemEvent) {
        if (event.source.holder is CraftTableGUIHolder || event.destination.holder is CraftTableGUIHolder) {
            event.isCancelled = true
        }
    }
}