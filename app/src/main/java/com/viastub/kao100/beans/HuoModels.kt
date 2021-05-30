package com.viastub.kao100.beans

data class UserSignupResponse(
    var code: String?,
    var message: String?,
    var expiryInSeconds: Int?
)

enum class UserSignupResponseCode {
    invalid_password_or_username,
    username_conflict,
    validated
}
