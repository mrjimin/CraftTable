package com.github.mrjimin.crafttable.engine

import com.github.mrjimin.crafttable.config.data.ConditionType
import com.github.mrjimin.crafttable.config.data.CraftRecipe
import com.github.mrjimin.crafttable.config.data.RequirementType
import com.github.mrjimin.crafttable.item.ItemManager
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class CraftingSession(
    private val plugin: JavaPlugin,
    private val player: Player,
    private val recipe: CraftRecipe
) {
    private var isCancelled = false

    fun start() {
        if (!recipe.enabled) return

        if (recipe.permission != null && !player.hasPermission(recipe.permission)) {
            player.sendMessage("§c권한이 부족합니다.")
            return
        }
        if (!checkAllRequirements()) return

        consumeRequirements()

        ActionExecutor.execute(player, recipe.process.startActions.map { it.toActionData() })

        val totalTicks = recipe.process.craftingTime.toLong()
        if (totalTicks <= 0) {
            finish()
            return
        }

        object : BukkitRunnable() {
            var elapsed = 0
            val startLoc = player.location.block.location

            override fun run() {
                if (!player.isOnline || isCancelled) {
                    cancel()
                    return
                }

//                if (player.location.block.location != startLoc) {
//                    player.sendMessage("§c움직임이 감지되어 제작이 취소되었습니다.")
//                    cancel()
//                    return
//                }

                if (++elapsed >= totalTicks) {
                    finish()
                    cancel()
                }
            }
        }.runTaskTimer(plugin, 0L, 1L)
    }

    private fun checkAllRequirements(): Boolean {
        val itemReqs = recipe.requirements.filter { it.type == RequirementType.ITEM }
        for (req in itemReqs) {
            val requiredId = req.material ?: continue
            if (!hasEnoughItems(requiredId, req.amount.toInt())) {
                player.sendMessage("재료가 부족합니다.")
                return false
            }
        }

        for (cond in recipe.conditions.filter { it.type == ConditionType.PLACEHOLDER }) {
            if (!evaluateCondition(cond.check ?: "")) {
                player.sendMessage("제작 조건을 충족하지 못했습니다.")
                return false
            }
        }
        return true
    }

    private fun hasEnoughItems(id: String, amount: Int): Boolean {
        var count = 0
        for (item in player.inventory.contents) {
            if (item == null || item.type.isAir) continue
            if (ItemManager.id(item).equals(id, ignoreCase = true)) {
                count += item.amount
            }
            if (count >= amount) return true
        }
        return false
    }

    private fun consumeRequirements() {
        recipe.requirements.forEach { req ->
            if (!req.consume) return@forEach

            when (req.type) {
                RequirementType.ITEM -> {
                    val targetId = req.material ?: return@forEach
                    var toRemove = req.amount.toInt()
                    val contents = player.inventory.contents

                    for (item in contents) {
                        if (item == null || item.type.isAir) continue
                        if (ItemManager.id(item).equals(targetId, ignoreCase = true)) {
                            if (item.amount <= toRemove) {
                                toRemove -= item.amount
                                item.amount = 0
                            } else {
                                item.amount -= toRemove
                                toRemove = 0
                            }
                        }
                        if (toRemove <= 0) break
                    }
                    player.inventory.contents = contents
                }
                RequirementType.MONEY -> { /* Vault 연동 */ }
                else -> {}
            }
        }
    }

    private fun evaluateCondition(expression: String): Boolean {
        val parsed = PlaceholderAPI.setPlaceholders(player, expression)
        val regex = Regex("""(-?\d+\.?\d*)\s*(>=|<=|>|<|==)\s*(-?\d+\.?\d*)""")
        val match = regex.find(parsed) ?: return false

        val (left, op, right) = match.destructured
        val lNum = left.toDouble()
        val rNum = right.toDouble()

        return when (op) {
            ">=" -> lNum >= rNum
            "<=" -> lNum <= rNum
            ">" -> lNum > rNum
            "<" -> lNum < rNum
            "==" -> lNum == rNum
            else -> false
        }
    }

    private fun finish() {
        val chanceValue = recipe.conditions.find { it.type == ConditionType.CHANCE }?.value?.toDoubleOrNull() ?: 1.0
        val isSuccess = Math.random() <= chanceValue

        val result = if (isSuccess) recipe.success else recipe.fail

        result.items.forEach { reward ->
            try {
                val item = ItemManager.build(reward.material).asQuantity(reward.amount)
                val leftovers = player.inventory.addItem(item)
                leftovers.values.forEach { player.world.dropItemNaturally(player.location, it) }
            } catch (e: Exception) {
                plugin.logger.warning("아이템 생성 실패: ${reward.material}")
            }
        }

        ActionExecutor.execute(player, result.actions)
    }
}