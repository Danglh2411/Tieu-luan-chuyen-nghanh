package haidang.com.myappff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfFriendActivity extends AppCompatActivity {

    String JsonURL = "https://apptimnhau.000webhostapp.com/getfriend.php";
    String urldeleteFriend = "https://apptimnhau.000webhostapp.com/deleteFriend.php";
    String urladdLocationRq = "https://apptimnhau.000webhostapp.com/UpdateSttLocation.php";
    public static String IdFriend;
    public static String IdUser;
    public static String NameFriend;
    public static String NameUser;
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

    Button YCViTri;
    Button HuyKB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_friend);
        //OneSignal
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        Bundle bdLF = getIntent().getExtras();
        if(bdLF!=null){
            IdFriend = bdLF.getString("Userid1");
            NameFriend = bdLF.getString("NaUser1");
            IdUser = bdLF.getString("Userid2");

        }
        //Thi?t l?p thu?c tính Tag trong OneSignal
        OneSignal.sendTag("User_ID",IdUser);
        Anhxa();
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);
        txtNS.setVisibility(View.GONE);
        txtSDT.setVisibility(View.GONE);
        txtEmail.setVisibility(View.GONE);
        YCViTri.setVisibility(View.GONE);
        HuyKB.setVisibility(View.GONE);

        RequestQueue requestQueue = Volley.newRequestQueue(InfFriendActivity.this);
        StringRequest obreq  = new StringRequest(Request.Method.POST, JsonURL,new Response.Listener<String>() {
            @Override
            public void onResponse(String  response) {
                progressBar.setVisibility(View.INVISIBLE);
                txtNS.setVisibility(View.VISIBLE);
                txtSDT.setVisibility(View.VISIBLE);
                txtEmail.setVisibility(View.VISIBLE);
                YCViTri.setVisibility(View.VISIBLE);
                HuyKB.setVisibility(View.VISIBLE);

                try {

                    JSONObject jsonobject = new JSONObject(response);
                    JSONArray jsonarray = jsonobject.getJSONArray("user");
                    JSONObject object = jsonarray.getJSONObject(0);
                    tvName.setText(object.getString("Name"));
                    tvNgaySinh.setText(object.getString("BirthDay"));
                    tvEmail.setText(object.getString("Email"));
                    tvSDT.setText(object.getString("Phone"));
                    Picasso.with(InfFriendActivity.this)
                            .load(object.getString("Photo"))
                            .placeholder(R.drawable.loading)
                            .into(coverpic);

                    urlavatar ="https://graph.facebook.com/"+IdFriend+"/picture?type=large";
                    Picasso.with(InfFriendActivity.this)
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
                Toast.makeText(InfFriendActivity.this, "Không tìm thấy", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Userid",IdFriend);
                return params;
            }
        };
        requestQueue.add(obreq);

        /// Xét sự kiện xóa kết bạn
        HuyKB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ShowDialogUnFriend();

            }
        });
        /// Xét sự kiện yêu cầu chia sẽ vị trí
        YCViTri.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateSttLocationRq(urladdLocationRq);
                YCViTri.setText("Chờ xác nhận");
                YCViTri.setBackgroundResource(R.drawable.style_btn_clicked);
                YCViTri.setEnabled(false);
                sendNotification(IdFriend);

            }
        });

    }
    private void ShowDialogUnFriend(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Bạn có muốn hủy kết bạn?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteFriend1(urldeleteFriend);
                        DeleteFriend2(urldeleteFriend);
                        Toast.makeText(InfFriendActivity.this,"Đã xóa!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(InfFriendActivity.this,MainActivity.class));
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


    private void Anhxa() {
        tvNgaySinh =(TextView)findViewById(R.id.txtNgaysinh);
        tvEmail =(TextView)findViewById(R.id.txtEmail);
        tvSDT =(TextView)findViewById(R.id.txtDienthoai);
        tvName =(TextView)findViewById(R.id.tvUserName);
        avatarphoto = (CircleImageView)findViewById(R.id.imgAvatarFriend);
        coverpic = (ImageView)findViewById(R.id.imgCoverFriend);


        txtNS = (TextView)findViewById(R.id.tvNS);
        txtSDT = (TextView)findViewById(R.id.tvSDT);
        txtEmail = (TextView)findViewById(R.id.tvEmail);
        YCViTri = (Button) findViewById(R.id.btnYcViTri);
        HuyKB = (Button) findViewById(R.id.btnHuyKb);

    }

    private  void DeleteFriend1(String url){
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
                params.put("Userid1",IdUser);
                params.put("Userid2",IdFriend);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    /////
    private  void DeleteFriend2 (String url){
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
                params.put("Userid1",IdFriend);
                params.put("Userid2",IdUser);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    /// Gửi yêu cầu chia sẽ vị trí
    private void UpdateSttLocationRq(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {

                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(this, "Error!!!", Toast.LENGTH_LONG).show();
                        Log.d("AAA", "Error:\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Userid1",IdUser);
                params.put("Userid2",IdFriend);
                params.put("Status","1");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void sendNotification(final String userIdFriend)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);


                    //This is a Simple Logic to Send Notification different Device Programmatically....


                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic NTZlOTJjZDAtMzAxOS00NTI2LTg4MzctZDBlMWUzMjY5MTEx");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"74e1a00d-7240-4836-98f9-990b8f5d30fa\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + userIdFriend + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"Bạn có yêu cầu chia sẻ vị trí\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);
                       // Toast.makeText(InfFriendActivity.this, "Đã gửi yêu cầu", Toast.LENGTH_LONG).show();
                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }

            }

        });
    }
}
