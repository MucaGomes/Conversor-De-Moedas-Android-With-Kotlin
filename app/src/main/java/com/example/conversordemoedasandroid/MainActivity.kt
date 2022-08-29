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
    // Usamos o lateinit para iniciarmos pois não temos todos as propiedades carregadas antes da inicialização do projeto
    // pra evitar que os objetos iniciem com valores nulos usei o lateinit
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var btConversion: Button
    private lateinit var tvresult: TextView
    private lateinit var value: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

<<<<<<< HEAD
        // Aqui identificamos os objetos
        spinnerFrom = findViewById(R.id.spinnerfrom)
=======
        spinnerFrom = findViewById(R.id.spinnerFrom)
>>>>>>> 5a3f7ff911dae401dd820872b6d5dcc22b2bea58
        spinnerTo = findViewById(R.id.spinnerTo)
        btConversion = findViewById(R.id.btConversao)
        tvresult = findViewById(R.id.tvResult)
        value = findViewById(R.id.valorDigitado)

        getCurrencies()

        btConversion.setOnClickListener {
            convertMoeda()
        }
    }

<<<<<<< HEAD
    // Nessa função faremos a conversão dos valores obtidos da API , depois realocamos para que essa função seja atribuida ao botão Calcular
    fun convertMoeda(){
=======
    fun convertMoeda() {
>>>>>>> 5a3f7ff911dae401dd820872b6d5dcc22b2bea58
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencyRate(spinnerFrom.selectedItem.toString(), spinnerTo.selectedItem.toString()).enqueue(object :
            retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

<<<<<<< HEAD
                // Depois de toda a conversão , pegamos o resultado e tranformamos o objeto Resultado(TextView) nesse valor
                resultado.setText(conversao.toString())
=======
                try {
                    var data = response.body()?.entrySet()?.find { it.key == spinnerTo.selectedItem.toString() }
                    val rate: Double = data?.value.toString().toDouble()
                    val conversion = value.text.toString().toDouble() * rate

                    tvresult.setText(conversion.toString())

                } catch (e: Exception) {
                    print(e.message)
                }
>>>>>>> 5a3f7ff911dae401dd820872b6d5dcc22b2bea58
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                print("Não carregou!")
            }
        })
    }

    // Essa função puxa as moedas de dentro da API e guarda elas dentro de uma mutableList
    // assim fica mais facil de pegarmos elas dentro do nosso código
    fun getCurrencies() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencies().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var data = mutableListOf<String>()

<<<<<<< HEAD
                response.body()?.keySet()?.iterator()?.forEach{
                    data.add(it.uppercase())
                    // faço que elas fiquem em Caixa Alta para melhor visualização dentro do app
=======
                response.body()?.keySet()?.iterator()?.forEach {
                    data.add(it)
>>>>>>> 5a3f7ff911dae401dd820872b6d5dcc22b2bea58

                }

                val posicaoBRL = data.indexOf("brl")
                val posicaoUSD = data.indexOf("usd")

                // Atribuimos essa mutableList(data) aos nossos spinners , que vão conter todas as nossas moedas ♥
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



