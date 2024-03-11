package com.winterry.nbc_kiosk.model.product

import com.winterry.nbc_kiosk.model.Category

class Drink(id: Int, name: String, price: Int): Beverage(id, name, price), Category {

    override fun getCategoryName(): String {
        TODO("Not yet implemented")
    }

    override fun getCategoryInfo(): String {
        TODO("Not yet implemented")
    }
}