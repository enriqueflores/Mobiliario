package com.example.ph.mobiliario.ChecadorBluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Soporte.agregartiket.AgregarTiket;

import java.util.ArrayList;
import java.util.Set;

public class ListaChecadorBluetooth extends AppCompatActivity {

    TextView textView;
    public static String nombreRed;
    //Declaramos Los Componentes
    Button btnVinculados;
    ListView listaDispositivos;
    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> dispVinculados;
    public static String EXTRA_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_checador_bluetooth);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView= (TextView) findViewById(R.id.textView);

        textView.setText("Usuario: "+ Login.restaurante+" ,  Red: "+compruebaConexion(this));

        btnVinculados = (Button)findViewById(R.id.button);
        listaDispositivos = (ListView)findViewById(R.id.listView);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null)
        {
            Toast.makeText(getApplicationContext(), "Bluetooth no disponible", Toast.LENGTH_LONG).show();
            finish();
        }
        else if(!myBluetooth.isEnabled())
        {
            //Preguntamos al usuario si desea encender el bluetooth
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }
        btnVinculados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listaDispositivosvinculados();
            }
        });
        listaDispositivosvinculados();

    }

    public static String compruebaConexion(Context context)
    {
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
        return nombreRed;
    }

    private void listaDispositivosvinculados()
    {
        dispVinculados = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (dispVinculados.size()>0)
        {
            for(BluetoothDevice bt : dispVinculados)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Obtenemos los nombres y direcciones MAC de los disp. vinculados
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No se han encontrado dispositivos vinculados", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        listaDispositivos.setAdapter(adapter);
        listaDispositivos.setOnItemClickListener(myListClickListener);

    }
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3)
        {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Make an intent to start next activity.
            Intent i = new Intent(ListaChecadorBluetooth.this, ControlChecadorBluetooth.class);

            //Change the activity.
            i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
            startActivity(i);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.agragar) {

            Intent intent = new Intent(getApplicationContext(), AgregarTiket.class);
            //intent.putExtra("parametro", "");
            startActivity(intent);
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
}
