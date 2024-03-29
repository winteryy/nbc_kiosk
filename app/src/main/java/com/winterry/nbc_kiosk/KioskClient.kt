package com.winterry.nbc_kiosk

import com.winterry.nbc_kiosk.model.product.Category
import com.winterry.nbc_kiosk.data.ServerData
import com.winterry.nbc_kiosk.data.ServerThread
import com.winterry.nbc_kiosk.model.product.MenuItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class KioskClient {

    private lateinit var order: Order
//    private val serverThread: ServerThread by lazy {
//        ServerThread {
//            Thread.sleep(5000L)
//            println("\n===현재 대기 중인 주문 수는 ${ServerData.getOrderNum()}개===")
//        }
//    }
    private val menuData = ServerData.getMenuData()
    private val receiptMaker = ReceiptMaker("CAFE A")

    fun execute() {
//        serverThread.start()
        getOrder()
//        if(serverThread.isAlive) serverThread.interrupt()
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
                            receiptMaker.makeReceipt(order)
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

    //메뉴 대분류 출력
    private fun printMenuCategory() {
        println("[ CAFE A's Menu ]\n")
        for((ind, category) in menuData.withIndex()) {
            println("${ind+1}. ${category.second.getCategoryName()}  : ${category.second.getCategoryInfo()}")
        }
        println("9. 주문 취소 및 나가기\n0. 주문/결제하기")
    }

    //상세 메뉴 출력
    private fun printDetailMenu(menuList: List<MenuItem>, category: Category) {
        println("[ ${category.getCategoryName()} Menu ]")
        for((ind, menu) in menuList.withIndex()) {
            println("${ind+1}. ${menu.getName()}  : ${menu.getPrice()} 원")
        }
        println("0. 뒤로가기")
    }

    //실제 메뉴 추가
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

    //주문 처리
    private fun makeOrder(): Boolean {
        if(order.getCurrentOrderList().isEmpty()) {
            println("주문 내역이 없습니다.\n")
            return false
        }

        println("아래와 같이 주문 하시겠습니까? (현재 주문 대기 수: ${ServerData.getOrderNum()})\n\n[ Orders ]")
        for(orderData in order.getCurrentOrderList()) {
            val orderItem = orderData.value.menuItem
            println("${orderItem.getName()}  : ${orderItem.getPrice()} 원 * ${orderData.value.amount} 개 | ${orderItem.getPrice()*orderData.value.amount} 원")
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

    //결제 처리
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

    //결제 시간 유효성 체크
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