# LorikeetDeviceApp

The DeviceApp is a robust, self-sufficient application designed to ensure uninterrupted advertisement playback on car-top devices.
It adapts to various connectivity scenarios and maintains performance integrity by caching content and persisting data locally.
The project requires developer with expertise in Kotlin, Android development, and experience with the mentioned libraries to implement and optimize this application for a seamless advertisement display experience.

# Current Setup

install io.lorikeet.deviceapp.apk
adb shell am broadcast -a io.lorikeet.ENCRYPT_AND_STORE --es username "device.1" --es password "Abc123123" --es clientId "HgRVNNTWlRSdedG2iCL9Qvd9og6imj9m"

# DeviceApp requirements

DeviceApp should be written in Kotlin for Android 9 OS
The app needs to work on Android 9 (API 28) (This is crucial)

# Overview

The point of the Application is to play advertisements in a playlist that has been provided by the server.
The app needs to be fault tollorant and work without ANY user interaction.

This means that it needs to work based on its environment.

-   Device has internet connection and API is online
    -   Download Advertisements periodically (And cache them)
    -   Play Cached Advertisements
    -   Send current location on every Advertisement change
-   Android Device has no connection
    -   IF Advertisements cached
        -   Play As normal persist everything to local DB
    -   else (No Advertisements cached)
        -   Play A Local Image
-   External API is down
    -   IF Advertisements cached
        -   Play As normal persist everything to local DB
    -   else (No Advertisements cached)
        -   Play A Local Image

# API Calls

## Schedule Call

Provides an Array of Advertisements to Device App

### Endpoint

GET:https://api.lorikeet.io/api/v1/device/schedule

### Data Structure returned

```JSON
{
  "data": {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6", // Device ID
    "scheduleSetTimeUtc": "2024-06-04T11:55:01.388", //Schedule set time
    "schedule": [
	  {
		"campaignId": "3fa85f64-5717-4562-b3fc-2c963f66afa6", //Campaign ID
		"name": "Ad 1", //Campaign Name
		"advertisement": {
		  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6", //Advertisement ID
		  "name": "Ad 1", // Advertisement Name
		  "duration": 10, // Advertisement Duration in seconds
		  "description": "Description 1", // Advertisment description
		  "contentUrl": "https://s3-ap-southeast-2.amazonaws.com/lorikeet-au-tenant-advertisement/25e2ece3-c742-46b2-b80f-19c08e8f9889_7a23cf7d-5068-473c-b624-56f8afd1efe6", // URL
		  "contentType": "Image" //Content Type (Image, Video, WebView)
		}
	  }
      // ...
    ]
  }
}
```
## Location Logs

Sends location logs to server

POST:https://api.lorikeet.io/api/v1/device/location-logs

### Data Structure returned

The Advertisements depend on GPS logs being sent to the server as fast as possible

```JSON
{
  "deviceLocationLogs": [
    {
      "sessionId": "3fa85f64-5717-4562-b3fc-2c963f66afa6", //Random GUID Generated everytime the application starts
      "contentId": "3fa85f64-5717-4562-b3fc-2c963f66afa6", //ContentID
      "lat": 0,
      "long": 0,
      "localTime": "2024-06-04T11:54:44.099Z"
    }
  ]
}
```
# Current External Libraries

-   Room
-   Glide
-   Media3 (ExoPlayer)
-   Okhttp
-   GSON

# Current Theoretical Structure

```
/io.lorikeet.deviceapp
    /data
        /models
            Advertisement.kt
            LocationLog.kt
        /repositories
            AdvertisementDao.kt
            LocationLogDao.kt
        /datasources
            RemoteAdvertisement.kt
            RemoteLocationLog.kt
        /apimodels
            RemoteAdvertisementModels.kt // API DTO
            RemoteLocationLogModels.kt // API DTO
        /database
            AppDatabase.kt
        /converters
            Converters.kt
        /enums
            AdvertisementType.
    /presentation
        /views
            MainActivity.kt
            UIManager.kt
        /viewmodels
            MainViewModel.kt
            AdvertisementViewModel.kt
            LocationViewModel.kt
        /services
            AuthService.kt
            LocationService.kt
            ScheduleManager.kt
            CacheManager.kt
            AdvertisementCache.kt
        /themes
            Theme.kt
    /core
        AppManager.kt
        /caching
            GlideCache.kt
            ExoPlayerCache.kt
    /res ... (Resource files like layouts, strings, etc.)
```
