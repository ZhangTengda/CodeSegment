package com.xnjz.one.room;

import android.content.Context;

public class RoomHelper {

    public static UserInfoDatabase init(Context mContext) {
        return UserInfoDatabase.getInstance(mContext);
    }
}
