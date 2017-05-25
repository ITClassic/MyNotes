package com.shendrikov.alex.mynotes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.shendrikov.alex.mynotes.R;
import com.shendrikov.alex.mynotes.activity.MyNotesActivity;
import com.shendrikov.alex.mynotes.model.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 17.12.2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.PersonViewHolder>
        implements Filterable {

    // Store a member variable for the contacts
    private List<Person> mPersonList;
    private MyNotesActivity mMyNotesActivity;
    private Context mContext;
    private PersonFilter mPersonFilter;
    private View.OnClickListener mOnItemClickListener = null;

    protected List<Person> mFilteredList;

    public View.OnClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setDataSource(MyNotesActivity activity, Context context, List<Person> list) {
        mPersonList = list;
        mFilteredList = list;
        mMyNotesActivity = activity;
        mContext = context;
        notifyDataSetChanged();
        getFilter();
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        // Inflate the custom layout
        View personView = inflater.inflate(R.layout.my_note_item, parent, false);
        // Return a new holder instance
        return new PersonViewHolder(personView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        // Get the data model based on position
        Person person = mFilteredList.get(position);
        holder.bindView(person);
        holder.itemView.setOnClickListener(mOnItemClickListener);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mFilteredList == null ? 0 : mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        if (mPersonFilter == null) {
            mPersonFilter = new PersonFilter();
        }
        return mPersonFilter;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.id_name)
        TextView name;
        @BindView(R.id.id_surName)
        TextView surName;
        @BindView(R.id.id_date)
        TextView date;

        private Person mPerson;

        public PersonViewHolder(View itemView) {

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(Person person) {
            mPerson = person;
            id.setText(String.valueOf(person.getId()));
            name.setText(person.getName());
            surName.setText(person.getSurName());
            date.setText(person.getTime());
        }

        public Person getPerson() {
            return mPerson;
        }
    }

    private class PersonFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                filterResults.values = mPersonList;
                filterResults.count = mPersonList.size();
            } else {
                // We perform filtering operation
                ArrayList<Person> tempPersonList = new ArrayList<>();

                // search content in person list
                for (Person person: mPersonList) {
                    if (person.getName().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            person.getSurName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempPersonList.add(person);
                    }
                }
                filterResults.values = tempPersonList;
                filterResults.count = tempPersonList.size();
            }
            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredList = (ArrayList<Person>)results.values;
            notifyDataSetChanged();
        }
    }
}
