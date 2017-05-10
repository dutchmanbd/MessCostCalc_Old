package com.example.dutchman.messcostcalc;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMembersFragment extends Fragment implements View.OnClickListener{


    private EditText etMemberName,etAdvancedTK,etIsAvailable;

    private Button btnAddMember, btnUpdateMember, btnShowMember, btnDeleteMember;

    private String mDate,mName,advancedTk,isAvailable;

    private DBHandler handler;
    private Context context;


    public AddMembersFragment() {
        // Required empty public constructor
    }

    public void setContext(Context context){
        this.context = context;
        handler = new DBHandler(this.context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_members, container, false);

        etMemberName    = (EditText) view.findViewById(R.id.etMemberName);
        etAdvancedTK    = (EditText) view.findViewById(R.id.etAdvancedTK);
        etIsAvailable   = (EditText) view.findViewById(R.id.etIsAvailable);


        btnAddMember    = (Button) view.findViewById(R.id.btnAddMember);
        btnUpdateMember = (Button) view.findViewById(R.id.btnUpdateMember);
        btnShowMember   = (Button) view.findViewById(R.id.btnShowMember);
        btnDeleteMember = (Button) view.findViewById(R.id.btnDeleteMember);

        mDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        etIsAvailable.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    etIsAvailable.setError("when member leave set 0 otherwise 1");
                }
            }
        });
        // action

        btnAddMember.setOnClickListener(this);
        btnUpdateMember.setOnClickListener(this);
        btnShowMember.setOnClickListener(this);
        btnDeleteMember.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {


        if((etMemberName.getText().toString().trim().length() > 0 && etAdvancedTK.getText().toString().trim().length() > 0) || view.getId() == R.id.btnShowMember || view.getId() == R.id.btnDeleteMember) {

            mName = etMemberName.getText().toString().trim();

            advancedTk = etAdvancedTK.getText().toString().trim();
            isAvailable = etIsAvailable.getText().toString().trim();

            switch (view.getId()) {

                case R.id.btnAddMember:


                    new AlertDialog.Builder(context)
                            .setTitle("Add Entry")
                            .setMessage("Are you sure want to add this entry ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with add

                                    if(handler.insertMember(mDate,mName,advancedTk)){

                                        Toast.makeText(context, mName+" added successfully", Toast.LENGTH_SHORT).show();

                                    } else{

                                        Toast.makeText(context, "Data can not added.", Toast.LENGTH_SHORT).show();

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

                    break;
                case R.id.btnUpdateMember:

                    new AlertDialog.Builder(context)
                            .setTitle("Update Entry")
                            .setMessage("Are you sure want to Update this entry ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with add

                                    if(handler.isUpdateMember(mDate,mName,advancedTk,isAvailable)){

                                        Toast.makeText(context, mName+" update successfully", Toast.LENGTH_SHORT).show();

                                    } else{

                                        Toast.makeText(context, "No data found for update", Toast.LENGTH_SHORT).show();

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


                    break;

                case R.id.btnShowMember:

//                    List<MemberInfo> list = handler.getMembers();
//
//                    StringBuffer sb = new StringBuffer();
//
//                    sb.append("Date\t\tMember Name\t\tAdvanced Tk\n");
//
//                    for(MemberInfo memberInfo : list){
//
//                        sb.append(memberInfo.getDate()).append("\t\t");
//                        sb.append(memberInfo.getpName()).append("\t\t");
//                        sb.append(memberInfo.getpTk()).append("\n");
//                    }
//
//                    new AlertDialog.Builder(context)
//                            .setTitle("Member List")
//                            .setMessage(sb.toString())
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    dialog.dismiss();
//                                }
//                            })
//                            .show();


                    CustomMemberDetailDialog dialog = new CustomMemberDetailDialog(context,handler);

                    dialog.showMemberDetailDialog();



                    break;

                case R.id.btnDeleteMember:
                    if(etMemberName.getText().toString().trim().length() > 0){

                        String name = etMemberName.getText().toString().trim();

                        if(handler.isDeleteMember(name)){

                            Toast.makeText(context,name+" is successfully deleted.",Toast.LENGTH_SHORT).show();

                        } else{
                            Toast.makeText(context,name+" is not deleted",Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(context,"Fill name field first",Toast.LENGTH_SHORT).show();
                    }

                    break;

            }
        } else{
            Toast.makeText(AddMembersFragment.this.getContext(),"Fill all the field", Toast.LENGTH_SHORT).show();
        }

    }
}
