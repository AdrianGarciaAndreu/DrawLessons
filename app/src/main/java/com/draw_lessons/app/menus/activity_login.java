
package com.draw_lessons.app.menus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.draw_lessons.app.R;
import com.draw_lessons.app.contenidos.webservice;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class activity_login extends ActionBarActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "Login";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    /**
     * A flag indicating that a PendingIntent is in progress and prevents us
     * from starting further intents.
     */
    private boolean mIntentInProgress;

    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

    private SignInButton btnSignIn;

    private int personUserID;
    private String personGoogleID;
    private String personEmail;
    private String personName;
    private String personPhotoUrl;
    private String personCoverUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

        // Button click listeners
        btnSignIn.setOnClickListener(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method to resolve any signin errors
     */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;


        // Get user's information
        getProfileInformation();

        comprobarUsuario cu = new comprobarUsuario(this);
        cu.execute();

        Toast.makeText(this, "Usuario " + personEmail + " conectado!", Toast.LENGTH_SHORT).show();


    }

    /**
     * Fetching user's information name, email, profile pic
     */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

                personGoogleID = currentPerson.getId();
                personEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);

                if (currentPerson.hasName()) {
                    personName = currentPerson.getDisplayName();
                } else {
                    personName = null;
                }

                if (currentPerson.hasImage()) {
                    personPhotoUrl = currentPerson.getImage().getUrl();
                } else {
                    personPhotoUrl = null;
                }

                if (currentPerson.hasCover()) {
                    personCoverUrl = currentPerson.getCover().getCoverPhoto().getUrl();
                } else {
                    personCoverUrl = null;
                }


            } else {
                Log.e(TAG, "Person information is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_login, menu);
        return true;
    }

    /**
     * Button on click listener
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                // Signin button clicked
                signInWithGplus();
                break;
        }
    }

    /**
     * Sign-in into google
     */
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    /**
     * Sign-out from google
     */
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }

    /**
     * Revoking access from google
     */
    private void revokeGplusAccess() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status arg0) {
                    Log.e(TAG, "User access revoked!");
                    mGoogleApiClient.connect();
                }

            });
        }
    }


    private class comprobarUsuario extends AsyncTask<Void, Void, Void> {

        private Context context;
        private ProgressDialog pDialog;
        private boolean comprobacion=false;

        public comprobarUsuario(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            pDialog = new ProgressDialog(this.context);
            pDialog.setMessage("Cargando...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            //Creando instancia de la clase de webservice
            webservice wb = new webservice();

            //Guarda el string del JSON

            String jsonString = wb.makeServiceCall("http://draw-lessons.com/api/?a=getExisteUsuario&id="+personGoogleID);
            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);
                    JSONArray json_array = jsonObj.getJSONArray("Datos");
                    JSONObject c = json_array.getJSONObject(0);
                    int existe = c.getInt("Existe");
                    if (existe < 1) {
                        jsonString = wb.makeServiceCall("http://draw-lessons.com/api/?a=addUsuario&id="+personGoogleID);
                        c = new JSONObject(jsonString);
                       // json_array = jsonObj.getJSONArray("Datos");
                      //  c = json_array.getJSONObject(0);
                        int añadido = c.getInt("Datos");
                        if (añadido > 0) {
                            Log.d(TAG, "Usuario agregado a la base de datos.");
                            comprobacion=true;
                        }
                    } else { comprobacion=true; }
                    if (comprobacion) {
                        jsonString = wb.makeServiceCall("http://draw-lessons.com/api/?a=getIdUsuario&id=" + personGoogleID);
                        jsonObj = new JSONObject(jsonString);
                        json_array = jsonObj.getJSONArray("Datos");
                        c = json_array.getJSONObject(0);
                        personUserID = c.getInt("id_usuario");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (comprobacion) {
                Intent i = new Intent().setClass(activity_login.this, activity_homescreen.class);
                i.putExtra("personID", personUserID);
                i.putExtra("personName",personName);
                i.putExtra("personEmail",personEmail);
                i.putExtra("personCoverUrl",personCoverUrl);
                i.putExtra("personPhotoUrl",personPhotoUrl);
                i.putExtra("personUserID", personUserID);
                i.putExtra("personGoogleID", personGoogleID);
                startActivity(i);
                finish();
             } else {
                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT);
            }

        }

    }
}