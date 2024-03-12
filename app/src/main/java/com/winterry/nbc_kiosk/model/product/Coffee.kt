package com.winterry.nbc_kiosk.model.product

import com.winterry.nbc_kiosk.model.Category

class Coffee(id: Int, name: String, price: Int): Beverage(id, name, price) {

    companion object: Category {

        override fun getCategoryName(): String {
            return "Coffee"
        }

        override fun getCategoryInfo(): String {
            return "고품질 원두를 사용한 커피"
        }
    }

}