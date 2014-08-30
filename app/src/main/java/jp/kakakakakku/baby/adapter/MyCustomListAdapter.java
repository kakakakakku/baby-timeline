package jp.kakakakakku.baby.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jp.kakakakakku.baby.R;

public class MyCustomListAdapter extends ArrayAdapter<MyCustomListData > {
    private LayoutInflater layoutInflater;
    //- 背景色変更判断用フラグ
    private int loopCount = 1;

    private Context myContext;

    public MyCustomListAdapter (Context context, int viewResourceId, List<MyCustomListData> objects) {
        super(context, viewResourceId, objects);
        myContext = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //- 特定の行(position)のデータを得る
        MyCustomListData item = (MyCustomListData)getItem(position);

        //- リスト用のレイアウトを初回のみ作成
        if( convertView == null ) {
            convertView = layoutInflater.inflate(R.layout.list, null);
        }

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.listContainer);

        //- 背景色を交互に入替える
        if( loopCount%2 == 0 ) {
            linearLayout.setBackgroundColor(myContext.getResources().getColor(R.color.listFirst));
        } else {
            linearLayout.setBackgroundColor(myContext.getResources().getColor(R.color.listSecond));
        }
        loopCount++;

        // 画像
        ImageView imageView = (ImageView) convertView.findViewById(R.id.listImg);
        imageView.setImageBitmap(item.getBitmap());

        // アクションタイプ
        TextView tvRecordType = (TextView) convertView.findViewById(R.id.recordType);
        tvRecordType .setText(item.getRecordType());

        // メッセージ
        // TODO: 名前を変える
        TextView listMessageTextView = (TextView) convertView.findViewById(R.id.listMessage);
        listMessageTextView .setText(item.getMessage());

        // 経過時間
        TextView tvDiffTime = (TextView) convertView.findViewById(R.id.diffTime);
        tvDiffTime .setText(item.getDiffTime());

        return convertView;
    }
}
