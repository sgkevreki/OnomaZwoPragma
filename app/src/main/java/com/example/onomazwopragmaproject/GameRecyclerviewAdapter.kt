package com.example.onomazwopragmaproject

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class GameRecyclerviewAdapter(private val categoriesList: List<String>): RecyclerView.Adapter<GameRecyclerviewAdapter.MyViewHolder>(){
    // STOP. GO WATCH THIS: https://www.youtube.com/watch?v=17NbUcEts9c. NOW GO

    // tldr: MyViewHolder is a necessary class that contains references to all the things on the 'card_for_category' layout
    // Provide a reference to the views for each data item.
    // You create a class that has references to every view inside our CardView. Then later in the adapter when you need to access one of the views you do it through this ViewHolder (or holder) class!
    // See on bindViewHolder for examples
    // itemView is like a reference to the whole 'card_for_category' layout...
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // ...and here we declare the views inside that layout...
        internal var category_image: ImageView
        internal var category_name: TextView
        internal var category_user_input: EditText

        // ...and associate them with the correct views!
        // init { ... } is one of many ways to write constructors in Kotlin.
        init {
            category_image = itemView.findViewById(R.id.category_image)
            category_name = itemView.findViewById(R.id.category_name)
            category_user_input = itemView.findViewById(R.id.category_user_input)
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
        holder.category_name.text = categoriesList[position]
        holder.category_user_input.hint = categoriesList[position]
        // holder.category_image = ...
        // I don't have anything to set on the image at the moment, but this is where it would be set.
    }

    // The layoutManager must always know the total of items in the container, so we always return this:
    override fun getItemCount() = categoriesList.size

    // That's all!

}