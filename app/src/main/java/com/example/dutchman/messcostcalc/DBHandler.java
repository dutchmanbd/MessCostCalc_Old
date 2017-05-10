package com.example.dutchman.messcostcalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dutchman on 10/8/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private Context context;

    // Database Name
    private static final String DATABASE_NAME       = "messCostInfo";

    // table name
    private static final String TB_MEMBER_INFO      = "member_info";

    private static final String TB_BAZAR_INFO       = "bazar_info";
    private static final String TB_BAZAR_PER_CREDIT = "bazar_per_credit";

    private static final String TB_MEAL_INFO        = "meal_info";

    private static final String TB_RENT_INFO        = "rent_info";
    private static final String TB_RENT_PER_CREDIT  = "rent_per_credit";

    private static final String TB_MEMBERS          = "members";

    // Table Columns names
    //For BazarInfo Table
    private static final String KEY_ADVANCE_TK   = "advance_tk";
    private static final String KEY_ID           = "id";
    private static final String KEY_DATE         = "date";
    private static final String KEY_NAME         = "person_name";
    private static final String KEY_TK           = "tk";

    //For Person Credit table
    private static final String KEY_CREDIT       = "credit";

    //For Rent Info table

    private static final String KEY_MONTH        = "month";
    private static final String KEY_YEAR         = "year";
    private static final String KEY_H_RENT       = "house_rent";
    private static final String KEY_GUS_CURRENT  = "gus_current";
    private static final String KEY_SERVENT      = "servent";
    private static final String KEY_NET_BILL     = "net_bill";
    private static final String KEY_PAPER        = "paper";
    private static final String KEY_DIRST        = "dirst";
    private static final String KEY_OTHERS       = "others";
    private static final String KEY_MEMBERS      = "members";
    private static final String KEY_TOTAL_RENT   = "total_rent";
    private static final String KEY_PERHEAD      = "perhead";

    private static final String KEY_IS_AVAILABLE = "isAvailable";
    private static final String KEY_MEAL         = "meal";

    private static final String KEY_NUMBER       = "number";

    // Sql Command create table


    private static final String SQL_TB_MEMBER_INFO = "CREATE TABLE "+ TB_MEMBER_INFO +"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DATE +" TEXT," + KEY_MONTH+" TEXT,"+KEY_YEAR+" TEXT,"+
            KEY_NAME +" TEXT,"+
            KEY_ADVANCE_TK+" INTEGER," + KEY_IS_AVAILABLE +" INTEGER" + ")";

    private static final String SQL_TB_BAZAR_INFO = "CREATE TABLE "+ TB_BAZAR_INFO+"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DATE +" TEXT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_NAME +" TEXT,"+ KEY_TK +" INTEGER" + ")";

    private static final String SQL_TB_BAZAR_PER_CREDIT = "CREATE TABLE "+ TB_BAZAR_PER_CREDIT +"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DATE +" TEXT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_NAME +" TEXT,"+ KEY_CREDIT +" INTEGER" + ")";


    private static final String SQL_TB_RENT_INFO = "CREATE TABLE "+ TB_RENT_INFO +"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_H_RENT +" INTEGER,"+ KEY_GUS_CURRENT +" INTEGER,"+ KEY_SERVENT +" INTEGER,"+ KEY_NET_BILL +" INTEGER,"+ KEY_PAPER +" INTEGER,"+KEY_DIRST+" INTEGER,"
            + KEY_OTHERS +
            " INTEGER,"+ KEY_MEMBERS +" INTEGER,"+ KEY_TOTAL_RENT +" INTEGER,"+ KEY_PERHEAD +" INTEGER" + ")";

    private static final String SQL_TB_RENT_PER_CREDIT = "CREATE TABLE "+ TB_RENT_PER_CREDIT +"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DATE +" TEXT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_NAME +" TEXT,"+ KEY_CREDIT +" INTEGER" + ")";


    private static final String SQL_TB_MEAL_INFO = "CREATE TABLE "+ TB_MEAL_INFO+"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_DATE +" TEXT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_NAME +" TEXT,"+ KEY_MEAL +" INTEGER" + ")";

    private static final String SQL_TB_MEMBERS = "CREATE TABLE "+ TB_MEMBERS+"("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_MONTH +" TEXT,"+
            KEY_YEAR +" TEXT,"+ KEY_NUMBER +" INTEGER" + ")";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_TB_MEMBER_INFO);

        db.execSQL(SQL_TB_BAZAR_INFO);
        db.execSQL(SQL_TB_BAZAR_PER_CREDIT);

        db.execSQL(SQL_TB_RENT_INFO);
        db.execSQL(SQL_TB_RENT_PER_CREDIT);

        db.execSQL(SQL_TB_MEAL_INFO);

        //db.execSQL(SQL_TB_MEMBERS);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TB_MEMBER_INFO);

        db.execSQL("DROP TABLE IF EXISTS " + TB_BAZAR_INFO);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_BAZAR_PER_CREDIT);

        db.execSQL("DROP TABLE IF EXISTS " + TB_RENT_INFO);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_RENT_PER_CREDIT);

        db.execSQL("DROP TABLE IF EXISTS "+ TB_MEAL_INFO);

        //db.execSQL("DROP TABLE IF EXISTS "+ TB_MEMBERS);

        // Create tables again
        onCreate(db);
    }


    // Start TB_MEMBER_INFO

    // Insert Member into TB_MEMBER_INFO

    public boolean insertMember(String date, String name, String advancedTk){


        String month = new SimpleDateFormat("MMMM").format(new Date());
        String year = new SimpleDateFormat("yyyy").format(new Date());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DATE, date);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_NAME, name);                                    // Person Name
        values.put(KEY_ADVANCE_TK, Integer.parseInt(advancedTk));     // Person TK
        values.put(KEY_IS_AVAILABLE, 1);

        // Inserting Row
        long res = db.insert(TB_MEMBER_INFO, null, values);
        db.close(); // Closing database connection

        if(res == -1)
            return false;
        else
            return true;

    }


    //Table MEMBERS Insert

