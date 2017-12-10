package com.example.zcjse.easywallet;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.zcjse.easywallet.adapter.WalletListAdapter;
import com.example.zcjse.easywallet.db.WalletDbHelper;
import com.example.zcjse.easywallet.model.WalletItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button mInButton;
    private Button mOutButton;

    private WalletDbHelper mHelper;
    private SQLiteDatabase mDb;

    private ArrayList<WalletItem> mWalletItemList = new ArrayList<>();
    private WalletListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new WalletDbHelper(this);
        mDb = mHelper.getReadableDatabase();

        loadDataFromDb();

        mAdapter = new WalletListAdapter(
                this,
                R.layout.item,
                mWalletItemList
        );

        ListView lv = findViewById(R.id.Wallet_List_View);
        lv.setAdapter(mAdapter);

        mInButton = findViewById(R.id.In_Button);
        mOutButton = findViewById(R.id.Out_Button);

        mInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddInActivity.class);
                startActivity(intent);
            }
        });

        mOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddOutActivity.class);
                startActivity(intent);

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("ยืนยันลบรายการหรือไม่");
                String[] items = new String[]{"NO", "YES"};

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) { // แก้ไขข้อมูล
                            WalletItem item = mWalletItemList.get(position);
                            int phoneId = item.id;

                            ContentValues cv = new ContentValues();
                            cv.put(WalletDbHelper.COL_MONEY, "12345");


                            loadDataFromDb();
                            mAdapter.notifyDataSetChanged();

                        } else if (i == 1) { // ลบข้อมูล
                            WalletItem item = mWalletItemList.get(position);
                            int phoneId = item.id;

                            mDb.delete(
                                    WalletDbHelper.TABLE_NAME,
                                    WalletDbHelper.COL_ID + "=?",
                                    new String[]{String.valueOf(phoneId)}
                            );
                            loadDataFromDb();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

    }

    private void loadDataFromDb() {
        Cursor cursor = mDb.query(
                WalletDbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        mWalletItemList.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(WalletDbHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(WalletDbHelper.COL_TITLE));
            String money = cursor.getString(cursor.getColumnIndex(WalletDbHelper.COL_MONEY));
            String picture = cursor.getString(cursor.getColumnIndex(WalletDbHelper.COL_PICTURE));

            WalletItem item = new WalletItem(id,title,money,picture);
            mWalletItemList.add(item);
        }
    }


}

