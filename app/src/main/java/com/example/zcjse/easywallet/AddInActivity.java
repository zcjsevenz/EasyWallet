package com.example.zcjse.easywallet;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zcjse.easywallet.db.WalletDbHelper;

import java.io.File;

public class AddInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = AddInActivity.class.getName();

    private EditText mDetailEditText, mMoneyEditText;
    private Button mSaveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_in);

        mDetailEditText = findViewById(R.id.in_detail_text_view);
        mMoneyEditText = findViewById(R.id.in_total_text_view);
        mSaveButton = findViewById(R.id.in_add_button);

        mSaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        saveDataToDb();
        setResult(RESULT_OK);
        finish();
    }

    private void saveDataToDb() {
        String walletTitle = mDetailEditText.getText().toString();
        String walletMoney = mMoneyEditText.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(WalletDbHelper.COL_TITLE,walletTitle);
        cv.put(WalletDbHelper.COL_MONEY,walletMoney);

        WalletDbHelper dbHelper = new WalletDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.insert(WalletDbHelper.TABLE_NAME,null,cv);
    }
}
