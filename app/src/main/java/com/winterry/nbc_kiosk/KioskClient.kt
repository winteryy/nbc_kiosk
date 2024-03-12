package com.winterry.nbc_kiosk

import com.winterry.nbc_kiosk.model.Order
import com.winterry.nbc_kiosk.model.data.ServerData
import com.winterry.nbc_kiosk.model.data.ServerThread
import com.winterry.nbc_kiosk.model.product.Coffee
import com.winterry.nbc_kiosk.model.product.Dessert
import com.winterry.nbc_kiosk.model.product.Tea
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class KioskClient {

    private lateinit var order: Order
    private val serverThread: ServerThread by lazy {
        ServerThread {
            Thread.sleep(5000L)
            println("\n===현재 대기 중인 주문 수는 ${ServerData.getOrderNum()}개===")
        }
    }

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
        serverThread.start()
        getOrder()
        if(serverThread.isAlive) serverThread.interrupt()
    }

    private fun getOrder() {
        while(true) {
            println("환영합니다. 카페 A입니다.\n예산을 입력해 주문을 시작하거나, -1을 입력해 키오스크를 종료합니다.")
            val money = readln()
            if(money=="-1") break
            try {
                order = Order(money.toInt())
                println()
            } catch (e: Exception) {
                printWrongInputWarn()
                continue
            }

            while(true) {
                println("""[ CAFE A's Menu ]
            
           &1. ${Coffee.getCategoryName()}  : ${Coffee.getCategoryInfo()}
           &2. ${Tea.getCategoryName()}  : ${Tea.getCategoryInfo()}
           &3. ${Dessert.getCategoryName()}  : ${Dessert.getCategoryInfo()}
           &9. 주문 취소 및 나가기
           &0. 주문/결제하기
        """.trimMargin("&"))

                val orderCategory = readln()
                println()

                when(orderCategory) {
                    "1" -> orderCoffee()
                    "2" -> orderTea()
                    "3" -> orderDessert()
                    "9" -> break
                    "0" -> {
                        if(makeOrder()) {
                            ServerData.increaseOrderNum()
                            Thread.sleep(3000L)
                            break
                        }
                    }
                    else -> printWrongInputWarn()
                }
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

            try {
                val selectMenu = coffeeMenu[orderNum.toInt()-1]
                if(order.addItem(selectMenu)) {
                    println("${selectMenu.getName()} (이)가 추가되었습니다.")
                }else {
                    println("잔액을 초과하는 주문입니다.")
                }
            } catch (e: Exception) {
                printWrongInputWarn()
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

            try {
                val selectMenu = teaMenu[orderNum.toInt()-1]
                if(order.addItem(selectMenu)) {
                    println("${selectMenu.getName()} (이)가 추가되었습니다.")
                }else {
                    println("잔액을 초과하는 주문입니다.")
                }
            } catch (e: Exception) {
                printWrongInputWarn()
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

            try {
                val selectMenu = dessertMenu[orderNum.toInt()-1]
                if(order.addItem(selectMenu)) {
                    println("${selectMenu.getName()} (이)가 추가되었습니다.")
                }else {
                    println("잔액을 초과하는 주문입니다.")
                }
            } catch (e: Exception) {
                printWrongInputWarn()
            }

        }
    }

    private fun makeOrder(): Boolean {
        println("아래와 같이 주문 하시겠습니까?\n\n[ Orders ]")
        for(item in order.getCurrentOrderList()) {
            println("${item.getName()}  : ${item.getPrice()} 원")
        }
        println("\n[ Total ]\n${order.getTotalCost()} 원\n\n1. 주문/결제하기\t2. 돌아가기\n")
        while(true) {
            val cmd = readln()
            when(cmd) {
                "1" -> {
                    return purchase()
                }
                "2" -> return false
                else -> { printWrongInputWarn() }
            }
        }

    }

    private fun purchase(): Boolean {
        return if (isValidTime()) {
            val currentTime = LocalDateTime.now()
            val formatted = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            println("결제를 완료했습니다. (${formatted})\n3초 뒤 초기화면으로 돌아갑니다.\n")
            true
        } else {
            false
        }
    }

    private fun isValidTime(): Boolean {
        val currentTime = LocalDateTime.now()

        if(currentTime.hour==23 && currentTime.minute in 10..20) {
            println("현재 시각은 ${currentTime.hour}시 ${currentTime.minute}분입니다.\n은행 점검 시간은 23시 10분 ~ 23시 20분이므로 결제할 수 없습니다.")
            return false
        }
        return true
    }

    private fun printWrongInputWarn() {
        println("잘못된 입력입니다.")
    }
}