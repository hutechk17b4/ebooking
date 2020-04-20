package com.example.ungdungdatvexemphim.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME="session";
    String SESSION_KEY="session_user";
    String SESSION_KEY_MAIL="session_mail_user";

    public SessionManagement(Context context)
    {
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public  void saveSession(Customer customer)
    {
        int id=customer.getIdCustomer();
        editor.putInt(SESSION_KEY,id).commit();

    }
    public void saveSessionMail(Customer customer)
    {
        String email=customer.getEmail();
        editor.putString(SESSION_KEY_MAIL,email).commit();
    }

    public int getSession()
    {
        // trả về dữ liệu đã save khi đăng nhập
        return sharedPreferences.getInt(SESSION_KEY,-1);
    }
    public String getSessionMail()
    {
        return sharedPreferences.getString(SESSION_KEY_MAIL,"error");
    }
    public void removeSession(){
        editor.remove(SESSION_KEY);
        editor.commit();


    }
    public void removeSessionMail()
    {
        editor.remove(SESSION_KEY_MAIL);
        editor.commit();
    }
}
