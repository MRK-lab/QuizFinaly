package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

    }
}