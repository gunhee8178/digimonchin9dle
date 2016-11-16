package com.example.ryan.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by darrentong on 4/11/16.
 */
public class LeaderboardAdapter extends BaseAdapter {
    public ArrayList<LeaderBoxes> list = new ArrayList<LeaderBoxes>();
    private static LayoutInflater inflater = null;
    private Context mContext;

    public LeaderboardAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    static class ViewHolder {
        TextView name;
        TextView rank;
        TextView wins;

    }

    // Notify observers that the data set has changed
    public void add(LeaderBoxes item) {
        list.add(item);
        notifyDataSetChanged();
    }

    // Clears the list adapter of all items.
    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final LeaderBoxes leaderBadge = (LeaderBoxes) getItem(position);

        RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.leader_card, null);


        final TextView rankView = (TextView) itemLayout.findViewById(R.id.place_name);
        rankView.setText(leaderBadge.rank);

        final TextView nameView = (TextView) itemLayout.findViewById(R.id.country_name);
        nameView.setText(leaderBadge.name);

        final TextView winsView = (TextView) itemLayout.findViewById(R.id.rank_name);
        winsView.setText(leaderBadge.wins+"-"+leaderBadge.losses+"-"+leaderBadge.draws);

//        Drawable drawable = new BitmapDrawable(mContext.getResources(), placeBadge.flagBit);
//        final ImageView flagView = (ImageView) itemLayout.findViewById(R.id.flag_image);
//        flagView.setImageDrawable(drawable);

        // Return the View you just created
        return itemLayout;

    }
}


