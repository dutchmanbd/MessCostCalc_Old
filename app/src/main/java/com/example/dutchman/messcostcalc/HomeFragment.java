package com.example.dutchman.messcostcalc;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //private Button btnCost,btnPersonCost,btnView;

    private TextView tvHomeDate;

    private TextView tvHBDate,tvHBDay;

    private TextView tvHLName,tvHLCost;

    private TextView tvHTotalBazar,tvHBPerhead;

    private TextView tvHRent,tvHRentCost;


    private TextView tvHPerheadView,tvHTRView,tvPerheadBView,tvHTBazarView;

    private LinearLayout llayout1,llayout2,llayout3,llayout4,llayout5,llayout6,llayout7;


    private SimpleDateFormat simpleDateFormat;

    private Context context;
    private DBHandler handler;


    public HomeFragment() {
        // Required empty public constructor


    }

    public void setContext(Context context){

        if(this.context == null)
            this.context = context;
        if(this.handler == null)
            this.handler = new DBHandler(this.context);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        btnCost       = (Button) view.findViewById(R.id.btnCost);
//        btnPersonCost = (Button) view.findViewById(R.id.btnPersonCost);
//        btnView       = (Button) view.findViewById(R.id.btnView);
//
//
//        btnCost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                CostFragment costFragment = new CostFragment();
//
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//
//                manager.beginTransaction().replace(R.id.rlContent,costFragment,costFragment.getTag())
//                        .addToBackStack(null)
//                        .commit();
//
//            }
//        });
//
//        btnPersonCost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        btnView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        Display display = ((WindowManager)HomeFragment.this.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight()/7;



//        if(height >= 40)
//            height /= 2;
//        else
//            height = 40;

        height = 50;



        //init part

        llayout1 = (LinearLayout) view.findViewById(R.id.linearLayout11);
        llayout2 = (LinearLayout) view.findViewById(R.id.linearLayout12);
        llayout3 = (LinearLayout) view.findViewById(R.id.linearLayout13);
        llayout4 = (LinearLayout) view.findViewById(R.id.linearLayout14);
        llayout5 = (LinearLayout) view.findViewById(R.id.linearLayout15);
        llayout6 = (LinearLayout) view.findViewById(R.id.linearLayout25);
        llayout7 = (LinearLayout) view.findViewById(R.id.linearLayout26);


        //Date
        tvHomeDate   = (TextView) view.findViewById(R.id.tvHomeDate);

        //Total Rent and perhead
        tvHRent      = (TextView) view.findViewById(R.id.tvHRent);

        tvHRentCost  = (TextView) view.findViewById(R.id.tvHRentCost);

        //Last Date
        tvHBDate     = (TextView) view.findViewById(R.id.tvHBDate);
        tvHBDay      = (TextView) view.findViewById(R.id.tvHBDay);

        //Last bazar member name and tk

        tvHLName     = (TextView) view.findViewById(R.id.tvHLName);
        tvHLCost     = (TextView) view.findViewById(R.id.tvHLCost);



        tvHTotalBazar     = (TextView) view.findViewById(R.id.tvHTotalBazar);
        tvHBPerhead     = (TextView) view.findViewById(R.id.tvHBPerhead);

        tvHPerheadView = (TextView) view.findViewById(R.id.tvHPerheadView);
        tvHTRView      = (TextView) view.findViewById(R.id.tvHTRView);
        tvPerheadBView = (TextView) view.findViewById(R.id.tvPerheadBView);
        tvHTBazarView  = (TextView) view.findViewById(R.id.tvHTBazarView);

        float txtSize;
        if(height/2 >= 35) {
            txtSize = 25;

            tvHomeDate.setTextSize(txtSize);
            tvHRent.setTextSize(txtSize);
            tvHRentCost.setTextSize(txtSize);
            tvHBDate.setTextSize(txtSize);
            tvHBDay.setTextSize(txtSize);
            tvHLName.setTextSize(txtSize);
            tvHLCost.setTextSize(txtSize);
            tvHTotalBazar.setTextSize(txtSize);
            tvHBPerhead.setTextSize(txtSize);

            tvHPerheadView.setTextSize(txtSize);
            tvHTRView.setTextSize(txtSize);
            tvPerheadBView.setTextSize(txtSize);
            tvHTBazarView.setTextSize(txtSize);


        }




//        llayout1.getLayoutParams().height = height;
//        llayout2.getLayoutParams().height = height;
//        llayout3.getLayoutParams().height = height;
//        llayout4.getLayoutParams().height = height;
//        llayout5.getLayoutParams().height = height;
//        llayout6.getLayoutParams().height = height;
//        llayout7.getLayoutParams().height = height;



        //Show Date

        simpleDateFormat = new SimpleDateFormat("EEEE  dd MMMM yyyy");

        String dt = simpleDateFormat.format(new Date());

        tvHomeDate.setText(dt);

        // Show Total and perhead rent cost

        String month = new SimpleDateFormat("MMMM").format(new Date());
        String year = new SimpleDateFormat("yyyy").format(new Date());


        int total = handler.getTotalCostForRent(month,year);
        int perhead = handler.getPerheadCostFromRent(month,year);

        MemberInfo memberInfo = handler.getLastDateAndName(month,year);


        if(total > 0 && perhead > 0){

            tvHRent.setText(total+"");
            tvHRentCost.setText(perhead+"");

        } else {

            Calendar cal1 =  Calendar.getInstance();
            cal1.add(Calendar.MONTH ,-1);

            if(month.equals("January")){

                Calendar cal2 =  Calendar.getInstance();
                cal2.add(Calendar.YEAR ,-1);
                year = new SimpleDateFormat("yyyy").format(cal1.getTime());
            }

            month = new SimpleDateFormat("MMMM").format(cal1.getTime());

            total = handler.getTotalCostForRent(month,year);
            perhead = handler.getPerheadCostFromRent(month,year);
            tvHRent.setText(total+"");
            tvHRentCost.setText(perhead+"");
        }

        if(memberInfo != null){

            tvHBDate.setText(memberInfo.getDate());
            tvHLName.setText(memberInfo.getpName());
            tvHLCost.setText(memberInfo.getpTk());

        } else{

            Calendar cal1 =  Calendar.getInstance();
            cal1.add(Calendar.MONTH ,-1);

            if(month.equals("January")){

                Calendar cal2 =  Calendar.getInstance();
                cal2.add(Calendar.YEAR ,-1);
                year = new SimpleDateFormat("yyyy").format(cal1.getTime());
            }

            month = new SimpleDateFormat("MMMM").format(cal1.getTime());

            memberInfo = handler.getLastDateAndName(month,year);

            if(memberInfo != null){

                tvHBDate.setText(memberInfo.getDate());
                tvHLName.setText(memberInfo.getpName());
                tvHLCost.setText(memberInfo.getpTk());
            }

//            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            Date MyDate = newDateFormat.parse("28/12/2013");
//            newDateFormat.applyPattern("EEEE d MMM yyyy")
//            String MyDate = newDateFormat.format(MyDate);
        }
        try {

            String dTemp = tvHBDate.getText().toString().trim();

            if(dTemp != null && !dTemp.equals("")) {

                Date myDate = new SimpleDateFormat("dd-MM-yyyy").parse(dTemp);
                String dayName = new SimpleDateFormat("EEEE").format(myDate);
                tvHBDay.setText(dayName);
            } else{

                tvHBDay.setText("");
            }


        } catch (ParseException e) {
            tvHBDay.setText("Error day");
        }
        month = new SimpleDateFormat("MMMM").format(new Date());
        year = new SimpleDateFormat("yyyy").format(new Date());

        int totalBazar = handler.getTotalBazar(month,year);

        int bPerCost = handler.getBazarPerCost(month,year);

        tvHTotalBazar.setText(totalBazar+"");
        tvHBPerhead.setText(bPerCost+"");

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        SharedPreferences sharedPref = context.getSharedPreferences("settings",Context.MODE_PRIVATE);

        if(sharedPref != null)
            if(sharedPref.getBoolean("is_without",true)) {

                navigationView.getMenu().findItem(R.id.nav_add_meal).setVisible(false);

            } else if(sharedPref.getBoolean("is_with",true)){

                navigationView.getMenu().findItem(R.id.nav_add_meal).setVisible(true);
            }
    }

}
