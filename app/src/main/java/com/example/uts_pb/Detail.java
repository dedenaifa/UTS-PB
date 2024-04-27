package com.example.uts_pb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uts_pb.data.response.GithubUser;
import com.example.uts_pb.data.retrofit.ApiConfig;
import com.example.uts_pb.data.retrofit.ApiService;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBar = findViewById(R.id.progressBar);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String username = extras.getString("username");
            ApiService apiService = ApiConfig.getApiService();
            Call<GithubUser> userCall = apiService.getUser(username);

            TextView textView = findViewById(R.id.nameTextView);
            TextView textView2 = findViewById(R.id.usernameTextView);
            TextView textView3 = findViewById(R.id.bioTextView);
            ImageView imageView = findViewById(R.id.avatarImageView);

            showLoading(true);
            userCall.enqueue(new Callback<GithubUser>() {
                @Override
                public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                    showLoading(false);
                    if (response.isSuccessful()){
                        GithubUser user = response.body();
                        if (user != null){
                            String name = "Name: " + user.getName();
                            String usernames = "Username: " + user.getUsername();
                            String bio = "Bio: " + user.getBio();
                            String gambar = user.getAvatarUrl();

                            textView.setText(name);
                            textView2.setText(usernames);
                            textView3.setText(bio);
                            Picasso.get().load(gambar).into(imageView);
                        } else {
                            Toast.makeText(Detail.this, "Data pengguna tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        android.util.Log.e("Detail", "Gagal mendapatkan data pengguna: " + response.message());
                        Toast.makeText(Detail.this, "Gagal mendapatkan data pengguna", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GithubUser> call, Throwable t) {

                    android.util.Log.e("Detail", "Error: " + t.getMessage(), t);
                    Toast.makeText(Detail.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}