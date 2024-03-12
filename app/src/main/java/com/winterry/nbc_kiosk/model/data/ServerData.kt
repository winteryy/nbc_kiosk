package com.winterry.nbc_kiosk.model.data

object ServerData {
    private var currentOrderNum = 0

    fun increaseOrderNum() {
        currentOrderNum++
    }

    fun getOrderNum(): Int {
        return currentOrderNum
    }
}