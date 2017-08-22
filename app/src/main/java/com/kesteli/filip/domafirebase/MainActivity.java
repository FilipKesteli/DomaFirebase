package com.kesteli.filip.domafirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView tvCondition;
    private Button btnSunny;
    private Button btnFoggy;
    private Button btnHistory;
    private Button btnNewTeam;

    //Firebase setup
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference childReference = databaseReference.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupFirebaseDatabase();
        setupListeners();
    }

    private void initViews() {
        tvCondition = (TextView) findViewById(R.id.tvCondition);
        btnSunny = (Button) findViewById(R.id.btnSunny);
        btnFoggy = (Button) findViewById(R.id.btnFoggy);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnNewTeam = (Button) findViewById(R.id.btnNewTeam);
    }

    private void setupFirebaseDatabase() {
        //postavljamo moguce pisanje po android internal disku
        //podaci su spremljeni u cache
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        childReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class); //ovaj listener slusa sto je u firebase bazi
                tvCondition.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupListeners() {
        btnSunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference childReference2 = databaseReference.child("child-var");
                childReference2.setValue("child-value");

                DatabaseReference childReference3 = databaseReference.child("Zivotinja").child("Vjeverica").child("broj nogu");
                childReference3.setValue("45");

                childReference.setValue("Sunny"); //updateamo realtime database
            }
        });
        btnFoggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childReference.setValue("Foggy");
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        btnNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTeamActivity.class);
                startActivity(intent);
            }
        });
    }
}




