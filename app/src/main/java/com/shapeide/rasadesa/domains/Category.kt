package com.shapeide.rasadesa.domains

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
