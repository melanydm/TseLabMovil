package com.example.fakenews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fakenews.apiWeb.ApiClient;
import com.example.fakenews.apiWeb.ApiInterface;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks {

    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity >>>>>>>>> ";
    SignInButton signInButton;
    TextView statusTextView;
    GoogleSignInClient mGoogleSignInClient;
    String url = "http://r179-27-99-70.ir-static.anteldata.net.uy:8080/FakeNews-web/RESTServices/citizen/";
    private ApiInterface restApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
        restApi = ApiClient.getClient(url).create(ApiInterface.class);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton = findViewById(R.id.sign_in_button);
        statusTextView = findViewById(R.id.textView);
        signInButton.setOnClickListener(this);

    } // onCreate

    @Override
    protected void onStart(){
        super.onStart();
        //  Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called

        // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //  updateUI(account);


    }



    public void onClick(View v){
        switch (v.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
/*
        String email = account.getEmail();
        String tokenId = account.getIdToken();
        requestLogin(email, tokenId);
*/


        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        //if (requestCode == RC_SIGN_IN){
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.w(TAG, "signInResult: ok=" + account.getDisplayName());
            String idToken = account.getIdToken();
            String mail = account.getEmail();
            sendIdTokenMail(idToken, mail);
            //
            // updateUI(account);
            Intent intent = new Intent(this, com.example.fakenews.MainActivity.class);
            startActivity(intent);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }

    private void sendIdTokenMail(String token, final String mail){
        String body = mail + ", " + token; //new LoginBody(mail, token)
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("email", mail);
            jsonObj.put("token_id", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<String> loginCall = restApi.login(jsonObj);
        // Log.w(TAG, "lalalala, por encolar el pedido" + restApi.toString());
        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse
                    (Call<String> call, Response<String> response) {

                // Ocultar la barra de progreso
                // showProgress(false);

                if (response.isSuccessful()){
                    Log.w(TAG, "login response successful");
                    if (response.body() != null){
                        if (!(response.body().contains("Error"))){

                            // Guardar token de sesion y userID en preferencias
                            //SessionPrefs.get(GoogleLoginActivity.this).guardarToken(response.body(), mail);
                            Log.w(TAG, "response = " + response.body());
                            // Ir al menu principal (main activity)

                        }
                        else {
                            String error = response.body().toString();
                            // showLoginError(error);
                            Log.w(TAG, "response error del ojete: " + error);
                        }

                    }else{

                        String error = "Error: Usuario o contraseña incorrecta";
                        // showLoginError(error);
                        Log.w(TAG, "response error 2" + error);
                    }
                }
                // Procesar errores
                else {

                    String error = response.body();
                    Log.w(TAG, "response error: " + error ); //+ " " +
                    Log.w(TAG, "error" + response.code() + response.errorBody());
                            //showLoginError(error);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*showProgress(false);
                showLoginError(t.getMessage());*/
                Log.w(TAG, "response error on failure: " + t + t.getCause());
            }
        });

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){
        Log.d(TAG, "onConnectionFailed: " + connectionResult);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


}
