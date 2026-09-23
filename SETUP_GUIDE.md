# Nashik Smart Trip Planner - Setup Guide

## Quick Start (3 Steps)

### Step 1: Open in Android Studio
1. Download & install Android Studio: https://developer.android.com/studio
2. File → Open → Select the NashikTripPlanner folder → Trust Project
3. Wait for Gradle sync to complete (~3-5 min)

### Step 2: Create Emulator
- Tools → Device Manager → Create Device → Pixel 7, API 34 → Finish → ▶️ Start

### Step 3: Run
- Click ▶️ Run button → App launches!

## Add Google Maps (Optional but Recommended)
1. Go to: https://console.cloud.google.com
2. Create project → Enable Maps SDK for Android
3. Create API Key → Copy it
4. Open app/build.gradle → Replace: `YOUR_GOOGLE_MAPS_API_KEY_HERE`

## Fix "SDK not found" Error
Create `local.properties` in root folder:
```
# Windows:
sdk.dir=C\:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
# Mac/Linux:
sdk.dir=/Users/YourName/Library/Android/sdk
```

## Features Included
- ✅ Splash → Onboarding → Login/Signup flow
- ✅ Home dashboard with trips, quick actions, weather
- ✅ Trip Creation: 4-step wizard (Name→Dates→Travelers→Budget)
- ✅ Mood Preferences: 6 moods (Pilgrimage/Wine/Adventure/Heritage/Nature/Family)
- ✅ AI Itinerary Generation with loading animation
- ✅ Trip Overview: Itinerary/Budget/Packing tabs (packing checkboxes persist!)
- ✅ Hotel Recommendations: search, sort, book button
- ✅ Map with Google Maps + 12 Nashik attraction markers
- ✅ AI Chat: real responses for 15+ Nashik topics
- ✅ Traffic & Routes: 8 routes with Google Maps directions
- ✅ Profile: edit profile, toggles, language/currency picker, logout
- ✅ Notifications: Clear All dialog, mark read, delete individual

## App Version: 1.0.0
## Package: com.nashik.tripplanner
## Min SDK: 24 (Android 7.0+)
