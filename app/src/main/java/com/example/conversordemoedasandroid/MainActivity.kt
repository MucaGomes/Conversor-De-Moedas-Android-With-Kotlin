package com.example.conversordemoedasandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.conversordemoedasandroid.API.Endpoint
import com.example.conversordemoedasandroid.Ultilidades.NetworkUtil.NetworkUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var btConversion: Button
    private lateinit var tvresult: TextView
    private lateinit var value: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        btConversion = findViewById(R.id.btConversao)
        tvresult = findViewById(R.id.tvResult)
        value = findViewById(R.id.valorDigitado)

        getCurrencies()

        btConversion.setOnClickListener {
            convertMoeda()
        }
    }

    fun convertMoeda() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencyRate(spinnerFrom.selectedItem.toString(), spinnerTo.selectedItem.toString()).enqueue(object :
            retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                try {
                    var data = response.body()?.entrySet()?.find { it.key == spinnerTo.selectedItem.toString() }
                    val rate: Double = data?.value.toString().toDouble()
                    val conversion = value.text.toString().toDouble() * rate

                    tvresult.setText(conversion.toString())

                } catch (e: Exception) {
                    print(e.message)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                print("Não carregou!")
            }
        })
    }

    fun getCurrencies() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencies().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var data = mutableListOf<String>()

                response.body()?.keySet()?.iterator()?.forEach {
                    data.add(it)

                }

                val posicaoBRL = data.indexOf("brl")
                val posicaoUSD = data.indexOf("usd")

                val adapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_dropdown_item, data)
                spinnerFrom.adapter = adapter
                spinnerTo.adapter = adapter

                spinnerFrom.setSelection(posicaoUSD)
                spinnerTo.setSelection(posicaoBRL)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                print("Não carregou as informações")
            }
        })

    }
}



