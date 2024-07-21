package com.testapplication.www.onboardingscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.util.constants.Constants.DEFAULT_USER_ID

class OnboardingViewModel(private val preferencesManager: PreferencesManager):ViewModel() {

    fun getUserId(): Long {
        return preferencesManager.getUserId(DEFAULT_USER_ID)
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OnboardingViewModel(PreferencesManager(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}