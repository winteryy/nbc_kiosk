package com.winterry.nbc_kiosk.model

import com.winterry.nbc_kiosk.model.product.MenuItem

class Order(private val money: Int) {
    private val orderList = mutableListOf<MenuItem>()
    private var totalCost = 0

    fun addItem(menuItem: MenuItem): Boolean {
        if (totalCost + menuItem.getPrice() <= money) {
            orderList.add(menuItem)
            totalCost += menuItem.getPrice()
            return true
        } else {
            return false
        }
    }

    fun getCurrentOrderList(): List<MenuItem> {
        return orderList
    }
}