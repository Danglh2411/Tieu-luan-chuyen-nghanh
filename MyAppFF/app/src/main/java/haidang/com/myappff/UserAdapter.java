package haidang.com.myappff;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HaiDang_PC on 10/26/2017.
 */

public class UserAdapter extends BaseAdapter{
    String urlfriendrq ="https://apptimnhau.000webhostapp.com/insertfriendrq.php";
    private Context context;
    private  int layout;
    private String userid2;

    public UserAdapter(Context context, int layout, List<User> userlist) {
        this.context = context;
        this.layout = layout;
        this.userlist = userlist;
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
        TextView txtName, txtPhone;
        ImageButton imgbtnDetail;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtName = (TextView) view.findViewById(R.id.txtName);
            holder.txtPhone = (TextView) view.findViewById(R.id.txtPhone);
            holder.imgbtnDetail =(ImageButton) view.findViewById(R.id.btnDetail);
            view.setTag(holder);
        }else  {
            holder = (ViewHolder) view.getTag();
        }
        final User user = userlist.get(i);
        holder.txtName.setText(user.getName());
        holder.txtPhone.setText(user.getPhone());
        // Bat su kien cho btnXoa va btnSua
        holder.imgbtnDetail.setOnClickListener(new    View.OnClickListener(){
            @Override
            public void onClick(View view){
                userid2 = user.getId();
                AddFriendRq(urlfriendrq);
            }
        });
        return view;
    }

    private  void AddFriendRq(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(context,"Success!",Toast.LENGTH_LONG).show();

                        }else
                        {
                            Toast.makeText(context,"Error!",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,"Error!!!",Toast.LENGTH_LONG).show();
                        Log.d("AAA","Error:\n" +error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Userid1","123457789");
                params.put("Userid2",userid2);


                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
