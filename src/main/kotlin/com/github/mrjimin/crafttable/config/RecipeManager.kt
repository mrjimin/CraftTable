package com.github.mrjimin.crafttable.config

import com.github.mrjimin.crafttable.config.data.*
import com.github.mrjimin.crafttable.engine.toActionDataList
import com.github.mrjimin.crafttable.util.*
import org.bukkit.configuration.ConfigurationSection

class RecipeManager {

    fun loadRecipe(id: String, section: ConfigurationSection): CraftRecipe {
        return CraftRecipe(
            id = id,
            enabled = section.getBoolean("enabled"),
            group = section.getString("group", "DEFAULT")!!,
            permission = section.getString("permission"),
            display = DisplayData(
                material = section.getString("display.material", "STONE")!!,
                amount = section.getInt("display.amount", 1),
                name = section.getString("display.name", id)!!.toMMComponent(),
                lore = section.getStringList("display.lore").map { it.toMMComponent() }
            ),
            requirements = section.getSectionList("requirements").map {
                RequirementData(
                    type = RequirementType.valueOf(it.getString("type", "ITEM")!!.uppercase()),
                    material = it.getString("material"),
                    amount = it.getInt("amount", 0),
                    consume = it.getBoolean("consume", true),
                    name = it.getString("name")
                )
            },
            conditions = section.getSectionList("conditions").map {
                ConditionData(
                    type = ConditionType.valueOf(it.getString("type")!!.uppercase()),
                    world = it.getString("world"),
                    center = it.getString("center"),
                    range = it.getInt("range").takeIf { r -> r != 0 },
                    check = it.getString("check"),
                    value = it.getString("value")
                )
            },
            process = ProcessData(
                craftingTime = section.getInt("process.crafting_time"),
                startActions = section.getStringList("process.start_action")
            ),
            success = loadResult(section.getConfigurationSection("success")),
            fail = loadResult(section.getConfigurationSection("fail"))
        )
    }

    private fun loadResult(section: ConfigurationSection?): ResultData {
        if (section == null) return ResultData(emptyList(), emptyList())

        val items = section.getSectionList("items").map {
            ItemRewardData(
                material = it.getString("material", "STONE")!!,
                amount = it.getInt("amount", 1)
            )
        }

        val actions = section.getStringList("actions").toActionDataList()

        return ResultData(items, actions)
    }
}