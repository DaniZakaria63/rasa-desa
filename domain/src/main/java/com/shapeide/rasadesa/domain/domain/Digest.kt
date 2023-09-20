package com.shapeide.rasadesa.domain.domain

data class Digest(
    var label: String? = null,
    var tag: String? = null,
    var schemaOrgTag: String? = null,
    var total: Double? = null,
    var hasRDI: Boolean? = null,
    var daily: Double? = null,
    var unit: String? = null,
    var sub: List<DigestSub> = listOf()
)