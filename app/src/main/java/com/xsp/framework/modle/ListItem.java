package com.xsp.framework.modle;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 首页四个Tab 列表信息
 */
public class ListItem implements Parcelable {
    private String itemName;
    private Class<?> itemClick;
    private String itemExtra = "";

    public ListItem () {

    }
    public ListItem(String itemName, Class<?> itemClick) {
        this.itemName = itemName;
        this.itemClick = itemClick;
    }

    public ListItem(String itemName, Class<?> itemClick, String itemExtra) {
        this.itemName = itemName;
        this.itemClick = itemClick;
        this.itemExtra = itemExtra;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Class<?> getItemClick() {
        return itemClick;
    }

    public void setItemClick(Class<?> itemClick) {
        this.itemClick = itemClick;
    }

    public String getItemExtra() {
        return itemExtra;
    }

    public void setItemExtra(String itemExtra) {
        this.itemExtra = itemExtra;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemClick == null ? "" : itemClick.getClass().getCanonicalName());
        dest.writeString(itemExtra);
    }

    public static final Parcelable.Creator<ListItem> CREATOR = new Creator(){

        @Override
        public ListItem createFromParcel(Parcel source) {
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            ListItem item = new ListItem();
            item.setItemName(source.readString());
            Class<?> mClz;
            try {
                mClz = Class.forName(source.readString());
            } catch (ClassNotFoundException e) {
                mClz = null;
            }
            item.setItemClick(mClz);
            item.setItemExtra(source.readString());
            return item;
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

}
