package uk.ac.tees.mad.E4621366.mobileappdevelopment.model


data class RegisterRequest(
    val email: String,
    val password: String,
//    val gender: String,
    val firstname: String,
    val middlename: String,
//    val lastname: String,
//    val dateOfBirth: String,
//    val streetAddress: String,
    val town: String,
    val city: String,
    val county: String,
    val country: String,
    val postcode: String,
//    val phoneNumber: String,
//    val emergencyContactName: String,
//    val emergencyContactPhoneNumber: String
)
