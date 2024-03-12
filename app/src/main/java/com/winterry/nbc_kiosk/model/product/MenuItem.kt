package com.winterry.nbc_kiosk.model.product


open class MenuItem(private val id: Int, private val name: String, private val price: Int) {

    fun getName(): String {
        return name
    }

    fun getPrice(): Int {
        return price
    }
}