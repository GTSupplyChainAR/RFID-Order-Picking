package com.cs3605.orderpicking.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExperimentDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "order_picking";

    // Experiments
    private static final String TABLE_EXPERIMENTS = "experiments";
    private static final String KEY_EXPERIMENT_ID = "_id";
    private static final String KEY_EXPERIMENT_NAME = "experiment_name";
    private static final String KEY_EXPERIMENT_TRIALS = "experiment_trials";

    // Trials
    private static final String TABLE_TRIALS = "trials";
    private static final String KEY_TRIAL_ID = "_id";
    private static final String KEY_TRIAL_RACK_ID = "rack_id";
    private static final String KEY_TRIAL_R1_QUANTITY = "r1_quantity";
    private static final String KEY_TRIAL_R2_QUANTITY = "r2_quantity";
    private static final String KEY_TRIAL_R3_QUANTITY = "r3_quantity";
    private static final String KEY_TRIAL_Y1_QUANTITY = "y1_quantity";
    private static final String KEY_TRIAL_Y2_QUANTITY = "y2_quantity";
    private static final String KEY_TRIAL_Y3_QUANTITY = "y3_quantity";
    private static final String KEY_TRIAL_G1_QUANTITY = "g1_quantity";
    private static final String KEY_TRIAL_G2_QUANTITY = "g2_quantity";
    private static final String KEY_TRIAL_G3_QUANTITY = "g3_quantity";
    private static final String KEY_TRIAL_B1_QUANTITY = "b1_quantity";
    private static final String KEY_TRIAL_B2_QUANTITY = "b2_quantity";
    private static final String KEY_TRIAL_B3_QUANTITY = "b3_quantity";
    private static final String KEY_TRIAL_CART_POS = "cart_pos";

    public ExperimentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_EXPERIMENTS + " ("
                + KEY_EXPERIMENT_ID + " INTEGER PRIMARY KEY, "
                + KEY_EXPERIMENT_NAME + " TEXT, "
                + KEY_EXPERIMENT_TRIALS + " TEXT);");

        db.execSQL("CREATE TABLE " + TABLE_TRIALS + " ("
                + KEY_TRIAL_ID + " INTEGER PRIMARY KEY, "
                + KEY_TRIAL_RACK_ID + " TEXT, "
                + KEY_TRIAL_R1_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_R2_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_R3_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_Y1_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_Y2_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_Y3_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_G1_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_G2_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_G3_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_B1_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_B2_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_B3_QUANTITY + " INTEGER DEFAULT 0, "
                + KEY_TRIAL_CART_POS + " INTEGER DEFAULT 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }

    /**
     * Trials
     */

    public long addTrial(Trial trial) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TRIAL_RACK_ID, trial.getRackId());
        values.put(KEY_TRIAL_R1_QUANTITY, trial.getR1Quantity());
        values.put(KEY_TRIAL_R2_QUANTITY, trial.getR2Quantity());
        values.put(KEY_TRIAL_R3_QUANTITY, trial.getR3Quantity());
        values.put(KEY_TRIAL_Y1_QUANTITY, trial.getY1Quantity());
        values.put(KEY_TRIAL_Y2_QUANTITY, trial.getY2Quantity());
        values.put(KEY_TRIAL_Y3_QUANTITY, trial.getY3Quantity());
        values.put(KEY_TRIAL_G1_QUANTITY, trial.getG1Quantity());
        values.put(KEY_TRIAL_G2_QUANTITY, trial.getG2Quantity());
        values.put(KEY_TRIAL_G3_QUANTITY, trial.getG3Quantity());
        values.put(KEY_TRIAL_B1_QUANTITY, trial.getB1Quantity());
        values.put(KEY_TRIAL_B2_QUANTITY, trial.getB2Quantity());
        values.put(KEY_TRIAL_B3_QUANTITY, trial.getB3Quantity());
        values.put(KEY_TRIAL_CART_POS, trial.getCartPos());

        long id = db.insert(TABLE_TRIALS, null, values);
        db.close();
        return id;
    }

    public Trial getTrial(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_TRIALS, new String[]{KEY_TRIAL_ID, KEY_TRIAL_RACK_ID,
                        KEY_TRIAL_R1_QUANTITY, KEY_TRIAL_R2_QUANTITY, KEY_TRIAL_R3_QUANTITY,
                        KEY_TRIAL_Y1_QUANTITY, KEY_TRIAL_Y2_QUANTITY, KEY_TRIAL_Y3_QUANTITY,
                        KEY_TRIAL_G1_QUANTITY, KEY_TRIAL_G2_QUANTITY, KEY_TRIAL_G3_QUANTITY,
                        KEY_TRIAL_B1_QUANTITY, KEY_TRIAL_B2_QUANTITY, KEY_TRIAL_B3_QUANTITY,
                        KEY_TRIAL_CART_POS},
                KEY_TRIAL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Trial trial = new Trial();
        trial.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_ID))));
        trial.setRackId(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_RACK_ID)));
        trial.setR1Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_R1_QUANTITY))));
        trial.setR2Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_R1_QUANTITY))));
        trial.setR3Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_R1_QUANTITY))));
        trial.setY1Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_Y1_QUANTITY))));
        trial.setY2Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_Y2_QUANTITY))));
        trial.setY3Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_Y3_QUANTITY))));
        trial.setG1Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_G1_QUANTITY))));
        trial.setG2Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_G2_QUANTITY))));
        trial.setG3Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_G3_QUANTITY))));
        trial.setB1Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_B1_QUANTITY))));
        trial.setB2Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_B2_QUANTITY))));
        trial.setB3Quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_B3_QUANTITY))));
        trial.setCartPos(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TRIAL_CART_POS))));
        // return contact
        db.close();
        cursor.close();
        return trial;
    }

    public void updateTrial(Trial trial) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TRIAL_ID, trial.getId());
        values.put(KEY_TRIAL_RACK_ID, trial.getRackId());
        values.put(KEY_TRIAL_R1_QUANTITY, trial.getR1Quantity());
        values.put(KEY_TRIAL_R2_QUANTITY, trial.getR2Quantity());
        values.put(KEY_TRIAL_R3_QUANTITY, trial.getR3Quantity());
        values.put(KEY_TRIAL_Y1_QUANTITY, trial.getY1Quantity());
        values.put(KEY_TRIAL_Y2_QUANTITY, trial.getY2Quantity());
        values.put(KEY_TRIAL_Y3_QUANTITY, trial.getY3Quantity());
        values.put(KEY_TRIAL_G1_QUANTITY, trial.getG1Quantity());
        values.put(KEY_TRIAL_G2_QUANTITY, trial.getG2Quantity());
        values.put(KEY_TRIAL_G3_QUANTITY, trial.getG3Quantity());
        values.put(KEY_TRIAL_B1_QUANTITY, trial.getB1Quantity());
        values.put(KEY_TRIAL_B2_QUANTITY, trial.getB2Quantity());
        values.put(KEY_TRIAL_B3_QUANTITY, trial.getB3Quantity());
        values.put(KEY_TRIAL_CART_POS, trial.getCartPos());

        long id = db.insert(TABLE_TRIALS, null, values);
        db.update(TABLE_TRIALS, values, KEY_TRIAL_ID + " = " + trial.getId(), null);
        db.close();
    }

    public void deleteTrial(int trialId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_TRIALS, KEY_TRIAL_ID + "=" + trialId, null);
        db.close();
    }

    /**
     * Experiments
     */

    public long addExperiment(Experiment experiment) {
        SQLiteDatabase db = getWritableDatabase();
        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put(KEY_EXPERIMENT_NAME, experiment.getExperimentName());
        values.put(KEY_EXPERIMENT_TRIALS, gson.toJson(experiment.getTrialListIds()));

        long id = db.insert(TABLE_EXPERIMENTS, null, values);
        db.close();
        return id;
    }

    public Experiment getExperiment(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPERIMENTS, new String[]{KEY_EXPERIMENT_ID,
                        KEY_EXPERIMENT_NAME,
                        KEY_EXPERIMENT_TRIALS},
                KEY_EXPERIMENT_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Experiment experiment = new Experiment();
        experiment.setId(Integer.parseInt(cursor.getString(0)));
        experiment.setExperimentName(cursor.getString(1));

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> trialIdList = gson.fromJson(cursor.getString(2), type);

        experiment.setTrialList(getTrialArrayListFromIds(trialIdList));

        db.close();
        cursor.close();
        return experiment;
    }

    // Helper method to get trials for each id in arraylist of ids
    private ArrayList<Trial> getTrialArrayListFromIds(List<String> ids) {
        ArrayList<Trial> trialList = new ArrayList<>();
        for (String strId : ids) {
            int id = Integer.parseInt(strId);
            Trial trial = getTrial(id);
            trialList.add(trial);
        }
        return trialList;
    }

    public ArrayList<Experiment> getAllExperiments() {
        ArrayList<Experiment> experimentList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_EXPERIMENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Experiment experiment = new Experiment();
                experiment.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_EXPERIMENT_ID))));
                experiment.setExperimentName(cursor.getString(cursor.getColumnIndex(KEY_EXPERIMENT_NAME)));

                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> trialIdList = gson.fromJson(cursor.getString(cursor.getColumnIndex(KEY_EXPERIMENT_TRIALS)), type);

                experiment.setTrialList(getTrialArrayListFromIds(trialIdList));

                // Adding contact to list
                experimentList.add(experiment);
            } while (cursor.moveToNext());
        }
        db.close();
        return experimentList;
    }

    public void updateExperiment(Experiment experiment) {
        SQLiteDatabase db = getWritableDatabase();
        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put(KEY_EXPERIMENT_ID, experiment.getId());
        values.put(KEY_EXPERIMENT_NAME, experiment.getExperimentName());
        values.put(KEY_EXPERIMENT_TRIALS, gson.toJson(experiment.getTrialListIds()));

        db.update(TABLE_EXPERIMENTS, values, KEY_EXPERIMENT_ID + " = " + experiment.getId(), null);
        db.close();
    }

    public void deleteExperiment(int experimentId) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_EXPERIMENTS, KEY_EXPERIMENT_ID + "=" + experimentId, null);
        db.close();
    }
}


