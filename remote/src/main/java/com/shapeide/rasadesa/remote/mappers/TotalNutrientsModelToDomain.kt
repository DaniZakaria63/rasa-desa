package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.domain.domain.Nutrients
import com.shapeide.rasadesa.remote.domain.TotalNutrientsModel


fun TotalNutrientsModel.toDomainModel(): Nutrients {
    return Nutrients(
        ENERCKCAL?.toDomainModel(),
        FAT?.toDomainModel(),
        FATRN?.toDomainModel(),
        FAMS?.toDomainModel(),
        FAPU?.toDomainModel(),
        CHOCDF?.toDomainModel(),
        FIBTG?.toDomainModel(),
        SUGAR?.toDomainModel(),
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
        FOLFD?.toDomainModel(),
        FOLAC?.toDomainModel(),
        VITB12?.toDomainModel(),
        VITD?.toDomainModel(),
        TOCPHA?.toDomainModel(),
        VITK1?.toDomainModel(),
        WATER?.toDomainModel(),
    )
}