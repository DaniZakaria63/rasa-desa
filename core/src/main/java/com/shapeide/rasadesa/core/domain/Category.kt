package com.shapeide.rasadesa.core.domain

import com.shapeide.rasadesa.utills.smartTruncate

data class Category(
    val id: Int,
    val name: String,
    val thumb: String,
    val description: String
) {
    val shortDesc: String
        get() = description.smartTruncate(100)
}
