package com.example.zcjse.easywallet.model;

/**
 * Created by zcjse on 12/10/2017.
 */

public class WalletItem {
    public final String title;
    public final String money;
    public final String picture;
    public int id;

    public WalletItem(int id,String title, String money, String picture) {
        this.id = id;
        this.title = title;
        this.money = money;
        this.picture = picture;
    }
    @Override
    public String toString(){
        return  title;
    }
}
