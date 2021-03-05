package com.khansafzh.authapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.khansafzh.authapp.Add
import com.khansafzh.authapp.R
import com.khansafzh.authapp.model.Recipes
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeAdapter(private val context : Context,
                    private val collection: CollectionReference, options : FirestoreRecyclerOptions<Recipes>
) : FirestoreRecyclerAdapter<Recipes, RecipeAdapter.ViewHolder>(options){
    class ViewHolder (val view : View): RecyclerView.ViewHolder(view) {
        fun bind(recipes: Recipes){
            view.apply {
                val name = "${recipes.recipeName}"
                val jenis = "${recipes.recipeJenis}"
                tv_name_food.text = name
                tv_jenis_food.text = jenis
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false))
    }

    override fun onBindViewHolder(holder: RecipeAdapter.ViewHolder, position: Int, model: Recipes) {
        holder.bind(model)
        holder.itemView.setOnClickListener {
            showOptions(model)
        }
    }

    private fun showOptions(model: Recipes) {
        //popup option
        val builder = AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val option = arrayOf("Edit", "Delete")
        builder.setItems(option){
                dialog, which -> when (which){
            // 0 buat pindah activity
            0 -> context.startActivity(Intent(context, Add::class.java).apply {
                putExtra(Add.EDIT_DATA, true)
                putExtra((Add.EXTRA_DATA), model)
            })
            1 -> optionDelete(model.recipeId)
        }
        }
        builder.create().show()
    }

    private fun optionDelete(recipeId: String) {
        //popup
        val builder = AlertDialog.Builder(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
            .setTitle("Hapus Data")
            .setMessage("Yakin mau hapus?").setPositiveButton(context.getString(R.string.txt_yes)){
                    dialog, which -> deleteById(recipeId)
            }
            .setNegativeButton(android.R.string.cancel,null)
        builder.create().show()
    }

    private fun deleteById(recipeId: String) {
        //untuk ngehapus
        collection.document(recipeId).delete().addOnCompleteListener{
            Toast.makeText(context,"Sucsess", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener{
                Toast.makeText(context,"Delete Failed", Toast.LENGTH_SHORT).show()
            }
    }
}