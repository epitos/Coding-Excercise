package exercise.coding.codiingexercisejpmc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import exercise.coding.codiingexercisejpmc.R;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private ArrayList<String> albumTitlesList;

    public AlbumAdapter(ArrayList<String> albumTitlesList) {
        this.albumTitlesList = albumTitlesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        viewHolder.albumTitle.setText(albumTitlesList.get(pos));
    }

    @Override
    public int getItemCount() {
        return albumTitlesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView albumTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            albumTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
