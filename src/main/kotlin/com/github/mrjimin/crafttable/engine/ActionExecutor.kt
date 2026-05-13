package com.github.mrjimin.crafttable.engine

import com.github.mrjimin.crafttable.config.data.ActionData
import com.github.mrjimin.crafttable.config.data.ActionType
import com.github.mrjimin.crafttable.util.SoundUtil
import com.github.mrjimin.crafttable.util.toMMComponent
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object ActionExecutor {
    fun execute(player: Player, actions: List<ActionData>) {
        actions.forEach { action ->
            val value = action.value.replace("%player_name%", player.name)

            when (action.type) {
                ActionType.MESSAGE -> player.sendMessage(value.toMMComponent())
                ActionType.BROADCAST -> Bukkit.broadcast(value.toMMComponent())
                ActionType.COMMAND -> {
                    val command = if (value.startsWith("/")) value.substring(1) else value
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command)
                }
                ActionType.SOUND -> {
                    SoundUtil.parseSound(value)?.let { sound ->
                        player.playSound(player.location, sound, 1f, 1f)
                    }
                }
                ActionType.TITLE -> {
                    val split = value.split(";", limit = 2)
                    val title = split[0].toMMComponent()
                    val subTitle = if (split.size > 1) split[1].toMMComponent() else net.kyori.adventure.text.Component.empty()
                    player.showTitle(Title.title(title, subTitle))
                }
            }
        }
    }
}

fun String.toActionData(): ActionData {
    val split = this.split(":", limit = 2)
    val type = try {
        ActionType.valueOf(split[0].uppercase())
    } catch (e: Exception) {
        ActionType.MESSAGE
    }
    val value = if (split.size > 1) split[1].trim() else ""
    return ActionData(type, value)
}

fun List<String>.toActionDataList(): List<ActionData> = map { it.toActionData() }