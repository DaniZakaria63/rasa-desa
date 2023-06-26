package com.shapeide.rasadesa.domains

data class Search(
    val namespace: String = "meal",
    val id: String,
    val text: String,
    val is_local: Boolean = false
)