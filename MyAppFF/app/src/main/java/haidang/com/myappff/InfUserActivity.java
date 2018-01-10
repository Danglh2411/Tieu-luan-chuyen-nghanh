package haidang.com.myappff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class InfUserActivity extends AppCompatActivity {

    String JsonURL = "https://apptimnhau.000webhostapp.com/getfriend.php";
    String urldeleteUser ="https://apptimnhau.000webhostapp.com/deleteUser.php";
    public static String IdUser;
    String urlavatar;
    TextView tvSDT;
    TextView tvNgaySinh;
    TextView tvEmail;

    TextView txtNS;
    TextView txtSDT;
    TextView txtEmail;

    ImageView coverpic;
    CircleImageView avatarphoto;
    TextView tvName;

    Button btnChinhTT;
    Button btnXoaTK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_user);
        Bundle bdLF = getIntent().getExtras();
        if(bdLF!=null){
            IdUser = bdLF.getString("Userid");

        }
        Anhxa();
        // Bắt sự kiện chỉnh sửa thông tin
        btnChinhTT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mhedit = new Intent(InfUserActivity.this, EditInfActivity.class);
                mhedit.putExtra("Userid", IdUser);
                startActivity(mhedit);
                finish();
            }
        });


        // Bắt sự kiện chỉnh sửa thông tin
        btnXoaTK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ShowDialogDeleteUser();
            }
        });
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarUser);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);
        txtNS.setVisibility(View.GONE);
        txtSDT.setVisibility(View.GONE);
        txtEmail.setVisibility(View.GONE);
        btnChinhTT.setVisibility(View.GONE);
        btnXoaTK.setVisibility(View.GONE);

        RequestQueue requestQueue = Volley.newRequestQueue(InfUserActivity.this);
        StringRequest obreq  = new StringRequest(Request.Method.POST, JsonURL,new Response.Listener<String>() {
            @Override
            public void onResponse(String  response) {
                progressBar.setVisibility(View.INVISIBLE);
                txtNS.setVisibility(View.VISIBLE);
                txtSDT.setVisibility(View.VISIBLE);
                txtEmail.setVisibility(View.VISIBLE);
                btnXoaTK.setVisibility(View.VISIBLE);
                btnChinhTT.setVisibility(View.VISIBLE);

                try {

                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonarray = jsonobject.getJSONArray("user");
                    JSONObject object = jsonarray.getJSONObject(0);
                    tvName.setText(object.getString("Name"));
                    tvNgaySinh.setText(object.getString("BirthDay"));
                    tvEmail.setText(object.getString("Email"));
                    tvSDT.setText(object.getString("Phone"));
                    Picasso.with(InfUserActivity.this)
                            .load(object.getString("Photo"))
                            .placeholder(R.drawable.loading)
                            .into(coverpic);

                    urlavatar ="https://graph.facebook.com/"+IdUser+"/picture?type=large";
                    Picasso.with(InfUserActivity.this)
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
                Toast.makeText(InfUserActivity.this, "Không tìm thấy", Toast.LENGTH_LONG).show();
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
        tvNgaySinh =(TextView)findViewById(R.id.txtNgaysinhUser);
        tvEmail =(TextView)findViewById(R.id.txtEmailUser);
        tvSDT =(TextView)findViewById(R.id.txtDienthoaiUser);
        tvName =(TextView)findViewById(R.id.tvNameUser);
        avatarphoto = (CircleImageView)findViewById(R.id.imgAvatarUser);
        coverpic = (ImageView)findViewById(R.id.imgCoverUser);


        txtNS = (TextView)findViewById(R.id.tvNSUser);
        txtSDT = (TextView)findViewById(R.id.tvSDTUser);
        txtEmail = (TextView)findViewById(R.id.tvEmailUser);
        btnChinhTT = (Button) findViewById(R.id.btnEdit);
        btnXoaTK = (Button) findViewById(R.id.btnXoaTK);

    }


    ////Xóa tài khoản
    private  void DeleteUser (String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("succ " +
                                "ess")){
                            //Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
                        }else
                        {
                            //Toast.makeText(MainActivity.this,"Error!",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(MainActivity.this,"Error!!!",Toast.LENGTH_LONG).show();
                        Log.d("AAA","Error:\n" +error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Userid",IdUser);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ShowDialogDeleteUser(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Bạn có muốn xóa tài khoản?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteUser(urldeleteUser);
                        Toast.makeText(InfUserActivity.this,"Đã xóa!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InfUserActivity.this,LoginActivity.class));
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
