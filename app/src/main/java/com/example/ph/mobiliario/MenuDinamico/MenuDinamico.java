package com.example.ph.mobiliario.MenuDinamico;

import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ph.mobiliario.Chat.users.Usuarios;
import com.example.ph.mobiliario.ChecadorBluetooth.ListaChecadorBluetooth;
import com.example.ph.mobiliario.EditPerfi.CambiandoDatos;
import com.example.ph.mobiliario.Inicio.Inicio;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.MenuDinamico.SubMenuDinamico.Frag;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Soporte.Tiket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuDinamico extends AppCompatActivity {
    public static Typeface font;
    static RecyclerView recyclerView;
    public static FragmentManager fm;
    public static Frag tv;
    String b;
    int tamañoAd_permiso;
    private static List<Constructor> colors;
    String tipo_de_Usuario;
    public static int contadorChats,contadorSoporte;
    TextView txtContadorChats,txtContadorChats2;
    TextView txtContadorSoporte,txtContadorSoporte2;
    FloatingActionButton fabContadorChat,fabContadorSoporte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dinamico);
        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
       /// getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        txtContadorChats= (TextView) findViewById(R.id.txtContadorChats);
        txtContadorChats2= (TextView) findViewById(R.id.txtContadorChats2);
        txtContadorChats2.setTypeface(MenuDinamico.font);
        txtContadorChats2.setText("\uf0e6");

        txtContadorSoporte= (TextView) findViewById(R.id.txtContadorSoporte);
        txtContadorSoporte2= (TextView) findViewById(R.id.txtContadorSoporte2);
        txtContadorSoporte2.setTypeface(MenuDinamico.font);
        txtContadorSoporte2.setText("\uf29c");

         fabContadorChat = (FloatingActionButton) findViewById(R.id.fabContadorChat);
         f_Contador_chats();

        fabContadorSoporte = (FloatingActionButton) findViewById(R.id.fabContadorSoporte);
        f_Contador_soporte();

        fabContadorChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Usuarios.class);
                startActivity(intent);

            }
        });

        fabContadorSoporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Tiket.class);
                startActivity(intent);

            }
        });

        Login.progressDoalog.dismiss();

        colors = new ArrayList<Constructor>();


        tipo_de_Usuario = getIntent().getExtras().getString("parametro");
        // Toast.makeText(this,extras,Toast.LENGTH_LONG).show();

        f_Consulta_Permiso(tipo_de_Usuario);
        //f_Consulta_Permiso(tipo_de_Usuario);
        fm = getFragmentManager();
        getSupportActionBar().setDisplayShowCustomEnabled(false);
    }

    public void f_Contador_soporte() {

        FirebaseDatabase databaseContadorChats = FirebaseDatabase.getInstance();
        DatabaseReference myRefContadorChats = databaseContadorChats.getReference().child(Login.restaurante)
                .child("tickets").child(Login.uidUsuario);

        // Read from the database
        myRefContadorChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contadorSoporte=0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String w = postSnapshot.getKey();
                    String estatus = String.valueOf(postSnapshot.child("chat").getValue());
                    String receptor = String.valueOf(postSnapshot.child("receptor").getValue());
                 f_Contador_soporte_mensajes(w);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }

    public int f_Contador_soporte_mensajes(String w) {

        FirebaseDatabase databaseContadorChats = FirebaseDatabase.getInstance();
        DatabaseReference myRefContadorChats = databaseContadorChats.getReference().child(Login.restaurante)
                .child("tickets").child(Login.uidUsuario).child(w).child("chat");

        // Read from the database
        myRefContadorChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String k = postSnapshot.getKey();
                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    String receptor = String.valueOf(postSnapshot.child("receptor").getValue());
                    if (receptor.equals(Login.uidUsuario)&&estatus.equals("no-leido"))
                    {
                        contadorSoporte++;
                    }

                }
                if (contadorSoporte==0)
                {
                    fabContadorSoporte.setVisibility(View.INVISIBLE);
                }
                else {
                    txtContadorSoporte.setText(contadorSoporte+"");
                    fabContadorSoporte.setVisibility(View.VISIBLE);
                    //mn_Soporte();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        return contadorSoporte;
    }

    private void f_Consulta_Permiso(final String tipo_de_Usuario) {


        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_permiso");
        //sirve para traer  nodo completo
        // My top posts by number of stars
        myRefSoporte.child(tipo_de_Usuario).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                b = "";

                String snap = null;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    //Log.i("postSnap", b = String.valueOf(postSnapshot.getKey()));
                    b = String.valueOf(postSnapshot.getKey());
                    f_Consulta_Modulo(b, tipo_de_Usuario);
                }
                tamañoAd_permiso = (int) dataSnapshot.getChildrenCount();


                snap = String.valueOf(dataSnapshot.getChildren());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }


    private void f_Consulta_Modulo(String resultadoArr, final String tipo_de_Usuario) {

        final DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_modulo");

        myRefSoporte.child(resultadoArr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                }

                dataSnapshot.getValue();
                String mobil = String.valueOf(dataSnapshot.child("mobil").getValue());
                String key_Id = dataSnapshot.getKey();
                String icono = String.valueOf(dataSnapshot.child("iconm").getValue());
                String estatus = String.valueOf(dataSnapshot.child("estatus").getValue());
                String descripcion = String.valueOf(dataSnapshot.child("description").getValue());
                String title = String.valueOf(dataSnapshot.child("title").getValue());
                String url = String.valueOf(dataSnapshot.child("url").getValue());
                String parent = String.valueOf(dataSnapshot.child("parent").getValue());
                if (mobil.equals("si"))
                {
                    String[] datosMenu = {icono, estatus, descripcion, title, url, key_Id};
                    f_Llenar_Recycler(datosMenu, tipo_de_Usuario);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public int f_Contador_chats()
    {

        FirebaseDatabase databaseContadorChats = FirebaseDatabase.getInstance();
        DatabaseReference myRefContadorChats = databaseContadorChats.getReference().child(Login.restaurante)
                .child("chat");

        // Read from the database
        myRefContadorChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                contadorChats=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    String z = postSnapshot.getKey();
                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    String receptor = String.valueOf(postSnapshot.child("receptor").getValue());
                    if (receptor.equals(Login.uidUsuario)&&estatus.equals("no-leido"))
                    {
                        contadorChats++;
                    }
                }
                if (contadorChats==0)
                {
                    fabContadorChat.setVisibility(View.INVISIBLE);
                }
                else {
                    txtContadorChats.setText(contadorChats+"");
                    fabContadorChat.setVisibility(View.VISIBLE);
                    //mn_chat();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
return contadorChats;
    }

    public void mn_Soporte()
    {
        // Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pubnub.com/console/"));
        Intent intent = new Intent(this, Tiket.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.adoy_logo);
        notificationBuilder.setContentTitle("Adoy Soporte");
        notificationBuilder.setContentText("Tienes mensajes de algun Tiket");
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());

    }

    public void mn_chat()
    {
        // Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pubnub.com/console/"));
        Intent intent = new Intent(this, Usuarios.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.adoy_logo);
        notificationBuilder.setContentTitle("Adoy Chat");
        notificationBuilder.setContentText("Tienes mensajes");
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    public void f_Llenar_Recycler(final String[] datos, final String tipo_de_Usuario) {
        //colors = new ArrayList<Constructor>();

        final String titulo = datos[3];
        String subtitulo = datos[2];
        String icono = datos[0];
        String iconHeart = icono;
        String valHexStr = iconHeart.replace("&#x", "").replace(";", "");
        long valLong = Long.parseLong(valHexStr,16);
        icono=(((char)valLong+""));
        final String url = datos[4];
        final String keyId = datos[5];

        colors.add(new Constructor(titulo, keyId, icono));

        if (colors.size() > tamañoAd_permiso) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            colors = new ArrayList<Constructor>();
            f_Consulta_Permiso(tipo_de_Usuario);
        }


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();

        recyclerView.setAdapter(new Adaptador(colors, new Clickeador() {
            @Override
            public void onClick(View v, int position) {

                String aux = String.valueOf(colors.get(position).getTabla());
               // Toast.makeText(MenuDinamico.this, aux, Toast.LENGTH_SHORT).show();
                F_Consutar_Submenu(aux, url, tipo_de_Usuario);
            }
        }));
        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(MenuDinamico.this));
        //HORIZONTAL
        //recyclerView.setLayoutManager(new LinearLayoutManager(Historial.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new Divider(MenuDinamico.this));

    }

    private void F_Consutar_Submenu(final String padre, final String url, final String extras) {

        DatabaseReference myRefSoporte;
        myRefSoporte = FirebaseDatabase.getInstance().getReference()
                .child(Login.restaurante).child("ad_modulo");

        myRefSoporte.child(padre).child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                }
                String parapasar = String.valueOf(dataSnapshot.exists());
                String urlHaciaQueActivity= String.valueOf(dataSnapshot.getValue());
                if (parapasar.equals("false")) {
                    fm = getFragmentManager();
                    tv = new Frag(padre, extras);
                    tv.show(fm,"TV");
                }
                else if(urlHaciaQueActivity.equals("/chat/")) {
                    Intent intent = new Intent(getApplicationContext(), Usuarios.class);
                    startActivity(intent);
                }
                else if(urlHaciaQueActivity.equals("/confChec/")) {
                    Intent intent = new Intent(getApplicationContext(), ListaChecadorBluetooth.class);
                    intent.putExtra("parametro", tipo_de_Usuario);
                    startActivity(intent);
                }
                else if(urlHaciaQueActivity.equals("/soporte/")) {
                    Intent intent = new Intent(getApplicationContext(), Tiket.class);
                    startActivity(intent);
                }
                else if(urlHaciaQueActivity.equals("/home/")) {
                    Intent intent = new Intent(getApplicationContext(), Inicio.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // Log.w("ta000g", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    public void cambiarUsuario(View v) {
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menus, menu);
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

        if (id == R.id.Editar_perfil) {
            Intent intent = new Intent(getApplicationContext(), CambiandoDatos.class);
            intent.putExtra("parametro", tipo_de_Usuario);
                startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


