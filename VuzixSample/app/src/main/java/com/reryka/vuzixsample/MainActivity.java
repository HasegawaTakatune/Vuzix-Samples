package com.reryka.vuzixsample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // 横幅
    private final int MAX_WIDTH = 72;
    // 縦幅
    private final int MAX_HEIGHT = 15;
    // 最大文字数
    private final int MAX_LENGTH = MAX_WIDTH * MAX_HEIGHT;

    // スタート
    private static final int START = 0;
    // シェイク
    private static final int SHAKE = 1;
    // ウサギ
    private static final int RABBIT = 2;
    // いつねるの？
    private static final int WHEN_DO_YOU_SLEEP = 3;
    // 今でしょ
    private static final int RIGHT_NOW = 4;
    // ブランク
    private static final int BLANK = 99;

    // 待ち時間
    private static final int WAIT_TIME = 20;

    // ハンドラー
    private Handler handler = new Handler();
    // タイマー
    private Timer timer = new Timer();
    // 乱数
    private Random random = new Random();

    // メッセージ表示
    TextView massegeLog;
    // メッセージ保持
    private StringBuffer massege = new StringBuffer();

    // ドラえもん
    private String draemon = "                    _,ノ‐''''''^¨¨¨⌒￣⌒^^''￢-､,_\n" +
            "　　　　　　　　._v-''¨｀.,,vｰ─-､　　　 .,,vｰ─-､　 .¨'ｰu_\n" +
            "　　　　　　_ノ'″　　./′　　　 ¨┐ .／　　　　 ﾞ'┐　　 .ﾞ'┐\n" +
            "　　　　 ,／′　　　./　　　　　　 .ﾐ .i′　　　　　 .）　　　　 ﾞ＼\n" +
            "　　　 ,/′　　　　　|　　　　　●　| ｝ _●,　　　　 |　　　　　　＼\n" +
            "　　 ./′　　　 ._,,､-ﾐ.　　　　.　　/¨ﾚ　　　 　　 .人,_　　　　　　ミ\n" +
            "　 .,ﾉ′　　._ノ'″　　＼_　　 ._,rlﾄ冖へy　　　_／　 ¨'‐u　　　　 .ﾞlr\n" +
            "　.,i′　　／ｰ-v､.,,_　　 ¨^^¨´〔　　　 〕.¨^^¨′　　__.,､ ﾞ＼.　　　 ｛\n" +
            "　〕　　 ./′　　　　.⌒'''''　　　 ＼,,,,,,ノ′　 v-ｰ'''¨′　　　ﾞ┐　　 ｝のび太く～ん。\n" +
            "　|　　 ﾉ　 .────ｰ　　　　　　｝　　　　　 __,,.,､v--ｰ''　　｛　　 .］　　\n" +
            "　|　　:|　　　　　　　 .__,..　　　　　 .!　　　　　 ｀　　　　　　　　.｝　　.｝　\n" +
            "　｝　 .|　　 .--:;:冖^￣　　　　　　 .|　　　　　 ¨¨¨¨¨¨ﾞﾌ¨¨′ .｝　　｝　　\n" +
            "　.|　 .｝　　　　.＼_　　　　　　　　 .｝　　　　　　　　._／　　　　｝　 .:|　　　　\n";

    // いつねるの？
    private String[] whenDoYouSleep = {
            "じゃあいつ寝るの？\n" +
                    "　 ∧_∧\n" +
                    "　( ･ω･)\n" +
                    "　｜⊃／(＿＿＿\n" +
                    "／└-(＿＿＿_／",
            "今でしょ!!\n" +
                    "\n" +
                    "　＜⌒／ヽ-､_＿\n" +
                    "／＜_/＿＿＿_／"};

    // うさぎ
    private String rabbit = "  ╱╲┈┈╱╲\n" +
            "  ▏┈╲┈▏┈╲\n" +
            "  ╲┈▕┈╲┈▕\n" +
            "  ┈╲▕┈┈╲▕\n" +
            "  ┈╱┈▔▔▔┈╲\n" +
            "  ▕╭┈╮┈╭┈╮▏\n" +
            "  ╱┊▉┊┈┊▉┊╲\n" +
            "  ▏┈┳▕▇▏┳┈▕\n" +
            "  ╲┈╰┳┻┳╯┈╱\n" +
            "  ┈▔▏┗┻┛▕▔┈╱╲\n" +
            "  ┈╱▕╲▂╱▏╲╱┈╱\n" +
            "  ╱┈▕╱▔╲▏┈┈╱\n" +
            "  ┈╱▏┈┈┈▕╲╱";

    // ステータス
    private int state = START;

    // 緑色になっているか
    private boolean isGreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        massegeLog = (TextView) findViewById(R.id.MassegeLog);

        // メインループ
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        show();
                    }
                });
            }
        }, 0, 10);
    }

    // 表示制御
    public void show() {
        switch (state) {
            case START:
                CreateStart();
                break;
            case SHAKE:
                Shake();
                break;
            case RABBIT:
                Rabbit();
                break;
            case WHEN_DO_YOU_SLEEP:
                WhenDoYouSleep();
                break;
            case RIGHT_NOW:
                RightNow();
                break;
            default:
                break;
        }
    }

    // 初期表示
    private void CreateStart() {
        massege.append("0");
        massegeLog.setText(massege);

        if (massege.length() >= MAX_LENGTH)
            state = SHAKE;
    }

    // シャッフル
    private void Shake() {
        int index = random.nextInt(MAX_LENGTH);

        if (!isGreen) {
            massegeLog.setTextColor(Color.GREEN);
            timer.purge();
            isGreen = true;
        }
        if (massege.charAt(index) == '0')
            massege.setCharAt(index, '1');
        else
            massege.setCharAt(index, '0');

        massegeLog.setText(massege);
        if (index == 564)
            state = RABBIT;
        else if(index == 127)
            state = WHEN_DO_YOU_SLEEP;
    }

    // ウサギ表示
    private void Rabbit() {
        massegeLog.setTextColor(Color.RED);
        massegeLog.setText(rabbit);
        state = BLANK;
        isGreen = false;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = SHAKE;
            }
        }, (random.nextInt(WAIT_TIME) + 1) * 100);
    }

    // いつ寝るの？
    private void WhenDoYouSleep() {
        massegeLog.setTextColor(Color.CYAN);
        massegeLog.setText(whenDoYouSleep[0]);
        state = BLANK;
        isGreen = false;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = RIGHT_NOW;
            }
        }, WAIT_TIME * 100);
    }

    // 今でしょ
    private void RightNow() {
        massegeLog.setText(whenDoYouSleep[1]);
        state = BLANK;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                state = SHAKE;
            }
        }, WAIT_TIME * 100);
    }
}
