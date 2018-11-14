package org.ieszaidinvergeles.dam.chaterbot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBot;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotFactory;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotSession;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {

    private Button btSend;
    private Button btHistory;
    private EditText etTexto;
    private ScrollView svScroll;
    private TextView tvTexto;

    private ChatterBot bot;
    private ChatterBotFactory factory;
    private ChatterBotSession botSession;
    private long currentSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btSend = findViewById(R.id.btSend);
        etTexto = findViewById(R.id.etTexto);
        svScroll = findViewById(R.id.svScroll);
        tvTexto = findViewById(R.id.tvTexto);
        btHistory = findViewById(R.id.btHistory);
        registerDate();
        startBot();
    }

    //Este método va registrando las fechas de conexión al bot en una tabla de la base de datos.
    private void registerDate(){
        Ayudante helper = new Ayudante(MainActivity.this);
        GestorConversaciones gc = new GestorConversaciones(MainActivity.this);
        gc.open();
        Conversaciones conv = new Conversaciones();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        currentSession = gc.insert(conv);
        conv = gc.get(currentSession);
        conv.setFecha(currentDateTimeString);
        gc.update(conv);
    }

    private void chat(final String text) {
        registerChat(getResources().getString(R.string.you), text);
        Chater chater = new Chater();
        chater.execute(text);
    }

    //Este método va almacenando los mensajes que se chatean, guardando diversos campos,
    //que permiten identificar el emisor del mensaje y la hora a la que se envió.
    private void registerChat(String emisor, String mensaje){
        Ayudante helper = new Ayudante(MainActivity.this);
        GestorChat gct = new GestorChat(MainActivity.this);
        gct.open();
        Chat chat = new Chat();
        SimpleDateFormat format = new SimpleDateFormat("HH", Locale.FRANCE);
        String hora = format.format(new Date());
        long id = gct.insert(chat);
        chat = gct.get(id);
        chat.setIdconv(currentSession);
        chat.setHora(hora);
        chat.setEmisor(emisor);
        chat.setMensaje(mensaje);
        gct.update(chat);

    }


    //Este AsyncTask combina dos de los métodos del programa original. En el
    // background, conecta con el bot, envía los mensajes y recibe las respuestas.
    //Tras la ejecución (onPostExecute), muestra las respuestas por pantalla.
    private class Chater extends AsyncTask<String, String, String>{

        Chater(){}

        @Override
        protected String doInBackground(String... text) {
            String response;

            try {
                response = getString(R.string.bot) + " " + botSession.think(text[0]);
            } catch (final Exception e) {
                response = getString(R.string.exception) + " " + e.toString();
            }

            return response;
        }

        @Override
        protected void onProgressUpdate(String... response) {
            super.onProgressUpdate(response);


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            registerChat(getResources().getString(R.string.bot), s);
            etTexto.requestFocus();
            tvTexto.append(s + "\n");
            svScroll.fullScroll(View.FOCUS_DOWN);
            btSend.setEnabled(true);
            hideKeyboard();
        }
    }

    private void setEvents() {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text = getString(R.string.you) + " " + etTexto.getText().toString().trim();
                btSend.setEnabled(false);
                etTexto.setText("");
                tvTexto.append(text + "\n");

                chat(text);
            }
        });

        btHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, VerConversaciones.class);
                startActivity(i);
            }
        });
    }


    //Método de inicio del bot. Es idéntico al original, pero ahora se ejecuta en AsyncTask.
    private void startBot() {

        StartBot sb = new StartBot();
        sb.execute();
    }


    private class StartBot extends AsyncTask<Boolean, String, Boolean>{

        StartBot(){
        }

        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            boolean result = true;
            String initialMessage;
            factory = new ChatterBotFactory();
            try {
                bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
                botSession = bot.createSession();
                initialMessage = getString(R.string.messageConnected) + "\n";
            } catch(Exception e) {
                initialMessage = getString(R.string.messageException) + "\n" + getString(R.string.exception) + " " + e.toString();
                result = false;

            }
            onProgressUpdate(initialMessage);

            return result;
        }

        @Override
        protected void onProgressUpdate(String... initialMessage) {
            super.onProgressUpdate(initialMessage);
            tvTexto.setText(initialMessage[0]);
            setEvents();

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                setEvents();
            }
        }
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    
}