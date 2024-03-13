package com.winterry.nbc_kiosk.model.product

class Tea(id: Int, name: String, price: Int) : MenuItem(id, name, price) {

    companion object: Category {

        override fun getCategoryName(): String {
            return "Tea"
        }

        override fun getCategoryInfo(): String {
            return "다양한 풍미의 차 종류"
        }
    }

}
