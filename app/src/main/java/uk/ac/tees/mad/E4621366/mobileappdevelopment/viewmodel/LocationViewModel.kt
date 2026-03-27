package uk.ac.tees.mad.E4621366.mobileappdevelopment.viewmodel



import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.LocationHelper


class LocationViewModel(app: Application) : AndroidViewModel(app) {

    private val fusedClient = LocationServices.getFusedLocationProviderClient(app)
    private val helper = LocationHelper(app, fusedClient)
    private val _status = MutableStateFlow("FETCHING")
    val status: StateFlow<String> = _status
    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    fun autoFetchIfEnabled() {
        if (helper.isGpsEnabled()) {
            helper.fetchLocation {
                _location.value = it
            }
        }
    }

    fun forceFetch() {
        helper.fetchLocation {
            _location.value = it
        }
    }

    fun isGpsEnabled(): Boolean = helper.isGpsEnabled()

    fun setLocation(loc: Location) { _location.value = loc }

    fun refresh(context: Context) {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!gpsEnabled) {
            _status.value = "OFF"
            _location.value = null
            return
        }

        // you must also have permission check before this in real code
        _status.value = "FETCHING"
        val fused = LocationServices.getFusedLocationProviderClient(context)

        @SuppressLint("MissingPermission")
        fused.lastLocation.addOnSuccessListener { last ->
            if (last != null) {
                setLocation(last)
            }
//        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
        fused.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnSuccessListener { loc ->
                _location.value = if (loc != null && last != loc) loc else last
                _status.value = if (loc == null) "UNKNOWN" else "OK"
            }
            .addOnFailureListener {
                _status.value = "UNKNOWN"
            }
        }
    }
}
