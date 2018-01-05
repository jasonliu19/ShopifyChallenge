/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jason
 */

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HTTPHandler {
  OkHttpClient client = new OkHttpClient();

  String run(String url) throws IOException {
    Request request = new Request.Builder()
        .url(url)
        .build();

    try (Response response = client.newCall(request).execute()) {
      return response.body().string();
    }
  }

}