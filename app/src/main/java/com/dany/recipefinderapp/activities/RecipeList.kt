package com.dany.recipefinderapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest

import com.android.volley.toolbox.Volley
import com.dany.recipefinderapp.R
import com.dany.recipefinderapp.data.RecipeListAdapter
import com.dany.recipefinderapp.model.LEFT_LINK
import com.dany.recipefinderapp.model.QUERY
import com.dany.recipefinderapp.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    lateinit var volleyRequest: RequestQueue
    lateinit var recipeList: ArrayList<Recipe>
    lateinit var recipeAdapter: RecipeListAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        var urlString = "http://www.recipepuppy.com/api/"
        var url: String?

        var extras = intent.extras
        var ingredients = extras?.get("Ingredients")
        var searchTerm = extras?.get("search")

        Log.d("===>extras", extras.toString())

        if (extras != null &&
            !ingredients?.equals("")!!
                          && !searchTerm?.equals("")!!)
        {
            // Construct our url
            var tempUrl = LEFT_LINK+ingredients + QUERY+searchTerm

            url = tempUrl

        } else{
            url = "https://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3"
        }

        recipeList = ArrayList<Recipe>()

        volleyRequest = Volley.newRequestQueue(this)


        getRecipe(urlString)
    }

    fun getRecipe(url: String) {
        val recipeRequest = JsonObjectRequest(Request.Method.GET, url, JSONObject(),
            Response.Listener { response: JSONObject ->
                try {
                    val resultArray = response.getJSONArray("results")
                    for (i in 0 until resultArray.length()) {
                        val recipeObj = resultArray.getJSONObject(i)

                        var title = recipeObj.getString("title")
                        var link = recipeObj.getString("href")
                        var ingredients = recipeObj.getString("ingredients")
                        var thumbnail = recipeObj.getString("thumbnail")

                        Log.d("===>Title", title)

                        // constructing the object:

                        var recipe = Recipe()
                        recipe.title = title
                        recipe.link = link
                        recipe.ingredients = "Ingredients: $ingredients"
                        recipe.thumbnail = thumbnail

                        recipeList.add(recipe)

                        recipeAdapter = RecipeListAdapter(recipeList, this)
                        layoutManager = LinearLayoutManager(this)

                        // Setup the recycler view:
                        recyclerViewId.adapter = recipeAdapter
                        recyclerViewId.layoutManager = layoutManager
                    }

                    recipeAdapter.notifyDataSetChanged()


                } catch (e: JSONException) {
                    e.printStackTrace()
                Log.d("===>CATCH", e.toString())
                }
            },
            Response.ErrorListener { error: VolleyError ->
                try {
                    Log.d("Error", error.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.d("===>CATCH ERROR", e.toString())
                }
            })

        volleyRequest.add(recipeRequest)

    }
}