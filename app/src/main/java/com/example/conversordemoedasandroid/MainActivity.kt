package com.example.conversordemoedasandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.conversordemoedasandroid.API.Endpoint
import com.example.conversordemoedasandroid.Ultilidades.NetworkUtil.NetworkUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var btConversao: Button
    private lateinit var resultado: TextView
    private lateinit var valorDigitado: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFrom = findViewById(R.id.spinnerfrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        btConversao = findViewById(R.id.btConversao)
        resultado = findViewById(R.id.resultado)
        valorDigitado = findViewById(R.id.valorDigitado)

        getCurrencies()

        btConversao.setOnClickListener{
            convertMoeda()
        }
    }


    fun convertMoeda(){
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencyRate(spinnerFrom.selectedItem.toString(), spinnerTo.selectedItem.toString()).enqueue(object :
            retrofit2.Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val data = response.body()?.entrySet()?.find { it.key == spinnerTo.selectedItem.toString() }
                val rate: Double = data?.value.toString().toDouble()
                val conversao = valorDigitado.text.toString().toDouble() * rate

                resultado.setText(conversao.toString())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                print("Não carregou!")
            }
        })
    }

    fun getCurrencies() {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance("https://cdn.jsdelivr.net/")

        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencies().enqueue(object : retrofit2.Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val data = mutableListOf<String>()

                response.body()?.keySet()?.iterator()?.forEach{
                    data.add(it.uppercase())

                }

                val posicaoMoedaBR = data.indexOf("BRL")
                val  posicaoMoedaUSA = data.indexOf("USD")

                val adapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_dropdown_item, data)
                spinnerFrom.adapter = adapter
                spinnerTo.adapter = adapter

                spinnerFrom.setSelection(posicaoMoedaUSA)
                spinnerTo.setSelection(posicaoMoedaBR)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                print("Não carregou as informações")
            }
        })

    }
}



