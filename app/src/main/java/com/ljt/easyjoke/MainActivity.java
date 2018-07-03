package com.ljt.easyjoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ljt.baselibrary.base.BaseActivity;
import com.ljt.baselibrary.dialog.AlertDialog;
import com.ljt.baselibrary.ioc.CheckNet;
import com.ljt.baselibrary.ioc.FindView;
import com.ljt.baselibrary.ioc.OnClick;
import com.ljt.baselibrary.ioc.ViewFindUtil;

public class MainActivity extends BaseActivity {

    @FindView(R.id.tv_test)
    private TextView mTvTest;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.tv_test)
    public void onClick(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.detail_comment_dialog)
                .setText(R.id.submit_btn, "发送")
                .fullWidth()
                .fromBottom(true)
                .show();

        final EditText editText = dialog.getView(R.id.comment_editor);
        dialog.setOnClickListener(R.id.submit_btn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, editText.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
