package com.github.mrjimin.crafttable.config.data

enum class RequirementType { ITEM, MONEY, VAULT_CURRENCY }
enum class ConditionType { RADIUS, PLACEHOLDER, WEATHER, CHANCE }
enum class ActionType { MESSAGE, SOUND, COMMAND, TITLE, BROADCAST }

data class ActionData(
    val type: ActionType,
    val value: String
)

data class ItemRewardData(
    val material: String,
    val amount: Int = 1
)

data class ResultData(
    val items: List<ItemRewardData>,
    val actions: List<ActionData>
)