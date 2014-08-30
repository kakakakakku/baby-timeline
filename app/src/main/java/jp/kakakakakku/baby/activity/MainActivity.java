package jp.kakakakakku.baby.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

import jp.kakakakakku.baby.R;
import jp.kakakakakku.baby.db.entity.BabyHistoryEntity;
import jp.kakakakakku.baby.db.model.BabyHistoryModel;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // リリース用キャプチャ取得用
        // ステータスバー非表示
//        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button bSleep = (Button) findViewById(R.id.sleep);
        bSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("寝た時間を記録しました！");
                BabyHistoryModel model = new BabyHistoryModel(MainActivity.this);
                model.save(new BabyHistoryEntity(1, new Date(), null));
            }
        });

        Button bWake = (Button) findViewById(R.id.wake);
        bWake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("起きた時間を記録しました！");
                BabyHistoryModel model = new BabyHistoryModel(MainActivity.this);
                model.updateEndTime(new Date());
            }
        });

        Button bMilk = (Button) findViewById(R.id.milk);
        bMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("授乳した時間を記録しました！");
                BabyHistoryModel model = new BabyHistoryModel(MainActivity.this);
                model.save(new BabyHistoryEntity(2, new Date(), new Date()));
            }
        });

        Button bHistory = (Button) findViewById(R.id.history);
        bHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
