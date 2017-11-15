package com.example.ph.mobiliario.EditPerfi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.ph.mobiliario.Chat.users.Usuarios;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity {
    int dia,mes,año;
    private static DatePickerDialog.OnDateSetListener oyenteSelectorFecha;
    private ImageView AUXivFotoUsuario;
    private ImageButton mSelectorImage;
    private EditText mPostDesc;
    private Uri mImageUri = null;
    private static final int GALLERY_REQUEST = 1;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;
    String POSTtxtDisplayName, POSTtxtCelular, POSTtxtImage, POSTtxtSexo, POSTtxtFechaNacimiento, POSTtxtColonia,
            POSTtxtCiudad, POSTtxtCalleNumero, POSTtxtEstado, POSTtxtCodigoPostal;
    private EditText POSTtvDisplayName, POSTtvCelular, POSTSexo, POSTtvFechaNacimiento, POSTtvCalleNumero, POSTtvColonia, POSTtvCiudad, POSTtvEstado, POSTtvCodigoPostal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_persona").child(Login.uidUsuario);

        mSelectorImage = (ImageButton) findViewById(R.id.imageSelec);
        AUXivFotoUsuario = (ImageView) findViewById(R.id.AUXsingleBlogImage);

        POSTtvDisplayName = (EditText) findViewById(R.id.POSTtvDisplayName);
        POSTtvCelular = (EditText) findViewById(R.id.POSTtvCelular);
        POSTSexo = (EditText) findViewById(R.id.POSTtvSexo);
        POSTtvFechaNacimiento = (EditText) findViewById(R.id.POSTtvFechaNacimiento);
        POSTtvCalleNumero = (EditText) findViewById(R.id.POSTtvCalleNumero);
        POSTtvColonia = (EditText) findViewById(R.id.POSTtvColonia);
        POSTtvCiudad = (EditText) findViewById(R.id.POSTtvCiudad);
        POSTtvEstado = (EditText) findViewById(R.id.POSTtvEstado);
        POSTtvCodigoPostal = (EditText) findViewById(R.id.POSTtvCodigoPostal);

        oyenteSelectorFecha= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                año=year;
                mes=month+1;
                dia=day;
                POSTtvFechaNacimiento.setText(dia+"/"+mes+"/"+año);
            }
        };



        mProgress = new ProgressDialog(this);

        POSTtxtDisplayName = getIntent().getExtras().getString("txtDisplayName");
        POSTtxtCelular = getIntent().getExtras().getString("txtCelular");
        POSTtxtImage = getIntent().getExtras().getString("txtImage");
        POSTtxtSexo = getIntent().getExtras().getString("txtSexo");
        POSTtxtFechaNacimiento = getIntent().getExtras().getString("txtFechaNacimiento");
        String [] txtFecNac=POSTtxtFechaNacimiento.split("/");
        dia= Integer.parseInt(txtFecNac[0]);
        mes= Integer.parseInt(txtFecNac[1]);
        año= Integer.parseInt(txtFecNac[2]);
        POSTtxtColonia = getIntent().getExtras().getString("txtColonia");
        POSTtxtCiudad = getIntent().getExtras().getString("txtCiudad");
        POSTtxtCalleNumero = getIntent().getExtras().getString("txtCalleNumero");
        POSTtxtEstado = getIntent().getExtras().getString("txtEstado");
        POSTtxtCodigoPostal = getIntent().getExtras().getString("txtCodigoPostal");

        POSTtvDisplayName.setText(POSTtxtDisplayName);
        POSTtvCelular.setText(POSTtxtCelular);
        POSTSexo.setText(POSTtxtSexo);
        POSTtvFechaNacimiento.setText(dia+"/"+mes+"/"+año);
        POSTtvCalleNumero.setText(POSTtxtCalleNumero);
        POSTtvColonia.setText(POSTtxtColonia);
        POSTtvCiudad.setText(POSTtxtCiudad);
        POSTtvEstado.setText(POSTtxtEstado);
        POSTtvCodigoPostal.setText(POSTtxtCodigoPostal);
        Picasso.with(PostActivity.this).load(POSTtxtImage).into(AUXivFotoUsuario);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statrtPosting();
            }
        });


        mSelectorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id)
        {
            case 0:
                return new DatePickerDialog(this,oyenteSelectorFecha, año,mes,dia);

        }
        return null;
    }
    public void mostrarCalendario(View view)
    {
        showDialog(0);
    }

    private void statrtPosting() {

        mProgress.setMessage("Posting to blog...");

        final String nombre = POSTtvDisplayName.getText().toString().trim();
        final String celular = POSTtvCelular.getText().toString().trim();
        final String sexo = POSTSexo.getText().toString().trim();
        final String fechaNacimiento = POSTtvFechaNacimiento.getText().toString().trim();
        final String calleNumero = POSTtvCalleNumero.getText().toString().trim();
        final String colonia = POSTtvColonia.getText().toString().trim();
        final String ciudad = POSTtvCiudad.getText().toString().trim();
        final String estado = POSTtvEstado.getText().toString().trim();
        final String codigoPostal = POSTtvCodigoPostal.getText().toString().trim();
            mProgress.show();

            if (mImageUri!=null) {
                StorageReference filepath = mStorage.child("FotosClientes").child(mImageUri.getLastPathSegment());

                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        String UrlParaWeb = taskSnapshot.getMetadata().getPath();
                        DatabaseReference newPOst = mDatabase.push();
                        // newPOst.child("title").setValue("title_val");

                        mDatabase.child("displayName").setValue(nombre);
                        mDatabase.child("ciudad").setValue(ciudad);
                        mDatabase.child("cel").setValue(celular);
                        mDatabase.child("sexo").setValue(sexo);
                        mDatabase.child("fechaNac").setValue(fechaNacimiento);
                        mDatabase.child("domicilio").setValue(calleNumero);
                        mDatabase.child("colonia").setValue(colonia);
                        mDatabase.child("estado").setValue(estado);
                        mDatabase.child("cp").setValue(codigoPostal);

                        mDatabase.child("foto").setValue(UrlParaWeb);
                        mDatabase.child("image").setValue(downloadUri.toString());
                        mProgress.dismiss();
                        Login.f_logg("Cambio su perfil","/perfil/","ad_persona");
                        finish();
                        //  startActivity(new Intent(PostActivity.this, CambiandoDatos.class));
                    }
                });
            }
            else {
                mDatabase.child("displayName").setValue(nombre);
                mDatabase.child("ciudad").setValue(ciudad);
                mDatabase.child("cel").setValue(celular);
                mDatabase.child("sexo").setValue(sexo);
                mDatabase.child("fechaNac").setValue(fechaNacimiento);
                mDatabase.child("domicilio").setValue(calleNumero);
                mDatabase.child("colonia").setValue(colonia);
                mDatabase.child("estado").setValue(estado);
                mDatabase.child("cp").setValue(codigoPostal);

                mProgress.dismiss();
                Login.f_logg("Cambio su perfil","/perfil/","ad_persona");
                finish();
            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            mSelectorImage.setImageURI(mImageUri);

        }
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

