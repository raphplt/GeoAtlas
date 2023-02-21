package com.example.kotlin101

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountriesAdapter(private val dataSet: List<Country>) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    // On instancie onItemClick qui est nul par défaut
    var onItemClick: ((Country) -> Unit)? = null

    // ViewHolder interne, qui contient les éléments de la vue à mettre à jour
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Recherche des vues pour chaque élément de la liste
        private val textViewFlag: TextView = itemView.findViewById(R.id.textViewFlag)
        private val textViewName: TextView = itemView.findViewById(R.id.textView)
        private val textViewContinent: TextView = itemView.findViewById(R.id.textViewRegion)

        // Assignation du onClickItem au constructeur pour pouvoir l'appeler au clic
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(dataSet[adapterPosition])
            }
        }

        // Mise à jour des vues avec les données pour chaque élément de la liste
        fun bind(country: Country) {
            textViewFlag.text = country.flag
            textViewName.text = country.name
            textViewContinent.text = country.continent
        }
    }

    // Création du ViewHolder (vide)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country_card, parent, false)
        return ViewHolder(view)
    }

    // Ajout des données au ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    // Renvoie la taille de la liste (du nombre de pays)
    override fun getItemCount() = dataSet.size
}
