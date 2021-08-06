package com.delta_inductions.delta_task_3.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.delta_inductions.delta_task_3.R;

public class LoadingDialog {

     private AlertDialog alertDialog;
     private Activity activity;
    public LoadingDialog(Activity activity)
    {
        this.activity = activity;
    }
    public void Loadingalert()
    {
          AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.customdialog,null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

    }
    public void dismissdialog()
    {
        alertDialog.dismiss();
    }

}
