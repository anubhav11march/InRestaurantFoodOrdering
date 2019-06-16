package com.anubhav11march.clientapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SingleFood extends AppCompatActivity {
    private TextView name, desc, price;
    private DatabaseReference databaseReference, userdata, myref;
    private String foodkey, namee, descc, pricee, imagee;
    private ImageView imageView;
    private Button button;
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);
        foodkey = getIntent().getExtras().getString("Foodid");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Item");
        name = (TextView) findViewById(R.id.name);
        desc = (TextView) findViewById(R.id.desc);
        price = (TextView) findViewById(R.id.price);
        imageView = (ImageView ) findViewById(R.id.imagee);
        mAuth = FirebaseAuth.getInstance();
         currentuser = mAuth.getCurrentUser();
         userdata = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser.getUid());
        databaseReference.child(foodkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 namee = (String) dataSnapshot.child("name").getValue();
                 descc = (String) dataSnapshot.child("desc").getValue();
                 pricee = (String) dataSnapshot.child("price").getValue();
                 imagee = (String) dataSnapshot.child("image").getValue();
                name.setText(namee);
                desc.setText(descc);
                price.setText(pricee);
                Picasso.get().load(imagee).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void order(View view){
        myref = FirebaseDatabase.getInstance().getReference().child("Orders");
        final DatabaseReference neworder = myref.push();
        userdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                neworder.child("itemname").setValue(namee);
                neworder.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(SingleFood.this,MenuActivity.class));
                        Toast.makeText(SingleFood.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
