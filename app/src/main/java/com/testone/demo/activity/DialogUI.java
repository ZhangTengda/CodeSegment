package com.testone.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.testone.demo.R;
import com.testone.demo.utils.ToastUtil;
import com.testone.demo.utils.dialog.AlertDialog;

public class DialogUI extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_ui);

    }


    public void createDialog(View view) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.layout_dialog)
                .setText(R.id.dialogBtnShare, "接收")
                .addDefaultAnimation()
//                .formBottom(false)
                .show();
        final EditText dialogEtShare = dialog.getView(R.id.dialogEtShare);
        dialog.setOnClickListener(R.id.dialogBtnShare, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(DialogUI.this, "哈哈哈" + dialogEtShare.getText().toString());
            }
        });

    }
}
