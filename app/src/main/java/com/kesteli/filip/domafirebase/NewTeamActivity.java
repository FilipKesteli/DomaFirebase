package com.kesteli.filip.domafirebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewTeamActivity extends AppCompatActivity {

    private EditText etImeTima;
    private EditText etKorisnici;
    private EditText etNet;
    private EditText etVjera;
    private EditText etUlog;
    private EditText etRealno;
    private EditText etTkoUlog;
    private EditText etMotivacija;
    private EditText etPodrucja;
    private EditText etSansaUlog;
    private EditText etBrojClanova;
    private Button btnMembers;

    //Firebase setup
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference childReference = databaseReference.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);

        setupFirebase();
        initViews();
        setupListeners();
    }

    private void setupFirebase() {
        //postavljamo moguce pisanje po android internal disku
        //podaci su spremljeni u cache
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private void initViews() {
        etImeTima = (EditText) findViewById(R.id.etImeTima);
        etKorisnici = (EditText) findViewById(R.id.etKorisnici);
        etNet = (EditText) findViewById(R.id.etNet);
        etVjera = (EditText) findViewById(R.id.etVjera);
        etUlog = (EditText) findViewById(R.id.etUlog);
        etRealno = (EditText) findViewById(R.id.etRealno);
        etTkoUlog = (EditText) findViewById(R.id.etTkoUlog);
        etMotivacija = (EditText) findViewById(R.id.etMotivacija);
        etPodrucja = (EditText) findViewById(R.id.etPodrucja);
        etSansaUlog = (EditText) findViewById(R.id.etSansaUlog);
        etBrojClanova = (EditText) findViewById(R.id.etBrojClanova);
        btnMembers = (Button) findViewById(R.id.btnMembers);
    }

    SharedPreferences sharedpreferences;

    private void setupListeners() {
        btnMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addToFirebase();
                startIntent();
            }
        });
    }

    private void addToFirebase() {
        sharedpreferences = getSharedPreferences(POJO.KEY_MOJ_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String ime_tima = (etImeTima.getText().toString() + Math.random());
        editor.putString(POJO.KEY_IME_TIMA, ime_tima);

        int broj_clanova = Integer.parseInt(etBrojClanova.getText().toString());
        editor.putInt(POJO.KEY_BROJ_CLANOVA, broj_clanova);

        DatabaseReference childNewTeam = databaseReference.child("Timovi").child(ime_tima);
        childNewTeam.child("Broj korisnika koje ce zahvatiti projekt").setValue(etKorisnici.getText().toString());
        childNewTeam.child("Koliko takvih projekata postoji na netu").setValue(etNet.getText().toString());
        childNewTeam.child("Koliko vi vjerujete da ce projekt uspjeti").setValue(etVjera.getText().toString());
        childNewTeam.child("Koliko cete uloziti u projekt").setValue(etUlog.getText().toString());
        childNewTeam.child("Koliko je realno da ce projekt uspjeti").setValue(etRealno.getText().toString());
        childNewTeam.child("Tko ce uloziti u projekt").setValue(etTkoUlog.getText().toString());
        childNewTeam.child("Koja je motivacija za projekt").setValue(etMotivacija.getText().toString());
        childNewTeam.child("Checkirajte koja sva podrucja pokriva projekt").setValue(etPodrucja.getText().toString());
        childNewTeam.child("Kolika je sansa da ce netko uloziti u vas projekt").setValue(etSansaUlog.getText().toString());
        childNewTeam.child("Koliko je clanova u vasem timu").setValue(etBrojClanova.getText().toString());
    }

    private void startIntent() {
        Intent intent = new Intent(NewTeamActivity.this, MembersActivity.class);
        startActivity(intent);
    }
}





