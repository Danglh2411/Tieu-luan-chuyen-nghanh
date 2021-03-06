package haidang.com.myappff;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HaiDang_PC on 10/27/2017.
 */

public class AdapterLocationRq extends BaseAdapter {
    private Context context;
    private int layout;
    String urlShareLocation = "https://apptimnhau.000webhostapp.com/UpdateSttLocation.php";
    String userid1;
    String userid2;


    public AdapterLocationRq(Context context, int layout, List<Friend> userlist) {
        this.context = context;
        this.layout = layout;
        this.userlist = userlist;

    }

    private List<Friend> userlist;

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

    private class ViewHolder {
        TextView txtName;
        Button btnShare;
        CircleImageView imgPic;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final AdapterLocationRq.ViewHolder holder;
        if (view == null) {
            holder = new AdapterLocationRq.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txtNameLocationRq);
            holder.imgPic = (CircleImageView) view.findViewById(R.id.imgPicLocationRq);
            holder.btnShare = (Button) view.findViewById(R.id.btnChapNhanLocationRq);
            view.setTag(holder);
        } else {
            holder = (AdapterLocationRq.ViewHolder) view.getTag();
        }
        final Friend user = userlist.get(i);

        holder.txtName.setText(user.getNameu1());
        String urlpic = "https://graph.facebook.com/" + user.getUserid1() + "/picture?type=large";

        Picasso.with(context)
                .load(urlpic)
                .placeholder(R.drawable.loading)
                .into(holder.imgPic);
        // Bat su kien cho share
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userid1 = user.getUserid1();
                userid2 = user.getUserid2();
                UpdateSttLocation(urlShareLocation);
                holder.btnShare.setText("Đã gửi");
                holder.btnShare.setEnabled(false);
                Intent adt = new Intent(context, ListLocationRqActivity.class);
                adt.putExtra("Userid",user.getUserid2());
                context.startActivity(adt);
            }
        });
        return view;
    }



    //// Hàm cập nhật trạng thái chia sser vị trí
    private void UpdateSttLocation(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                        Toast.makeText(context, "Error!!!", Toast.LENGTH_LONG).show();
                        Log.d("AAA", "Error:\n" + error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Userid1",userid1);
                params.put("Userid2",userid2);
                params.put("Status","2");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

