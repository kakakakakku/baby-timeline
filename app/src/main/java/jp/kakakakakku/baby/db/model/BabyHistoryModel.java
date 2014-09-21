package jp.kakakakakku.baby.db.model;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.kakakakakku.baby.db.DatabaseHelper;
import jp.kakakakakku.baby.db.entity.BabyHistoryEntity;

public class BabyHistoryModel {

	private static final String TAG = BabyHistoryModel.class.getSimpleName();
	private Context context;

	public BabyHistoryModel(Context context) {
		this.context = context;
	}

    public List<BabyHistoryEntity> findAll() {
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
            return dao.queryForAll();
        } catch (Exception e) {
            Log.e(TAG, "例外が発生しました", e);
            return null;
        } finally {
            helper.close();
        }
    }

    public BabyHistoryEntity findById(int id) {
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
            return dao.queryForId(id);
        } catch (Exception e) {
            Log.e(TAG, "例外が発生しました", e);
            return null;
        } finally {
            helper.close();
        }
    }

    public BabyHistoryEntity findLatest() {
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
            return dao.queryBuilder().limit(1l).orderBy("startTime", false).queryForFirst();
        } catch (Exception e) {
            Log.e(TAG, "例外が発生しました", e);
            return null;
        } finally {
            helper.close();
        }
    }

    public List<BabyHistoryEntity> findByStartDate(String startDate) {
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            // FIXME: Date 型で SQL 投げられるように調べる
            SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyy/MM/dd");
            Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
            List<BabyHistoryEntity> tmpList = dao.queryForAll();
            for (int i = tmpList.size() - 1; i >= 0 ; i--) {
                String tmp = sdfYYYYMMDD.format(tmpList.get(i).getStartTime());
                if (!tmp.equals(startDate)) {
                    tmpList.remove(i);
                }
            }
            return tmpList;
        } catch (Exception e) {
            Log.e(TAG, "例外が発生しました", e);
            return null;
        } finally {
            helper.close();
        }
    }

    public int updateEndTime(Date endTime) {
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
            UpdateBuilder<BabyHistoryEntity, Integer> updateBuilder = dao.updateBuilder();
            updateBuilder.where().isNull("endTime");
            updateBuilder.updateColumnValue("endTime", endTime);
            updateBuilder.update();
        } catch (Exception e) {
            Log.e(TAG, "例外が発生しました", e);
        } finally {
            helper.close();
        }
        return 0;
    }

    public void save(BabyHistoryEntity word) {
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
//            dao.createOrUpdate(word);
            dao.create(word);
        } catch (Exception e) {
            Log.e(TAG, "例外が発生しました", e);
        } finally {
            helper.close();
        }
}

    public void saveAll(ArrayList<BabyHistoryEntity> word) {
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
            for (BabyHistoryEntity c: word) {
                dao.createOrUpdate(c);
            }
        } catch (Exception e) {
            Log.e(TAG, "例外が発生しました", e);
        } finally {
            helper.close();
        }
    }

    public int update(BabyHistoryEntity obj, Date endTime) {
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            obj.setEndTime(endTime);
            Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
            return dao.update(obj);
        } catch (Exception e) {
            Log.e(TAG, "例外が発生しました", e);
        } finally {
            helper.close();
        }
        return 0;
    }

	public int delete(BabyHistoryEntity word) {
		DatabaseHelper helper = new DatabaseHelper(context);
		try {
			Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
			return dao.delete(word);
		} catch (Exception e) {
			Log.e(TAG, "例外が発生しました", e);
		} finally {
			helper.close();
		}
		return 0;
    }

	public int deleteAll() {
		DatabaseHelper helper = new DatabaseHelper(context);
		try {
			Dao<BabyHistoryEntity, Integer> dao = helper.getDao(BabyHistoryEntity.class);
			return dao.delete(findAll());
		} catch (Exception e) {
			Log.e(TAG, "例外が発生しました", e);
		} finally {
			helper.close();
		}
		return 0;
	}

}
