package haidang.com.myappff;

import android.content.Context;
import android.graphics.Bitmap;
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

public class AdapterFindFriend extends BaseAdapter {
    private Context context;
    private  int layout;
    Bitmap bitmap_pic;
    String userid1 ;
    String userid2;
    String nameu1;
    String nameu2;

    String urlFriendRq = "https://apptimnhau.000webhostapp.com/insertFriendRq.php";


    public AdapterFindFriend(Context context, int layout, List<User> userlist, String userid1, String nameu1) {
        this.context = context;
        this.layout = layout;
        this.userlist = userlist;
        this.userid1 = userid1;
        this.nameu1 = nameu1;
    }

    private List<User> userlist;
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
        Button addFriendRq;
        CircleImageView imgPic;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final AdapterFindFriend.ViewHolder holder;
        if(view == null){
            holder = new AdapterFindFriend.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtName = (TextView) view.findViewById(R.id.txtNameLF);
            holder.imgPic = (CircleImageView) view.findViewById(R.id.imgPicLF);
            holder.addFriendRq =(Button) view.findViewById(R.id.btnKetBan);
            view.setTag(holder);
        }else  {
            holder = (AdapterFindFriend.ViewHolder) view.getTag();
        }
        final User user = userlist.get(i);
        holder.txtName.setText(user.getName());
        String urlpic =user.getPhoto();
        Picasso.with(context)
                .load(urlpic)
                .placeholder(R.drawable.loading)
                .into(holder.imgPic);
        // Bat su kien cho btnXoa va btnMore
        holder.addFriendRq.setOnClickListener(new    View.OnClickListener(){
            @Override
            public void onClick(View view){
                userid2 = user.getId();
                nameu2 = user.getName();
                AddFriendRq(urlFriendRq);
                holder.addFriendRq.setEnabled(false);
                holder.addFriendRq.setText("Đã gửi");
            }
        });
        return view;
    }
// Hàm thêm yêu cầu kết bạn vào cơ sở dữ liệu
    private  void AddFriendRq(String url){
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
}
