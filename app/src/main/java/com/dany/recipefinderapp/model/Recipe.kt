package com.dany.recipefinderapp.model

import android.icu.text.CaseMap

class Recipe {

    var title: String? = null
    var link: String? = null
    var ingredients: String? = null
    var thumbnail: String? = null

    constructor() {
        this.title = title
        this.link = link
        this.ingredients = ingredients
        this.thumbnail = thumbnail
    }
}