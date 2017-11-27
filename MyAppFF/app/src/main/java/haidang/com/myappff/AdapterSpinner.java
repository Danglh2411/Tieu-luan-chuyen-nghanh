package haidang.com.myappff;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HaiDang_PC on 10/27/2017.
 */

public class AdapterSpinner extends BaseAdapter {
    private Context context;
    private int layout;
    String name;

    public AdapterSpinner(Context context, int layout, List<ShareLoaction> userlist ,String name) {
        this.context = context;
        this.layout = layout;
        this.userlist = userlist;
        this.name = name;

    }

    private List<ShareLoaction> userlist;

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
        CircleImageView imgPic;
        LinearLayout item;


    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final AdapterSpinner.ViewHolder holder;
        if (view == null) {
            holder = new AdapterSpinner.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName = (TextView) view.findViewById(R.id.txtNameSpinner);
            holder.imgPic = (CircleImageView) view.findViewById(R.id.imgPicSpinner);
            holder.item = (LinearLayout)view.findViewById(R.id.layoutLocation);
            view.setTag(holder);
        } else {
            holder = (AdapterSpinner.ViewHolder) view.getTag();
        }
        final ShareLoaction user = userlist.get(i);

        holder.txtName.setText(user.getNameu1());
        final String urlpic = "https://graph.facebook.com/" + user.getUserid1() + "/picture?type=large";

        Picasso.with(context)
                .load(urlpic)
                .placeholder(R.drawable.loading)
                .into(holder.imgPic);
        holder.txtName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("Lati", user.getLati());
                intent.putExtra("Longi", user.getLongi());
                intent.putExtra("Userid", user.getUserid2());
                intent.putExtra("Name", name);
                context.startActivity(intent);

            }
        });
        return view;
    }
}

