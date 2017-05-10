package com.example.dutchman.messcostcalc;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMealFragment extends Fragment {

    private TextView tvMealDate;
    private EditText etPersonMeal;
    private Spinner etMealName;

    private Button btnMealAdd,btnMealUpdate;


    private Context context;
    private DBHandler handler;

    private List<String> mNameList;
    private List<String> allNameList;
    private List<String> selectedList;


    private String date,month,year,name,meal;


    public AddMealFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context){
        this.context = context;
        this.handler = new DBHandler(this.context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);

        month = new SimpleDateFormat("MMMM").format(new Date());
        year = new SimpleDateFormat("yyyy").format(new Date());

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        mNameList = new ArrayList<>();

        allNameList = new ArrayList<>();

        allNameList = handler.getMNameList();

        selectedList = new ArrayList<>();

        selectedList = handler.getMNameList(date);



//        for(String mName: allNameList){
//            for(String selectedItem: selectedList){
//
//                if(!mName.contains(selectedItem))
//                    mNameList.add(mName);
//            }
//        }

        //allNameList.removeAll(selectedList);

        mNameList.addAll(allNameList);



        tvMealDate    = (TextView) view.findViewById(R.id.tvMealDate);

        etMealName    = (Spinner) view.findViewById(R.id.etMealName);
        etPersonMeal  = (EditText) view.findViewById(R.id.etPersonMeal);

        btnMealAdd    = (Button) view.findViewById(R.id.btnMealAdd);
        btnMealUpdate = (Button) view.findViewById(R.id.btnMealUpdate);


        mNameList.add(0,"Select Name");

        if(mNameList.size() > 1) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, mNameList);

            etMealName.setAdapter(adapter);

        }

        etMealName.setSelection(0);

        tvMealDate.setText(date);

        if(!handler.isMemberExists()){

            CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

            dialog.goToAddMemeberFragment();

        }

        btnMealAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(handler.isMemberExists()) {

                    if (etMealName.getSelectedItemPosition() > 0) {

                        if (etPersonMeal.getText().toString().trim().length() > 0) {

                            name = etMealName.getSelectedItem().toString().trim();
                            meal = etPersonMeal.getText().toString().trim();

                            //Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

                            if (handler.isMemberExists(name)) {

                                new AlertDialog.Builder(context)
                                        .setTitle("Add Entry")
                                        .setMessage("Are you sure want to add this entry ?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with add


                                                if (handler.insertMealInfo(date, month, year, name, meal)) {

                                                    Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
                                                    etMealName.setSelection(0);

                                                } else {

                                                    Toast.makeText(context, "Data can not added", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            } else {
                                Toast.makeText(context, name + " is not exists. Please check member list", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, "Add number of meal for " + etMealName.getSelectedItem(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        etMealName.performClick();
                        Toast.makeText(context, "Please select name first", Toast.LENGTH_SHORT).show();
                    }

                } else{


                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

                    dialog.goToAddMemeberFragment();


                }
            }
        });

        return view;
    }

}
