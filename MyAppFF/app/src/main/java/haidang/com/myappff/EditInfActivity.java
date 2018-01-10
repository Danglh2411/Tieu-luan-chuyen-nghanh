package haidang.com.myappff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditInfActivity extends AppCompatActivity {

    String JsonURL = "https://apptimnhau.000webhostapp.com/getfriend.php";
    String urlUpdateInf = "https://apptimnhau.000webhostapp.com/updateInfUser.php";
    public static String IdUser;
    String urlavatar;
    EditText edtSDT;
    EditText edtNgaySinh;
    EditText edtEmail;
    EditText edtNameHT;

    TextView txtNS;
    TextView txtSDT;
    TextView txtEmail;

    ImageView coverpic;
    CircleImageView avatarphoto;
    TextView txtNameHT;

    Button btnLuuTT;
    Button btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inf);
        Bundle bdLF = getIntent().getExtras();
        if(bdLF!=null){
            IdUser = bdLF.getString("Userid");

        }
        Anhxa();
        // Bắt sự kiện lưu cập nhật thông tin
        btnLuuTT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateInfUser(urlUpdateInf);

            }
        });

        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarUserEdit);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);
        txtNS.setVisibility(View.GONE);
        txtSDT.setVisibility(View.GONE);
        txtEmail.setVisibility(View.GONE);
        txtNameHT.setVisibility(View.GONE);

        edtNameHT.setVisibility(View.GONE);
        edtEmail.setVisibility(View.GONE);
        edtNgaySinh.setVisibility(View.GONE);
        edtSDT.setVisibility(View.GONE);

        btnHuy.setVisibility(View.GONE);
        btnLuuTT.setVisibility(View.GONE);

        RequestQueue requestQueue = Volley.newRequestQueue(EditInfActivity.this);
        StringRequest obreq  = new StringRequest(Request.Method.POST, JsonURL,new Response.Listener<String>() {
            @Override
            public void onResponse(String  response) {
                progressBar.setVisibility(View.INVISIBLE);
                txtNS.setVisibility(View.VISIBLE);
                txtSDT.setVisibility(View.VISIBLE);
                txtEmail.setVisibility(View.VISIBLE);
                txtNameHT.setVisibility(View.VISIBLE);

                btnHuy.setVisibility(View.VISIBLE);
                btnLuuTT.setVisibility(View.VISIBLE);

                edtNameHT.setVisibility(View.VISIBLE);
                edtEmail.setVisibility(View.VISIBLE);
                edtNgaySinh.setVisibility(View.VISIBLE);
                edtSDT.setVisibility(View.VISIBLE);

                try {

                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonarray = jsonobject.getJSONArray("user");
                    JSONObject object = jsonarray.getJSONObject(0);
                    edtNameHT.setText(object.getString("Name"));
                    edtNgaySinh.setText(object.getString("BirthDay"));
                    edtEmail.setText(object.getString("Email"));
                    edtSDT.setText(object.getString("Phone"));
                    Picasso.with(EditInfActivity.this)
                            .load(object.getString("Photo"))
                            .placeholder(R.drawable.loading)
                            .into(coverpic);

                    urlavatar ="https://graph.facebook.com/"+IdUser+"/picture?type=large";
                    Picasso.with(EditInfActivity.this)
                            .load(urlavatar)
                            .placeholder(R.drawable.loading)
                            .into(avatarphoto);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditInfActivity.this, "Không tìm thấy", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Userid",IdUser);
                return params;
            }
        };
        requestQueue.add(obreq);
    }


    private void Anhxa() {
        edtNgaySinh =(EditText) findViewById(R.id.edtNgaysinhUserEdit);
        edtEmail =(EditText) findViewById(R.id.edtEmailUserEdit);
        edtSDT =(EditText) findViewById(R.id.edtDienthoaiUserEdit);
        edtNameHT =(EditText) findViewById(R.id.edtNameUserEdit);
        avatarphoto = (CircleImageView)findViewById(R.id.imgAvatarUserEdit);
        coverpic = (ImageView)findViewById(R.id.imgCoverUserEdit);


        txtNS = (TextView)findViewById(R.id.tvNSUserEdit);
        txtSDT = (TextView)findViewById(R.id.tvSDTUserEdit);
        txtEmail = (TextView)findViewById(R.id.tvEmailUserEdit);
        txtNameHT = (TextView)findViewById(R.id.tvNameUserEdit);
        btnLuuTT = (Button) findViewById(R.id.btnLuuEdit);
        btnHuy = (Button) findViewById(R.id.btnHuyEdit);

    }

    //Hàm cập nhật thông tin
    private void UpdateInfUser(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(EditInfActivity.this, "Cập nhật thành công...", Toast.LENGTH_SHORT).show();
                            Intent mhuser = new Intent(EditInfActivity.this, InfUserActivity.class);
                            startActivity(mhuser);
                            finish();
                        } else {
                            //Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainActivity.this, "Error!!!", Toast.LENGTH_LONG).show();
                        Log.d("AAA", "Error:\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Userid", IdUser);
                params.put("Name", edtNameHT.getText().toString().trim());
                params.put("Birthday", edtNgaySinh.getText().toString().trim());
                params.put("Phone", edtSDT.getText().toString().trim());
                params.put("Email", edtEmail.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
