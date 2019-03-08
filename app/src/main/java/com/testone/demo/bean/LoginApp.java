package com.testone.demo.bean;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by roger on 2017/4/8.
 */

public class LoginApp {
    private SharedPreferences app_preferences;

    public LoginApp(Context context_from_activity) {
        app_preferences = context_from_activity.getSharedPreferences(
                "userinfo", Context.MODE_PRIVATE);
    }

    public void logout() {
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.remove("login_token");
        editor.remove("title");
        editor.remove("foreName");
        editor.remove("surName");
        editor.remove("mobile");
        editor.remove("email");
        editor.remove("uid");
        editor.remove("points");
        editor.remove("custom_area_code");
        editor.remove("notificationStatus");
        editor.putBoolean("isRemoveRecordCache", false);
        editor.remove("facebookID");
        editor.remove("createtime");
        editor.remove("birthday");
        editor.remove("restaurantuid");
        editor.remove("firstrestime");
        editor.remove("reservationCount");
        editor.remove("reservationCreateTime");
        editor.remove("userName");
        editor.remove("countReservation");
        editor.apply();
    }

    public void setI18Language(String languageCode) {
        app_preferences.edit().putString("languageCode", languageCode).apply();
    }

    public String getI18Language() {
        return app_preferences.getString("languageCode", null);
    }

    /**
     * Locale Language
     */
    public void setLocaleLanguage(String localeLanguage) {
        app_preferences.edit().putString("localeLanguage", localeLanguage)
                .apply();
    }

    public String getLocaleLanguage() {
        return app_preferences.getString("localeLanguage", null);
    }

    public void setRemoveRecordCache(boolean isRemoveRecordCache) {
        app_preferences.edit().putBoolean("isRemoveRecordCache", isRemoveRecordCache).apply();
    }

    public boolean isRemoveRecordCache() {
        return app_preferences.getBoolean("isRemoveRecordCache", true);
    }

    public String getLoginToken() {
        return app_preferences.getString("login_token", null);
    }

    public void setLoginToken(String loginToken) {
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("login_token", loginToken);
        editor.apply();
    }

    public String getTitle() {
        return app_preferences.getString("title", null);
    }

    public void setTitle(String title) {
        app_preferences.edit().putString("title", title).apply();
    }

    public String getLanguage() {
        return app_preferences.getString("lang", null);
    }

    public void setLanguage(String title) {
        app_preferences.edit().putString("lang", title).apply();
    }

    public String getTitlePosition() {
        return app_preferences.getString("title_position", null);
    }

    public void setTitlePosition(int titlePosition) {
        app_preferences.edit().putString("title_position", String.valueOf(titlePosition)).apply();
    }

    public String getForeName() {
        return app_preferences.getString("foreName", null);
    }

    public void setForeName(String foreName) {
        app_preferences.edit().putString("foreName", foreName).apply();
    }

    public String getSurName() {
        return app_preferences.getString("surName", null);
    }

    public void setSurName(String surName) {
        app_preferences.edit().putString("surName", surName).apply();
    }

    public String getMobile() {
        return app_preferences.getString("mobile", null);
    }

    public void setMobile(String mobile) {
        app_preferences.edit().putString("mobile", mobile).apply();
    }

    public String getEmail() {
        return app_preferences.getString("email", null);
    }

    public void setEmail(String email) {
        app_preferences.edit().putString("email", email).apply();
    }

    /**
     * Dynamic get country code and country name
     */
    public void setCityCode(String cityInfo) {
        app_preferences.edit().putString("cityCode", cityInfo).apply();
    }

    public String getCityCode() {
        return app_preferences.getString("cityCode", null);
    }

    public void setLanguageString(String languageString) {
        app_preferences.edit().putString("languageString", languageString)
                .apply();
    }

    public String getLanguageString() {
        return app_preferences.getString("languageString", null);
    }

    public void setCityInfo(String cityInfo) {
        app_preferences.edit().putString("city", cityInfo).apply();
    }

    public String getCityInfo() {
        return app_preferences.getString("city", null);
    }

    public void setTimeZoneKeyValueString(String timeZoneKeyValueString) {
        app_preferences.edit()
                .putString("timeZoneKeyValueString", timeZoneKeyValueString)
                .apply();
    }

    public String getTimeZoneKeyValueString() {
        return app_preferences.getString("timeZoneKeyValueString", null);
    }

