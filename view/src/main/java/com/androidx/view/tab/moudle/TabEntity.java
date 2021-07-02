package com.androidx.view.tab.moudle;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.androidx.view.tab.listener.CustomTabEntity;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

@SuppressLint("ParcelCreator")
public class TabEntity implements CustomTabEntity {

    public String title;
    public int selectedIcon;
    public int unselectedIcon;

    private TabEntity(String title, int selectedIcon, int unselectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
    }

    public static TabEntity init(String title, int selectedIcon, int unselectedIcon) {
        return new TabEntity(title, selectedIcon, unselectedIcon);
    }

    public TabEntity() {
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unselectedIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(selectedIcon);
        dest.writeInt(unselectedIcon);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(title);
        out.writeInt(selectedIcon);
        out.writeInt(unselectedIcon);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        title = in.readUTF();
        selectedIcon = in.readInt();
        unselectedIcon = in.readInt();
    }
}
