package com.example.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quiz.databinding.RowLeaderboardsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class LeaderboardsAdapter extends RecyclerView.Adapter<LeaderboardsAdapter.LeaderboardViewHolder>{

    Context context;
    ArrayList<User> users;
    public LeaderboardsAdapter(Context context, ArrayList<User> users){
        this.context=context;
        this.users=users;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_leaderboards, parent,false);
        return new LeaderboardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {

        FirebaseFirestore database=FirebaseFirestore.getInstance();

        User user=users.get(position);

        holder.binding.name.setText(user.getName());
        holder.binding.coins.setText(String.valueOf(user.getCoins()));
        holder.binding.index.setText(String.format("#%d",position+1));
        holder.binding.like.setText(String.valueOf(user.getLike()));

        /* bir kişinin sadece bir gün sadece bir kişiyi beğenmesi gerekiyor. Bunun için aklıma gelen düşünce:
            her kullanıcı için firebasede integer tipinde likeDate değişkeni oluşturacağız
            likeDate değerin getter ve setter değerlerini user Activity kısmıana yazacapız *
                beğeni butonuna bastıktan sonra firebaseden bu değeri alıyoruz o gün ayın kaçı olduğuna bakıyoruz
                eğer iki değer aynıysa aynı gün alnamına gelir ve değişiklik yazmaz
                eğer değerler farklı ise beğeni sayısını bir arttırır ve firebasedeki değeri o ayın günü olarak güncelleyeceğiz
                bu yönetemin bir sıkıntısı var o da;
                    ayın 1 inde güncelleme yaptıktan sonra diğer ayın tam 1 inde güncelleme yapamayacak anlamına gelir.
                    ama basit olması nedeniyle bunu göze alıyoruz ve bu şekilde yapılmasını onaylıyoruz
         */

        holder.binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("users").document(FirebaseAuth.getInstance().getUid()); // mevcut kullanıcının userId sini alıyorum
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Object value=documentSnapshot.get("likeData");
                        Long longValue=(Long) value;

                        int likeDataFirebase=longValue.intValue(); // firebaseden gelern değer

                        Calendar calendar = Calendar.getInstance();
                        int likeDateCurrently = calendar.get(Calendar.DAY_OF_MONTH); // şu anki ayın günü

                        if (likeDateCurrently!=likeDataFirebase){
                            long updatedLikes=user.getLike()+1;
                            user.setLike(updatedLikes);

                            database.collection("users")
                                    //.document(FirebaseAuth.getInstance().getUid())// bu şekilde açık olan kullanıcıya like verisini girer
                                    .document(user.getUserId())
                                    .update("like",updatedLikes);

                            database.collection("users")
                                            .document(FirebaseAuth.getInstance().getUid())
                                                    .update("likeData",likeDateCurrently);

                            holder.binding.like.setText(String.valueOf(user.getLike())); // beğeni sonrası recently viewda mevcut yeri güncelliyor

                        }
                        else{
                            Toast.makeText(context, "Günde sadece bir kez beğeni yapabilirsiniz...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "HATA...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        Glide.with(context)
                .load(user.getProfile())
                .into(holder.binding.imageView9);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder{

        RowLeaderboardsBinding binding;
        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RowLeaderboardsBinding.bind(itemView);
        }
    }

}
