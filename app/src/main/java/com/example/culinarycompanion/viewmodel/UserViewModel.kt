package com.example.culinarycompanion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.culinarycompanion.data.AppDatabase
import com.example.culinarycompanion.data.User
import com.example.culinarycompanion.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for user registration, login, and session management.
 */
class UserViewModel(private val app: Application) : AndroidViewModel(app) {
    private val dao: UserDao = AppDatabase.getInstance(app).userDao()

    // Backing LiveData holding the currently‐logged‐in user (or null)
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    // Convenience getters if you ever bind directly to LiveData fields
    val userName: String?
        get() = _currentUser.value?.username

    val fullName: String?
        get() = _currentUser.value?.fullName

    val email: String?
        get() = _currentUser.value?.email

    /**
     * Registers a new user (inserts into DB) and, if successful,
     * updates currentUser. Returns a LiveData that emits the new rowId (>0 if OK).
     */
    fun register(
        username: String,
        fullName: String,
        email: String,
        password: String
    ): LiveData<Long> = liveData(Dispatchers.IO) {
        // Insert user
        val newId = dao.insert(
            User(
                username = username,
                fullName = fullName,
                email    = email,
                password = password
            )
        )
        // If insertion succeeded, post the full User record into currentUser
        if (newId > 0L) {
            _currentUser.postValue(
                User(
                    id       = newId,
                    username = username,
                    fullName = fullName,
                    email    = email,
                    password = password
                )
            )
        }
        emit(newId)
    }

    /**
     * Attempts to authenticate. If a matching user is found,
     * updates currentUser and emits true. Emits false otherwise.
     */
    fun login(username: String, password: String): LiveData<Boolean> = liveData(Dispatchers.IO) {
        val user = dao.authenticate(username, password)
        _currentUser.postValue(user)
        emit(user != null)
    }

    /** Clears the current session (logs out). */
    fun logout() {
        _currentUser.value = null
    }

    /**
     * Factory for creating UserViewModel with an Application argument.
     *
     * Usage:
     *   private val userVM: UserViewModel by activityViewModels {
     *     UserViewModel.Factory(requireActivity().application)
     *   }
     */
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                return UserViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}