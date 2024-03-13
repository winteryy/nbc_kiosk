package com.winterry.nbc_kiosk

import com.winterry.nbc_kiosk.model.order.OrderData
import com.winterry.nbc_kiosk.model.product.MenuItem

class Order(private val money: Int) {
    private val orderList = mutableMapOf<Int, OrderData>()
    private val idString = createIdString()
    private var totalCost = 0

    init {
        //입력 예산 음수인 경우 예외 throw
        if(money<0) throw Exception()
    }

    fun addItem(menuItem: MenuItem): Boolean {
        //메뉴 추가 성공여부 반환
        return if (totalCost + menuItem.getPrice() <= money) {
            if(orderList.containsKey(menuItem.getId())) {
                orderList[menuItem.getId()]!!.amount++
            }else {
                orderList[menuItem.getId()] = OrderData(menuItem, 1)
            }
            totalCost += menuItem.getPrice()
            true
        } else {
            false
        }
    }

    fun getIdString(): String {
        return idString
    }

    fun getCurrentOrderList(): Map<Int, OrderData> {
        return orderList
    }

    fun getTotalCost(): Int {
        return totalCost
    }

    private fun createIdString(): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..10)
            .map { charset.random() }
            .joinToString("")
    }

}
