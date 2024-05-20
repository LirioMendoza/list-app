package com.lirioams.list.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lirioams.list.R
import com.lirioams.list.databinding.ActivityElementDataBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.lirioams.list.util.Constants
import com.lirioams.list.data.remote.model.ListDetailDto
import com.lirioams.list.data.remote.ListApi

class ElementData : AppCompatActivity() {

    private lateinit var binding: ActivityElementDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElementDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val id = bundle?.getString("id", "")

        Log.d(Constants.LOGTAG, "Id recibido $id")

        //Generamos una instancia a retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val listApi = retrofit.create(ListApi::class.java)

        val call: Call<ListDetailDto> = listApi.getElementDetail(id!!)

        binding.pbLoading.visibility = View.VISIBLE
        call.enqueue(object: Callback<ListDetailDto> {
            override fun onResponse(p0: Call<ListDetailDto>, response: Response<ListDetailDto>) {
                binding.pbLoading.visibility = View.INVISIBLE

                binding.apply {
                    tvName.text = response.body()?.name
                    val alliesText = response.body()?.allies?.filter { it.isNotBlank() }?.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "Allies Unknown"
                    binding.tvProffesion.text = binding.root.context.getString(R.string.allies, alliesText)
                    binding.tvAffiliation.text = binding.root.context.getString(R.string.affiliation, response.body()?.affiliation?: "Unknown")
                    binding.tvGender.text = binding.root.context.getString(R.string.gender, response.body()?.gender?: "Unknown")
                    binding.tvPosition.text = binding.root.context.getString(R.string.position, response.body()?.position?: "Unknown")
                    binding.tvWeapons.text = binding.root.context.getString(R.string.weapon, response.body()?.weapon?: "Unknown")

                    Glide.with(this@ElementData)
                        .load(response.body()?.image)
                        .placeholder(R.drawable.loading_anim)
                        .error(R.drawable.noprofile)
                        .into(ivImage)
                }

            }
            override fun onFailure(p0: Call<ListDetailDto>, p1: Throwable) {
                //Manejamos error de conexion

                binding.pbLoading.visibility = View.INVISIBLE

                Toast.makeText(this@ElementData,
                    "No hay conexión disponible",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        })


    }


}