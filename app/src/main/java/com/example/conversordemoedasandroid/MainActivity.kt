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

        // Aqui identificamos os objetos
        spinnerFrom = findViewById(R.id.spinnerfrom)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        
        spinnerTo = findViewById(R.id.spinnerTo)
        btConversion = findViewById(R.id.btConversao)
        tvresult = findViewById(R.id.tvResult)
        value = findViewById(R.id.valorDigitado)

        getCurrencies()

        btConversao.setOnClickListener {

        btConversion.setOnClickListener {
            convertMoeda()
        }
    }


    // Nessa função faremos a conversão dos valores obtidos da API , depois realocamos para que essa função seja atribuida ao botão Calcular


    fun convertMoeda() {

    fun convertMoeda(){

    fun convertMoeda() {

        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencyRate(spinnerFrom.selectedItem.toString(),
            spinnerTo.selectedItem.toString()).enqueue(object :
            retrofit2.Callback<JsonObject> {



            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                try {

                    var data = response.body()?.entrySet()
                        ?.find { it.key == spinnerTo.selectedItem.toString() }
                    val rate: Double = data?.value.toString().toDouble()
                    val conversao = valorDigitado.text.toString().toDouble() * rate
                    val conversaoFormat = "%.2f".format(conversao)

                    // Depois de toda a conversão , pegamos o resultado e tranformamos o objeto Resultado(TextView) nesse valor
                    resultado.setText(conversaoFormat.toString())

                } catch (e: Exception) {
                    print(e)
                }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {


                // Depois de toda a conversão , pegamos o resultado e tranformamos o objeto Resultado(TextView) nesse valor
                resultado.setText(conversao.toString())

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
                Toast.makeText(baseContext, "Valor inválido!!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Essa função puxa as moedas de dentro da API e guarda elas dentro de uma mutableList
    // assim fica mais facil de pegarmos elas dentro do nosso código.
    fun getCurrencies() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencies().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                var data = mutableListOf<String>()

                response.body()?.keySet()?.iterator()?.forEach {
                    data.add(it)

                response.body()?.keySet()?.iterator()?.forEach{
                    data.add(it.uppercase())

                    // faço que elas fiquem em Caixa Alta para melhor visualização dentro do app

                response.body()?.keySet()?.iterator()?.forEach {
                    data.add(it)
                }


                val posicaoReal = data.indexOf("brl")
                val posicaoDolar = data.indexOf("usd")

                val posicaoBRL = data.indexOf("brl")
                val posicaoUSD = data.indexOf("usd")


                // Atribuimos essa mutableList(data) aos nossos spinners , que vão conter todas as nossas moedas ♥
                val adapter = ArrayAdapter(baseContext, R.layout.spinner_item, data)
                spinnerFrom.adapter = adapter
                spinnerTo.adapter = adapter


                spinnerFrom.setSelection(posicaoDolar)
                spinnerTo.setSelection(posicaoReal)

                spinnerFrom.setSelection(posicaoUSD)
                spinnerTo.setSelection(posicaoBRL)

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                print("Não carregou as informações")
            }
        })
    }
}



