package com.xnjz.one.http3.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BannerBean2 {

    private List<BannerDataBean2> data;
    private String errorMsg;
    private int errorCode;

    public List<BannerDataBean2> getData() {
        return data;
    }

    public void setData(List<BannerDataBean2> data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public static class BannerDataBean2 implements Parcelable {
        private String imagePath;
        private String desc;
        private String title;
        private String url;

        protected BannerDataBean2(Parcel in) {
            imagePath = in.readString();
            desc = in.readString();
            title = in.readString();
            url = in.readString();
        }

        public static final Creator<BannerDataBean2> CREATOR = new Creator<BannerDataBean2>() {
            @Override
            public BannerDataBean2 createFromParcel(Parcel in) {
                return new BannerDataBean2(in);
            }

            @Override
            public BannerDataBean2[] newArray(int size) {
                return new BannerDataBean2[size];
            }
        };

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(imagePath);
            parcel.writeString(desc);
            parcel.writeString(title);
            parcel.writeString(url);
        }
    }
}
