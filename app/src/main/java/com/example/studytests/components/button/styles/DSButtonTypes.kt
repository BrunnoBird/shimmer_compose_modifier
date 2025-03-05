package com.example.studytests.components.button.styles

sealed class ButtonType {
    object Primary : ButtonType()
    object Secondary : ButtonType()
    object Icon : ButtonType()
    object Sealed : ButtonType()  // Botões empilhados verticalmente
    object Piled : ButtonType()   // Botões lado a lado
    object Action : ButtonType()  // Botão com ícone do lado esquerdo
    object Navigation : ButtonType()  // Botão com seta na direita
}