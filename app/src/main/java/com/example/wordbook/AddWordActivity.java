package com.example.wordbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;

public class AddWordActivity extends AppCompatActivity {

    EditText etWord, etReading, etMeaning;
    TextView tvTarget;
    Button btnAdd, btnClose, btnTranslate;
    DeepLApi deepLApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        etWord = findViewById(R.id.etWord);
        etReading = findViewById(R.id.etReading);
        etMeaning = findViewById(R.id.etMeaning);
        tvTarget = findViewById(R.id.tvTarget);
        btnAdd = findViewById(R.id.btnAdd);
        btnClose = findViewById(R.id.btnClose);
        btnTranslate = findViewById(R.id.btnTranslate);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-free.deepl.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        deepLApi = retrofit.create(DeepLApi.class);

        String wordbookName = getIntent().getStringExtra("wordbookName");
        if (wordbookName == null) {
            wordbookName = "내 단어장";
        }
        tvTarget.setText(getString(R.string.target_format, wordbookName));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWord();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateMeaning();
            }
        });
    }

    private void translateMeaning() {
        String meaning = etMeaning.getText().toString().trim();

        if (meaning.isEmpty()) {
            etMeaning.setError(getString(R.string.error_meaning_required));
            return;
        }

        String authKey = "DeepL-Auth-Key " + BuildConfig.DEEPL_API_KEY;
        DeepLRequest request = new DeepLRequest(meaning, "JA", "KO");

        deepLApi.translate(authKey, request).enqueue(new retrofit2.Callback<DeepLResponse>() {
            @Override
            public void onResponse(Call<DeepLResponse> call, retrofit2.Response<DeepLResponse> response) {
                if (response.isSuccessful() && response.body() != null
                        && !response.body().translations.isEmpty()) {
                    String result = response.body().translations.get(0).text;
                    etWord.setText(result);
                } else {
                    Toast.makeText(AddWordActivity.this, "번역 실패: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeepLResponse> call, Throwable t) {
                Toast.makeText(AddWordActivity.this, "인터넷 연결을 확인해 주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addWord() {
        String word = etWord.getText().toString().trim();
        String reading = etReading.getText().toString().trim();
        String meaning = etMeaning.getText().toString().trim();

        if (word.isEmpty()) {
            etWord.setError(getString(R.string.error_word_required));
            return;
        }
        if (meaning.isEmpty()) {
            etMeaning.setError(getString(R.string.error_meaning_required));
            return;
        }

        Word newWord = new Word(word, reading, meaning);

        Intent result = new Intent();
        result.putExtra("word", newWord);
        setResult(RESULT_OK, result);

        Toast.makeText(this, "단어가 추가되었습니다", Toast.LENGTH_SHORT).show();
        finish();
    }
}