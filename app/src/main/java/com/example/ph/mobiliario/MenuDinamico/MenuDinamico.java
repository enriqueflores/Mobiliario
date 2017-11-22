package com.example.ph.mobiliario.MenuDinamico;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph.mobiliario.R;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuDinamico extends AppCompatActivity {
    private static List<Constructor> colors;
    public static int contadorChats;
    public static int contadorSoporte;
    public static FragmentManager fm;
    public static Typeface font;
    public static String nombreGlobal;
    static RecyclerView recyclerView;
    public static Frag tv;
    ArrayList<String> LModulosdeacceso = new ArrayList();
    ArrayList<String> LdatosMenu = new ArrayList();
    String f31b;
    FloatingActionButton fabContadorChat;
    FloatingActionButton fabContadorSoporte;
    int f32tamaoAd_permiso;
    String tipo_de_Usuario;
    TextView txtContadorChats;
    TextView txtContadorChats2;
    TextView txtContadorSoporte;
    TextView txtContadorSoporte2;

    class C02791 implements OnClickListener {
        C02791() {
        }

        public void onClick(View view) {
            MenuDinamico.this.startActivity(new Intent(MenuDinamico.this.getApplicationContext(), Usuarios.class));
        }
    }

    class C02802 implements OnClickListener {
        C02802() {
        }

        public void onClick(View view) {
            MenuDinamico.this.startActivity(new Intent(MenuDinamico.this.getApplicationContext(), Tiket.class));
        }
    }

    class C07183 implements ValueEventListener {
        C07183() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            MenuDinamico.contadorSoporte = 0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String w = postSnapshot.getKey();
                String estatus = String.valueOf(postSnapshot.child("chat").getValue());
                String receptor = String.valueOf(postSnapshot.child("receptor").getValue());
                MenuDinamico.this.f_Contador_soporte_mensajes(w);
            }
        }

        public void onCancelled(DatabaseError error) {
            Log.w("TAG", "Failed to read value.", error.toException());
        }
    }

    class C07194 implements ValueEventListener {
        C07194() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String k = postSnapshot.getKey();
                String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                if (String.valueOf(postSnapshot.child("receptor").getValue()).equals(Login.uidUsuario) && estatus.equals("no-leido")) {
                    MenuDinamico.contadorSoporte++;
                }
            }
            if (MenuDinamico.contadorSoporte == 0) {
                MenuDinamico.this.fabContadorSoporte.setVisibility(4);
                return;
            }
            MenuDinamico.this.txtContadorSoporte.setText(MenuDinamico.contadorSoporte + "");
            MenuDinamico.this.fabContadorSoporte.setVisibility(0);
        }

        public void onCancelled(DatabaseError error) {
            Log.w("TAG", "Failed to read value.", error.toException());
        }
    }

    class C07205 implements ValueEventListener {
        C07205() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            MenuDinamico.this.LModulosdeacceso.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                MenuDinamico.this.LModulosdeacceso.add(String.valueOf(postSnapshot.getKey()));
                MenuDinamico.this.f_Consulta_Modulo(MenuDinamico.this.LModulosdeacceso);
            }
        }

        public void onCancelled(DatabaseError databaseError) {
        }
    }

    class C07227 implements ValueEventListener {
        C07227() {
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            MenuDinamico.contadorChats = 0;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String z = postSnapshot.getKey();
                String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                if (String.valueOf(postSnapshot.child("receptor").getValue()).equals(Login.uidUsuario) && estatus.equals("no-leido")) {
                    MenuDinamico.contadorChats++;
                }
            }
            if (MenuDinamico.contadorChats == 0) {
                MenuDinamico.this.fabContadorChat.setVisibility(4);
                return;
            }
            MenuDinamico.this.txtContadorChats.setText(MenuDinamico.contadorChats + "");
            MenuDinamico.this.fabContadorChat.setVisibility(0);
        }

        public void onCancelled(DatabaseError error) {
            Log.w("TAG", "Failed to read value.", error.toException());
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_menu_dinamico);
        font = Typeface.createFromAsset(getAssets(), "font/fontawesome-webfont.ttf");
        getSupportActionBar().setTitle((CharSequence) "Menu Adoy Food");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon((int) R.drawable.adoy_logo);
        this.txtContadorChats = (TextView) findViewById(R.id.txtContadorChats);
        this.txtContadorChats2 = (TextView) findViewById(R.id.txtContadorChats2);
        this.txtContadorChats2.setTypeface(font);
        this.txtContadorChats2.setText("");
        this.txtContadorSoporte = (TextView) findViewById(R.id.txtContadorSoporte);
        this.txtContadorSoporte2 = (TextView) findViewById(R.id.txtContadorSoporte2);
        this.txtContadorSoporte2.setTypeface(font);
        this.txtContadorSoporte2.setText("");
        this.fabContadorChat = (FloatingActionButton) findViewById(R.id.fabContadorChat);
        f_Contador_chats();
        this.fabContadorSoporte = (FloatingActionButton) findViewById(R.id.fabContadorSoporte);
        f_Contador_soporte();
        this.fabContadorChat.setOnClickListener(new C02791());
        this.fabContadorSoporte.setOnClickListener(new C02802());
        colors = new ArrayList();
        this.tipo_de_Usuario = getIntent().getExtras().getString("parametro");
        f_Consulta_Permiso(this.tipo_de_Usuario);
        fm = getFragmentManager();
        getSupportActionBar().setDisplayShowCustomEnabled(false);
    }

    public void f_Contador_soporte() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets").child(Login.uidUsuario).addValueEventListener(new C07183());
    }

    public int f_Contador_soporte_mensajes(String w) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("tickets").child(Login.uidUsuario).child(w).child("chat").addListenerForSingleValueEvent(new C07194());
        return contadorSoporte;
    }

    private void f_Consulta_Permiso(String tipo_de_Usuario) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_permiso").child(tipo_de_Usuario).addValueEventListener(new C07205());
    }

    private void f_Consulta_Modulo(final ArrayList<String> LModulosdeacceso) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_modulo").addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                MenuDinamico.this.LdatosMenu.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String key_Id = postSnapshot.getKey();
                    for (int i = 0; i < LModulosdeacceso.size(); i++) {
                        if (((String) LModulosdeacceso.get(i)).equals(key_Id)) {
                            String mobil = String.valueOf(postSnapshot.child("mobil").getValue());
                            String icono = String.valueOf(postSnapshot.child("iconm").getValue());
                            String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                            String descripcion = String.valueOf(postSnapshot.child("description").getValue());
                            String title = String.valueOf(postSnapshot.child("title").getValue());
                            String url = String.valueOf(postSnapshot.child("url").getValue());
                            String parent = String.valueOf(postSnapshot.child("parent").getValue());
                            if (mobil.equals("si")) {
                                MenuDinamico.this.LdatosMenu.add(icono + "°" + estatus + "°" + descripcion + "°" + title + "°" + url + "°" + key_Id);
                            }
                        }
                    }
                }
                MenuDinamico.this.f_Llenar_Recycler(MenuDinamico.this.LdatosMenu, MenuDinamico.this.tipo_de_Usuario);
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public int f_Contador_chats() {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("chat").addValueEventListener(new C07227());
        return contadorChats;
    }

    public void f_Llenar_Recycler(ArrayList<String> datos, String tipo_de_Usuario) {
        colors.clear();
        for (int i = 0; i < datos.size(); i++) {
            String[] aux = ((String) datos.get(i)).split("°");
            String titulo = aux[3];
            String subtitulo = aux[2];
            String icono = ((char) ((int) Long.parseLong(aux[0].replace("&#x", "").replace(";", ""), 16))) + "";
            String url = aux[4];
            colors.add(new Constructor(titulo, aux[5], icono));
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.clearOnScrollListeners();
        recyclerView.clearOnChildAttachStateChangeListeners();
        final String str = tipo_de_Usuario;
        recyclerView.setAdapter(new Adaptador(colors, new Clickeador() {
            public void onClick(View v, int position) {
                MenuDinamico.nombreGlobal = String.valueOf(((Constructor) MenuDinamico.colors.get(position)).getName());
                String aux = String.valueOf(((Constructor) MenuDinamico.colors.get(position)).getTabla());
                Toast.makeText(MenuDinamico.this, MenuDinamico.nombreGlobal, 0).show();
                MenuDinamico.this.F_Consutar_Submenu(aux, str);
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void F_Consutar_Submenu(final String padre, final String extras) {
        FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_modulo").child(padre).child("url").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                }
                String parapasar = String.valueOf(dataSnapshot.exists());
                String urlHaciaQueActivity = String.valueOf(dataSnapshot.getValue());
                if (parapasar.equals("false")) {
                    MenuDinamico.fm = MenuDinamico.this.getFragmentManager();
                    MenuDinamico.tv = new Frag(padre, extras);
                    MenuDinamico.tv.show(MenuDinamico.fm, "TV");
                } else if (urlHaciaQueActivity.equals("/chat/")) {
                    MenuDinamico.this.startActivity(new Intent(MenuDinamico.this.getApplicationContext(), Usuarios.class));
                } else if (urlHaciaQueActivity.equals("/confChec/")) {
                    Intent intent = new Intent(MenuDinamico.this.getApplicationContext(), ListaChecadorBluetooth.class);
                    intent.putExtra("parametro", MenuDinamico.this.tipo_de_Usuario);
                    MenuDinamico.this.startActivity(intent);
                } else if (urlHaciaQueActivity.equals("/soporte/")) {
                    MenuDinamico.this.startActivity(new Intent(MenuDinamico.this.getApplicationContext(), Tiket.class));
                } else if (urlHaciaQueActivity.equals("/home/")) {
                    MenuDinamico.this.startActivity(new Intent(MenuDinamico.this.getApplicationContext(), Inicio.class));
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onPostResume() {
        super.onPostResume();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Login.f_Salir();
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
            return true;
        } else if (id != R.id.Editar_perfil) {
            return super.onOptionsItemSelected(item);
        } else {
            Intent intent = new Intent(getApplicationContext(), CambiandoDatos.class);
            intent.putExtra("parametro", this.tipo_de_Usuario);
            startActivity(intent);
            return true;
        }
    }
}
