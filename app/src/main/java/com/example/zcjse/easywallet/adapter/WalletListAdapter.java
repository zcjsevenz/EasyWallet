package com.example.zcjse.easywallet.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zcjse.easywallet.R;
import com.example.zcjse.easywallet.model.WalletItem;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class WalletListAdapter extends ArrayAdapter<WalletItem> {
    private Context mContext;
    private int mLayoutResId;
    private ArrayList<WalletItem> mWalletItemList;

    public WalletListAdapter(@NonNull Context context, int layoutResId,@NonNull ArrayList<WalletItem> walletItemList) {
        super(context, layoutResId,walletItemList);

        this.mContext = context;
        this.mLayoutResId = layoutResId;
        this.mWalletItemList = walletItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemLayout = inflater.inflate(mLayoutResId,null);

        WalletItem item = mWalletItemList.get(position);

        ImageView walletImageView = itemLayout.findViewById(R.id.show_image);
        TextView walletTitleTextView = itemLayout.findViewById(R.id.titile_text_view);
        TextView walletMoneyTextView = itemLayout.findViewById(R.id.money_text_view);

        walletTitleTextView.setText(item.title);
        walletMoneyTextView.setText(item.money);

        String pictureFileName = item.picture;

        AssetManager am = mContext.getAssets();
        try {
            InputStream stream = am.open(pictureFileName);
            Drawable drawable = Drawable.createFromStream(stream, null);
            walletImageView.setImageDrawable(drawable);

        } catch (IOException e) {
            e.printStackTrace();

            File pictureFile = new File(mContext.getFilesDir(), pictureFileName);
            Drawable drawable = Drawable.createFromPath(pictureFile.getAbsolutePath());
            walletImageView.setImageDrawable(drawable);
        }

        return itemLayout;
    }

}
