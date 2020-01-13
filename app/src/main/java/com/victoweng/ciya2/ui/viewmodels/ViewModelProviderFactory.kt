package com.victoweng.ciya2.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelProviderFactory @Inject constructor(val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>)  : ViewModelProvider.Factory {

    val TAG = "ViewModelProviderFactory"

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator = creators.get(modelClass)
        if(creator == null) {
            for (entry: Map.Entry<Class<out ViewModel>, Provider<ViewModel>> in creators.entries) {
                if (modelClass.isAssignableFrom(entry.key)) {
                    creator = entry.value
                }
            }
        }

        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

}