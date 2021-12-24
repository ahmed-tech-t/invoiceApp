package com.example.myapplication1.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication1.Models.BillModel;
import com.example.myapplication1.Models.Bill_Model;
import com.example.myapplication1.Models.CustomerModel;
import com.example.myapplication1.Models.ProductModel;
import com.example.myapplication1.Models.QrCodeModel;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final int VERSION = 3 ;

    private static final String DATABASE_NAME = "CUSTOMER_DB";

    //TABLES NAMES
    private static final String CUSTOMER_TABLE ="customer";
    private static final String PRODUCT_TABLE ="product";
    private static final String BILL_TABLE ="bill";
    private static final String SOLD_PRODUCT_TABLE="sold_product";
    private static final String PRODUCT_QUANTITY_TABLE="product_quantity";


    //COLUMN NAME
    private static final String ID ="id";
    private static final String NAME = "name";
    private static final String ID_CARD="id_card";
    private static final String JOP ="jop";
    private static final String AGE = "age";
    private static final String ADDRESS="address";
    private static final String PHONE_NUMBER ="phone_number";
    private static final String GENDER = "gender";
    private static final String PURCHASING_PRICE ="PurchasingPrice";
    private static final String CUS_ID ="cus_id";
    private static final String COUNTRY_OF_ORIGIN ="country_of_origin";
    private static final String CODE ="code";
    private static final String PRODUCTION_DATE ="production_date";
    private static final String EXPIRY_DATE ="expiry_date";
    private static final String SECTION ="section";
    private static final String STORE ="store";
    private static final String WEIGHT ="weight";
    private static final String DATE ="date";
    private static final String TOTAL ="total";
    private static final String PAY_METHOD ="pay_method";
    private static final String CUS_NAME = "customer_name";
    private static final String PRO_NAME = "pro_name";
    private static final String BILL_ID = "bill_id";
    private static final String PRICE = "price";
    private static final String QUANTITY = "quantity";






    //CREATE TABLES
    private static final String CREATE_TABLE_CUSTOMER= " CREATE TABLE IF NOT EXISTS "
            + CUSTOMER_TABLE + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + NAME + " TEXT NOT NULL , "
            + AGE + " TEXT , "
            + GENDER + " TEXT ,"
            + PHONE_NUMBER + " TEXT NOT NULL , "
            + JOP + " TEXT NOT NULL, "
            + ADDRESS + " TEXT , "
            + ID_CARD + " TEXT NOT NULL ) " ;

    private static final String CREATE_TABLE_BILL= " CREATE TABLE IF NOT EXISTS "
            + BILL_TABLE + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + CUS_ID + " TEXT NOT NULL , "
            + CUS_NAME + " TEXT NOT NULL , "
            + TOTAL + " TEXT ,"
            + DATE + " TEXT , "
            + PAY_METHOD + " TEXT NOT NULL ) ";

    private static final String CREATE_TABLE_SOLD_PRODUCT= " CREATE TABLE IF NOT EXISTS "
            + SOLD_PRODUCT_TABLE + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + BILL_ID + " TEXT NOT NULL , "
            + CODE + " TEXT NOT NULL , "
            + PRICE + " TEXT ) ";

    private static final String CREATE_TABLE_PRODUCT_QUANTITY= " CREATE TABLE IF NOT EXISTS "
            + PRODUCT_QUANTITY_TABLE + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + CODE + " TEXT NOT NULL , "
            + QUANTITY + " INTEGER NOT NULL ) ";

    private static final String CREATE_TABLE_PRODUCTS= " CREATE TABLE IF NOT EXISTS "
            + PRODUCT_TABLE + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + CODE + " TEXT NOT NULL , "
            + NAME + " TEXT NOT NULL , "
            + PRICE + " TEXT NOT NULL , "
            + COUNTRY_OF_ORIGIN + " TEXT NOT NULL , "
            + PRODUCTION_DATE + " TEXT NOT NULL , "
            + EXPIRY_DATE + " TEXT NOT NULL , "
            + WEIGHT + " TEXT NOT NULL , "
            + SECTION + " TEXT NOT NULL , "
            + STORE + " TEXT NOT NULL ) ";


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CUSTOMER);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_BILL);
        db.execSQL(CREATE_TABLE_SOLD_PRODUCT);
        db.execSQL(CREATE_TABLE_PRODUCT_QUANTITY);

        Log.w("db","onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BILL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SOLD_PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_QUANTITY_TABLE);

        onCreate(db);
    }

    public void setValueProductQuantityTable(int code){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(CODE,code);
        values.put(QUANTITY,0);
        db.insert(PRODUCT_QUANTITY_TABLE,null,values);
    }//true
    public void setProductQuantity(String code,int quantity){
      ContentValues values =new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        values.put(CODE,code);
        values.put(QUANTITY,quantity);
        db.update(PRODUCT_QUANTITY_TABLE,values,"   CODE = ? " , new String[]{ code });
    }
    public void updateProduct(ProductModel product){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put(NAME,product.getName());
        values.put(PRICE,product.getPrice());
        values.put(COUNTRY_OF_ORIGIN,product.getCountryOfOrigin());
        values.put(PRODUCTION_DATE,product.getProductionDate());
        values.put(EXPIRY_DATE,product.getExpiryDate());
        values.put(WEIGHT,product.getWeight());
        values.put(SECTION,product.getSection());
        values.put(STORE,product.getStoreName());
        db.update(PRODUCT_TABLE,values,"   CODE = ? " , new String[]{ String.valueOf(product.getCode()) });
    }
    public int getProductQuantity(String code){
        SQLiteDatabase db =getReadableDatabase();
        Cursor cursor = db.query(
                PRODUCT_QUANTITY_TABLE,
                new String[]{QUANTITY},
                CODE + "=?",
                new String[]{code},null,null,null,null
        );
        int quantity = -1;
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            quantity = cursor.getInt(0);
            return quantity;
        }else{
            return -1;
        }
    }

    public void addCustomer(CustomerModel customer){
        SQLiteDatabase db = getWritableDatabase();
       setValueCustomerTable(db,customer);
    }
    public void addProduct(ProductModel products){
        SQLiteDatabase db = getWritableDatabase();
        setValueProductTable(db,products);
    }
    private void setValueCustomerTable(SQLiteDatabase db,CustomerModel customer){
        ContentValues values =new ContentValues();
        values.put(NAME,customer.getName());
        values.put(AGE,customer.getAge());
        values.put(GENDER,customer.getGender());
        values.put(JOP,customer.getJop());
        values.put(ID_CARD,customer.getIdCard());
        values.put(PHONE_NUMBER,customer.getPhoneNumber());
        values.put(ADDRESS,customer.getAddress());
        db.insert(CUSTOMER_TABLE,null,values);
    }//true

    private ContentValues setValueBillTable(SQLiteDatabase db ,Long cusId){
        ContentValues values =new ContentValues();
        values.put(CUS_ID,cusId);
        values.put(CUS_NAME,BillModel.getCustomerName());
        values.put(TOTAL,BillModel.totalPrice);
        values.put(DATE,BillModel.payedTime);
        values.put(PAY_METHOD,BillModel.payedType);
        //TODO
        return values;
        }//true

    private void setValueToSoldProductTable(SQLiteDatabase db , Long billId,ArrayList<ProductModel> products){
        for (int i=0;i<products.size();i++){
            ContentValues values =new ContentValues();
            values.put(BILL_ID,billId);
            values.put(CODE,products.get(i).getCode());
            values.put(PRICE,products.get(i).getPrice());
            db.insert(SOLD_PRODUCT_TABLE,null,values);
        }
    }//true

    private void setValueProductTable(SQLiteDatabase db , ProductModel products){

        ContentValues values =new ContentValues();
        values.put(CODE,products.getCode());
        values.put(NAME,products.getName());
        values.put(PRICE,products.getPrice());
        values.put(COUNTRY_OF_ORIGIN,products.getCountryOfOrigin());
        values.put(PRODUCTION_DATE,products.getProductionDate());
        values.put(EXPIRY_DATE,products.getExpiryDate());
        values.put(WEIGHT,products.getWeight());
        values.put(SECTION,products.getSection());
        values.put(STORE,products.getStoreName());
        db.insert(PRODUCT_TABLE,null,values);
    } //true

    private void dropAllTables(){
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SOLD_PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BILL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_QUANTITY_TABLE);
    }
    private void dropProductTables(){
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
    }


    private int getNameId(String name){
        SQLiteDatabase db =getReadableDatabase();
        Cursor cursor = db.query(
                CUSTOMER_TABLE,
                new String[]{ID},
                NAME + "=?",
                new String[]{name},null,null,null,null
        );
        int id = -1;
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
            return id;
        }else{
            return -1;
        }
    }


    public ArrayList<CustomerModel> getAllCustomers(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ CUSTOMER_TABLE ;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList customers =new ArrayList();
        CustomerModel customer;
        if(cursor.moveToFirst()){
            do {
                customer = new CustomerModel(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );
                customers.add(customer);
            }while(cursor.moveToNext());
        }
        return customers;
    }


    public ArrayList<Bill_Model> getAllBIlls(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ BILL_TABLE ;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList bills =new ArrayList();
        Bill_Model bill;
        if(cursor.moveToFirst()){
            do {
                bill = new Bill_Model(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                bills.add(bill);
            }while(cursor.moveToNext());
        }
        return bills;
    }

    public ArrayList<ProductModel> getAllProducts(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ PRODUCT_TABLE ;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList products =new ArrayList();
        ProductModel product;
        if(cursor.moveToFirst()){
            do {
                product = new ProductModel(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getDouble(7),
                        cursor.getString(8),
                        cursor.getString(9)
                        );
                products.add(product);
            }while(cursor.moveToNext());
        }
        return products;
    }

    public void deleteProduct(String code){
        SQLiteDatabase db =getWritableDatabase();
        db.delete(PRODUCT_TABLE," CODE = ? " ,new String[]{code});
        db.delete(PRODUCT_QUANTITY_TABLE," CODE = ? " ,new String[]{code});
    }
    public String getProductName(String code){

        SQLiteDatabase db =getReadableDatabase();
        Cursor cursor = db.query(
                PRODUCT_TABLE,
                new String[]{NAME},
                CODE + "=?",
                new String[]{code},null,null,null,null
        );
        String name = "";
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            name = cursor.getString(0);
            return name;
        }else{
            return "";
        }
    }
    public List<QrCodeModel> getAllQrCode(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ PRODUCT_TABLE ;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList qrCode =new ArrayList();
        QrCodeModel qrCodeModel;
        if(cursor.moveToFirst()){
            do {
                qrCodeModel = new QrCodeModel(
                        cursor.getString(1),
                        cursor.getString(2)
                );
                qrCode.add(qrCodeModel);
            }while(cursor.moveToNext());
        }
        return qrCode;
    }



}
