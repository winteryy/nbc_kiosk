package com.winterry.nbc_kiosk

import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ReceiptMaker(private val tradeName: String) {

    fun makeReceipt(order: Order) {
        val directory = "./receipt"
        val dir = File(directory)
        if(!dir.exists()) {
            dir.mkdirs()
        }

        val fileNameChunk = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss_")) + order.getIdString()
        val exportDate = fileNameChunk.take(8)
        val file = File("$directory/$fileNameChunk.txt")
        val writer = file.bufferedWriter()

        var context =
            """
                [ 영수증 ]
                
                상호명: $tradeName
                영수증 번호: ${order.getIdString()}
                발행 일자: $exportDate
                 
                =거래 내역=
            """.trimIndent()
        println("영수증 들어온거: ${order.getCurrentOrderList().size}개")
        for (orderData in order.getCurrentOrderList()) {
            val orderItem = orderData.value.menuItem
            context += "\n${orderItem.getName()}  : ${orderItem.getPrice()} 원 * ${orderData.value.amount} 개 | ${orderItem.getPrice()*orderData.value.amount} 원"
        }
        context += "\n\n총 ${order.getTotalCost()} 원"

        try {
            writer.write(context)
        }catch (e: Exception) {
            println("영수증 출력 실패")
        }finally {
            writer.close()
        }

    }
}