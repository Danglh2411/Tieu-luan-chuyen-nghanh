package haidang.com.myappff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindFriendActivity extends AppCompatActivity {

    String myurl ="https://apptimnhau.000webhostapp.com/findfriend.php";
    ListView lvuser;
    ArrayList<User> arrayUser;
    AdapterFindFriend adapter;
    String userid;
    String nauser;
    ImageButton btnBack;
    EditText edtPhone;
    ImageButton btnSearch;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);
        Bundle bdLF = getIntent().getExtras();
        if(bdLF!=null){
            userid = bdLF.getString("Userid");
            nauser = bdLF.getString("NaUser");
        }


        lvuser = (ListView)findViewById(R.id.listFindFriend);
        arrayUser = new ArrayList<>();
        adapter = new AdapterFindFriend(FindFriendActivity.this, R.layout.itemlistfriend,arrayUser,userid,nauser);
        lvuser.setAdapter(adapter);

        btnBack =(ImageButton) findViewById(R.id.btnBackFF);

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindFriendActivity.this,MainActivity.class));
                finish();

            }
        });
        edtPhone = (EditText) findViewById(R.id.et_search);
        btnSearch =(ImageButton) findViewById(R.id.btnFindFriend);

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //getting the progressbar
                progressBar = (ProgressBar) findViewById(R.id.progressBarFF);
                //making the progressbar visible
                progressBar.setVisibility(View.VISIBLE);
                FindFriend(myurl);
            }
        });

    }

    private void FindFriend(String url  ){
        RequestQueue requestQueue = Volley.newRequestQueue(FindFriendActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  response) {
                progressBar.setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonobject = new JSONObject(response);
                        JSONArray jsonarray = jsonobject.getJSONArray("user");
                        JSONObject object = jsonarray.getJSONObject(i);
                        arrayUser.add(new User(
                                object.getString("Id"),
                                object.getString("Name"),
                                object.getString("BirthDay"),
                                object.getString("Email"),
                                object.getString("Phone"),
                                object.getInt("Status"),
                                object.getString("Photo")

                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FindFriendActivity.this, "Không tìm thấy", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Phone",edtPhone.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
