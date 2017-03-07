package com.nuhin13.com.SimpleLifeHack;

/**
 * Created by nuhin13 on 2/19/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<AlbumOfPlaylist> albumList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public RelativeLayout relativeCard;


        public MyViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            count = (TextView) itemView.findViewById(R.id.count);

            relativeCard =(RelativeLayout)itemView.findViewById(R.id.relativeCard);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);

            CardView card =(CardView)itemView.findViewById(R.id.card_viewforplaylist);

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition(); //getAdapterPosition();

                    if (position == 0) {
                        Intent intent = new Intent(mContext,AmericanHacker.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 1) {
                        Intent intent = new Intent(mContext,YuriOstr.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 2) {
                        Intent intent = new Intent(mContext,MrGear.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 3) {
                        Intent intent = new Intent(mContext,min5Craft.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 4) {
                        Intent intent = new Intent(mContext,IndianLifeHack.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 5) {
                        Intent intent = new Intent(mContext,CrazyRussianHacker.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 6) {
                        Intent intent = new Intent(mContext,ThaiTrick.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 7) {
                        Intent intent = new Intent(mContext,WeAreX.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 8) {
                        Intent intent = new Intent(mContext,List25.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 9) {
                        Intent intent = new Intent(mContext,LxgDesign.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 10) {
                        Intent intent = new Intent(mContext,MrSagoo.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 11) {
                        Intent intent = new Intent(mContext,WowShow.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 12) {
                        Intent intent = new Intent(mContext,SlivkiShowEn.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 13) {
                        Intent intent = new Intent(mContext,AmrMci.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 14) {
                        Intent intent = new Intent(mContext,Beamazed.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 15) {
                        Intent intent = new Intent(mContext,Wengie.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 16) {
                        Intent intent = new Intent(mContext,HouseHoldHacker.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 17) {
                        Intent intent = new Intent(mContext,Vinesrb.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 18) {
                        Intent intent = new Intent(mContext,AdrinneFinch.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 19) {
                        Intent intent = new Intent(mContext,NewKew.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 20) {
                        Intent intent = new Intent(mContext,ProtectYourMind.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 21) {
                        Intent intent = new Intent(mContext,Adology.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 22) {
                        Intent intent = new Intent(mContext,MrHacker.class);
                        mContext.startActivity(intent);
                    }
                    else if (position == 23) {
                        Intent intent = new Intent(mContext,MadeMyDay.class);
                        mContext.startActivity(intent);
                    }


                }
            });
        }


    }


    public AlbumsAdapter(Context mContext, List<AlbumOfPlaylist> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        AlbumOfPlaylist album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getNumOfSongs() + " Videos");

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopupMenu(holder.overflow);

            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

}

