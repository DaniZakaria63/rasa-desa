package com.shapeide.rasadesa.room.mappers

import com.shapeide.rasadesa.core.domain.Area
import com.shapeide.rasadesa.room.domain.AreaEntity

fun List<AreaEntity>.asDomainModel() : List<com.shapeide.rasadesa.core.domain.Area>{
    return map {
        com.shapeide.rasadesa.core.domain.Area(
            strArea = it.strArea
        )
    }
}