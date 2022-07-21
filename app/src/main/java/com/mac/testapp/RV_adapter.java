package com.mac.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class RV_adapter extends RecyclerView.Adapter<RV_adapter.VH> {
    Context mContext;
    ArrayList<String> f_name = new ArrayList<>(),l_name = new ArrayList<>(),gender = new ArrayList<>(),
            Age = new ArrayList<>(),Addr = new ArrayList<>(),Mobile = new ArrayList<>(),Course = new ArrayList<>(),
            year = new ArrayList<>(),RegNo = new ArrayList<>();
    public RV_adapter(Context Contextm, ArrayList<String> fname,ArrayList<String> lname,
                      ArrayList<String> gender,ArrayList<String> Age,ArrayList<String> Addr,
                      ArrayList<String> Mobile, ArrayList<String> Course, ArrayList<String> year,
                      ArrayList<String> RegNo)
    {
        mContext = Contextm;
        this.f_name = fname;
        this.l_name = lname;
        this.gender = gender;
        this.Age = Age;
        this.Addr = Addr;
        this.Mobile = Mobile;
        this.Course = Course;
        this.year = year;
        this.RegNo = RegNo;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(mContext).inflate(R.layout.rv_layout,parent,false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.TV_name.setText(f_name.get(position) );
        holder.TV_regId.setText(RegNo.get(position));
    }

    @Override
    public int getItemCount() {
        return f_name.size();
    }

    public class VH extends  RecyclerView.ViewHolder{
        CircularImageView profpic;
        TextView TV_name,TV_regId;

        public VH(@NonNull View itemView) {
            super(itemView);
            //Initializing objects
            profpic= itemView.findViewById(R.id.propic);
            TV_name= itemView.findViewById(R.id.TV_name);
            TV_regId= itemView.findViewById(R.id.TV_regId);
        }
    }
    public void setData(ArrayList<String> fname,ArrayList<String> lname,
                        ArrayList<String> gender,ArrayList<String> Age,ArrayList<String> Addr,
                        ArrayList<String> Mobile, ArrayList<String> Course, ArrayList<String> year,
                        ArrayList<String> RegNo){
        this.f_name = fname;
        this.l_name = lname;
        this.gender = gender;
        this.Addr = Addr;
        this.Mobile = Mobile;
        this.Course = Course;
        this.year = year;
        this.RegNo = RegNo;
        notifyDataSetChanged();
    }
}
