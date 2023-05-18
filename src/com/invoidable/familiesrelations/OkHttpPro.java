package com.invoidable.familiesrelations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OkHttpPro {

    // This class is used only when connecting the database to a REST API Server and not in a json file in the executable directory

    private final okhttp3.OkHttpClient httpClient = new okhttp3.OkHttpClient();

    public OkHttpPro() {

    }

    public ArrayList<Family> sendGet() throws Exception {
        Request request = new Request.Builder()
                .url("http://127.0.0.1:6969/getFamilies")
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String jsonData = response.body().string();

            JSONArray tempArray = new JSONArray(jsonData);

            Type listType = new TypeToken<ArrayList<Family>>(){}.getType();
            List<Family> myFamilyList = new Gson().fromJson(String.valueOf(tempArray), listType);

            ArrayList<Family> familia = new ArrayList<Family>(myFamilyList);

            return familia;
        }
    }

    public void sendPost(Family family) throws Exception {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        String data = new Gson().toJson(family);

        RequestBody body = RequestBody.create(JSON, data);

        Request req = new Request.Builder().url("http://127.0.0.1:6969/newFamily")
                .post(body).build();

        try (Response res = httpClient.newCall(req).execute()) {
            if (!res.isSuccessful()) throw new IOException("Unexpected Code " + res);
            System.out.println(res.body().string());
        }
    }

}
