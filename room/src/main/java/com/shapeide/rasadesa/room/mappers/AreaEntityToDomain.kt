package com.shapeide.rasadesa.room.mappers

import com.shapeide.rasadesa.domain.Area
import com.shapeide.rasadesa.room.domain.AreaEntity

fun List<AreaEntity>.asDomainModel() : List<Area>{
    return map {
        Area(
            strArea = it.strArea
        )
    }
}