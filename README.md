# Nashik Trip Planner

A smart Android travel application designed for tourists visiting Nashik, Maharashtra. The app helps users explore attractions, create custom trip itineraries, book hotels, check traffic routes, and interact with an AI-powered travel assistant.

## Features

- Smart trip planning based on mood, duration, and budget
- AI travel assistant for destination-related questions
- Hotel explorer with ratings, amenities, and booking options
- Route and traffic information for common local destinations
- Attraction discovery for heritage, spiritual, and scenic spots
- User profile and login/signup flow
- Onboarding and modern Material UI design

## Tech Stack

- Android (Java)
- Android Studio
- Gradle
- Google Maps SDK
- Glide for image loading
- Material Components
- Gson for JSON handling

## Project Overview

This app aims to make trip planning easier for visitors to Nashik by combining essential travel features into a single mobile experience. It includes both practical travel information and personalized recommendations tailored to traveler preferences.

## Screenshots

Add screenshots here after launching the app in Android Studio.

## Getting Started

### Prerequisites

- Android Studio
- JDK 8 or above
- Android SDK
- A Google Maps API key

### Setup

1. Open the project in Android Studio.
2. Let Gradle sync complete.
3. Add your local Android SDK path in `local.properties`:

```properties
# Windows
sdk.dir=C\:\Users\YourName\AppData\Local\Android\Sdk

# macOS/Linux
sdk.dir=/Users/YourName/Library/Android/sdk
```

4. Replace the placeholder Google Maps API key in `app/build.gradle`:

```gradle
manifestPlaceholders = [mapsApiKey: "YOUR_GOOGLE_MAPS_API_KEY_HERE"]
```

5. Build and run the app on an emulator or physical device.

## Project Structure

```text
NashikTripPlanner/
├── app/
│   ├── src/
│   ├── build.gradle
│   └── proguard-rules.pro
├── gradle/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── local.properties
├── README.md
├── SETUP_GUIDE.md
├── PROJECT_EXPLANATION.txt
└── .gitignore
```

## Notes

- The app uses a placeholder Google Maps API key and must be configured before running map-related features.
- This project is intended for educational/final-year demonstration purposes unless extended for production use.

## Author

Vyanku051

## License

This project is for academic demonstration and personal portfolio use.
