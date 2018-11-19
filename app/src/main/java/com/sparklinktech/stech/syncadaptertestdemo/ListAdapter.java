package com.sparklinktech.stech.syncadaptertestdemo;



import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<NewProduct>
{

    public ArrayList<NewProduct> MainList;

    public ArrayList<NewProduct> StudentListTemp;

    public ListAdapter.SubjectDataFilter studentDataFilter;

    public ListAdapter(Context context, int id, ArrayList<NewProduct> studentArrayList)
    {

        super(context, id, studentArrayList);

        this.StudentListTemp = new ArrayList<NewProduct>();

        this.StudentListTemp.addAll(studentArrayList);

        this.MainList = new ArrayList<NewProduct>();

        this.MainList.addAll(studentArrayList);
    }

    @NonNull
    @Override
    public Filter getFilter()
    {

        if (studentDataFilter == null)
        {

            studentDataFilter = new ListAdapter.SubjectDataFilter();
        }
        return studentDataFilter;
    }


    public class ViewHolder {

        TextView Pname,Pid,Sid,Cat,Dscr,Mrp,Offerpr,Views;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ListAdapter.ViewHolder holder = null;

        if (convertView == null)
        {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.custom_layout, null);

            holder = new ListAdapter.ViewHolder();

            holder.Pid = (TextView) convertView.findViewById(R.id.tv_pid_);

            holder.Pname = (TextView) convertView.findViewById(R.id.tv_pname_);

            holder.Sid = (TextView) convertView.findViewById(R.id.tv_sid_);

            holder.Cat = (TextView) convertView.findViewById(R.id.tv_cat_);
            holder.Dscr = (TextView) convertView.findViewById(R.id.tv_dscr_);

            holder.Mrp = (TextView) convertView.findViewById(R.id.tv_mrp_);
            holder.Offerpr = (TextView) convertView.findViewById(R.id.tv_offerpr_);

            holder.Views = (TextView) convertView.findViewById(R.id.tv_views_);



            convertView.setTag(holder);

        } else
            {

            holder = (ListAdapter.ViewHolder) convertView.getTag();
        }

        NewProduct student = StudentListTemp.get(position);
        holder.Pid.setText(student.getPid());

        holder.Pname.setText(student.getPname());
        holder.Sid.setText(student.getSid());

        holder.Cat.setText(student.getCat());
        holder.Dscr.setText(student.getDscr());

        holder.Mrp.setText(student.getMrp());
        holder.Offerpr.setText(student.getOfferpr());

        holder.Views.setText(student.getViews());



        return convertView;

    }

    private class SubjectDataFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence)
        {

            charSequence = charSequence.toString().toLowerCase();
            Log.e("Filtered TEXT       >>>","          "+charSequence);

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0)
            {

                ArrayList<NewProduct> arrayList1 = new ArrayList<NewProduct>();

                for (int i = 0, l = MainList.size(); i < l; i++)
                {
                    NewProduct subject = MainList.get(i);
                    Log.e("STUDENT LIST<<"+i+">>>","          "+MainList.get(i).toString());

                    Log.e("SUBJECT  <<"+i+">>>   ","  "+subject.toString());

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                    Log.e("FResults <<"+i+">>>","  "+subject);

                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
                Log.e("ARRAY LIST1    >>>","  "+arrayList1);

            } else
                {
                synchronized (this) {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            Log.e("Filtered Results FFF>>>","  "+filterResults);

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults)
        {

            StudentListTemp = (ArrayList<NewProduct>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = StudentListTemp.size(); i < l; i++)
                add(StudentListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }
}