//    public boolean insertNumberOfMembers(String month, String year, int numbers){
//
//
////        String month = new SimpleDateFormat("MMMM").format(new Date());
////        String year = new SimpleDateFormat("yyyy").format(new Date());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(KEY_MONTH, month);
//        values.put(KEY_YEAR, year);
//        values.put(KEY_NUMBER, numbers);     // Member Number
//
//        // Inserting Row
//        long res = db.insert(TB_MEMBERS, null, values);
//        db.close(); // Closing database connection
//
//        if(res == -1)
//            return false;
//        else
//            return true;
//
//    }

    // Get number of members

    public int getNumberOfMembers(){

        String isAvail = "1";

        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEMBER_INFO+" WHERE "+KEY_IS_AVAILABLE +" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{isAvail});

        int persons = 0;
        if(cursor.moveToFirst())
            persons = cursor.getCount();

        cursor.close();
        db.close();

        return persons;
    }






    // Get Number of member in specefic month and year

    public int getNumberOfMembers(String type,String month,String year){


        String currMonth = new SimpleDateFormat("MMMM").format(new Date());
        String currYear = new SimpleDateFormat("yyyy").format(new Date());

        int persons = 0;

        if(currMonth.equals(month) && currYear.equals(year)){

            persons = getNumberOfMembers();

            return persons;

        } else{

            if(type.equals("Meal")){


                persons = getNumberOfMembersFromMeal(month,year);

            } else if(type.equals("Rent")){

                persons = getNumberOfMembersFromRent(month,year);
            }

        }

        return persons;
    }


    // Get Number of member in specefic month and year

    public int getNumberOfMembersFromMeal(String month,String year){


        String selectQuery = "SELECT DISTINCT "+KEY_NAME+" FROM " + TB_BAZAR_PER_CREDIT+" WHERE "+KEY_MONTH +" = ? AND "+KEY_YEAR+" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{month,year});

        int persons = 0;
        if(cursor.moveToFirst())
            persons = cursor.getCount();

        cursor.close();
        db.close();

        return persons;
    }



    // Get Number of member in specefic month and year

    public int getNumberOfMembersFromRent(String month,String year){


        String selectQuery = "SELECT DISTINCT "+KEY_NAME+" FROM " + TB_RENT_PER_CREDIT+" WHERE "+KEY_MONTH +" = ? AND "+KEY_YEAR+" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{month,year});

        int persons = 0;
        if(cursor.moveToFirst())
            persons = cursor.getCount();

        cursor.close();
        db.close();

        return persons;
    }




    // GET Member info

    public List<MemberInfo> getMembers(){

        List<MemberInfo> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TB_MEMBER_INFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MemberInfo memberInfo = new MemberInfo(cursor.getString(1),cursor.getString(4),cursor.getString(5));

                list.add(memberInfo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    // get Member Name
    public List<String> getMNameList(){

        List<String> list = new ArrayList<>();

        String isAvail = "1";

        // Select All Query
        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEMBER_INFO+" WHERE "+KEY_IS_AVAILABLE +" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{isAvail});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                list.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    // Member List by date

    public List<String> getMNameList(String date){

        List<String> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEAL_INFO+" WHERE "+KEY_DATE +" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{date});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                list.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }

    public boolean isMemberExists(String name){

        String isAvail = "1";

        // Select All Query
        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEMBER_INFO+" WHERE "+KEY_NAME+" = ? AND "+KEY_IS_AVAILABLE +" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{name,isAvail});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            if(cursor.getString(0).equals(name)) {
                cursor.close();
                db.close();
                return true;
            }
        }

        cursor.close();
        db.close();

        return false;
    }




    public boolean isUpdateMember(String date, String name, String advancedTk, String isAvail){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DATE, date);
        values.put(KEY_NAME, name);                                    // Person Name
        values.put(KEY_ADVANCE_TK, Integer.parseInt(advancedTk));     // Person TK
        values.put(KEY_IS_AVAILABLE, Integer.parseInt(isAvail));

        int res = db.update(TB_MEMBER_INFO, values,KEY_NAME+" = ?", new String[]{name});

        db.close();

        return res > 0;
    }

    public boolean isDeleteMember(String name){

        SQLiteDatabase db = this.getWritableDatabase();


        int res = db.delete(TB_MEMBER_INFO,KEY_NAME+" = ?",new String[]{name});

        db.close();

        return res > 0;
    }

    // get memberlist

    public List<String> getMemberList(String month,String year){
        List<String> nameList = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_NAME +" FROM " + TB_MEMBER_INFO +" WHERE "+KEY_MONTH+" = ? AND "+KEY_YEAR+" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{month,year});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                nameList.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return nameList;
    }


    // END TB_MEMBER_INFO


    // Start TB_BAZAR_INFO

    // Date,Month,Year,Name,Tk

    public boolean insertBazarInfo(String date, String month, String year, String name, String tk){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DATE, date);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_NAME, name);                            // Person Name
        values.put(KEY_TK, Integer.parseInt(tk));               // Bazar TK

        // Inserting Row
        long res = db.insert(TB_BAZAR_INFO, null, values);
        db.close(); // Closing database connection

        if(res == -1)
            return false;
        else
            return true;

    }

    // view last date for homeFragment

    public MemberInfo getLastDateAndName(String month, String year){


        MemberInfo memberInfo = null;

        SQLiteDatabase db = this.getWritableDatabase();

        //SELECT ROWID from MYTABLE order by ROWID DESC limit 1

        String sql = "SELECT "+KEY_DATE+","+KEY_NAME+","+ KEY_TK+" FROM "+ TB_BAZAR_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ? ORDER BY "+ KEY_ID +" DESC LIMIT 1";

        Cursor cursor = db.rawQuery(sql,new String[]{month,year});


        if(cursor == null)
            return null;


        if(cursor.moveToFirst()){

            memberInfo = new MemberInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2));

        }


        cursor.close();
        db.close();


        return memberInfo;
    }


    public int getTotalBazar(String month,String year){

        int totalCost = 0;

        int persons = getNumberOfMembers();

        if(persons > 0) {

            SQLiteDatabase db = this.getWritableDatabase();


            //String sql = "SELECT SUM("+KEY_TK+") FROM "+TB_BAZAR_INFO+" WHERE "+ KEY_MONTH +" = "+ month +" AND "+ KEY_YEAR +" = "+ year;

            String sql = "SELECT SUM(" + KEY_TK + ") FROM " + TB_BAZAR_INFO + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ?";

            Cursor cursor = db.rawQuery(sql, new String[]{month, year});

            if (cursor.moveToFirst()) {

                totalCost = cursor.getInt(0);

            }

            cursor.close();
            db.close();
            return totalCost;

        } else{
            return totalCost;
        }
    }




    public int getBazarPerCost(String month,String year){

        int personCost = 0;


        int persons = getNumberOfMembers();

        if(persons > 0) {


            SQLiteDatabase db = this.getWritableDatabase();


            //String sql = "SELECT SUM("+KEY_TK+") FROM "+TB_BAZAR_INFO+" WHERE "+ KEY_MONTH +" = "+ month +" AND "+ KEY_YEAR +" = "+ year;

            String sql = "SELECT SUM(" + KEY_TK + ") FROM " + TB_BAZAR_INFO + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ?";

            Cursor cursor = db.rawQuery(sql, new String[]{month, year});

            int totalCost = 0;

            if (cursor.moveToFirst()) {

                totalCost = cursor.getInt(0);

            }

            //Toast.makeText(context,persons+" person "+totalCost,Toast.LENGTH_SHORT).show();


            personCost = totalCost / persons;

            cursor.close();
            db.close();
            if(personCost > 0)
                return personCost+1;
            else
                return personCost;

        } else{
            return personCost;
        }


    }


    //Call
    public int getTotalBazarTk(String month, String year){

        int sumOfBazar = 0;

        SQLiteDatabase db = this.getWritableDatabase();


        String sql = "SELECT "+"SUM("+KEY_TK+") FROM "+ TB_BAZAR_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";


        Cursor cursor = db.rawQuery(sql,new String[]{month,year});

        if (cursor.moveToFirst()) {

            sumOfBazar = cursor.getInt(0);

        }


        return sumOfBazar;
    }

    // For Meal History
    // Show Bazar Info
    // Date,Month,Year,Name,Tk

    public List<MemberInfo> getBazarInfo(String month, String year){

        List<MemberInfo> list = new ArrayList<>();
        MemberInfo memberInfo;

        int sumOfBazar = getTotalBazarTk(month,year);

        SQLiteDatabase db = this.getWritableDatabase();


        //String sql = "SELECT "+KEY_DATE+","+KEY_NAME+","+ KEY_TK+",SUM("+KEY_TK+") FROM "+ TB_BAZAR_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";

        String sql = "SELECT "+KEY_DATE+","+KEY_NAME+","+ KEY_TK+" FROM "+ TB_BAZAR_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";

        Cursor cursor = db.rawQuery(sql,new String[]{month,year});


        if(cursor == null)
            return null;


        if (cursor.moveToFirst()) {
            do {
                //memberInfo = new MemberInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));

                memberInfo = new MemberInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2),sumOfBazar);
                list.add(memberInfo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return list;
    }




    // END TB_BAZAR_INFO


    // Start TB_BAZAR_PER_CREDIT

    // Date,Month,Year,Name,Tk

    public boolean insertPersonCredit(String type,String date, String month, String year, String name, String tk){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DATE, date);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_NAME, name);                            // Person Name
        values.put(KEY_CREDIT, Integer.parseInt(tk));     // Person TK

        // Inserting Row
        long res = -1;
        if(type.equals("meal")){

            res = db.insert(TB_BAZAR_PER_CREDIT, null, values);

        } else if(type.equals("rent")){
            res = db.insert(TB_RENT_PER_CREDIT, null, values);
        }

        db.close(); // Closing database connection

        if(res == -1)
            return false;
        else
            return true;

    }


    public boolean isMemberExists(){


        String isAvail = "1";

        // Select All Query
        String selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_MEMBER_INFO+" WHERE "+KEY_IS_AVAILABLE +" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{isAvail});

        int persons = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            persons = cursor.getCount();
        }

        cursor.close();
        db.close();



        return persons > 0;

    }



    //Search memeber by name
    public boolean isMemberExistForSearch(String type, String month, String year, String name){

        boolean isExist;

        String currMonth = new SimpleDateFormat("MMMM").format(new Date());
        String currYear = new SimpleDateFormat("yyyy").format(new Date());


        if(month.equals(currMonth) && year.equals(currYear)){

            isExist = isMemberExists(name);

        } else{

            isExist = isMemberExists(type,month,year,name);

        }


        return isExist;

    }



    //isMemeberExists in month and year

    public boolean isMemberExists(String type, String month, String year, String name){

        // Select All Query
        String selectQuery = "";
        if(type.equals("meal"))
            selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_BAZAR_PER_CREDIT+" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ? AND "+ KEY_NAME +" = ?";
        else if(type.equals("rent"))
            selectQuery = "SELECT "+KEY_NAME+" FROM " + TB_RENT_PER_CREDIT+" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ? AND "+ KEY_NAME +" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{month,year,name});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            if(cursor.getString(0).equals(name)) {
                cursor.close();
                db.close();
                return true;
            }
        }

        cursor.close();
        db.close();

        return false;

    }


    //search data result

    public int searchPaidTakaByName(String type, String month, String year, String name){

        // Select All Query
        String sql="";
        int pCost = 0;

        if(type.equals("meal"))

            sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_BAZAR_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";

        else if(type.equals("rent"))

            sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_RENT_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, new String[]{month, year, name});


        if(cursor.moveToFirst()){

            pCost = cursor.getInt(0);
        }


        cursor.close();
        db.close();

        return pCost;

    }



    // Update Person Credit

    public boolean isUpdatePersonCredit(String type,String date, String month, String year, String name, String tk){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DATE, date);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_NAME, name);                            // Person Name
        values.put(KEY_CREDIT, Integer.parseInt(tk));          // Person TK

        // Updated Row
        long res = -1;
        if(type.equals("meal")){

            //res = db.insert(TB_BAZAR_PER_CREDIT, null, values);
            res = db.update(TB_BAZAR_PER_CREDIT, values,KEY_NAME+" = ?", new String[]{name});

        } else if(type.equals("rent")){
            //res = db.insert(TB_RENT_PER_CREDIT, null, values);
            res = db.update(TB_RENT_PER_CREDIT, values,KEY_NAME+" = ?", new String[]{name});
        }

        db.close(); // Closing database connection

        return res > 0;

    }



    // For DebitFragment

    public List<PersonCredit> getPersonCredit(String type){

        List<PersonCredit> list = new ArrayList<>();
        List<MemberInfo> nameList = new ArrayList<>();

        String selectQuery = "SELECT "+ KEY_NAME +" FROM " + TB_MEMBER_INFO +" WHERE "+KEY_IS_AVAILABLE+" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"1"});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MemberInfo memberInfo = new MemberInfo(cursor.getString(0));

                nameList.add(memberInfo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        db = this.getWritableDatabase();

        String month = new SimpleDateFormat("MMMM").format(new Date());
        String year = new SimpleDateFormat("yyyy").format(new Date());

        for(MemberInfo memberInfo : nameList){

            //String sql = "SELECT "+KEY_CREDIT+" FROM "+TB_BAZAR_PER_CREDIT+" WHERE "+KEY_MONTH+"="+month+" AND "+KEY_YEAR+"="+year+" AND "+KEY_NAME+"="+memberInfo.getpName();

            String sql="";
            if(type.equals("meal")) {
                sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_BAZAR_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";

                cursor = db.rawQuery(sql, new String[]{month, year, memberInfo.getpName()});

            } else if(type.equals("rent")){

                sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_RENT_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";

                cursor = db.rawQuery(sql, new String[]{month, year, memberInfo.getpName()});

            }

            if(cursor.moveToFirst()){
                do{
//                    PersonCredit personCredit = new PersonCredit(cursor.getString(0),month,year,memberInfo.getpName(),cursor.getString(1));

                    PersonCredit personCredit = new PersonCredit(memberInfo.getpName(),cursor.getInt(0));
                    //Toast.makeText(context,personCredit.getName()+" "+personCredit.getTk(),Toast.LENGTH_SHORT).show();



                    list.add(personCredit);



                } while(cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return list;
    }

    //DebitFragment item clicked

    public List<PersonCredit> getPersonCreditDetails(String type,String month,String year,String name){

        List<PersonCredit> list = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;

        String sql="";
        if(type.equals("meal")) {
            sql = "SELECT "+KEY_DATE+","+KEY_CREDIT+" FROM " + TB_BAZAR_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";

            cursor = db.rawQuery(sql, new String[]{month, year, name});

        } else if(type.equals("rent")){

            sql = "SELECT " + KEY_DATE + ","+KEY_CREDIT+" FROM " + TB_RENT_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";

            cursor = db.rawQuery(sql, new String[]{month, year, name});
        }

        if(cursor.moveToFirst()){
            do{
                PersonCredit personCredit = new PersonCredit(cursor.getString(0),Integer.toString(cursor.getInt(1)));
                list.add(personCredit);

            } while(cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return list;
    }


    public List<String> getNameOfMembers(){
        List<String> nameList = new ArrayList<String>();

        String selectQuery = "SELECT "+ KEY_NAME +" FROM " + TB_MEMBER_INFO +" WHERE "+KEY_IS_AVAILABLE+" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"1"});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);

                nameList.add(name);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return nameList;
    }

    public List<String> getNameOfMembersFromMeal(String month, String year){

        List<String> nameList = new ArrayList<String>();


        String selectQuery = "SELECT DISTINCT "+KEY_NAME+" FROM " + TB_BAZAR_PER_CREDIT+" WHERE "+KEY_MONTH +" = ? AND "+KEY_YEAR+" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{month,year});

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);

                nameList.add(name);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();



        return nameList;
    }

    public List<String> getNamerOfMembersFromRent(String month, String year){

        List<String> nameList = new ArrayList<String>();

        String selectQuery = "SELECT DISTINCT "+KEY_NAME+" FROM " + TB_RENT_PER_CREDIT+" WHERE "+KEY_MONTH +" = ? AND "+KEY_YEAR+" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{month,year});

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);

                nameList.add(name);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return nameList;
    }


    // Get Number of member in specefic month and year

    public List<String> getNameOfMembers(String type,String month,String year){


        String currMonth = new SimpleDateFormat("MMMM").format(new Date());
        String currYear = new SimpleDateFormat("yyyy").format(new Date());

        List<String> nameList = null;

        if(currMonth.equals(month) && currYear.equals(year)){

            nameList = getNameOfMembers();


        } else{

            if(type.equals("Meal")){


                nameList = getNameOfMembersFromMeal(month,year);


            } else if(type.equals("Rent")){

                nameList = getNamerOfMembersFromRent(month,year);

            }

        }

        return nameList;

    }




    //For MealHistoryFragment

    public List<PersonCredit> getPersonCreditForMHistory(String type, String month, String year){

        List<PersonCredit> list = new ArrayList<>();
        List<String> nameList = getNameOfMembers(type,month,year);

        /*String selectQuery = "SELECT "+ KEY_NAME +" FROM " + TB_MEMBER_INFO +" WHERE "+KEY_MONTH+" = ? AND "+KEY_YEAR+" = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{month,year});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MemberInfo memberInfo = new MemberInfo(cursor.getString(0));

                nameList.add(memberInfo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();*/

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        for(String name : nameList){

            //String sql = "SELECT "+KEY_CREDIT+" FROM "+TB_BAZAR_PER_CREDIT+" WHERE "+KEY_MONTH+"="+month+" AND "+KEY_YEAR+"="+year+" AND "+KEY_NAME+"="+memberInfo.getpName();

            String sql="";

            sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_BAZAR_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
            cursor = db.rawQuery(sql, new String[]{month, year, name});


            if(cursor.moveToFirst()){
                do{
//                    PersonCredit personCredit = new PersonCredit(cursor.getString(0),month,year,memberInfo.getpName(),cursor.getString(1));

                    PersonCredit personCredit = new PersonCredit(name,cursor.getInt(0));
                    //Toast.makeText(context,personCredit.getName()+" "+personCredit.getTk(),Toast.LENGTH_SHORT).show();



                    list.add(personCredit);



                } while(cursor.moveToNext());
            }
        }

        if(cursor != null)
            cursor.close();
        if(db != null)
            db.close();

        return list;
    }



    //For RentHistoryFragment

    public List<PersonCredit> getPersonCreditForRHistory(String type, String month, String year){

        List<PersonCredit> list = new ArrayList<>();
        List<String> nameList = getNameOfMembers(type,month,year);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;

        for(String name : nameList){

            String sql="";

            sql = "SELECT SUM(" + KEY_CREDIT + ") FROM " + TB_RENT_PER_CREDIT + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ? AND " + KEY_NAME + " = ?";
            cursor = db.rawQuery(sql, new String[]{month, year, name});


            if(cursor.moveToFirst()){
                do{
                    PersonCredit personCredit = new PersonCredit(name,cursor.getInt(0));
                    list.add(personCredit);

                } while(cursor.moveToNext());
            }
        }

        if(cursor != null)
            cursor.close();
        if(db != null)
            db.close();

        return list;
    }




    // START TB_RENT_INFO

    // MONTH, YEAR, H_RENT, GUS_CURRENT, SERVENT, NET, PAPER, OTHERS, MEMBERS,TOTAL_COST,PERHEAD

    public boolean insertIntoRentInfo(Calculator calculator){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        String month = new SimpleDateFormat("MMMM").format(new Date());
        String year = new SimpleDateFormat("yyyy").format(new Date());

        values.put(KEY_MONTH,month);
        values.put(KEY_YEAR,year);

        values.put(KEY_H_RENT,calculator.getHouseRent());
        values.put(KEY_GUS_CURRENT,calculator.getGusCurrent());
        values.put(KEY_SERVENT,calculator.getServent());
        values.put(KEY_NET_BILL,calculator.getInternet());
        values.put(KEY_PAPER,calculator.getPaper());
        values.put(KEY_DIRST,calculator.getDirst());
        values.put(KEY_OTHERS,calculator.getOthers());
        values.put(KEY_MEMBERS,calculator.getMembers());
        values.put(KEY_TOTAL_RENT,calculator.getTotal());
        values.put(KEY_PERHEAD,calculator.getPerhead());

        // Inserting Row
        long res = db.insert(TB_RENT_INFO, null, values);
        db.close(); // Closing database connection

        if(res == -1)
            return false;
        else
            return true;


    }


    //View Total Cost For Rent

    public int getTotalCostForRent(String month,String year){

        int total = 0;



        SQLiteDatabase db = this.getWritableDatabase();

        //String sql = "SELECT SUM("+KEY_TK+") FROM "+TB_BAZAR_INFO+" WHERE "+ KEY_MONTH +" = "+ month +" AND "+ KEY_YEAR +" = "+ year;

        String sql = "SELECT "+ KEY_TOTAL_RENT +" FROM "+ TB_RENT_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";

        Cursor cursor = db.rawQuery(sql,new String[]{month,year});

        if(cursor.moveToFirst()){

            total = cursor.getInt(0);

        }

        cursor.close();
        db.close();

        return total;
    }



    // view perhead cost For Rent

    public int getPerheadCostFromRent(String month,String year){

        int perhead = 0;

//        String month = new SimpleDateFormat("MMMM").format(new Date());
//        String year = new SimpleDateFormat("yyyy").format(new Date());

        SQLiteDatabase db = this.getWritableDatabase();

        //String sql = "SELECT SUM("+KEY_TK+") FROM "+TB_BAZAR_INFO+" WHERE "+ KEY_MONTH +" = "+ month +" AND "+ KEY_YEAR +" = "+ year;

        String sql = "SELECT "+ KEY_PERHEAD +" FROM "+ TB_RENT_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";

        Cursor cursor = db.rawQuery(sql,new String[]{month,year});

        if(cursor.moveToFirst()){

            perhead = cursor.getInt(0);

        }

        cursor.close();
        db.close();

        return perhead;
    }


    // view all data from rent

    public List<Calculator> getRentCostInfo(String month,String year){

        List<Calculator> calculators = new ArrayList<>();
        Calculator calculator;

        SQLiteDatabase db = this.getWritableDatabase();

        //String sql = "SELECT SUM("+KEY_TK+") FROM "+TB_BAZAR_INFO+" WHERE "+ KEY_MONTH +" = "+ month +" AND "+ KEY_YEAR +" = "+ year;

        String sql = "SELECT * FROM "+ TB_RENT_INFO +" WHERE "+ KEY_MONTH +" = ? AND "+ KEY_YEAR +" = ?";

        Cursor cursor = db.rawQuery(sql,new String[]{month,year});

        if(cursor.moveToFirst()) {
            do {
                // id,month,year

                calculator = new Calculator(cursor.getString(3),cursor.getString(4),cursor.getString(5),
                            cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),
                            cursor.getString(10),cursor.getString(11),cursor.getString(12)
                        );

                calculators.add(calculator);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return calculators;
    }

    // TB_MEAL_INFO START

    public boolean insertMealInfo(String date, String month, String year, String name, String meal){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DATE, date);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_NAME, name);                            // Person Name
        values.put(KEY_MEAL, Integer.parseInt(meal));               // Meal

        // Inserting Row
        long res = db.insert(TB_MEAL_INFO, null, values);

        db.close(); // Closing database connection

        if(res == -1)
            return false;
        else
            return true;

    }


    public List<MemberInfo> getMealInfo(String month, String year){
        List<MemberInfo> dateList = new ArrayList<>();
        MemberInfo memberInfo;

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT DISTINCT "+KEY_DATE+" FROM " + TB_MEAL_INFO + " WHERE " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{month, year});


        if(cursor.moveToFirst()){
            do{

                int mealNo = getTotalMealOnDate(cursor.getString(0));
                memberInfo = new MemberInfo(cursor.getString(0),mealNo);

                dateList.add(memberInfo);

            } while(cursor.moveToNext());
        } else{
            //Toast.makeText(context,"No date found",Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();

//        db = this.getWritableDatabase();
//
//        for(String date : dateList){
//            //Toast.makeText(context,date,Toast.LENGTH_SHORT).show();
//            sql = "";
//
//            sql = "SELECT "+KEY_NAME+","+KEY_MEAL+" FROM " + TB_MEAL_INFO + " WHERE " + KEY_DATE + " = ? AND " + KEY_MONTH + " = ? AND " + KEY_YEAR + " = ?";
//            cursor = db.rawQuery(sql, new String[]{date,month, year});
//
//            List<String> meals = new ArrayList<>();
//            List<String> names = new ArrayList<>();
//            MemberInfo memberInfo;
//
//            if(cursor.moveToFirst()){
//                do{
//                    names.add(cursor.getString(0));
//                    meals.add(cursor.getString(1));
//                    //Toast.makeText(context,cursor.getString(0),Toast.LENGTH_SHORT).show();
//
//                } while(cursor.moveToNext());
//
//                memberInfo = new MemberInfo(names,date,month,year,meals);
//                list.add(memberInfo);
//            }
//
//        }

//        cursor.close();
//        db.close();


        return dateList;
    }

    public int getTotalMealOnDate(String date){

        int mealNo = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT SUM("+KEY_MEAL+") FROM " + TB_MEAL_INFO + " WHERE " + KEY_DATE + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{date});


        if(cursor.moveToFirst()){
            do{

               mealNo = cursor.getInt(0);

            } while(cursor.moveToNext());
        } else{
            Toast.makeText(context,"No meal found",Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();

        return mealNo;
    }


    public List<MemberMealInfo> getMemberMeal(String date){

        int mealNo = 0;

        SQLiteDatabase db;
        Cursor cursor;

        List<String> nameList = getMNameList();

        List<MemberMealInfo> list = new ArrayList<>();
        MemberMealInfo memberInfo;

        for(String name : nameList) {

            db = this.getWritableDatabase();
            String sql = "SELECT "+KEY_NAME+", SUM(" + KEY_MEAL + ") FROM " + TB_MEAL_INFO + " WHERE " + KEY_DATE + " = ? AND " + KEY_NAME + " = ?";
            cursor = db.rawQuery(sql, new String[]{date,name});


            if (cursor.moveToFirst()) {

                String pName = cursor.getString(0);
                mealNo = cursor.getInt(1);
                memberInfo = new MemberMealInfo(pName,mealNo);

                list.add(memberInfo);

            } else{
                memberInfo = new MemberMealInfo(name,0);

                list.add(memberInfo);
            }
            cursor.close();
            db.close();

        }



        return list;
    }
    


}