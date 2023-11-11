package com.example.newsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// The application class for the NewsApp, annotated with @HiltAndroidApp.
// This annotation triggers the generation of Dagger Hilt components.
@HiltAndroidApp
class NewsApplication: Application() {
}