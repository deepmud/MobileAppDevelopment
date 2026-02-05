package uk.ac.tees.mad.E4621366.mobileappdevelopment.screen.common
import android.location.Geocoder
import android.location.Location
import java.util.Locale
import android.content.Context
import android.annotation.SuppressLint
import android.location.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority

fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double): String? {
    val geocoder = Geocoder(context, Locale.getDefault())
    return try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            val countryName = address.countryName    // Country
            val countryCode = address.countryCode    // Country
            val locality = address.locality          // City/town
            val adminArea = address.adminArea        // State/province
            "$locality, $countryName, $countryCode"
        } else {
            "Unknown"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}




class LocationHelper(
    private val context: Context,
    private val fusedClient: FusedLocationProviderClient
) {

    fun isGpsEnabled(): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun fetchLocation(onResult: (Location?) -> Unit) {
        fusedClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->
            onResult(location)
        }
    }
}
