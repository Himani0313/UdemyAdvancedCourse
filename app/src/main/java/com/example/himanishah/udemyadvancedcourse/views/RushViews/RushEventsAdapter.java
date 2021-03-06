package com.example.himanishah.udemyadvancedcourse.views.RushViews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.himanishah.udemyadvancedcourse.R;
import com.example.himanishah.udemyadvancedcourse.activities.BaseActivity;
import com.example.himanishah.udemyadvancedcourse.entities.RushEvents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by himanishah on 8/29/17.
 */

public class RushEventsAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_LIST_HEADER = 1;
    public static final int VIEW_TYPE_EXPANDABLE_LIST_HEADER = 2;
    public static final int VIEW_TYPE_EXPANDABLE_LIST_CHILD = 3;
    private final int VIEW_TYPE_LIST_FOOTER = 4;

    private List<Item> data;
    private BaseActivity activity;
    private LayoutInflater layoutInflater;
    private RushEventListener rushEventListener;

    public RushEventsAdapter(BaseActivity activity, RushEventListener rushEventListener) {
        this.activity = activity;
        this.rushEventListener = rushEventListener;
        layoutInflater = activity.getLayoutInflater();
        data = new ArrayList<>();
    }

    public List<Item> getData() {
        return data;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return VIEW_TYPE_LIST_HEADER;
        }
        position--;

        if (position<data.size()){
            return data.get(position).type;
        }
        position -= data.size();

        if(position<data.size()){
            return VIEW_TYPE_LIST_FOOTER;
        }
        position--;

        throw new IllegalArgumentException("We are being asked for a viewType for a position" + position +"although"+
                "we are at the end of the adapter supportviewholders. Please Check your adapter");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View expandableHeaderView = layoutInflater.inflate(R.layout.list_expandable_header,parent,false);
        View rushEventView = layoutInflater.inflate(R.layout.list_rush_events,parent,false);
        View rushHeadeView = layoutInflater.inflate(R.layout.header_fragment_rush,parent,false);
        View rushFooterView = layoutInflater.inflate(R.layout.footer_rush_event,parent,false);

        switch (viewType){
            case VIEW_TYPE_LIST_HEADER : return new RushHeaderHolder(rushHeadeView);
            case VIEW_TYPE_EXPANDABLE_LIST_CHILD:
                final RushEventsHolder rushEventsHolder = new RushEventsHolder(rushEventView);
                rushEventsHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RushEvents rushEvents = (RushEvents) rushEventsHolder.itemView.getTag();
                        rushEventListener.OnRushEventClicked(rushEvents);
                    }
                });
                return rushEventsHolder;
            case VIEW_TYPE_LIST_FOOTER:
                return new RushFooterHolder(rushFooterView);
            case VIEW_TYPE_EXPANDABLE_LIST_HEADER:
                return new RushExpandableHeaderHolder(expandableHeaderView);

        }
        throw new IllegalArgumentException(viewType + "is not supported in this adapter");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RushExpandableHeaderHolder){
            position--;
            final Item item = data.get(position);
            final RushExpandableHeaderHolder itemController = (RushExpandableHeaderHolder) holder;
            itemController.refferalItem = item;
            itemController.headerName.setText(item.header);

            if(item.invisibleChildren == null){
                itemController.buttonToggle.setImageResource(R.mipmap.ic_cancel);
            }
            else {
                itemController.buttonToggle.setImageResource(R.mipmap.ic_plus);
            }

            itemController.backgroundView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.invisibleChildren == null){
                        item.invisibleChildren = new ArrayList<Item>();
                        int count =0;
                        int position = data.indexOf(itemController.refferalItem);
                        while (data.size()>position+1 && data.get(position+1).type == VIEW_TYPE_EXPANDABLE_LIST_CHILD){
                            item.invisibleChildren.add(data.remove(position+1));
                            count++;
                        }
                        notifyItemRangeRemoved(position+1, count);
                        itemController.buttonToggle.setImageResource(R.mipmap.ic_plus);
                    }
                    else {
                        int position = data.indexOf(itemController.refferalItem);
                        int index = position+1;
                        for(Item item1: item.invisibleChildren){
                            data.add(index,item1);
                            index++;
                        }
                        notifyItemRangeInserted(position+1, index-position-1);
                        itemController.buttonToggle.setImageResource(R.mipmap.ic_cancel);
                        item.invisibleChildren = null;
                    }
                }
            });
        }
        else if(holder instanceof RushEventsHolder){
            position--;
            RushEventsHolder holder1 = (RushEventsHolder) holder;
            holder1.populate(data.get(position).rushEvents);
        }
        else if(holder instanceof RushHeaderHolder){
            RushHeaderHolder rushHeaderHolder = (RushHeaderHolder)holder;
        }
        else if(holder instanceof RushFooterHolder){
            ((RushFooterHolder) holder).populate(activity);
        }
    }

    @Override
    public int getItemCount() {
        int count =2;
        if(data.size()>0){
            count+=data.size();
        }
        return count;
    }

    public interface RushEventListener{
        void OnRushEventClicked(RushEvents rushEvents);
    }
}
