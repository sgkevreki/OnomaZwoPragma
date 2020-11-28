package com.example.onomazwopragmaproject

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class GameRecyclerviewAdapter(private val categoriesList: List<String>,
                              private val categoriesList2: List<Drawable>,
                              private val categoriesList3: List<Drawable>): RecyclerView.Adapter<GameRecyclerviewAdapter.MyViewHolder>(){
    // STOP. GO WATCH THIS: https://www.youtube.com/watch?v=17NbUcEts9c. NOW GO

    // tldr: MyViewHolder is a necessary class that contains references to all the things on the 'card_for_category' layout
    // Provide a reference to the views for each data item.
    // You create a class that has references to every view inside our CardView. Then later in the adapter when you need to access one of the views you do it through this ViewHolder (or holder) class!
    // See on bindViewHolder for examples
    // itemView is like a reference to the whole 'card_for_category' layout...
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // ...and here we declare the views inside that layout...
        internal var categoryImage: ImageView
        internal var categoryName: TextView
        internal var categoryUserInput: EditText
        internal var categoryBackground: ConstraintLayout

        // ...and associate them with the correct views!
        // init { ... } is one of many ways to write constructors in Kotlin.
        init {
            categoryImage = itemView.findViewById(R.id.category_image)
            categoryName = itemView.findViewById(R.id.category_name)
            categoryUserInput = itemView.findViewById(R.id.category_user_input)
            categoryBackground = itemView.findViewById(R.id.layout)

        }

    }


    // Create new Views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.card_category, parent, false) as CardView
        return MyViewHolder(myView)
    }

    // This is called every time we must show an item on the list
    // In here we set the content of the views, e.g. the correct category_name and the correct category_image.
    // From the position argument we know for which item in the list we are talking about and can use it to set correct data as follows.
    // Note that to access any view you go through the holder reference, a reference to the MyViewHolder class we wrote above! (This is where it is used)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.categoryName.text = categoriesList[position]
        holder.categoryUserInput.hint = categoriesList[position]
        holder.categoryImage.setImageDrawable(categoriesList2[position])
        holder.categoryBackground.background = categoriesList3[position]

    }

    // The layoutManager must always know the total of items in the container, so we always return this:
    override fun getItemCount() = categoriesList.size

    // That's all!

}