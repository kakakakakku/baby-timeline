package jp.kakakakakku.baby.async;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import jp.kakakakakku.baby.callback.Callback;
import jp.kakakakakku.baby.db.entity.BabyHistoryEntity;
import jp.kakakakakku.baby.db.model.BabyHistoryModel;

public class SkeletonAsync extends AsyncTask<Object, Integer, String > {

    private ArrayList<BabyHistoryEntity> mArrAPrivate ;
    private Context mContext ;

    private Callback mCallback;

    private ProgressDialog dialog;
    public SkeletonAsync(Context context, ArrayList<BabyHistoryEntity> array, Callback callback) {
        super();
        this.mContext = context;
        this.mArrAPrivate = array;
        this.mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("保存中");
        dialog.show();
    }

    @Override
    protected String doInBackground(Object... params) {
        BabyHistoryModel model = new BabyHistoryModel(mContext);
        model.saveAll(mArrAPrivate);

        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();
        mCallback.onSuccessInit();


    }
}
