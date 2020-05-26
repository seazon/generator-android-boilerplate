package <%= appPackage %>.api

import retrofit2.Response

interface ApiManager {

    suspend fun logIn(data: String): Response<String>

}
