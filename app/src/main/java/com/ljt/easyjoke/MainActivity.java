package com.ljt.easyjoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ljt.baselibrary.ioc.CheckNet;
import com.ljt.baselibrary.ioc.FindView;
import com.ljt.baselibrary.ioc.OnClick;
import com.ljt.baselibrary.ioc.ViewFindUtil;

public class MainActivity extends AppCompatActivity {

    @FindView(R.id.tv_test)
    private TextView mTvTest;

    @FindView(R.id.tv_test_2)
    private TextView mTvTest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewFindUtil.inject(this);

    }

    @OnClick({R.id.tv_test, R.id.tv_test_2})
    @CheckNet
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_test:
                Toast.makeText(this, "111111111111", Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_test_2:
                Toast.makeText(this, "222222222222", Toast.LENGTH_LONG).show();
                break;
        }
    }


}
