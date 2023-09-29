package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quiz.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);



        /*final ArrayList<CategoryModel> categories =new ArrayList<>();

        /*categories.add(new CategoryModel("","Mathematics","https://icons-for-free.com/iconfiles/png/512/math+tutor+icon-1320195955563127435.png"));
        categories.add(new CategoryModel("","Science","https://icons-for-free.com/iconfiles/png/512/math+tutor+icon-1320195955563127435.png"));
        categories.add(new CategoryModel("","History","https://icons-for-free.com/iconfiles/png/512/math+tutor+icon-1320195955563127435.png"));
        categories.add(new CategoryModel("","Language","https://icons-for-free.com/iconfiles/png/512/math+tutor+icon-1320195955563127435.png"));
        categories.add(new CategoryModel("","Puzzle","https://icons-for-free.com/iconfiles/png/512/math+tutor+icon-1320195955563127435.png"));
        categories.add(new CategoryModel("","Drama","https://icons-for-free.com/iconfiles/png/512/math+tutor+icon-1320195955563127435.png"));
        */

        /*final CategoryAdapter adapter=new CategoryAdapter(this,categories);
        database.collection("categories")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                categories.clear();
                                for (DocumentSnapshot snapshot : value.getDocuments()){
                                    CategoryModel model = snapshot.toObject(CategoryModel.class);
                                    model.setCategoryId(snapshot.getId());
                                    categories.add(model);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });

        binding.categorryList.setLayoutManager(new GridLayoutManager(this,2));
        binding.categorryList.setAdapter(adapter);

        BU KISIM FRAGMENT HOME KISMINA GEÇTİ
        */


        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new HomeFragment());
        transaction.commit();

        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                        transaction.replace(R.id.content, new HomeFragment());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.content, new LeaderboardsFragment());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.content, new WalletFragment());
                        transaction.commit();
                        break;
                    case 3:
                        transaction.replace(R.id.content, new ProfileFragment());
                        transaction.commit();
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.wallet){
            Toast.makeText(this, "wallet is clicked.", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}