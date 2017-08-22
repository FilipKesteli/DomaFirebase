package com.kesteli.filip.domafirebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MembersActivity extends AppCompatActivity {

    private Button btnRezultat;
    private TextView tvRezultat;

    //Firebase setup
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference childReference = databaseReference.child("condition");

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        setupFirebase();
        initViews();
        setupListeners();
        setupRecyclerView();
    }

    private void setupFirebase() {
        //postavljamo moguce pisanje po android internal disku
        //podaci su spremljeni u cache
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private void initViews() {
        btnRezultat = (Button) findViewById(R.id.btnRezultat);
        tvRezultat = (TextView) findViewById(R.id.tvRezultat);
    }

    private void setupListeners() {
        //ovdje ide logika rezultata:
        btnRezultat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logika();
            }
        });
    }

    private void logika() {

    }

    /**
     * RECYCLER VIEW -> Postavljanje dinamicke liste
     */
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private GridLayoutManager gridLayoutManager; //kartice u mreži

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_member);
        gridLayoutManager = new GridLayoutManager(MembersActivity.this, 2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    String ime;
    String prezime;
    String godine;
    String tehnoIskustvo;
    String obrazovanje;
    String znanje;
    String dani;
    String tehnoDani;

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_layout_member, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

        }

        @Override
        public int getItemCount() {
            sharedpreferences = getSharedPreferences(POJO.KEY_MOJ_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            return sharedpreferences.getInt(POJO.KEY_BROJ_CLANOVA, -1);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            EditText etIme;
            EditText etPrezime;
            EditText etGodine;
            EditText etTehnoIskustvo;
            EditText etIskustvo;
            EditText etObrazovanje;
            EditText etZnanje;
            EditText etDani;
            EditText etTehnoDani;

            public ViewHolder(View itemView) {
                super(itemView);

                etIme = (EditText) itemView.findViewById(R.id.etIme);
                etPrezime = (EditText) itemView.findViewById(R.id.etPrezime);
                etGodine = (EditText) itemView.findViewById(R.id.etGodine);
                etTehnoIskustvo = (EditText) itemView.findViewById(R.id.etTehnoIskustvo);
                etIskustvo = (EditText) itemView.findViewById(R.id.etIskustvo);
                etObrazovanje = (EditText) itemView.findViewById(R.id.etObrazovanje);
                etZnanje = (EditText) itemView.findViewById(R.id.etZnanje);
                etDani = (EditText) itemView.findViewById(R.id.etDani);
                etTehnoDani = (EditText) itemView.findViewById(R.id.etTehnoDani);

                ime = etIme.getText().toString();
                prezime = etPrezime.getText().toString();
                godine = etGodine.getText().toString();
                tehnoIskustvo = etTehnoIskustvo.getText().toString();
                obrazovanje = etObrazovanje.getText().toString();
                znanje = etZnanje.getText().toString();
                dani = etDani.getText().toString();
                tehnoDani = etTehnoDani.getText().toString();

                addClanoviToFirebase();

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }
    }

    private void addClanoviToFirebase() {
        sharedpreferences = getSharedPreferences(POJO.KEY_MOJ_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        DatabaseReference childClanovi = databaseReference.child("Timovi").child(sharedpreferences.getString(POJO.KEY_IME_TIMA, "")).child("Clanovi");
        childClanovi.child("Ime clana").setValue(ime);
        childClanovi.child("Ime clana").setValue(prezime);
        childClanovi.child("Ime clana").setValue(godine);
        childClanovi.child("Ime clana").setValue(tehnoIskustvo);
        childClanovi.child("Ime clana").setValue(obrazovanje);
        childClanovi.child("Ime clana").setValue(znanje);
        childClanovi.child("Ime clana").setValue(dani);
        childClanovi.child("Ime clana").setValue(tehnoDani);
    }
}
