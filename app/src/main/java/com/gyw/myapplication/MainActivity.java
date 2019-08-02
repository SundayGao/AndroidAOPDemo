package com.gyw.myapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {
    private Map<String, String> headers;
    private static final String TAG = "==gyw==";
    private Disposable disposable;
    private TextView tvTry;
    private EditText et_try;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("gyw_tag", "gyw_test");

        findViewById(R.id.bt_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpannableStringBuilder ssb = new SpannableStringBuilder("\n\n价格不符合市场行情，请重新修改或采用其他应检方式");

                Drawable drawable = getBaseContext().getResources().getDrawable(R.mipmap.ic_picture_failure);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                ImageSpan imageSpan = new ImageSpan(drawable,
                        DynamicDrawableSpan.ALIGN_BASELINE);
                SpannableString spanned = new SpannableString("[h]");
                spanned.setSpan(imageSpan, 0, spanned.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ssb.insert(0, spanned);
                Toast toast = Toast.makeText(MainActivity.this, ssb, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

//        tryUnsafeAllocateInstance();
//        tryClearEditText();
//        tryClockCount();
//        tryMultiThread();
    }

    private void tryUnsafeAllocateInstance() {
        Dog dog = null;
        try {
            Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
            Field f = unsafeClass.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            final Object unsafe = f.get(null);
            final Method allocateInstance = unsafeClass.getMethod("allocateInstance", Class.class);
            dog = (Dog) allocateInstance.invoke(unsafe, Dog.class);
        } catch (Exception ignored) {
        }

        Toast.makeText(this, dog == null ? "空" : dog.toString(), Toast.LENGTH_SHORT).show();
    }

    private void tryClearEditText() {
        et_try = findViewById(R.id.et_try);

        findViewById(R.id.bt_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_try.setText(null);
            }
        });
    }

    //========倒计时==========
    private void tryClockCount() {
        tvTry = findViewById(R.id.tv_try);

        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();

        long second = 10;
        final long totalCount = second * 1000;

        final long startTime = System.currentTimeMillis();
        disposable = Observable.interval(0, 20, TimeUnit.MILLISECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(MainActivity.this, "onDispose", Toast.LENGTH_SHORT).show();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long count) {
                        long remainTime = totalCount - (System.currentTimeMillis() - startTime);
                        if (remainTime < 0) {
                            disposable.dispose();
                        } else {
                            tvTry.setText(convertMilliSecond2Str(remainTime));
                            Log.i(TAG, convertMilliSecond2Str(remainTime));
                        }
                    }
                });
    }

    int interval = 1000;

    private String convertMilliSecond2Str(long millisecond) {
        long millis = millisecond % interval / 10;
        long minute = millisecond / (60 * interval);
        long second = millisecond % (60 * interval) / interval;
        return String.format("%02d", minute) + ":" + String.format("%02d", second) + "." + String.format("%02d", millis);
    }

    //====================测试多线程=====================
    private void tryMultiThread() {
        if (headers == null) {
            headers = new HashMap<>();
        }


        for (int k = 0; k < 1000; k++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runMyTask();
                }
            }).start();
        }
    }

    private void runMyTask() {
        if (headers != null) {
            headers.put("Sign", getSignature());
        }

        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                Log.i(TAG, headerKey + " : " + headers.get(headerKey));
            }
        }
    }

    private synchronized String getSignature() {
        String str = getData();
        return str == null ? "" : str;
    }

    private String getData() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
