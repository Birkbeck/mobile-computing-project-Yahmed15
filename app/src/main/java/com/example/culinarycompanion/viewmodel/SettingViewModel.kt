package com.example.culinarycompanion.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.culinarycompanion.data.AppDatabase
import com.example.culinarycompanion.data.Setting
import kotlinx.coroutines.launch

class SettingViewModel(private val dao: com.example.culinarycompanion.data.SettingDao) : ViewModel() {
    val setting: LiveData<Setting> = dao.get()
    fun upsert(s: Setting) = viewModelScope.launch { dao.upsert(s) }
}

class SettingViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = AppDatabase.getInstance(context).settingDao()
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}