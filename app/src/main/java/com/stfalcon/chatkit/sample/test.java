package com.stfalcon.chatkit.sample;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.stfalcon.chatkit.sample.prototype.mwmLoginProto;
import com.stfalcon.chatkit.sample.responseModel.LoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        task ta = new task();
        mwmLoginProto gitHubService = mwmLoginProto.retrofit.create(mwmLoginProto.class);
        final Call<LoginData> call = gitHubService.login(10, 12);
        call.enqueue(new Callback<LoginData>() {


            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                Log.i("retro ok",response.body() + " " + response.message() + " " + response.code() + " " + response.raw());
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Log.i("retro wrong", call.toString() + t.toString());
            }
        });
//        ta.execute(call);


    }

    class task extends AsyncTask<Call, Void, Void> {
        @Override
        protected Void doInBackground(Call... calls) {
            try {
                String s = run(calls[0]);
                Log.e("test", s);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "no luck");
            }

            return null;
        }

        String run(Call call) throws IOException {

            Call<String> call_parm = call;
            Response<String> response = call_parm.execute();
            return "";


        }
    }

    class retro implements rest {
        @Override
        public Call<List<String>> listRepos(String user) {
            return null;
        }
    }
}
