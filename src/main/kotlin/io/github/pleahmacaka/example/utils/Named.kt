package io.github.pleahmacaka.example.utils

import net.kyori.adventure.text.Component


@Suppress("unused")
sealed class Named {
    data class Text(val text: String) : Named()
    data class Comp(val component: Component) : Named()
}