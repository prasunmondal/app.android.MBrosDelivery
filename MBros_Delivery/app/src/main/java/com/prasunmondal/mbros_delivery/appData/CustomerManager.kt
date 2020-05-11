package com.prasunmondal.mbros_delivery.appData

import com.prasunmondal.mbros_delivery.blueprints.Customer
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext
import com.prasunmondal.mbros_delivery.utils.serializeUtils.SerializeUtil

class CustomerManager {

    object Singleton {
        var instance = CustomerManager()
    }

    lateinit var customerMap: MutableMap<String, Customer>

    fun save() {
        SerializeUtil().saveSerializable(appContext.getLoginCheckActivity(), customerMap, "customerMap")
    }

    fun read() {
        customerMap = SerializeUtil().readSerializable(appContext.getLoginCheckActivity(), "customerMap") as MutableMap<String, Customer>
    }
}