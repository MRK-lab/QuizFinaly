package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quiz.databinding.ActivityQuizBinding;
import com.example.quiz.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    int POINTS=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        int correctAnswers=getIntent().getIntExtra("correct", 0);
        int totalQuestions=getIntent().getIntExtra("total", 0);

        int points=correctAnswers * POINTS;

        binding.score.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        binding.earnedCoins.setText(String.valueOf(points));

        FirebaseFirestore database=FirebaseFirestore.getInstance();

        // kazanılan puanı eski punanın üzerine ekliyor firebasede
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));


        Button restartBtn = findViewById(R.id.restartBtn); // Geri dönüş butonu olarak varsayalım

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Burada ana menü sayfasına dönmek için uygun bir yöntem kullanmalısınız.
                // Örneğin, Intent kullanarak ana menü aktivitesini başlatabilirsiniz.

                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);

                // Eğer geçiş animasyonu kullanmak istiyorsanız, aşağıdaki satırı ekleyebilirsiniz:
                // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }



}