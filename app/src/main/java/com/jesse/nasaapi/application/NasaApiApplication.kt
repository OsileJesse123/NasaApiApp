package com.jesse.nasaapi.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// The application class here signifies that this app would be using hilt for dependency injection
@HiltAndroidApp
class NasaApiApplication: Application()