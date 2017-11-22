package com.example.ph.mobiliario.EditPerfi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ph.mobiliario.R;
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
    private ImageView ivFotoUsuario;
    String ltxtCalleNumero;
    private DatabaseReference mDatabase;
    private String mPost_key = null;
    private TextView tvCalleNumero;
    private TextView tvCelular;
    private TextView tvCiudad;
    private TextView tvCodigoPostal;
    private TextView tvColonia;
    private TextView tvDisplayNombre;
    private TextView tvEstado;
    private TextView tvFechaNacimiento;
    private TextView tvSexo;
    String txtCalleNumero;
    String txtCelular;
    String txtCiudad;
    String txtCodigoPostal;
    String txtColonia;
    String txtDisplayName;
    String txtEstado;
    String txtFechaNacimiento;
    String txtImage;
    String txtSexo;

    class C02681 implements OnClickListener {
        C02681() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(CambiandoDatos.this.getApplicationContext(), PostActivity.class);
            intent.putExtra("txtDisplayName", CambiandoDatos.this.txtDisplayName);
            intent.putExtra("txtCelular", CambiandoDatos.this.txtCelular);
            intent.putExtra("txtImage", CambiandoDatos.this.txtImage);
            intent.putExtra("txtSexo", CambiandoDatos.this.txtSexo);
            intent.putExtra("txtFechaNacimiento", CambiandoDatos.this.txtFechaNacimiento);
            intent.putExtra("txtColonia", CambiandoDatos.this.txtColonia);
            intent.putExtra("txtCiudad", CambiandoDatos.this.txtCiudad);
            intent.putExtra("txtCalleNumero", CambiandoDatos.this.txtCalleNumero);
            intent.putExtra("txtEstado", CambiandoDatos.this.txtEstado);
            intent.putExtra("txtCodigoPostal", CambiandoDatos.this.txtCodigoPostal);
            CambiandoDatos.this.startActivity(intent);
        }
    }

    class C06932 implements ValueEventListener {
        C06932() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            CambiandoDatos.this.txtDisplayName = (String) dataSnapshot.child("displayName").getValue();
            CambiandoDatos.this.txtCelular = (String) dataSnapshot.child("cel").getValue();
            CambiandoDatos.this.txtImage = (String) dataSnapshot.child("image").getValue();
            CambiandoDatos.this.txtSexo = (String) dataSnapshot.child("sexo").getValue();
            CambiandoDatos.this.txtFechaNacimiento = (String) dataSnapshot.child("fechaNac").getValue();
            CambiandoDatos.this.txtCalleNumero = (String) dataSnapshot.child("domicilio").getValue();
            CambiandoDatos.this.txtColonia = (String) dataSnapshot.child("colonia").getValue();
            CambiandoDatos.this.txtCiudad = (String) dataSnapshot.child("ciudad").getValue();
            CambiandoDatos.this.txtEstado = (String) dataSnapshot.child("estado").getValue();
            CambiandoDatos.this.txtCodigoPostal = (String) dataSnapshot.child("cp").getValue();
            CambiandoDatos.this.tvDisplayNombre.setText(CambiandoDatos.this.txtDisplayName);
            CambiandoDatos.this.tvCelular.setText(CambiandoDatos.this.txtCelular);
            CambiandoDatos.this.tvSexo.setText(CambiandoDatos.this.txtSexo);
            CambiandoDatos.this.tvFechaNacimiento.setText(CambiandoDatos.this.txtFechaNacimiento);
            CambiandoDatos.this.tvCalleNumero.setText(CambiandoDatos.this.txtCalleNumero);
            CambiandoDatos.this.tvColonia.setText(CambiandoDatos.this.txtColonia);
            CambiandoDatos.this.tvCiudad.setText(CambiandoDatos.this.txtCiudad);
            CambiandoDatos.this.tvEstado.setText(CambiandoDatos.this.txtEstado);
            CambiandoDatos.this.tvCodigoPostal.setText(CambiandoDatos.this.txtCodigoPostal);
            Picasso.with(CambiandoDatos.this).load(CambiandoDatos.this.txtImage).into(CambiandoDatos.this.ivFotoUsuario);
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_cambiando_datos);
        getSupportActionBar().setTitle((CharSequence) "Mi perfil");
        this.mDatabase = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_persona");
        this.mPost_key = getIntent().getExtras().getString("blog_id");
        this.tvCelular = (TextView) findViewById(R.id.tvCelular);
        this.ivFotoUsuario = (ImageView) findViewById(R.id.singleBlogImage);
        this.tvDisplayNombre = (TextView) findViewById(R.id.tvDisplayName);
        this.tvSexo = (TextView) findViewById(R.id.tvSexo);
        this.tvFechaNacimiento = (TextView) findViewById(R.id.tvFechaNacimiento);
        this.tvCalleNumero = (TextView) findViewById(R.id.tvCalleNumero);
        this.tvColonia = (TextView) findViewById(R.id.tvColonia);
        this.tvCiudad = (TextView) findViewById(R.id.tvCiudad);
        this.tvEstado = (TextView) findViewById(R.id.tvEstado);
        this.tvCodigoPostal = (TextView) findViewById(R.id.tvCodigoPostal);
        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new C02681());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.mDatabase.child(Login.uidUsuario).addValueEventListener(new C06932());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Login.f_Salir();
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
            return true;
        }
        switch (item.getItemId()) {
            case 16908332:
                Log.i("ActionBar", "Atrás!");
                Intent singleBlogIntent = new Intent(this, Usuarios.class);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
