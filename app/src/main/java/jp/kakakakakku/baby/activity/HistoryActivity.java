package jp.kakakakakku.baby.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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

    MyCustomListAdapter myCustomListAdapter = null;
    List<MyCustomListData> objects = null;
    List<String> uniqueDateList = new ArrayList<String>();

    SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        // リリース用キャプチャ取得用
        // ステータスバー非表示
//        getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

        BabyHistoryModel model = new BabyHistoryModel(HistoryActivity.this);

        //
        // Spinner 用の日付を重複を排除して保持する
        //

        List<BabyHistoryEntity> allHistories = model.findAll();
        for (BabyHistoryEntity h: allHistories) {
            String tmpDate = sdfYYYYMMDD.format(h.getStartTime());
            if (!uniqueDateList.contains(tmpDate)) {
                uniqueDateList.add(tmpDate);
            }
        }

        //
        // 初期表示用のレコードを取得する
        //

        BabyHistoryEntity latestHistory = model.findLatest();
        String latestDate = sdfYYYYMMDD.format(latestHistory.getStartTime());
        List<BabyHistoryEntity> defaultHistories = model.findByStartDate(latestDate);

        objects = new ArrayList<MyCustomListData>();
        for (BabyHistoryEntity h: defaultHistories) {
            objects.add(getAdapterData(h));
        }

        myCustomListAdapter = new MyCustomListAdapter(this, 0, objects);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(myCustomListAdapter);

        // Add Spinner
        Spinner timestampSpinner = (Spinner)findViewById(R.id.timestampSpinner);

        String[] uniqueDateArray = uniqueDateList.toArray(new String[0]);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, uniqueDateArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timestampSpinner.setAdapter(spinnerAdapter);
        timestampSpinner.setSelection(uniqueDateArray.length - 1);

        timestampSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                BabyHistoryModel model = new BabyHistoryModel(HistoryActivity.this);
                List<BabyHistoryEntity> histories = model.findByStartDate(uniqueDateList.get(spinner.getSelectedItemPosition()));
                List<MyCustomListData> reloadObjects = new ArrayList<MyCustomListData>();
                for (BabyHistoryEntity h: histories) {
                    reloadObjects.add(getAdapterData(h));
                }

                myCustomListAdapter.clear();
                myCustomListAdapter.addAll(reloadObjects);
                myCustomListAdapter.notifyDataSetChanged();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    public MyCustomListData getAdapterData(BabyHistoryEntity h) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

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
        return tmpItem;
    }

    public String toDiffTime(Date startTime, Date endTime) {
        return DurationFormatUtils.formatPeriod(startTime.getTime(), endTime.getTime(), "HH:mm:ss");
    }

}
