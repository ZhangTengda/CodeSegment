package com.testone.demo.activity.ele;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.testone.demo.R;

public class StartSearchActivity extends Activity {
    private TextView mSearchBGTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ele);

        mSearchBGTxt = (TextView) findViewById(R.id.tv_search_bg);
        mSearchBGTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartSearchActivity.this, SearchContentActivity.class);
                int location[] = new int[2];
                mSearchBGTxt.getLocationOnScreen(location);

                Rect rect = new Rect();
                //获取元素的位置信息
                view.getGlobalVisibleRect(rect);
                //将位置信息附加到intent 上
//                intent.setSourceBounds(rect);
                Log.i("qqqqqqqqqq","底部："+rect.bottom+"=top:"+rect.top+"=left:"
                        +rect.left+"=right:"+rect.right+"=x:"+location[0]+"=y:"+ location[1]);

                intent.putExtra("x", location[0]);
                intent.putExtra("y", location[1]);
                intent.setSourceBounds(rect);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

}
