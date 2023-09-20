package com.shapeide.rasadesa.remote.mappers

import com.shapeide.rasadesa.domain.domain.Digest
import com.shapeide.rasadesa.remote.domain.DigestModel


fun ArrayList<DigestModel>.toDomainModel(): List<Digest> {
    return map {
        Digest(
            it.label,
            it.tag,
            it.schemaOrgTag,
            it.total,
            it.hasRDI,
            it.daily,
            it.unit,
            it.sub.toDomainModel()
        )
    }
}