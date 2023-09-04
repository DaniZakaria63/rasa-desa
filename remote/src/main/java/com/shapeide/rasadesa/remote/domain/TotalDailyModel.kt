package com.shapeide.rasadesa.remote.domain

import com.google.gson.annotations.SerializedName

data class TotalDailyModel(

    @SerializedName("ENERC_KCAL") var ENERCKCAL: TotalSubModel? = TotalSubModel(),
    @SerializedName("FAT") var FAT: TotalSubModel? = TotalSubModel(),
    @SerializedName("FASAT") var FASAT: TotalSubModel? = TotalSubModel(),
    @SerializedName("CHOCDF") var CHOCDF: TotalSubModel? = TotalSubModel(),
    @SerializedName("FIBTG") var FIBTG: TotalSubModel? = TotalSubModel(),
    @SerializedName("PROCNT") var PROCNT: TotalSubModel? = TotalSubModel(),
    @SerializedName("CHOLE") var CHOLE: TotalSubModel? = TotalSubModel(),
    @SerializedName("NA") var NA: TotalSubModel? = TotalSubModel(),
    @SerializedName("CA") var CA: TotalSubModel? = TotalSubModel(),
    @SerializedName("MG") var MG: TotalSubModel? = TotalSubModel(),
    @SerializedName("K") var K: TotalSubModel? = TotalSubModel(),
    @SerializedName("FE") var FE: TotalSubModel? = TotalSubModel(),
    @SerializedName("ZN") var ZN: TotalSubModel? = TotalSubModel(),
    @SerializedName("P") var P: TotalSubModel? = TotalSubModel(),
    @SerializedName("VITA_RAE") var VITARAE: TotalSubModel? = TotalSubModel(),
    @SerializedName("VITC") var VITC: TotalSubModel? = TotalSubModel(),
    @SerializedName("THIA") var THIA: TotalSubModel? = TotalSubModel(),
    @SerializedName("RIBF") var RIBF: TotalSubModel? = TotalSubModel(),
    @SerializedName("NIA") var NIA: TotalSubModel? = TotalSubModel(),
    @SerializedName("VITB6A") var VITB6A: TotalSubModel? = TotalSubModel(),
    @SerializedName("FOLDFE") var FOLDFE: TotalSubModel? = TotalSubModel(),
    @SerializedName("VITB12") var VITB12: TotalSubModel? = TotalSubModel(),
    @SerializedName("VITD") var VITD: TotalSubModel? = TotalSubModel(),
    @SerializedName("TOCPHA") var TOCPHA: TotalSubModel? = TotalSubModel(),
    @SerializedName("VITK1") var VITK1: TotalSubModel? = TotalSubModel()

)