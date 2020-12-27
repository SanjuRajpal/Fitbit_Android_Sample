package com.sr.myApp.helper

class Consts {

    companion object {
        const val clientId = "22BYLF"
        const val clientSecret = "50bd2e42187c9a7e59da3cf657f2d01a"
        const val redirectUrl = "https://www.fitbit.com"
        const val authURL =
            "https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=$clientId&redirect_uri=$redirectUrl&scope=activity heartrate location nutrition profile settings sleep social weight&expires_in=604800"
    }
}