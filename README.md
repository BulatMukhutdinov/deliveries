<a href="https://github.com/BulatMukhutdinov/deliveries/blob/master/deliveries.apk?raw=true" download>Click to download the apk</a>

# Build
To build and run the app add your own Google Maps API Key in AndroidManifest.xml 
```
<meta-data
        android:name="com.google.android.geo.API_KEY"
        android:value="YOUR_API_KEY"/>
```
# Tests 
As a bonus part there are examples of instrumented unit tests. Plug in at least one phone/emulator and call
```
./gradlew connectedAndroidTest
```
to run the tests
