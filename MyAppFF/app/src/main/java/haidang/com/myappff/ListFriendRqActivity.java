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

public class ListFriendRqActivity extends AppCompatActivity {

    String myurl ="https://apptimnhau.000webhostapp.com/getfriendrq.php";
    ListView lvuser;
    ArrayList<FiendRq> arrayUser;
    AdapterFriendRq adapter;
    ImageButton btnBack;
    String id;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friend_rq);
        Bundle bdLF = getIntent().getExtras();
        if(bdLF!=null){
            id = bdLF.getString("Userid");
        }
        lvuser = (ListView)findViewById(R.id.listFriendRq);
        //getting the progressbar
        progressBar = (ProgressBar) findViewById(R.id.progressBarLFRQ);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        arrayUser = new ArrayList<>();
        adapter = new AdapterFriendRq(ListFriendRqActivity.this, R.layout.itemfriendrq,arrayUser);
        lvuser.setAdapter(adapter);
        GetListFiendRQ(myurl);
        btnBack =(ImageButton) findViewById(R.id.btnBackFR);

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListFriendRqActivity.this,MainActivity.class));

            }
        });

    }

    private void GetListFiendRQ(String url  ){
        RequestQueue requestQueue = Volley.newRequestQueue(ListFriendRqActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  response) {
                progressBar.setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonobject = new JSONObject(response);
                        JSONArray jsonarray = jsonobject.getJSONArray("friend");
                        JSONObject object = jsonarray.getJSONObject(i);
                        arrayUser.add(new FiendRq(
                                object.getInt("Id"),
                                object.getString("Userid1"),
                                object.getString("NaUser1"),
                                object.getString("Userid2"),
                                object.getString("NaUser2")

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
                Toast.makeText(ListFriendRqActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
