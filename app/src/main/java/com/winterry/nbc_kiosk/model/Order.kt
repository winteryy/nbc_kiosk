package com.winterry.nbc_kiosk.model

import com.winterry.nbc_kiosk.model.product.MenuItem

class Order(private val money: Int) {
    private val orderList = mutableListOf<MenuItem>()
    private var totalCost = 0

    init {
        //입력 예산 음수인 경우 예외 throw
        if(money<0) throw Exception()
    }

    fun addItem(menuItem: MenuItem): Boolean {
        //메뉴 추가 성공여부 반환
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