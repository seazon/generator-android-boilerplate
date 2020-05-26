package <%= appPackage %>.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ApiManagerImpl @Inject constructor(
    private val rest: RestfulApiService
) : ApiManager {

    override suspend fun logIn(data: String): Response<String> =
        withContext(Dispatchers.IO) {
            rest.logIn(data)
        }

}