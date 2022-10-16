package com.muhammed.noteapp.util

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

object NavigationExtensions {
    const val TAG = "NavigationUtil"

    fun Fragment.navigate(@IdRes resId: Int) {
        this.navigate(resId, null)
    }

    fun Fragment.navigate(@IdRes resId: Int, args: Bundle?) {
        try {
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(resId, args)
        } catch (exception: java.lang.IllegalArgumentException) {
            Log.e(TAG, "navigate error IllegalArgumentException, please check it." + exception.message)
        } catch (exception: java.lang.IllegalStateException) {
            Log.e(TAG, "navigate error IllegalStateException, please check it" + exception.message)
        }
    }
}