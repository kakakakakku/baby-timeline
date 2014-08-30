package jp.kakakakakku.baby.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;

import jp.kakakakakku.baby.R;
import jp.kakakakakku.baby.adapter.MyCustomListAdapter;
import jp.kakakakakku.baby.adapter.MyCustomListData;
import jp.kakakakakku.baby.db.entity.BabyHistoryEntity;
import jp.kakakakakku.baby.db.model.BabyHistoryModel;

public class HistoryActivity extends Activity {

    // TODO: recordType を enum に

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        // リリース用キャプチャ取得用
        // ステータスバー非表示
//        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BabyHistoryModel model = new BabyHistoryModel(HistoryActivity.this);
        List<BabyHistoryEntity> histories;
        histories = model.findAll();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:KK");

        List<MyCustomListData> objects = new ArrayList<MyCustomListData>();

        for (BabyHistoryEntity h: histories) {

            MyCustomListData tmpItem = new MyCustomListData();
            String message = "";

            if (h.getRecordType() == 1) {
                tmpItem.setRecordType("睡眠");
                tmpItem.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sleep));
                if (h.getEndTime() == null) {
                    // 睡眠(startTime)
                    if (h.getStartTime() != null) {
                        message = sdf.format(h.getStartTime()) + " 〜";
                    }
                } else {
                    // 睡眠(startTime & endTime)
                    if (h.getStartTime() != null && h.getEndTime() != null) {
                        message = sdf.format(h.getStartTime()) + " 〜 " + sdf.format(h.getEndTime());
                        tmpItem.setDiffTime("睡眠時間: " + toDiffTime(h.getStartTime(), h.getEndTime()));
                    }
                }
                tmpItem.setMessage(message);
            } else {
                // 授乳
                tmpItem.setRecordType("授乳");
                tmpItem.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.female));
                if (h.getStartTime() != null) {
                    message = sdf.format(h.getStartTime());
                }
                tmpItem.setMessage(message);
            }
            objects.add(tmpItem);
        }


        MyCustomListAdapter myCustomListAdapter = new MyCustomListAdapter(this, 0, objects);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(myCustomListAdapter);

    }

    public String toDiffTime(Date startTime, Date endTime) {
        return DurationFormatUtils.formatPeriod(startTime.getTime(), endTime.getTime(), "HH:mm:ss");
    }

}
