package com.lirioams.list.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.lirioams.list.R
import com.lirioams.list.data.remote.ListApi
import com.lirioams.list.data.remote.model.ListDto
import com.lirioams.list.databinding.ActivityMainBinding
import com.lirioams.list.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instacia retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val listApi = retrofit.create(ListApi::class.java)

        val call: Call<List<ListDto>> = listApi.getElements("api/v1/characters?perPage=497")

        call.enqueue(object: Callback<List<ListDto>>{
            override fun onResponse(p0: Call<List<ListDto>>, response: Response<List<ListDto>>) {
                Log.d(Constants.LOGTAG, binding.root.context.getString(R.string.resp, response.toString()))
                Log.d(Constants.LOGTAG, binding.root.context.getString(R.string.resp_body, response.body()))
                //Dejamos de mostrar la carga
                binding.pbLoading.visibility = View.INVISIBLE

                response.body()?.let { elements ->
                    val miAdapter = ListAdapter(elements){element->
                        //click de cada elemento
                        element.id?.let { id ->
                            val bundle = bundleOf(
                                "id" to id
                            )
                            val intent = Intent(this@MainActivity, ElementData::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }
                    }
                    binding.rvElements.apply{
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = miAdapter
                    }
                }

            }

            override fun onFailure(p0: Call<List<ListDto>>, p1: Throwable) {
                //Error de conexión
                binding.pbLoading.visibility = View.INVISIBLE
                Toast.makeText(this@MainActivity,
                    R.string.conection_e,
                    Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}