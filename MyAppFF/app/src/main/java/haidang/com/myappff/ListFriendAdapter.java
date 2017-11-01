package haidang.com.myappff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by HaiDang_PC on 10/27/2017.
 */

public class ListFriendAdapter extends BaseAdapter {
    private Context context;
    private  int layout;

    public ListFriendAdapter(Context context, int layout, List<User> userlist) {
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
        ImageButton imgbtnDel, imgbtnMore;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ListFriendAdapter.ViewHolder holder;
        if(view == null){
            holder = new ListFriendAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtName = (TextView) view.findViewById(R.id.txtNameLF);
            holder.txtPhone = (TextView) view.findViewById(R.id.txtPhoneLF);
            holder.imgbtnMore =(ImageButton) view.findViewById(R.id.btnMoreLF);
            view.setTag(holder);
        }else  {
            holder = (ListFriendAdapter.ViewHolder) view.getTag();
        }
        final User user = userlist.get(i);
        holder.txtName.setText(user.getName());
        holder.txtPhone.setText(user.getPhone());
        // Bat su kien cho btnXoa va btnMore
        holder.imgbtnMore.setOnClickListener(new    View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(context,"Xóa"+user.getName(),Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