    public void setTimeZone(String timeZone) {
        app_preferences.edit().putString("timeZone", timeZone).apply();
    }

    public String getTimeZone() {
        return app_preferences.getString("timeZone", null);
    }

    /**
     * set UID
     *
     * @param uid UID
     */
    public void setUID(String uid) {
        app_preferences.edit().putString("uid", uid).apply();
    }

    /**
     * get UID
     *
     * @return UID
     */
    public String getUID() {
        return app_preferences.getString("uid", null);
    }

    /**
     * set Feed Display State <br>
     * 1-Share with my friends and their immediate friends <br>
     * 0-Share with my friends only
     *
     * @param feedDisplayState
     */
    public void setFeedDisplayState(String feedDisplayState) {
        app_preferences.edit().putString("feedDisplayState", feedDisplayState)
                .apply();
    }

    /**
     * get Feed Display State <br>
     * 1-Share with my friends and their immediate friends<br>
     * 0-Share with my friends only
     *
     * @return feedDisplayState
     */
    public String getFeedDisplayState() {
        return app_preferences.getString("feedDisplayState", "0");
    }

    /**
     * set Points
     *
     * @param points Points
     */
    public void setPoints(String points) {
        app_preferences.edit().putString("points", points).apply();
    }

    /**
     * get points
     *
     * @return Points
     */
    public String getPoints() {
        return app_preferences.getString("points", null);
    }

    /**
     * getCountryCode
     *
     * @return CountryCode
     */
    public String getCountryCode() {
        return app_preferences.getString("country_code", null);
    }

    /**
     * setCountryCode
     *
     * @param contryCode CountryCode
     */
    public void setCountryCode(String contryCode) {
        app_preferences.edit().putString("country_code", contryCode).apply();
    }

    public void removeLocationCode() {
        app_preferences.edit().remove("location_code").apply();
    }

    /**
     * @return default is 0 which is sg ,for 1 hk;
     */
    public int getLocation() {
        // TODO Auto-generated method stub
        return app_preferences.getInt("location_int", 0);
    }

    /**
     * set notification status<br>
     * value 0 user without new notification, else 1
     *
     * @param notificationStatus notification status
     */
    public void setNotificationStatus(int notificationStatus) {
        app_preferences.edit().putInt("notificationStatus", notificationStatus)
                .apply();
    }

    /**
     * get notification status <br>
     *
     * @return value 0 user without new notification, else 1
     */
    public int getNotificationStatus() {
        return app_preferences.getInt("notificationStatus", 0);
    }

    /**
     * @param key            country code
     * @param areaCodeString area code string
     */
    public void setAreaCode(String key, String areaCodeString) {
        // TODO Auto-generated method stub
        String areaCodeKey = key + "_area_code";
        app_preferences.edit().putString(areaCodeKey, areaCodeString).apply();
    }

    /**
     * @param key country code
     * @return area code
     */
    public String getAreaCode(String key) {
        String areaCodeKey = key + "_area_code";
        return app_preferences.getString(areaCodeKey, null);
    }

    /**
     * @param areaCode custom area code
     */
    public void setCustomAreaCode(String areaCode) {
        // TODO Auto-generated method stub
        app_preferences.edit().putString("custom_area_code", areaCode).apply();
    }

    /**
     * @return custom area code
     */
    public String getCustomAreaCode() {
        return app_preferences.getString("custom_area_code", null);
    }

    /**
     * @param facebookID
     */
    public void setFacebookID(String facebookID) {
        // TODO Auto-generated method stub
        app_preferences.edit().putString("facebookID", facebookID).apply();
    }

    /**
     * Facebook ID
     *
     * @return
     */
    public String getFacebookID() {
        return app_preferences.getString("facebookID", null);
    }


    /**
     * 设置用户的username 唯一ID
     *
     * @param userName user name
     */
    public void setUserName(String userName) {
        app_preferences.edit().putString("userName", userName).apply();
    }

    /**
     * 获得用户的username 唯一ID
     *
     * @return user name
     */
    public String getUserName() {
        return app_preferences.getString("userName", null);
    }

    /**
     * 设置用户下订单的数量
     *
     * @param countReservation reservation count
     */
    public void setCountReservation(String countReservation) {
        app_preferences.edit().putString("countReservation", countReservation).apply();
    }

    /**
     * 获得用户下订单的数量
     *
     * @return reservation count
     */
    public String getCountReservation() {
        return app_preferences.getString("countReservation", null);
    }
}
