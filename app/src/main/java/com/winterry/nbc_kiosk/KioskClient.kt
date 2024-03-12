package com.winterry.nbc_kiosk

import com.winterry.nbc_kiosk.model.Category
import com.winterry.nbc_kiosk.model.Order
import com.winterry.nbc_kiosk.model.data.ServerData
import com.winterry.nbc_kiosk.model.data.ServerThread
import com.winterry.nbc_kiosk.model.product.Coffee
import com.winterry.nbc_kiosk.model.product.Dessert
import com.winterry.nbc_kiosk.model.product.MenuItem
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
    private val menuData: List<Pair<List<MenuItem>, Category>> by lazy {
        listOf<Pair<List<MenuItem>, Category>>(
            ServerData.getCoffeeList() to Coffee,
            ServerData.getTeaList() to Tea,
            ServerData.getDessertList() to Dessert
        )
    }

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
                printMenuCategory()
                val orderCategory = readln()
                println()

                when(orderCategory) {
                    "9" -> break
                    "0" -> {
                        if(makeOrder()) {
                            ServerData.increaseOrderNum()
                            Thread.sleep(3000L)
                            break
                        }
                    }
                    else -> try {
                        orderMenu(menuData[orderCategory.toInt()-1].first, menuData[orderCategory.toInt()-1].second)
                    } catch (e: Exception) {
                        printWrongInputWarn()
                    }
                }
            }
        }

    }

    private fun printMenuCategory() {
        println("[ CAFE A's Menu ]\n")
        for((ind, category) in menuData.withIndex()) {
            println("${ind+1}. ${category.second.getCategoryName()}  : ${category.second.getCategoryInfo()}")
        }
        println("9. 주문 취소 및 나가기\n0. 주문/결제하기")
    }

    private fun printDetailMenu(menuList: List<MenuItem>, category: Category) {
        println("[ ${category.getCategoryName()} Menu ]")
        for((ind, menu) in menuList.withIndex()) {
            println("${ind+1}. ${menu.getName()}  : ${menu.getPrice()} 원")
        }
        println("0. 뒤로가기")
    }

    private fun orderMenu(menuList: List<MenuItem>, category: Category) {
        printDetailMenu(menuList, category)

        while(true) {
            val orderNum = readln()
            if(orderNum=="0") break

            try {
                val selectMenu = menuList[orderNum.toInt()-1]
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
        println("아래와 같이 주문 하시겠습니까? (현재 주문 대기 수: ${ServerData.getOrderNum()})\n\n[ Orders ]")
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
        println("잘못된 입력입니다.\n")
    }
}