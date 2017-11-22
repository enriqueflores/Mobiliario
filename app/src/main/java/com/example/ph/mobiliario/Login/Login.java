package com.example.ph.mobiliario.Login;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ph.mobiliario.Actividad.Actividad;
import com.example.ph.mobiliario.Chat.users.Usuarios;
import com.example.ph.mobiliario.EditPerfi.CambiandoDatos;
import com.example.ph.mobiliario.EditPerfi.PostActivity;
import com.example.ph.mobiliario.Estadisticas.Estadisticas;
import com.example.ph.mobiliario.Inicio.Inicio;
import com.example.ph.mobiliario.MenuDinamico.MenuDinamico;
import com.example.ph.mobiliario.MenuVentas.menu_con_fragment.Carta;
import com.example.ph.mobiliario.Mesas.Mesa;
import com.example.ph.mobiliario.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.ph.mobiliario.Mesas.Mesa.idComanda;

public class Login extends AppCompatActivity {
    public static String name;
    public static Typeface font;
    String jai="";
   // public static String link = "https://softadmin.adoysystems.com/php/searchProyect.php";
    public static String link = "http://admintest.adoysystems.com/php/searchProyect.php";
    //public static String link = "https://admin.adoysystems.com/php/searchProyect.php";//110
    String glog, nombre,n;
    String estatusPago, empezar;
    int id;
    private static final String TAG = "MainActivity";
    private static FirebaseAuth mAuth;
    String email, password;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    public static String uidUsuario;
    String tipoUsuario = "f_enviarPedido";
    EditText mail, pass;
    public static ProgressDialog progressDoalog;
    public static String restaurante;
    String valPass, valMail;
    static public String passSave, mailSave;
    public static DatabaseReference LoginConsultasUsuarios;
    int t=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        mail = (EditText) findViewById(R.id.txtmail);
        pass = (EditText) findViewById(R.id.txtpass);


