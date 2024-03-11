package com.winterry.nbc_kiosk

import com.winterry.nbc_kiosk.model.Order
import com.winterry.nbc_kiosk.model.product.Coffee
import com.winterry.nbc_kiosk.model.product.Dessert
import com.winterry.nbc_kiosk.model.product.Tea

class KioskClient {

    private lateinit var order: Order

    val coffeeMenu = listOf(
        Coffee(1, "아메리카노", 3000),
        Coffee(2, "카페 라떼", 3600),
        Coffee(3, "카푸치노", 3600),
        Coffee(4, "카라멜 마키아또", 4500),
        Coffee(5, "카페 모카", 4100)
    )

    val teaMenu = listOf(
        Tea(6, "캐모마일 티", 3100),
        Tea(7, "민트 티", 3100),
        Tea(8, "제주 녹차", 3900),
        Tea(9, "자몽 허니 블랙 티", 4300),
        Tea(10, "유자 민트 티", 4600)
    )

    val dessertMenu = listOf(
        Dessert(11, "초콜릿 케이크", 4900),
        Dessert(12, "치즈 케이크", 4900),
        Dessert(13, "레드벨벳 케이크", 5500),
        Dessert(14, "버터쿠키", 1500),
        Dessert(15, "마카롱", 2500)
    )

    fun execute() {
        getOrder()
    }

    private fun getOrder() {
        println("""환영합니다. 카페 A입니다.
            
           &1. 주문하기
           &2. 종료
        """.trimMargin("&"))

        order = Order(50000)
        readln()
        println()

        while(true) {
            println("""[ CAFE A's Menu ]
            
           &1. ${Coffee.getCategoryName()}  : ${Coffee.getCategoryInfo()}
           &2. ${Tea.getCategoryName()}  : ${Tea.getCategoryInfo()}
           &3. ${Dessert.getCategoryName()}  : ${Dessert.getCategoryInfo()}
           &0. 종료
        """.trimMargin("&"))

            val orderCategory = readln()
            println()

            when(orderCategory) {
                "1" -> orderCoffee()
                "2" -> orderTea()
                "3" -> orderDessert()
                "0" -> break
            }
        }

    }

    private fun orderCoffee() {
        println("[ Coffee Menu ]")
        for((ind, menu) in coffeeMenu.withIndex()) {
            println("${ind+1}. ${menu.getName()}  : ${menu.getPrice()} 원")
        }
        println("0. 뒤로가기")

        while(true) {
            val orderNum = readln()
            if(orderNum=="0") break

            val selectMenu = coffeeMenu[orderNum.toInt()-1]
            if(order.addItem(selectMenu)) {
                println("${selectMenu.getName()} (이)가 추가되었습니다.")
            }else {
                println("잔액을 초과하는 주문입니다.")
            }
        }
    }

    private fun orderTea() {
        println("[ Tea Menu ]")
        for((ind, menu) in teaMenu.withIndex()) {
            println("${ind+1}. ${menu.getName()}  : ${menu.getPrice()} 원")
        }
        println("0. 뒤로가기")

        while(true) {
            val orderNum = readln()
            if(orderNum=="0") break

            val selectMenu = teaMenu[orderNum.toInt()-1]
            if(order.addItem(selectMenu)) {
                println("${selectMenu.getName()} (이)가 추가되었습니다.")
            }else {
                println("잔액을 초과하는 주문입니다.")
            }
        }
    }

    private fun orderDessert() {
        println("[ Dessert Menu ]")
        for((ind, menu) in dessertMenu.withIndex()) {
            println("${ind+1}. ${menu.getName()}  : ${menu.getPrice()} 원")
        }
        println("0. 뒤로가기")

        while(true) {
            val orderNum = readln()
            if(orderNum=="0") break

            val selectMenu = dessertMenu[orderNum.toInt()-1]
            if(order.addItem(selectMenu)) {
                println("${selectMenu.getName()} (이)가 추가되었습니다.")
            }else {
                println("잔액을 초과하는 주문입니다.")
            }
        }
    }
}