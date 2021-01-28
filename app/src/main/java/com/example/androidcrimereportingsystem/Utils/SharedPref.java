package com.example.androidcrimereportingsystem.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private Context ctx;
    private SharedPreferences default_prefence;

    public SharedPref(Context context) {
        this.ctx = context;
        default_prefence = context.getSharedPreferences("crimereporting", Context.MODE_PRIVATE);
    }
        public void setUserId(String userId) {
            default_prefence.edit().putString("userId", userId).apply();
        }

        public String getuserId() {
            return default_prefence.getString("userId", null);
        }
    public void setimage(String image) {
        default_prefence.edit().putString("image", image).apply();
    }

    public String getimage() {
        return default_prefence.getString("image", null);
    }
    public void setpic(String pic) {
        default_prefence.edit().putString("pic", pic).apply();
    }

    public String getpic() {
        return default_prefence.getString("pic", null);
    }
    public void setname(String name) {
        default_prefence.edit().putString("name", name).apply();
    }

    public String getname() {
        return default_prefence.getString("name", null);
    }
    public void setusername(String username) {
        default_prefence.edit().putString("username", username).apply();
    }

    public String getusername() {
        return default_prefence.getString("username", null);
    }

    public void setGuestUsername(String username) {
        default_prefence.edit().putString("guestUsername", username).apply();
    }

    public String getGuestUsername() {
        return default_prefence.getString("guestUsername", null);
    }

    public void setReferralCode(String code) {
        default_prefence.edit().putString("ReferralCode", code).apply();
    }

    public String getReferralCode() {
        return default_prefence.getString("ReferralCode", null);
    }

    public void setIsGuest(boolean isGuest){

        default_prefence.edit().putBoolean("isGuest", isGuest).apply();
    }

    public boolean getIsGuest(){
        return default_prefence.getBoolean("isGuest", false);

    }
    public void setpostId(String postId) {
        default_prefence.edit().putString("postId", postId ).apply();
    }

    public String getpostId() {
        return default_prefence.getString("postId", null);
    }







    public String getchatimage() {
        return default_prefence.getString("chatimage", null);
    }
    public void setchatimage(String chatimage) {
        default_prefence.edit().putString("chatimage", chatimage).apply();
    }

    public String getfriend() {
        return default_prefence.getString("friend", null);
    }
    public void setfriend(String friend) {
        default_prefence.edit().putString("friend", friend).apply();
    }
    public String getbio() {
        return default_prefence.getString("bio", null);
    }
    public void setbio(String bio) {
        default_prefence.edit().putString("bio", bio).apply();
    }




    public int getCount() {
        return default_prefence.getInt("count", 0);
    }

    public void setCount(int count) {
        default_prefence.edit().putInt("count", count).apply();
    }

  


}
