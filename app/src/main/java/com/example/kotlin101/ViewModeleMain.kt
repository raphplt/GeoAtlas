package com.example.kotlin101

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.google.gson.JsonParser

// Création d'une data class pour typer les informations d'un pays
data class Country(val flag: String,
                   val name : String,
                   val id: Int,
                   val continent : String,
)

// Création du ViewModel qui stockera les données de l'application
class CountriesViewModel : ViewModel() {

    // Définition des variables LiveData
    private val countriesData = MutableLiveData<List<Country>>()
    val liveData: LiveData<List<Country>>
        get() = countriesData

    private val countriesString = MutableLiveData<String>()
    val liveString: LiveData<String>
        get() = countriesString

    //  Fonction pour récupérer les données des pays depuis l'API REST Countries
    fun getCountries() {
        Fuel.get("https://restcountries.com/v3.1/all").response { result ->
            when (result) {
                is Result.Success -> {
                    // On parse les données reçues en JSON
                    val jsonArrayOutput = JsonParser.parseString(String(result.get()))
                    val jo = jsonArrayOutput.asJsonArray
                    val countryList = mutableListOf<Country>()

                    // Itération sur le tableau JSON pour ajouter les données dans le tableau countryArray
                    for (i in 0 until jo.size()) {
                        countryList.add(
                            Country(
                                jo.get(i).asJsonObject["flag"].asString,
                                jo.get(i).asJsonObject["name"].asJsonObject["common"].asString,
                                i,
                                jo.get(i).asJsonObject["continents"].asString
                            )
                        )
                    }

                    // Trie la liste des pays par ordre alphabétique des noms de pays
                    countryList.sortWith(compareBy { it.name })

                    // On utilise les fonctions asynchrones postValue pour mettre à jour les LiveData
                    countriesData.postValue(countryList)
                    countriesString.postValue(String(result.get()))
                }
                is Result.Failure -> {
                    // En cas d'erreur, on affiche un message
                    val exception = result.getException()
                    val message =
                        "Erreur lors de la récupération des pays : ${exception.localizedMessage}"
                    println(message)
                }
            }
        }
    }
}
