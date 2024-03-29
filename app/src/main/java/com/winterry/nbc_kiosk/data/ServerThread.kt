package com.winterry.nbc_kiosk.data

class ServerThread(private val runnable: ()-> Unit): Thread() {
    override fun run() {
        super.run()

        while(true){
            try {
                runnable.invoke()
            } catch (e : InterruptedException) {
                break
            }
        }
    }
}