package com.anubhav11march.clientapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MenuActivity extends AppCompatActivity {
    private RecyclerView foodlist;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        foodlist = (RecyclerView) findViewById(R.id.foodlist);
        foodlist.setHasFixedSize(true);
        foodlist.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Item");
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MenuActivity.this, Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }
        }
    };
}

    @Override
    protected void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
        FirebaseRecyclerAdapter<Food, FoodViewHolder> FBRA = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.singleitem,
                FoodViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(), model.getImage());

                final String foodkey = getRef(position).getKey().toString();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MenuActivity.this, SingleFood.class).putExtra("Foodid", foodkey));

                    }
                });
            }
        };
        foodlist.setAdapter(FBRA);
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public FoodViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setName(String name){
            TextView namee = (TextView) mView.findViewById(R.id.foodName);
            namee.setText(name);
        }

        public void setDesc(String desc){
            TextView descc = (TextView) mView.findViewById(R.id.foodDesc);
            descc.setText(desc);
        }

        public void setPrice(String price){
            TextView pricee = (TextView) mView.findViewById(R.id.foodPrice);
            pricee.setText(price);
        }

        public void setImage(Context ctx, String image){
            ImageView imagee = (ImageView) mView.findViewById(R.id.imagee);
            Picasso.get().load(image).into(imagee);
        }
    }



}
