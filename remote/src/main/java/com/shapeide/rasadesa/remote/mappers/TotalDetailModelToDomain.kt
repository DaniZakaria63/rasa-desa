package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.domain.domain.NutrientsDaily
import com.shapeide.rasadesa.remote.domain.TotalDailyModel


fun TotalDailyModel.toDomainModel(): NutrientsDaily {
    return NutrientsDaily(
        ENERCKCAL?.toDomainModel(),
        FAT?.toDomainModel(),
        FASAT?.toDomainModel(),
        CHOCDF?.toDomainModel(),
        FIBTG?.toDomainModel(),
        PROCNT?.toDomainModel(),
        CHOLE?.toDomainModel(),
        NA?.toDomainModel(),
        CA?.toDomainModel(),
        MG?.toDomainModel(),
        K?.toDomainModel(),
        FE?.toDomainModel(),
        ZN?.toDomainModel(),
        P?.toDomainModel(),
        VITARAE?.toDomainModel(),
        VITC?.toDomainModel(),
        THIA?.toDomainModel(),
        RIBF?.toDomainModel(),
        NIA?.toDomainModel(),
        VITB6A?.toDomainModel(),
        FOLDFE?.toDomainModel(),
        VITB12?.toDomainModel(),
        VITD?.toDomainModel(),
        TOCPHA?.toDomainModel(),
        VITK1?.toDomainModel(),
    )
}
