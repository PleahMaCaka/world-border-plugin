package io.github.pleahmacaka.example.gui.shop

import org.bukkit.Material

@Suppress("unused")
data class ShopItem(
    val page: Int,
    val material: Material,

    val name: String? = null, // will be used as display name
    var needs: Int,           // 가져올 아이템 수
    var amount: Int,           // 받는 확장권 수

    var loc: Int? = null      // 인벤에서의 인덱스
)