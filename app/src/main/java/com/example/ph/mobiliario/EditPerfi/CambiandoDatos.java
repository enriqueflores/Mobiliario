package com.example.ph.mobiliario.EditPerfi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ph.mobiliario.Chat.users.Usuarios;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CambiandoDatos extends AppCompatActivity {

    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private ImageView ivFotoUsuario;
    private TextView tvDisplayNombre;
    private TextView tvCelular,tvSexo,tvFechaNacimiento,tvCalleNumero,tvColonia,tvCiudad,tvEstado,tvCodigoPostal;
    String txtDisplayName,txtCelular,txtImage,txtSexo,txtFechaNacimiento,ltxtCalleNumero,txtCalleNumero
            ,txtColonia,txtCiudad,txtEstado,txtCodigoPostal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiando_datos);

        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child(Login.restaurante).child("ad_persona");

        mPost_key = getIntent().getExtras().getString("blog_id");
        //Toast.makeText(blogSingle.this, post_key, Toast.LENGTH_LONG).show();

        tvCelular = (TextView) findViewById(R.id.tvCelular);
        ivFotoUsuario = (ImageView) findViewById(R.id.singleBlogImage);
        tvDisplayNombre = (TextView) findViewById(R.id.tvDisplayName);
        tvSexo= (TextView) findViewById(R.id.tvSexo);
        tvFechaNacimiento = (TextView) findViewById(R.id.tvFechaNacimiento);
        tvCalleNumero= (TextView) findViewById(R.id.tvCalleNumero);
        tvColonia= (TextView) findViewById(R.id.tvColonia);
        tvCiudad= (TextView) findViewById(R.id.tvCiudad);
        tvEstado= (TextView) findViewById(R.id.tvEstado);
        tvCodigoPostal= (TextView) findViewById(R.id.tvCodigoPostal);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                intent.putExtra("txtDisplayName", txtDisplayName);
                intent.putExtra("txtCelular", txtCelular);
                intent.putExtra("txtImage", txtImage);
                intent.putExtra("txtSexo", txtSexo);
                intent.putExtra("txtFechaNacimiento", txtFechaNacimiento);
                intent.putExtra("txtColonia", txtColonia);
                intent.putExtra("txtCiudad", txtCiudad);
                intent.putExtra("txtCalleNumero", txtCalleNumero);
                intent.putExtra("txtEstado", txtEstado);
                intent.putExtra("txtCodigoPostal", txtCodigoPostal);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase.child(Login.uidUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 txtDisplayName= (String) dataSnapshot.child("displayName").getValue();
                 txtCelular = (String) dataSnapshot.child("cel").getValue();
                 txtImage = (String) dataSnapshot.child("image").getValue();
                 txtSexo = (String) dataSnapshot.child("sexo").getValue();
                 txtFechaNacimiento = (String) dataSnapshot.child("fechaNac").getValue();
                 txtCalleNumero = (String) dataSnapshot.child("domicilio").getValue();
                 txtColonia = (String) dataSnapshot.child("colonia").getValue();
                 txtCiudad = (String) dataSnapshot.child("ciudad").getValue();
                 txtEstado = (String) dataSnapshot.child("estado").getValue();
                 txtCodigoPostal = (String) dataSnapshot.child("cp").getValue();

                tvDisplayNombre.setText(txtDisplayName);
                tvCelular.setText(txtCelular);
                tvSexo.setText(txtSexo);
                tvFechaNacimiento.setText(txtFechaNacimiento);
                tvCalleNumero.setText(txtCalleNumero);
                tvColonia.setText(txtColonia);
                tvCiudad.setText(txtCiudad);
                tvEstado.setText(txtEstado);
                tvCodigoPostal.setText(txtCodigoPostal);
                Picasso.with(CambiandoDatos.this).load(txtImage).into(ivFotoUsuario);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Login.f_Salir();
            finish();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                Intent singleBlogIntent = new Intent(this, Usuarios.class);

                //startActivity(singleBlogIntent);

                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
