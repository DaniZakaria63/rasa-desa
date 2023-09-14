package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.domain.domain.NutrientsSub
import com.shapeide.rasadesa.remote.domain.TotalSubModel


fun TotalSubModel.toDomainModel(): NutrientsSub {
    return NutrientsSub(label, quantity, unit)
}
