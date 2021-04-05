package com.dany.recipefinderapp.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dany.recipefinderapp.R
import com.dany.recipefinderapp.activities.ShowLinkActivity
import com.dany.recipefinderapp.model.Recipe
import com.squareup.picasso.Picasso

class RecipeListAdapter(
    private val list: ArrayList<Recipe>,
    private val context: Context
) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        // Inflate the layout, gets the list_row:
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (holder != null) {
            holder.bindView(list[position])
        }
    }

    // Place to create the view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.recipeTitleId)
        var ingredients = itemView.findViewById<TextView>(R.id.recipeIngredientsId)
        var linkButton = itemView.findViewById<Button>(R.id.linkButtonId)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)

        fun bindView(recipe: Recipe) {
            title.text = recipe.title
            ingredients.text = recipe.ingredients

            if (!TextUtils.isEmpty(recipe.thumbnail)) {
                Picasso.with(context)
                    .load(recipe.thumbnail)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(thumbnail)
            } else {
                Picasso.with(context).load(R.mipmap.ic_launcher).into(thumbnail)
            }

            linkButton.setOnClickListener {
                var intent = Intent(context, ShowLinkActivity::class.java)
                intent.putExtra("link", recipe.link.toString())
                context.startActivity(intent)

            }
        }

    }

}