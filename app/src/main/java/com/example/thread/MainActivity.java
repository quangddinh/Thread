package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int a , b , c;
    flag f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        a = b = c = 0;
        f = new flag(0);
        // 1 : Khoi tao ra thread
        //2 : Quan ly luong theo object
        // a : 1
        // b : 1
        // c : a + b
        Thread threada = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (f) {
                    for (int i = 0; i <= 50; ) {
                        if (f.count == 0){
                            a = i;
                            Log.d("BBB", "A " + a);
                            f.count = 1;
                            i++;
                            f.notifyAll();
                        }else{
                            try {
                                f.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        });

        Thread threadb = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (f){
                    for (int i = 0; i <= 50; ) {
                        if (f.count == 1){
                            b = i;
                            Log.d("BBB", "B " + b);
                            f.count = 2;
                            i++;
                            f.notifyAll();
                        }else{
                            try {
                                f.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        });
        Thread threadc = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (f){
                    for (int i = 0; i <= 50;) {
                        if (f.count == 2){
                            c = a + b;
                            Log.d("BBB", "C " + c);
                            f.count = 0;
                            i++;
                            f.notifyAll();
                        }else{
                            try {
                                f.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        threada.start();
        threadb.start();
        threadc.start();


    }
    // lam cho 1 thread ket thuc thi thread kia moi start
    // Deadlock
//    public synchronized void runningThread(String key){
//        for (int i = 0 ; i <= 10000 ; i++){
//            Log.d("BBB", key + " " + i);
//        }
//    }

}