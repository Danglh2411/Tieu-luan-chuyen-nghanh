package haidang.com.myappff;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FindFriendActivity extends AppCompatActivity {
    String myurl ="https://apptimnhau.000webhostapp.com/findfriend.php";
    ListView lvuser;
    ArrayList<User> arrayUser;
    UserAdapter adapter;
    ImageButton btnFind;
    public String iffbuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);
        btnFind =(ImageButton) findViewById(R.id.btnFindFriend);

        Bundle bdff = getIntent().getExtras();
        iffbuser = bdff.getString("userid1");


        btnFind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lvuser = (ListView)findViewById(R.id.listFindFriend);
                arrayUser = new ArrayList<>();
                adapter = new UserAdapter(FindFriendActivity.this, R.layout.userline,arrayUser);
                lvuser.setAdapter(adapter);
                GetData(myurl);
            }
        });


    }


    private void GetData(String url  ){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response){
                        for(int i =0; i<response.length();i++)
                        {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arrayUser.add(new User(
                                        object.getString("Id"),
                                        object.getString("Name"),
                                        object.getString("Email"),
                                        object.getString("Phone"),
                                        object.getInt("Status")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(FindFriendActivity.this,"Loi!",Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


}
