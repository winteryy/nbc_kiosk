package com.winterry.nbc_kiosk.model.product

import com.winterry.nbc_kiosk.model.Category

class Dessert(id: Int, name: String, price: Int): MenuItem(id, name, price) {

    companion object: Category {

        override fun getCategoryName(): String {
            return "Dessert"
        }

        override fun getCategoryInfo(): String {
            return "당일에 만들어 판매하는 디저트"
        }
    }

}