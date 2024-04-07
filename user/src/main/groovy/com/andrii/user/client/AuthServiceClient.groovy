package com.andrii.user.client

import groovy.json.JsonOutput
import okhttp3.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class AuthServiceClient {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8")
    @Value('${save-credentials-endpoint}')
    String saveCredentialsEndpoint
    OkHttpClient client = new OkHttpClient()

    void saveCredentials(Map createRequest) {
        RequestBody body = RequestBody.create(JsonOutput.toJson(createRequest), JSON)
        Request request = new Request.Builder()
                .url(saveCredentialsEndpoint)
                .post(body)
                .build()

        Call call = client.newCall(request)
        Response response = call.execute()
        if (response.code() != 200) {
            throw new ResponseStatusException(response.code(), response.body()?.string(), null)
        }
    }
}
