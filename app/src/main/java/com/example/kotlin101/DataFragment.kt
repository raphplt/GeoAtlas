package com.example.kotlin101

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonParser

// Création du fragment data (page d'affichage des informations d'un pays)
class DataFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflation du layour pour ce fragment
        val view =  inflater.inflate(R.layout.fragment_data, container, false)

        // Récupération des éléments de la vue
        val mainText = view.findViewById<TextView>(R.id.textName)
        val flagText = view.findViewById<TextView>(R.id.flagView)
        val currencyText = view.findViewById<TextView>(R.id.currencyText)
        val capitalText = view.findViewById<TextView>(R.id.capital)
        val timezoneText = view.findViewById<TextView>(R.id.timezoneView)
        timezoneText.movementMethod = ScrollingMovementMethod()
        val languagesText = view.findViewById<TextView>(R.id.languagesView)
        languagesText.movementMethod = ScrollingMovementMethod()
        val continentText = view.findViewById<TextView>(R.id.continent)
        val populationText = view.findViewById<TextView>(R.id.poulation)

        // Récupération du pays sélectionné dans 'country'
        val jsonArrayOutput = JsonParser.parseString(arguments?.getString("countries"))
        val jo = jsonArrayOutput.asJsonArray
        val index = arguments?.getInt("index")
        val country = jo.get(index!!).asJsonObject

        // Récupération des devises
        val rawCurrenciesArray = country.getAsJsonObject("currencies").keySet().iterator()
        var currencies = arrayOf<String>()
        // Boucle pour récupérer toutes les devises
        while (rawCurrenciesArray.hasNext()) {
            currencies += (country.getAsJsonObject("currencies").getAsJsonObject(rawCurrenciesArray.next()).get("name")).asString
        }

        // Récupération des langues
        val rawLanguagesArray = country.getAsJsonObject("languages").keySet().iterator()
        var languages = arrayOf<String>()
        // Boucle pour récupérer toutes les langues
        while (rawLanguagesArray.hasNext()) {
            languages += (country.getAsJsonObject("languages").get(rawLanguagesArray.next())).asString
        }
        var languagesString = ""
        for (lang in languages) {
            languagesString += lang + "\n"
        }

        // Récupération des fuseaux horaires
        val timezoneArray = country.getAsJsonArray("timezones")
        var timezoneString = ""
        // Boucle pour récupérer tous les fuseaux horaires
        for (time in timezoneArray) {
            timezoneString += time.asString + "\n"
        }

        // Récupération du nom ducontinent
        val continent = country.getAsJsonArray("continents")[0].asString

        // Récupération du nom de la capitale
        val capitalName = country.getAsJsonArray("capital")[0].asString

        // Récupération de la taille de la population
        val populationSize = country.get("population").asString

        // Définition des TextViews
        mainText.text = jo.get(index).asJsonObject["name"].asJsonObject["common"].asString
        flagText.text = jo.get(index).asJsonObject["flag"].asString
        currencyText.text = "Currency: ${currencies[0]}"
        capitalText.text = "Capital: $capitalName"
        timezoneText.text = "Timezones:\n$timezoneString"
        continentText.text = "Continent: $continent"
        languagesText.text = "Languages: \n$languagesString"
        populationText.text = "Population: $populationSize"

        return view
    }
}