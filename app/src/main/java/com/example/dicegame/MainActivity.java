package com.example.dicegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewDice;
    private TextView textViewNumber;
    private Button buttonStart;
    private boolean runFlag = false;
    private ObjectAnimator animRotate;
    private int Message_OK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewDice = (ImageView) findViewById(R.id.imageView_dice);
        textViewNumber = (TextView) findViewById(R.id.textView_number);
        textViewNumber.setText("");
        buttonStart = (Button) findViewById(R.id.button_start);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(runFlag){
                    return;
                }

                imageViewDice.setImageResource(R.drawable.dice_2);
                animRotate = ObjectAnimator.ofFloat(imageViewDice,"rotation",0,360,0);//圖片旋轉
                animRotate.setDuration(600);//旋轉時間
                animRotate.setRepeatCount(ObjectAnimator.INFINITE);//重複次數
                animRotate.setInterpolator(new AccelerateDecelerateInterpolator());//轉的方式慢慢轉快快轉
                animRotate.start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            runFlag = true;
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Message message = new Message();
                        message.what = Message_OK;
                        myHandler.sendMessage(message);
                    }
                }).start();
            }
        });

    }

    private final Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            runFlag = false;
            animRotate.end();
            if(msg.what != Message_OK)
                return false;
            int iRand = (int)(Math.random()*6+1);
            textViewNumber.setText("Dice number : "+iRand);
            switch (iRand){
                case 1:
                    imageViewDice.setImageResource(R.drawable.dice01);
                    break;
                case 2:
                    imageViewDice.setImageResource(R.drawable.dice02);
                    break;
                case 3:
                    imageViewDice.setImageResource(R.drawable.dice03);
                    break;
                case 4:
                    imageViewDice.setImageResource(R.drawable.dice04);
                    break;
                case 5:
                    imageViewDice.setImageResource(R.drawable.dice05);
                    break;
                case 6:
                    imageViewDice.setImageResource(R.drawable.dice06);
                    break;
            }
            return true;
        }
    });
}