package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.domain.domain.DigestSub
import com.shapeide.rasadesa.remote.domain.DigestSubModel


fun ArrayList<DigestSubModel>.toDomainModel(): List<DigestSub> {
    return map {
        DigestSub(
            it.label,
            it.tag,
            it.schemaOrgTag,
            it.total,
            it.hasRDI,
            it.daily,
            it.unit
        )
    }
}
