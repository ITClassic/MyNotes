package com.shendrikov.alex.mynotes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shendrikov.alex.mynotes.R;
import com.shendrikov.alex.mynotes.model.Person;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 17.12.2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Person> mPersonList;

    public MyAdapter (List<Person> list) {
        mPersonList = list;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        @BindView(R.id.name)
        TextView textViewName;
        @BindView(R.id.surname)
        TextView textViewSurName;

        public ViewHolder(View itemView) {
            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(Person person) {
            textViewName.setText(person.getName());
            textViewSurName.setText(person.getSurName());
        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View personView = inflater.inflate(R.layout.my_note_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(personView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        // Get the data model based on position
        Person person = mPersonList.get(position);
        holder.bindView(person);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mPersonList.size();
    }
}
