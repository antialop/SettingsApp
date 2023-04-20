package com.example.settingsactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.settingsactivity.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Delegado. Crear una unica instancia de la base de datos
//Singleton, para tener una unica instania en la bas de datos
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    companion object{
        const val  VOLUME_LVL = "volume_lvl"
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        binding.rsVolume.addOnChangeListener{_,value,_ ->
            Log.i("antia", "EL valor es $value")
            CoroutineScope(Dispatchers.IO).launch {
                saveVolume(value.toInt())
            }
        }
    }


    private suspend fun saveVolume(value:Int){
        dataStore.edit {preference ->
            preference[intPreferencesKey(VOLUME_LVL)] = value
        }
    }

}