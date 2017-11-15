package com.example.ph.mobiliario.ChecadorBluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph.mobiliario.Login.Login;
import com.example.ph.mobiliario.R;
import com.example.ph.mobiliario.Soporte.agregartiket.AgregarTiket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

public class ControlChecadorBluetooth extends AppCompatActivity {

    Button btnOn, btnDis;
    TextView textView2;
    EditText PasswordRed;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    String passRed;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    JSONObject message;
    JSONObject json = new JSONObject();
    JSONObject config = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_checador_bluetooth);

        Intent newint = getIntent();
        address = newint.getStringExtra(ListaChecadorBluetooth.EXTRA_ADDRESS);

        btnOn = (Button)findViewById(R.id.button2);
        btnDis = (Button)findViewById(R.id.button4);
        PasswordRed= (EditText) findViewById(R.id.PasswordRed);
        textView2= (TextView) findViewById(R.id.textView2);
        textView2.setText("Rama: "+ Login.restaurante+" ,  Red: "+ListaChecadorBluetooth.compruebaConexion(this));
        new ConnectBT().execute(); //Call the class to connect


        btnOn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                turnOnLed();


            }
        });



        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void haverJson()
    {
String red=ListaChecadorBluetooth.compruebaConexion(this).replace("\"","");
        passRed=PasswordRed.getText().toString();

        try {

            json.put("passwordRed", passRed);
            json.put("ramaRest", Login.restaurante);
            json.put("SSID", red);
            config.put("config",json);
        } catch (JSONException ignored) {

        }
        message = json;
    }

    private void Disconnect()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.close();
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish();

    }



    private void turnOnLed()
    {
        haverJson();
        String varEnviar= String.valueOf(config);
        if (!TextUtils.isEmpty(passRed))
        {


            if (btSocket!=null)
            {
                try
                {
                    btSocket.getOutputStream().write(varEnviar.toString().getBytes());
                    //Login.f_logg("Configuro checador","/chat/","chat");
                    finish();

                }
                catch (IOException e)
                {
                    msg("Error");
                }
            }
        }
        else {
            Toast.makeText(ControlChecadorBluetooth.this,"Campo Vacio",Toast.LENGTH_LONG).show();
        }
    }

    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ControlChecadorBluetooth.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//conectamos al dispositivo y chequeamos si esta disponible
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (!ConnectSuccess)
            {
                msg("Conexión Fallida");
                finish();
            }
            else
            {
                msg("Conectado");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_tickets, menu);
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
