package jp.kakakakakku.baby.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;
import java.util.Date;

@DatabaseTable(tableName = "histories")
public class BabyHistoryEntity {

    public BabyHistoryEntity() {
    }

    public BabyHistoryEntity(
            int recordType,
            Date startTime,
            Date endTime) {
        this.recordType = recordType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

//    @DatabaseField(id = true)
    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private int recordType;

//    @DatabaseField(format = "...")
//    @DatabaseField(dateType = DataType.DATE_LONG)

@DatabaseField
    private Date startTime;

//    @DatabaseField(format = "...")
    @DatabaseField
    private Date endTime;

    public void setId(Integer id) { this.id = id; }
    public Integer getId() { return this.id; }

    public void setRecordType(int recordType) { this.recordType = recordType; }
    public int getRecordType() { return this.recordType; }

    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public Date getStartTime() { return this.startTime; }

    public void setEndTime(Date endTime) { this.endTime = endTime; }
    public Date getEndTime() { return this.endTime; }

}
