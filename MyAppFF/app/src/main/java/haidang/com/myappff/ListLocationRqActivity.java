package haidang.com.myappff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class ListLocationRqActivity extends AppCompatActivity {

    String myurl ="https://apptimnhau.000webhostapp.com/getlocationrq.php";
    ListView lvlocationrq;
    ListView lvlocationshare;
    ArrayList<LocationRq> arrayUser;
    ArrayList<LocationRq> arrayUser1;
    AdapterLocationRq adapter;
    AdapterLocationShare adapter1;
    ImageButton btnBack;
    public static String id;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_location_rq);
        Bundle bdLF = getIntent().getExtras();
        if(bdLF!=null){
            id = bdLF.getString("Userid");
        }
        lvlocationrq = (ListView)findViewById(R.id.listLocatinRq);
        lvlocationshare = (ListView)findViewById(R.id.listSharedLocation);
        //getting the progressbar
        progressBar = (ProgressBar) findViewById(R.id.progressBarLLRq);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        arrayUser = new ArrayList<>();
        arrayUser1 = new ArrayList<>();
        adapter = new AdapterLocationRq(ListLocationRqActivity.this, R.layout.itemlocationrq,arrayUser);
        adapter1 = new AdapterLocationShare(ListLocationRqActivity.this, R.layout.itemlocationshare,arrayUser1);
        GetListLocationShare(myurl);
        GetListLocationRQ(myurl);
        lvlocationrq.setAdapter(adapter);
        lvlocationshare.setAdapter(adapter1);

        btnBack =(ImageButton) findViewById(R.id.btnBackLocation);

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListLocationRqActivity.this,MainActivity.class));

            }
        });

    }

    private void GetListLocationRQ(String url  ){
        RequestQueue requestQueue = Volley.newRequestQueue(ListLocationRqActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  response) {
                progressBar.setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonobject = new JSONObject(response);
                        JSONArray jsonarray = jsonobject.getJSONArray("friend");
                        JSONObject object = jsonarray.getJSONObject(i);
                        arrayUser.add(new LocationRq(
                                object.getInt("Id"),
                                object.getString("Userid1"),
                                object.getString("NaUser1"),
                                object.getString("Userid2"),
                                object.getString("NaUser2"),
                                object.getInt("Status")

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
                Toast.makeText(ListLocationRqActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("Status","0");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    ///////

    private void GetListLocationShare(String url  ){
        RequestQueue requestQueue = Volley.newRequestQueue(ListLocationRqActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  response) {
                progressBar.setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonobject = new JSONObject(response);
                        JSONArray jsonarray = jsonobject.getJSONArray("friend");
                        JSONObject object = jsonarray.getJSONObject(i);
                        arrayUser1.add(new LocationRq(
                                object.getInt("Id"),
                                object.getString("Userid1"),
                                object.getString("NaUser1"),
                                object.getString("Userid2"),
                                object.getString("NaUser2"),
                                object.getInt("Status")

                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter1.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListLocationRqActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                params.put("Status","1");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
