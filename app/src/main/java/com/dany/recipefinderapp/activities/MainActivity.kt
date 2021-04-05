package com.dany.recipefinderapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dany.recipefinderapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButtonId.setOnClickListener{

            var intent = Intent(this, RecipeList::class.java)
            var ingredients = ingredientsId.text.toString().trim()
            var searchTerm = searchTermId.text.toString().trim()

            println(ingredients.isEmpty())


            intent.putExtra("Ingredients", ingredients)
            intent.putExtra("search", searchTerm)

            startActivity(intent)
        }
    }
}