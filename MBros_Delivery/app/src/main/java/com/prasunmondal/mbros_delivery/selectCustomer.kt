package com.prasunmondal.mbros_delivery

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.mbros_delivery.sessionData.AppContext.Singleton.instance as appContext
import com.prasunmondal.mbros_delivery.sessionData.CurrentSession.Singleton.instance as currentSession
import com.prasunmondal.mbros_delivery.sessionData.FetchedRateList.Singleton.instance as fetchedRateList


class selectCustomer : AppCompatActivity() {

    var text_NoCustomerToSelect = "Select Customer"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_customer)
        appContext.setCustomerSelectionActivity(this)
        populateCustomerListSpinner()
        setActionbarTextColor()
    }

    fun onClickSaveUsername(view: View) {
        val customerSelector = findViewById<Spinner>(R.id.customerSelector)
        val customerName: String = customerSelector.selectedItem.toString()
        Toast.makeText(this@selectCustomer, "Selected User: " + customerName, Toast.LENGTH_SHORT).show()
        currentSession.setCurrentCustomer(customerName)
        goToCalculatingPage()
    }

    fun goToCalculatingPage() {
        if(currentSession.getCurrentCustomer().equals(text_NoCustomerToSelect)) {
            Toast.makeText(this, "Select valid customer", Toast.LENGTH_LONG).show()
        } else {
            val i = Intent(this@selectCustomer, WeighingPage::class.java)
            startActivity(i)
        }
    }

    fun onClickDownloadData(view: View) {
        goToDownloadPriceList()
    }

    fun goToDownloadPriceList() {
        val i = Intent(this@selectCustomer, DownloadPriceList::class.java)
        startActivity(i)
        finish()
    }

    fun populateCustomerListSpinner() {
        val spinner = findViewById<View>(R.id.customerSelector) as Spinner

        var geeks = mutableListOf<String>(text_NoCustomerToSelect)
        if(fetchedRateList.isDataFetched())
            geeks.addAll(fetchedRateList.getAllUserName())
        val dataAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, geeks)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter
    }

    fun setActionbarTextColor() {
        val title: String = "Mondal Bros."
        val spannableTitle: Spannable = SpannableString("")
        spannableTitle.setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            spannableTitle.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar!!.title = title
        window.statusBarColor = resources.getColor(R.color.selectCustomer_statusBar)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.selectCustomer_actionBar)))
    }
}