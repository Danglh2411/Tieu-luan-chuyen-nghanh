package haidang.com.myappff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LoginButton mBtnLoginFacebook;
    private CallbackManager mCallbackManager;
    public static String id;
    public static String name;
    public static String coverPhoto;
    String urladdUser = "https://apptimnhau.000webhostapp.com/addUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        mCallbackManager = CallbackManager.Factory.create();
        mBtnLoginFacebook = (LoginButton) findViewById(R.id.btn_login_facebook);
        mBtnLoginFacebook.setReadPermissions(Arrays.asList("public_profile"));
        SetLoginButton();


    }
    /////
    private void Result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON", response.getJSONObject().toString());
                try {
                    name = object.getString("name");
                    id= object.getString("id");
                    JSONObject JOSource = object.optJSONObject("cover");
                    coverPhoto = JOSource.getString("source");
                    Log.d("cover",coverPhoto);
                    AddUser(urladdUser);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("Userid",id);
                    intent.putExtra("Name",name);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large),cover");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    /////
    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
    ////
    public void SetLoginButton() {
        mBtnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Result();
                mBtnLoginFacebook.setVisibility(View.INVISIBLE);
                //goMainActivity();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Userid",id);
        intent.putExtra("Name",name);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    ////
    // Hàm thêm đối tượng user logi fb vào bảng User database
    private void AddUser(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            //Toast.makeText(MapActivity.this,"success",Toast.LENGTH_LONG).show();
                        } else {
                            //Toast.makeText(MapActivity.this,"Error!",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MapActivity.this,"Error!!!",Toast.LENGTH_LONG).show();
                        Log.d("AAA", "Error:\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Id", id);
                params.put("Name", name);
                params.put("BirthDay", "null");
                params.put("Email", "null");
                params.put("Phone", "null");
                params.put("Status", "null");
                params.put("Photo", coverPhoto);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
