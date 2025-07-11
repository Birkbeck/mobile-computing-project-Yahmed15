package com.example.culinarycompanion

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Obtain the application context
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        // Verify the package name
        assertEquals("com.example.culinarycompanion", appContext.packageName)
    }
}