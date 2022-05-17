package com.example.consumodeapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.consumodeapi.Api.Endpoint
import com.example.consumodeapi.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var  binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPesquisar.setOnClickListener{ getImage() }



    }

    private fun getImage() {

        val url = "https://dog.ceo/"
        var retrofitClient = retrofitInstance(url)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val raca = binding.editRaca.text.toString()

        endpoint.getDog(raca).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val urlImage = response.body()?.get("message")?.asString
                Picasso.get().load(urlImage).into(binding.imageDog)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(applicationContext, "Erro ao acessar", Toast.LENGTH_LONG)
            }

        })

      //  val urlImage = "https://todateen.com.br/wp-content/uploads/2018/04/frases-de-toy-story.jpg"
      //   Picasso.get().load(urlImage).into(binding.imageDog)
    }

    private fun retrofitInstance(url: String): Retrofit {
        return Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()

    }
}
