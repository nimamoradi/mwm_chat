package com.stfalcon.chatkit.sample;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class test extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        task ta = new task();
        ta.execute();

    }

    class task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String s = run("https://facebook.github.io/react-native/movies.json");
                Log.e("test", s);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "no luck");
            }

            return null;
        }

        String run(String url) throws IOException {

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }
    class retro implements rest{
        @Override
        public Call<List<String>> listRepos(String user) {
            return null;
        }
    }
}
