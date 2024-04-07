package com.xnjz.one.room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userInfoTable")
public class UserInfoBean implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String sex;
    private String national;
    private String birthday;
    private String idCardNumber;
    private String companyName;
    private String address;
    private String faceToken;

    public UserInfoBean(){}

    protected UserInfoBean(Parcel in) {
        id = in.readLong();
        name = in.readString();
        sex = in.readString();
        national = in.readString();
        birthday = in.readString();
        idCardNumber = in.readString();
        companyName = in.readString();
        address = in.readString();
        faceToken = in.readString();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(sex);
        parcel.writeString(national);
        parcel.writeString(birthday);
        parcel.writeString(idCardNumber);
        parcel.writeString(companyName);
        parcel.writeString(address);
        parcel.writeString(faceToken);
    }
}
