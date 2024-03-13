package com.winterry.nbc_kiosk.data

import com.winterry.nbc_kiosk.model.product.Coffee
import com.winterry.nbc_kiosk.model.product.Dessert
import com.winterry.nbc_kiosk.model.product.Tea

object ServerData {
    private var currentOrderNum = 0

    private val coffeeMenu = listOf(
        Coffee(1, "아메리카노", 3000),
        Coffee(2, "카페 라떼", 3600),
        Coffee(3, "카푸치노", 3600),
        Coffee(4, "카라멜 마키아또", 4500),
        Coffee(5, "카페 모카", 4100)
    )

    private val teaMenu = listOf(
        Tea(6, "캐모마일 티", 3100),
        Tea(7, "민트 티", 3100),
        Tea(8, "제주 녹차", 3900),
        Tea(9, "자몽 허니 블랙 티", 4300),
        Tea(10, "유자 민트 티", 4600)
    )

    private val dessertMenu = listOf(
        Dessert(11, "초콜릿 케이크", 4900),
        Dessert(12, "치즈 케이크", 4900),
        Dessert(13, "레드벨벳 케이크", 5500),
        Dessert(14, "버터쿠키", 1500),
        Dessert(15, "마카롱", 2500)
    )

    fun increaseOrderNum() {
        currentOrderNum++
    }

    fun getOrderNum(): Int {
        return currentOrderNum
    }

    fun getCoffeeList(): List<Coffee> {
        return coffeeMenu
    }

    fun getTeaList(): List<Tea> {
        return teaMenu
    }

    fun getDessertList(): List<Dessert> {
        return dessertMenu
    }
}