package com.fengjie.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    /**注释框架**/
//    @BindView(R.id.XXX)
//        Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);     //绑定Activity

    }

//    @OnClick({R.id.AA,R.id.BB})
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id:
//                break;
//            default:break;
//        }
//    }

}
