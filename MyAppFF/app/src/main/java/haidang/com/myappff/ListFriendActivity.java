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

public class ListFriendActivity extends AppCompatActivity {

    String myurl ="https://apptimnhau.000webhostapp.com/listfriend.php";
    ListView lvuser;
    ArrayList<Friend> arrayUser;
    AdapterListFriend adapter;
    ImageButton btnBack;
    String id;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friend);
        //getting the progressbar
        progressBar = (ProgressBar) findViewById(R.id.progressBarLF);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        Bundle bdLF = getIntent().getExtras();
        if(bdLF!=null){
            id = bdLF.getString("Userid");
            //Toast.makeText(ListFriendActivity.this,id,Toast.LENGTH_LONG).show();
        }
        lvuser = (ListView)findViewById(R.id.listFriend);



        arrayUser = new ArrayList<>();
        adapter = new AdapterListFriend(ListFriendActivity.this, R.layout.itemfriend,arrayUser);
        lvuser.setAdapter(adapter);
        GetListFiend(myurl);
        btnBack =(ImageButton) findViewById(R.id.btnBackLF);

        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListFriendActivity.this,MainActivity.class));
                finish();

            }
        });
    }
    private void GetListFiend(String url  ){
        RequestQueue requestQueue = Volley.newRequestQueue(ListFriendActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String  response) {
                progressBar.setVisibility(View.INVISIBLE);
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonobject = new JSONObject(response);
                        JSONArray jsonarray = jsonobject.getJSONArray("friend");
                        JSONObject object = jsonarray.getJSONObject(i);
                        arrayUser.add(new Friend(
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
                Toast.makeText(ListFriendActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
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
