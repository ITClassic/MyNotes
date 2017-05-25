package com.shendrikov.alex.mynotes.adapters;

import android.content.Context;
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
 * Created by Alex on 25.05.2017.
 */

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.PersonViewHolder> {

    private Context mContext;
    private List<Person> mSearchedList;
    private View.OnClickListener mOnItemClickListener = null;

    public View.OnClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(View.OnClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setDataSource(Context context, List<Person> list) {
        mContext = context;
        mSearchedList = list;
        notifyDataSetChanged();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View personView = inflater.inflate(R.layout.my_note_item, parent, false);
        return new PersonViewHolder(personView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Person person = mSearchedList.get(position);
        holder.bindView(person);
        holder.itemView.setOnClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mSearchedList == null ? 0 : mSearchedList.size();
    }

    private Person mPerson;

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.id_name)
        TextView name;
        @BindView(R.id.id_surName)
        TextView surName;
        @BindView(R.id.id_date)
        TextView date;

        public PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindView(Person person) {
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
}
