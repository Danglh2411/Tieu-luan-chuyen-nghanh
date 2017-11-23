package haidang.com.myappff;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HaiDang_PC on 10/27/2017.
 */

public class AdapterFriendRq extends BaseAdapter {
    private Context context;
    private  int layout;
    String urlAddFriend = "https://apptimnhau.000webhostapp.com/addfriend.php";
    String urldeleteFriendRq = "https://apptimnhau.000webhostapp.com/deleteFriendRq.php";
    String userid1;
    String userid2;
    String nameu1;
    String nameu2;


    public AdapterFriendRq(Context context, int layout, List<FiendRq> userlist) {
        this.context = context;
        this.layout = layout;
        this.userlist = userlist;

    }

    private List<FiendRq> userlist;
    @Override
    public int getCount() {
        return userlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView txtName;
        Button addFriend;
        CircleImageView imgPic;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final AdapterFriendRq.ViewHolder holder;
        if(view == null){
            holder = new AdapterFriendRq.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtName = (TextView) view.findViewById(R.id.txtNameFR);
            holder.imgPic = (CircleImageView) view.findViewById(R.id.imgPicFR);
            holder.addFriend =(Button) view.findViewById(R.id.btnChapNhanFR);
            view.setTag(holder);
        }else  {
            holder = (AdapterFriendRq.ViewHolder) view.getTag();
        }
        final FiendRq user = userlist.get(i);

        holder.txtName.setText(user.getNameu1());
        String urlpic ="https://graph.facebook.com/"+user.getUserid1()+"/picture?type=large";

        Picasso.with(context)
                .load(urlpic)
                .placeholder(R.drawable.loading)
                .into(holder.imgPic);
        // Bat su kien cho add
        holder.addFriend.setOnClickListener(new    View.OnClickListener(){
            @Override
            public void onClick(View view){
                    userid1 = user.getUserid1();
                    userid2 = user.getUserid2();
                    nameu1 = user.getNameu1();
                    nameu2 = user.getNameu2();
                    AddFriend1(urlAddFriend);
                    AddFriend2(urlAddFriend);
                    DeleteFriendRq(urldeleteFriendRq);
                    holder.addFriend.setText("Bạn bè");
                    holder.addFriend.setEnabled(false);
            }
        });
        return view;
    }


    // Hàm thêm bạn bè vào đanh sách bạn bè
    private  void AddFriend1(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                params.put("Userid1",userid1);
                params.put("NaUser1",nameu1);
                params.put("Userid2",userid2);
                params.put("NaUser2",nameu2);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    ////
    private  void AddFriend2(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                params.put("Userid1",userid2);
                params.put("NaUser1",nameu2);
                params.put("Userid2",userid1);
                params.put("NaUser2",nameu1);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    /////
    private  void DeleteFriendRq(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                params.put("Userid1",userid1);
                params.put("Userid2",userid2);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
