package com.example.fakenews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fakenews.prefs.SessionPrefs;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verifico sesión iniciada
        if (!SessionPrefs.get(this).isLoggedIn()) {
            startActivity(new Intent(this, GoogleLoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_login);

    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // Do something in response to button / Cambio displayMessageActivity por loginActivity
        // Intent intent = new Intent(this, co..............m.example.fakenews.DisplayMessageActivity.class);
        Intent intent = new Intent(this, com.example.fakenews.DisplayMessageActivity.class);
        /*EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        RequestQueue requestQueue;

        //Llamado al WS
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();<
        //intent.putExtra(EXTRA_MESSAGE, message);
*/
        startActivity(intent);
    }
}
