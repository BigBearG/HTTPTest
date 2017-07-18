package textitei.httptest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnsendata1,mBtnsendata2;
    private TextView mTvshow;
    private Handler mainHandler= new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
             Bundle bundle=msg.getData();
            String data=bundle.getString("data");
            mTvshow.setText(data);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnsendata1= (Button) findViewById(R.id.senddata1);
        mBtnsendata2= (Button) findViewById(R.id.senddata2);
        mTvshow= (TextView) findViewById(R.id.show);
        mBtnsendata1.setOnClickListener(this);
        mBtnsendata2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String adrress="http://10.39.1.16";
        switch (view.getId()){
            case R.id.senddata1:
                HttpUtil.sendHttpRequest(adrress, new HttpCallbackListenner() {
                    @Override public void onFinnsh(final String response) {
                       /* runOnUiThread(new Runnable() {
                            @Override
                            public void run() {*/
                                mTvshow.setText(response);
//                            }
//                        });

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
                break;
            case R.id.senddata2:
                HttpUtil.sendOkhttpRequest(adrress, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call,  Response response) throws IOException {
                                String resonsedata = response.body().string();
                                Message message=Message.obtain();
                                Bundle budle=new Bundle();
                                budle.putString("data",resonsedata);
                                message.setData(budle);
                                mainHandler.sendMessage(message);

                    }
                });
        }
    }
}
