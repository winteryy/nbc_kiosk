package com.winterry.nbc_kiosk.model

import com.winterry.nbc_kiosk.model.product.MenuItem

class Order(private val money: Int) {
    private val orderList = mutableListOf<MenuItem>()
    private var totalCost = 0

    init {
        if(money<0) throw Exception()
    }

    fun addItem(menuItem: MenuItem): Boolean {
        return if (totalCost + menuItem.getPrice() <= money) {
            orderList.add(menuItem)
            totalCost += menuItem.getPrice()
            true
        } else {
            false
        }
    }

    fun getCurrentOrderList(): List<MenuItem> {
        return orderList
    }

    fun getTotalCost(): Int {
        return totalCost
    }
}