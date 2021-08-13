package com.delta_inductions.delta_task_3.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.delta_inductions.delta_task_3.Api.ThedogApi;
import com.delta_inductions.delta_task_3.Model.Labels;
import com.delta_inductions.delta_task_3.Model.ResultResponse;
import com.delta_inductions.delta_task_3.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Result extends AppCompatActivity {
    private static final String TAG = "Result";
    private ThedogApi thedogApi;
    private Retrofit retrofit;
    private String BASE_URL = "https://api.thedogapi.com/";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadingDialog loadingDialog = new LoadingDialog(Result.this);
        loadingDialog.Loadingalert();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissdialog();
            }
        },1500);
        setContentView(R.layout.activity_result);
        textView = findViewById(R.id.textresult);
        thedogApi = getretrofit();
        getResults();

    }

    private ThedogApi getretrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return (retrofit.create(ThedogApi.class));
    }

    private void getResults() {
        Call<List<ResultResponse>> call = thedogApi.getLables("4998955b-fd71-48df-b0a3-e59382b293e8", getIntent().getStringExtra("id"));
           call.enqueue(new Callback<List<ResultResponse>>() {
               @Override
               public void onResponse(Call<List<ResultResponse>> call, Response<List<ResultResponse>> response) {
                  List< ResultResponse> resultResponse =  response.body();
                  ResultResponse response1 = resultResponse.get(0);
                  List<Labels> labelsList = response1.getListlabel();
                  textView.setText("Results\n\n");
                  for(int i=0;i<labelsList.size();i++) {
                      textView.append("Name :"+labelsList.get(i).getName()+"\n"+"Confidence :"+labelsList.get(i).getConfidence()+"\n\n");
                  }

               }

               @Override
               public void onFailure(Call<List<ResultResponse>> call, Throwable t) {

               }
           });
    }

}
