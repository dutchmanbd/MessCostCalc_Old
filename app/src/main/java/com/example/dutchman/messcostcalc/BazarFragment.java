package com.example.dutchman.messcostcalc;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class BazarFragment extends Fragment {


    private TextView tvBazarDate;
    private EditText etBazarTK;
    private Spinner etBazarName;

    private Button btnBazarAdd,btnBazarClear;

    private String bDate,bMonth,bYear;
    private String pName,advanceTk;

    private Context context;

    private DBHandler handler;

    private List<String> mNameList;


    public BazarFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context){

        this.context = context;

        this.handler = new DBHandler(this.context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bazar, container, false);


        mNameList = new ArrayList<>();

        mNameList = handler.getMNameList();


        tvBazarDate = (TextView) view.findViewById(R.id.tvBazarDate);
        etBazarName = (Spinner) view.findViewById(R.id.etBazarName);
        etBazarTK   = (EditText) view.findViewById(R.id.etBazarTk);

        btnBazarAdd = (Button) view.findViewById(R.id.btnBazarAdd);
        btnBazarClear = (Button) view.findViewById(R.id.btnBazarClear);

        mNameList.add(0,"Select Name");

        if(mNameList.size() > 1) {

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mNameList);

            etBazarName.setAdapter(adapter);
        }
        etBazarName.setSelection(0);

        bDate  = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        tvBazarDate.setText(bDate);

        bMonth = new SimpleDateFormat("MMMM").format(new Date());
        bYear = new SimpleDateFormat("yyyy").format(new Date());

        if(!handler.isMemberExists()){

            CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

            dialog.goToAddMemeberFragment();

        }

        btnBazarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!handler.isMemberExists()){

                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

                    dialog.goToAddMemeberFragment();

                } else {

                    if (etBazarName.getSelectedItemPosition() > 0) {

                        if (etBazarTK.getText().toString().trim().length() > 0) {

                            pName = etBazarName.getSelectedItem().toString().trim();
                            advanceTk = etBazarTK.getText().toString().trim();

                            if (handler.isMemberExists(pName)) {

                                new AlertDialog.Builder(context)
                                        .setTitle("Add Entry")
                                        .setMessage("Are you sure want to add this entry ?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with add

                                                if (handler.insertBazarInfo(bDate, bMonth, bYear, pName, advanceTk)) {

                                                    Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(context, pName + " is not exists. Please check member list", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Add bazar Cost first", Toast.LENGTH_SHORT).show();
                        }
                        etBazarName.setSelection(0);

                    } else {

                        etBazarName.performClick();
                        Toast.makeText(context, "Please select name first ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnBazarClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!handler.isMemberExists()){

                    CustomGoAlertDialog dialog = new CustomGoAlertDialog(context);

                    dialog.goToAddMemeberFragment();

                } else {

                    if (etBazarName.getSelectedItemPosition() > 0 || etBazarTK.getText().toString().trim().length() > 0) {

                        etBazarName.setSelection(0);
                        etBazarTK.setText("");

                    } else {

                        Toast.makeText(context, "Already empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        return view;
    }

}
