package com.example.kotlin101

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


// Création du fragment HomeFragment (Page d'accueil)
class HomeFragment : Fragment() {
    // Initialisation de la vue du fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflation du layout pour le fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // Configuration de la vue du fragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Création d'une instance de countriesViewModel pour récupérer les données
        val countriesViewModel: CountriesViewModel = ViewModelProvider(this)[CountriesViewModel::class.java]

        // Appel de la fonction getCountries
        countriesViewModel.getCountries()

        // Recherche de la RecyclerView dans la vue du fragment, et configuration du gestionnaire de disposition
        val recyclerview = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerview.layoutManager = LinearLayoutManager(context)

        // Déclaration d'une variable pour l'adaptateur de RecyclerView
        var adapter : CountriesAdapter

        // Observation du tableau obtenu à partir de countryViewModel pour mettre à jour le recyclerView
        countriesViewModel.liveData.observe(viewLifecycleOwner) { it ->
            // Initialisation de l'adaptateur à partir du tableau des pays
            adapter = CountriesAdapter(it)
            // Configuration de l'interface pour gérer le clic sur un élément du recyclerView
            adapter.onItemClick = {
                val bundle = Bundle()

                bundle.putString("country", it.name)
                bundle.putString("countries", countriesViewModel.liveString.value)
                bundle.putInt("index", it.id)
                // Navigation vers le fragment dataFragment en passant le Bundle de données
                findNavController().navigate(R.id.action_homeFragment_to_dataFragment, bundle)
            }
            // Mise à jour du recyclerView avec l'adaptateur configuré
            recyclerview.adapter = adapter

        }
    }
}