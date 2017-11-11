package haidang.com.myappff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HaiDang_PC on 11/10/2017.
 */

public class AdapterListFriend extends BaseAdapter {
    private Context context;
    private  int layout;

    public AdapterListFriend(Context context, int layout, List<Friend> userlist) {
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
    private class ViewHolder{
        TextView txtName;
        CircleImageView imgPic;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final AdapterListFriend.ViewHolder holder;
        if(view == null){
            holder = new AdapterListFriend.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtName = (TextView) view.findViewById(R.id.txtNameLFM);
            holder.imgPic = (CircleImageView) view.findViewById(R.id.imgPicLFM);
            view.setTag(holder);
        }else  {
            holder = (AdapterListFriend.ViewHolder) view.getTag();
        }
        final Friend user = userlist.get(i);
        holder.txtName.setText(user.getNameu2());
        String urlpic ="https://graph.facebook.com/"+user.getUserid2()+"/picture?type=large";
        Picasso.with(context)
                .load(urlpic)
                .placeholder(R.drawable.loading)
                .into(holder.imgPic);
        return view;
    }
}

