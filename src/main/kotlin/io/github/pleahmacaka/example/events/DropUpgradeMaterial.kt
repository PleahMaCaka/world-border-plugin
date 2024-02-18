package io.github.pleahmacaka.example.events

import io.github.pleahmacaka.example.GameManager
import io.github.pleahmacaka.example.items.UpgradeMaterial
import org.bukkit.entity.EntityType
import org.bukkit.entity.HumanEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

object DropUpgradeMaterial : Listener {

    // Drop 5/100 chance of dropping upgrade material (UpgradeMaterial.itemStack), count is 1-3
    // when the kill aggressively by player not only one mob
    @EventHandler
    fun dropUpgradeMaterial(event: EntityDeathEvent) {
        if (event.entity.killer !is HumanEntity) return
        // if not an animals
        for (animal in setOf(
            EntityType.COW,
            EntityType.PIG,
            EntityType.SHEEP,
            EntityType.CHICKEN,
            EntityType.RABBIT,
            EntityType.HORSE,
            EntityType.DONKEY,
            EntityType.MULE,
            EntityType.LLAMA,
            EntityType.POLAR_BEAR,
            EntityType.WOLF,
            EntityType.OCELOT,
            EntityType.PARROT,
            EntityType.TURTLE,
            EntityType.FOX,
            EntityType.BEE,
            EntityType.CAT,
            EntityType.PANDA,
        )) if (event.entity.type == animal) return

        if (Math.random() < GameManager.dropMultiplier / 100.0)
            event.drops.add(UpgradeMaterial.itemStack.clone().apply { amount = (Math.random() * 3).toInt() + 1 })
    }
}