        CargarPreferencia();
        valPass = pass.getText().toString();
        valMail = mail.getText().toString();


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (compruebaConexion(Login.this) == true) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {

                        ConsultawWebServiceIdRestaurant s = new ConsultawWebServiceIdRestaurant();
                        s.execute();
                        Toast.makeText(Login.this, "Autorizado", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Login.this, "Denegado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    f_Alert();

                }
            }
        };

    }
    private void f_Alerta_Entregados1() {
        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras");
        myRefSoporte.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  saberSiTodosSonEntregados="listo";
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String idCOM = postSnapshot.getKey();
                    String MeseroActual = String.valueOf(postSnapshot.child("idMesero").getValue());
                    String nombreMesa = String.valueOf(postSnapshot.child("nombre").getValue());
if (MeseroActual.equals(Login.uidUsuario)) {
    f_Alerta_Entregados2(idCOM,nombreMesa);
}
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void f_Alerta_Entregados2(final String idCOM, final String mesa) {

        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(idCOM)
                .child("Lista");
        myRefSoporte.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String destino = postSnapshot.getKey();
                        f_Alerta_Entregados3(idCOM,destino,mesa);
                        /*if (PPestatus.equals("Entregado")) {
                            //mn_Entregado();
                        }*/
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void f_Alerta_Entregados3(final String idCOM, final String destino, final String mesa) {

        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("listaCompras").child(idCOM)
                .child("Lista").child(destino);
        myRefSoporte.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String idPLA = postSnapshot.getKey();
                        String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                        if (estatus.equals("Listo")) {
                            mn_Entregado(idCOM,mesa);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public  void mn_Entregado(String idCOM,String mesa) {
        String aux="";
        for (int i=6;i<=12;i++)
        {
            aux+=idCOM.charAt(i);
        }
        int idNotificacion = Integer.parseInt(aux);

        // Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pubnub.com/console/"));
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, idNotificacion, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new android.support.v4.app.NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.adoy_logo);
        notificationBuilder.setContentTitle("Mesa: "+mesa);
        notificationBuilder.setContentText("Pedido listo");
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(soundUri);
        //  notificationBuilder.setSound(Uri.parse("uri://sadfasdfasdf.mp3"));
        notificationBuilder.setVibrate(new long[] { 500, 500, 500, 500, 500 });
        //notificationBuilder.setLights(Color.RED, 3000, 3000);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(idNotificacion, notificationBuilder.build());
    }

    public void mn_Pago() {

        // Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pubnub.com/console/"));
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.adoy_logo);
        notificationBuilder.setContentTitle("Adoy Soporte");
        notificationBuilder.setContentText("Tu pago ha expirado");
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(soundUri);
        //  notificationBuilder.setSound(Uri.parse("uri://sadfasdfasdf.mp3"));
        //notificationBuilder.setVibrate(new long[] { 100, 100, 100, 100, 100 });
        //notificationBuilder.setLights(Color.RED, 3000, 3000);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void f_sincronizar() {
        //email = mail.getText().toString();
        // password = pass.getText().toString();

        valPass = pass.getText().toString();
        valMail = mail.getText().toString();
        if (!TextUtils.isEmpty(valPass) && !TextUtils.isEmpty(valMail)) {
            mAuth.signInWithEmailAndPassword(valMail, valPass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "SIGN IN Fallo",
                                Toast.LENGTH_SHORT).show();
                    }
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "SIGN IN Completo", Toast.LENGTH_SHORT).show();
                        GuardarPreferencia();
                        ConsultawWebServiceIdRestaurant s = new ConsultawWebServiceIdRestaurant();
                        // s.execute();
                        //f_Tipo_Usuario();
                    }
                }
            });
        }
    }

    private void f_Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

        builder.setTitle("Error de conexion")
                .setMessage("Al parecer no tienes conexion a Internet")
                .setPositiveButton("Reintentar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Cerrar App",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
        builder.show();
    }

    public static void f_logg(String actividad, String modulo, String tabla) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(Login.restaurante).child("logg").child(f_Sacar_Fecha_Completa_id());

        myRef.child("actividad").setValue(actividad);
        myRef.child("displayName").setValue(name);
        myRef.child("fecha").setValue(f_Sacar_Fecha_Completa_bonita());
        myRef.child("modulo").setValue(modulo);
        myRef.child("sistema").setValue("android");
        myRef.child("tabla").setValue(tabla);
        myRef.child("uid").setValue(Login.uidUsuario);
    }

    public static String f_Sacar_Fecha_Completa_bonita() {
        Date d = new Date();
        String milisegundosparaKey = String.valueOf(d.getTime());
        SimpleDateFormat fecc = new SimpleDateFormat("d'-'MM'-'yyyy");
        String fechacComplString = fecc.format(d);
        Log.i("FECHA COMPLETA", fechacComplString);
        //SACAMOS LA HORA
        SimpleDateFormat ho = new SimpleDateFormat("h:mm:ss a");
        String horaString = ho.format(d);
        Log.i("HORA", horaString);
        String sFrecha = fechacComplString + "  " + horaString;
        //  fechaCompleta.setText(fecha);
        // SACAMOS EL DIA con letras
        SimpleDateFormat di = new SimpleDateFormat("EEEE");
        String currentDateTimeStrin = di.format(d);
        Log.i("dia con letras", currentDateTimeStrin);
        //SACAMOS EL MES con letras
        SimpleDateFormat me = new SimpleDateFormat("MMMM");
        String mesString = me.format(d);
        Log.i("mes con letras", mesString);
        //SACAMOS EL AÑO
        SimpleDateFormat an = new SimpleDateFormat("yyyy");
        String añoString = an.format(d);
        Log.i("Año", añoString);
        return sFrecha;
    }

    public static String f_Sacar_Fecha_Completa_id() {
        Date d = new Date();
        String milisegundosparaKey = String.valueOf(d.getTime());
        SimpleDateFormat fecc = new SimpleDateFormat("d'-'MM'-'yyyy");
        String fechacComplString = fecc.format(d);
        Log.i("FECHA COMPLETA", fechacComplString);
        //SACAMOS LA HORA
        SimpleDateFormat ho = new SimpleDateFormat("h:mm:ss a");
        String horaString = ho.format(d);
        Log.i("HORA", horaString);
        String sFrecha = fechacComplString + "  " + horaString;
        //  fechaCompleta.setText(fecha);
        // SACAMOS EL DIA con letras
        SimpleDateFormat di = new SimpleDateFormat("EEEE");
        String currentDateTimeStrin = di.format(d);
        Log.i("dia con letras", currentDateTimeStrin);
        //SACAMOS EL MES con letras
        SimpleDateFormat me = new SimpleDateFormat("MMMM");
        String mesString = me.format(d);
        Log.i("mes con letras", mesString);
        //SACAMOS EL AÑO
        SimpleDateFormat an = new SimpleDateFormat("yyyy");
        String añoString = an.format(d);
        Log.i("Año", añoString);
        return milisegundosparaKey;
    }

    public static boolean compruebaConexion(Context context) {
        String nombreRed = null;
        boolean connected = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
                nombreRed = redes[i].getExtraInfo();
            }
        }
        return connected;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public void GuardarPreferencia() {
        SharedPreferences miPreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = miPreferencias.edit();
        passSave = pass.getText().toString();
        editor.putString("pass", passSave);
        editor.apply();
        mailSave = mail.getText().toString();
        editor.putString("mail", mailSave);
        editor.apply();
    }

    public void CargarPreferencia() {
        SharedPreferences miPreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        mail.setText(miPreferencias.getString("mail", ""));
        pass.setText(miPreferencias.getString("pass", ""));
    }

    private void progressDialog() {
        progressDoalog = new ProgressDialog(Login.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Conectando....");
        progressDoalog.setTitle("Adoy");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDoalog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDoalog.getProgress() <= progressDoalog.getMax()) {
                        Thread.sleep(200);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDoalog.getProgress() == progressDoalog.getMax()) {
                            progressDoalog.show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDoalog.incrementProgressBy(1);
        }
    };


    public static String f_Datos_Usuario_ID() {
        String Datos = "";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            uidUsuario = user.getUid();
            Datos = "name " + name + "\nemail " + email + "\nphoto " + photoUrl + "\n Id " + uidUsuario;
            Log.i("si sirve", Datos);
        }
        return uidUsuario;
    }

    public static void f_line() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(restaurante).child("ad_persona").child(f_Datos_Usuario_ID());
        myRef.child("estatus").setValue("online");
    }

    public static void f_offline() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(restaurante).child("ad_persona").child(f_Datos_Usuario_ID());
        myRef.child("estatus").setValue("offline");
    }

    private void f_Hacia_Donde_Dirige(String tipoUsuario) {
        f_logg("Acceso", "/perfil/", "ad_persona");

        Intent intent = new Intent(getApplicationContext(), Estadisticas.class);
        intent.putExtra("parametro", tipoUsuario);
        //  if (empezar.equals("si"))
        {
            startActivity(intent);
            empezar = "no";
            f_Alerta_Entregados1();
        }
    }

    private void f_Tipo_Usuario() {
        progressDialog();
        LoginConsultasUsuarios = FirebaseDatabase.getInstance().getReference().child(restaurante).child("ad_persona").child(f_Datos_Usuario_ID()).child("perfil");
        LoginConsultasUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                }
                //String snap = String.valueOf(dataSnapshot);
                tipoUsuario = (String) dataSnapshot.getValue();
                f_Hacia_Donde_Dirige(tipoUsuario);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void f_Salir() {
        f_logg("Salio", "/perfil/", "ad_persona");
        mAuth.signOut();
    }

    private class ConsultawWebServiceIdRestaurant extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String data = URLEncoder.encode("mail", "UTF-8") + "=" +
                        URLEncoder.encode(valMail, "UTF-8");
                data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" +
                        URLEncoder.encode(valPass, "UTF-8");
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                glog = sb.toString();
                //glog="{\"id\":1,\"nombre\":\"ad\",\"estatus\":\"Pagado\"}";
            } catch (Exception ignored) {
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {

            String json = glog;
            JSONObject object = null;
            try {
                object = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                assert object != null;
                nombre = (String) object.get("nombre");
                id = (int) object.get("id");
                // estatusPago = (String) object.get("estatus");
                estatusPago = "pagado";
                if (estatusPago.equals("no-Pagado")) {
                    mn_Pago();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

                    builder.setTitle("Adoy Systems")
                            .setMessage("Al parecer tu pago ha expirado ")
                            .setPositiveButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            restaurante = String.valueOf(id);
                                            f_Tipo_Usuario();
                                        }
                                    });

                    builder.show();
                } else {
                    restaurante = String.valueOf(id);
                    //restaurante="4";
                    f_Tipo_Usuario();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public void sign(View v) {
        f_sincronizar();
    }

    @Override
    public void onStart() {
        super.onStart();
        empezar = "si";
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        // f_offline();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*public void actualizar(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()

                .setDisplayName("PH DEVICES")
                .setPhotoUri(Uri.parse("http://conceptodefinicion.de/wp-content/uploads/2016/09/pH.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                            Toast.makeText(Login.this, "Actializado",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/

   /* public void obtenerDatosCuenta(View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url

            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uidUsuario = user.getUid();

            String Datos = "name " + name + "\nemail " + email + "\nphoto " + photoUrl + "\n Id " + uidUsuario;
            Log.i("si sirve", Datos);
            Toast.makeText(Login.this, Datos,
                    Toast.LENGTH_SHORT).show();
        }
    }*/

    /*public void crear_usuario(View v) {
        email = "ph8@mail.com";
        password = "123456";


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Registro",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

    }*/
}
