package com.example.ph.mobiliario.EditPerfi;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Chat.users.Usuarios;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 1;
    private static OnDateSetListener oyenteSelectorFecha;
    private ImageView AUXivFotoUsuario;
    private EditText POSTSexo;
    private EditText POSTtvCalleNumero;
    private EditText POSTtvCelular;
    private EditText POSTtvCiudad;
    private EditText POSTtvCodigoPostal;
    private EditText POSTtvColonia;
    private EditText POSTtvDisplayName;
    private EditText POSTtvEstado;
    private EditText POSTtvFechaNacimiento;
    String POSTtxtCalleNumero;
    String POSTtxtCelular;
    String POSTtxtCiudad;
    String POSTtxtCodigoPostal;
    String POSTtxtColonia;
    String POSTtxtDisplayName;
    String POSTtxtEstado;
    String POSTtxtFechaNacimiento;
    String POSTtxtImage;
    String POSTtxtSexo;
    int f24ao;
    int dia;
    private DatabaseReference mDatabase;
    private Uri mImageUri = null;
    private EditText mPostDesc;
    private ProgressDialog mProgress;
    private ImageButton mSelectorImage;
    private StorageReference mStorage;
    int mes;

    class C02691 implements OnDateSetListener {
        C02691() {
        }

        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            PostActivity.this.f24ao = year;
            PostActivity.this.mes = month + 1;
            PostActivity.this.dia = day;
            PostActivity.this.POSTtvFechaNacimiento.setText(PostActivity.this.dia + "/" + PostActivity.this.mes + "/" + PostActivity.this.f24ao);
        }
    }

    class C02702 implements OnClickListener {
        C02702() {
        }

        public void onClick(View view) {
            PostActivity.this.statrtPosting();
        }
    }

    class C02713 implements OnClickListener {
        C02713() {
        }

        public void onClick(View v) {
            Intent galleryIntent = new Intent("android.intent.action.GET_CONTENT");
            galleryIntent.setType("image/*");
            PostActivity.this.startActivityForResult(galleryIntent, 1);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_post);
        getSupportActionBar().setTitle((CharSequence) "Editando perfil");
        this.mStorage = FirebaseStorage.getInstance().getReference();
        this.mDatabase = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_persona").child(Login.uidUsuario);
        this.mSelectorImage = (ImageButton) findViewById(R.id.imageSelec);
        this.AUXivFotoUsuario = (ImageView) findViewById(R.id.AUXsingleBlogImage);
        this.POSTtvDisplayName = (EditText) findViewById(R.id.POSTtvDisplayName);
        this.POSTtvCelular = (EditText) findViewById(R.id.POSTtvCelular);
        this.POSTSexo = (EditText) findViewById(R.id.POSTtvSexo);
        this.POSTtvFechaNacimiento = (EditText) findViewById(R.id.POSTtvFechaNacimiento);
        this.POSTtvCalleNumero = (EditText) findViewById(R.id.POSTtvCalleNumero);
        this.POSTtvColonia = (EditText) findViewById(R.id.POSTtvColonia);
        this.POSTtvCiudad = (EditText) findViewById(R.id.POSTtvCiudad);
        this.POSTtvEstado = (EditText) findViewById(R.id.POSTtvEstado);
        this.POSTtvCodigoPostal = (EditText) findViewById(R.id.POSTtvCodigoPostal);
        oyenteSelectorFecha = new C02691();
        this.mProgress = new ProgressDialog(this);
        this.POSTtxtDisplayName = getIntent().getExtras().getString("txtDisplayName");
        this.POSTtxtCelular = getIntent().getExtras().getString("txtCelular");
        this.POSTtxtImage = getIntent().getExtras().getString("txtImage");
        this.POSTtxtSexo = getIntent().getExtras().getString("txtSexo");
        this.POSTtxtFechaNacimiento = getIntent().getExtras().getString("txtFechaNacimiento");
        if (this.POSTtxtFechaNacimiento.equals("")) {
            this.dia = 1;
            this.mes = 1;
            this.f24ao = 2017;
        } else {
            String[] txtFecNac = this.POSTtxtFechaNacimiento.split("/");
            this.dia = Integer.parseInt(txtFecNac[0]);
            this.mes = Integer.parseInt(txtFecNac[1]);
            this.f24ao = Integer.parseInt(txtFecNac[2]);
        }
        this.POSTtxtColonia = getIntent().getExtras().getString("txtColonia");
        this.POSTtxtCiudad = getIntent().getExtras().getString("txtCiudad");
        this.POSTtxtCalleNumero = getIntent().getExtras().getString("txtCalleNumero");
        this.POSTtxtEstado = getIntent().getExtras().getString("txtEstado");
        this.POSTtxtCodigoPostal = getIntent().getExtras().getString("txtCodigoPostal");
        this.POSTtvDisplayName.setText(this.POSTtxtDisplayName);
        this.POSTtvCelular.setText(this.POSTtxtCelular);
        this.POSTSexo.setText(this.POSTtxtSexo);
        this.POSTtvFechaNacimiento.setText(this.dia + "/" + this.mes + "/" + this.f24ao);
        this.POSTtvCalleNumero.setText(this.POSTtxtCalleNumero);
        this.POSTtvColonia.setText(this.POSTtxtColonia);
        this.POSTtvCiudad.setText(this.POSTtxtCiudad);
        this.POSTtvEstado.setText(this.POSTtxtEstado);
        this.POSTtvCodigoPostal.setText(this.POSTtxtCodigoPostal);
        Picasso.with(this).load(this.POSTtxtImage).into(this.AUXivFotoUsuario);
        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new C02702());
        this.mSelectorImage.setOnClickListener(new C02713());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new DatePickerDialog(this, oyenteSelectorFecha, this.f24ao, this.mes, this.dia);
            default:
                return null;
        }
    }

    public void mostrarCalendario(View view) {
        showDialog(0);
    }

    private void statrtPosting() {
        this.mProgress.setMessage("Posting to blog...");
        final String nombre = this.POSTtvDisplayName.getText().toString().trim();
        final String celular = this.POSTtvCelular.getText().toString().trim();
        final String sexo = this.POSTSexo.getText().toString().trim();
        final String fechaNacimiento = this.POSTtvFechaNacimiento.getText().toString().trim();
        final String calleNumero = this.POSTtvCalleNumero.getText().toString().trim();
        final String colonia = this.POSTtvColonia.getText().toString().trim();
        final String ciudad = this.POSTtvCiudad.getText().toString().trim();
        final String estado = this.POSTtvEstado.getText().toString().trim();
        final String codigoPostal = this.POSTtvCodigoPostal.getText().toString().trim();
        this.mProgress.show();
        if (this.mImageUri != null) {
            this.mStorage.child("FotosClientes").child(this.mImageUri.getLastPathSegment()).putFile(this.mImageUri).addOnSuccessListener(new OnSuccessListener<TaskSnapshot>() {
                public void onSuccess(TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    String UrlParaWeb = taskSnapshot.getMetadata().getPath();
                    DatabaseReference newPOst = PostActivity.this.mDatabase.push();
                    PostActivity.this.mDatabase.child("displayName").setValue(nombre);
                    PostActivity.this.mDatabase.child("ciudad").setValue(ciudad);
                    PostActivity.this.mDatabase.child("cel").setValue(celular);
                    PostActivity.this.mDatabase.child("sexo").setValue(sexo);
                    PostActivity.this.mDatabase.child("fechaNac").setValue(fechaNacimiento);
                    PostActivity.this.mDatabase.child("domicilio").setValue(calleNumero);
                    PostActivity.this.mDatabase.child("colonia").setValue(colonia);
                    PostActivity.this.mDatabase.child("estado").setValue(estado);
                    PostActivity.this.mDatabase.child("cp").setValue(codigoPostal);
                    PostActivity.this.mDatabase.child("foto").setValue(UrlParaWeb);
                    PostActivity.this.mDatabase.child("image").setValue(downloadUri.toString());
                    PostActivity.this.mProgress.dismiss();
                    Login.f_logg("Cambio su perfil", "/perfil/", "ad_persona");
                    PostActivity.this.finish();
                }
            });
            return;
        }
        this.mDatabase.child("displayName").setValue(nombre);
        this.mDatabase.child("ciudad").setValue(ciudad);
        this.mDatabase.child("cel").setValue(celular);
        this.mDatabase.child("sexo").setValue(sexo);
        this.mDatabase.child("fechaNac").setValue(fechaNacimiento);
        this.mDatabase.child("domicilio").setValue(calleNumero);
        this.mDatabase.child("colonia").setValue(colonia);
        this.mDatabase.child("estado").setValue(estado);
        this.mDatabase.child("cp").setValue(codigoPostal);
        this.mProgress.dismiss();
        Login.f_logg("Cambio su perfil", "/perfil/", "ad_persona");
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            this.mImageUri = data.getData();
            this.mSelectorImage.setImageURI(this.mImageUri);
        }
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
