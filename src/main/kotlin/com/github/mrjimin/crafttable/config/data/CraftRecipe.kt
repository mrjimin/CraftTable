package com.github.mrjimin.crafttable.config.data

import net.kyori.adventure.text.Component

data class CraftRecipe(
    val id: String,
    val enabled: Boolean,
    val group: String,
    val permission: String?,
    val display: DisplayData,
    val requirements: List<RequirementData>,
    val conditions: List<ConditionData>,
    val process: ProcessData,
    val success: ResultData,
    val fail: ResultData
)

data class DisplayData(
    val material: String,
    val amount: Int,
    val name: Component,
    val lore: List<Component>
)

data class RequirementData(
    val type: RequirementType,
    val material: String? = null,
    val amount: Double = 0.0,
    val consume: Boolean = true,
    val name: String? = null
)

data class ConditionData(
    val type: ConditionType,
    val world: String? = null,
    val center: String? = null,
    val range: Int? = null,
    val check: String? = null,
    val value: String? = null
)

data class ProcessData(
    val craftingTime: Int,
    val startActions: List<String>
)