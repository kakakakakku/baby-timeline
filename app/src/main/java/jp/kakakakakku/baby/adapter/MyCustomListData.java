package jp.kakakakakku.baby.adapter;

import android.graphics.Bitmap;

public class MyCustomListData {

    private Bitmap bitmap;
    private String recordType;
    private String message;
    private String diffTime;

    public void setBitmap(Bitmap img) { this.bitmap = img; }
    public Bitmap getBitmap() { return this.bitmap; }

    public void setRecordType(String recordType) { this.recordType = recordType; }
    public String getRecordType() { return this.recordType; }

    public void setMessage(String message) { this.message = message; }
    public String getMessage() { return this.message; }

    public void setDiffTime(String diffTime) { this.diffTime = diffTime; }
    public String getDiffTime() { return this.diffTime; }

}
