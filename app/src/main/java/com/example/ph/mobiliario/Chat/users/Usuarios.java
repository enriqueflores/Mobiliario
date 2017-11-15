package com.example.ph.mobiliario.Chat.users;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ph.mobiliario.Chat.chat2;
import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Usuarios extends AppCompatActivity {

    private RecyclerView mBlogList;
    private DatabaseReference mDatabase,mDatabase2;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String v;
    int contadorChats;
    int contador;

    ArrayList<String> pornstas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(Login.restaurante).child("ad_persona");

        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public int f_Contador_chatsUsers()
    {

        FirebaseDatabase databaseContadorChats = FirebaseDatabase.getInstance();
        DatabaseReference myRefContadorChats = databaseContadorChats.getReference().child(Login.restaurante)
                .child("chat");

        // Read from the database
        myRefContadorChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contador=0;
                contadorChats=0;
                pornstas.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String z = postSnapshot.getKey();
                    String estatus = String.valueOf(postSnapshot.child("estatus").getValue());
                    String receptor = String.valueOf(postSnapshot.child("receptor").getValue());
                    String emisor = String.valueOf(postSnapshot.child("emisor").getValue());
                    if (receptor.equals(Login.uidUsuario)&&estatus.equals("no-leido"))
                    {
                        contadorChats++;
                        pornstas.add(emisor);
                        contador++;
                    }
                }
              /*  if (contadorChats==0)
                {
                    fabContadorChat.setVisibility(View.INVISIBLE);
                }
                else {
                    txtContadorChats.setText(contadorChats+"");
                    fabContadorChat.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        return contadorChats;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<BlogUsuarios, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogUsuarios, BlogViewHolder>(
                BlogUsuarios.class,
                R.layout.usuarios_row,
                BlogViewHolder.class,
                mDatabase) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, BlogUsuarios model, final int position) {
               // f_Contador_chatsUsers();
                final String post_key = getRef(position).getKey();

                for (int i=0;i<pornstas.size();i++)
                {
                    if (pornstas.get(i).equals(post_key))
                    {
                        viewHolder.mView.setBackgroundColor(Color.parseColor("#E2FFBB"));
                    }
                }
                if (model.getEstatus().equals("online"))
                {
                    //viewHolder.mView.setBackgroundColor(Color.parseColor("#E2FFBB"));
                }

                if (post_key.equals(Login.uidUsuario))
                {
                    Display display = getWindowManager().getDefaultDisplay();
                    int ancho = display.getWidth();
                    int  alto= display.getHeight();
                    //viewHolder.mView.setVisibility(View.INVISIBLE);
                    //viewHolder.mView.setBackgroundColor(Color.parseColor("#ffff00"));
                    Resources r = getResources();
                    float pxLeftMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());
                    float pxTopMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());
                    float pxRightMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());
                    float pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());

                    RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                    params.setMargins(Math.round(pxLeftMargin), Math.round(pxTopMargin), Math.round(pxRightMargin), Math.round(pxBottomMargin));
                    viewHolder.mView.setLayoutParams(params);
                    viewHolder.mView.getLayoutParams().height=0;
                }

                viewHolder.setTitle(model.getDisplayName());
                viewHolder.setDesc(model.getEstatus());
                viewHolder.setImage(getApplicationContext(), model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent singleBlogIntent = new Intent(Usuarios.this, chat2.class);
                        singleBlogIntent.putExtra("keySeleccionado", post_key);
                        startActivity(singleBlogIntent);
                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) mView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image) {
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_mas) {
           // startActivity(new Intent(MainActivity.this, PostActivity.class));
            //return true;
        }

        if (id == R.id.action_logout) {

            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override protected void onResume() {
        super.onResume();
        Login.f_line();
        f_Contador_chatsUsers();
    }

    @Override protected void onPause() {
        super.onPause();
        Login.f_offline();
    }

    @Override protected void onStop() {
        super.onStop();
    }

    @Override protected void onRestart() {
        super.onRestart();

    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }


}
