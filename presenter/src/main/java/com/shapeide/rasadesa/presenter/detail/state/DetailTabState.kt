package com.shapeide.rasadesa.presenter.detail.state

import com.shapeide.rasadesa.presenter.domain.DetailTab

data class DetailTabState(
    val tabActiveState: DetailTab? = DetailTab.INGREDIENTS,
    val tabList: List<DetailTab> = DetailTab.values().toList()
)