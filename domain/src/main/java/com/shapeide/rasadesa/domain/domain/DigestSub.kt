package com.shapeide.rasadesa.domain.domain

data class DigestSub(
    var label: String? = null,
    var tag: String? = null,
    var schemaOrgTag: String? = null,
    var total: Double? = null,
    var hasRDI: Boolean? = null,
    var daily: Double? = null,
    var unit: String? = null
)
