package com.winterry.nbc_kiosk.model.order

import com.winterry.nbc_kiosk.model.product.MenuItem

data class OrderData(
    val menuItem: MenuItem,
    var amount: Int = 0
)
