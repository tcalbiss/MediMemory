package hslu.bda.medimemory.entity;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;


import hslu.bda.medimemory.contract.DbObject;
import hslu.bda.medimemory.database.DbAdapter;
import hslu.bda.medimemory.database.DbHelper;

/**
 * Created by manager on 07.03.2016.
 */
public class Data implements DbObject{

    private int id;
    private String description;
    private int duration;
    private int amount;
    private int width;
    private int length;
    private Bitmap picture;
    private Calendar createDate;
    private Calendar endDate;
    private String note;
    private int active;
    private boolean changed;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Collection<Consumed> allConsumed = new ArrayList<Consumed>();
    private Collection<ConsumeIndividual> allConsumeIndividual = new ArrayList<ConsumeIndividual>();
    private Collection<ConsumeInterval> allConsumeInterval = new ArrayList<ConsumeInterval>();
    private Collection<PillCoords> allPillCoords = new ArrayList<PillCoords>();

    public Data(){this.setId(-1);}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(this.id != id) {
            this.id = id;
            this.setChanged(true);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(this.description!=description) {
            this.description = description;
            this.setChanged(true);
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if(this.duration!=duration) {
            this.duration = duration;
            this.setChanged(true);
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if(this.amount!=amount) {
            this.amount = amount;
            this.setChanged(true);
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if(this.width!=width) {
            this.width = width;
            this.setChanged(true);
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        if(this.length!=length) {
            this.length = length;
            this.setChanged(true);
        }
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        if(this.picture ==null || !this.picture.equals(picture)) {
            this.picture = picture;
            this.setChanged(true);
        }
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        if(this.createDate==null || !this.createDate.equals(createDate)) {
            this.createDate = createDate;
            this.setChanged(true);
        }
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        if(this.endDate==null || this.endDate.equals(endDate)) {
            this.endDate = endDate;
            this.setChanged(true);
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        if(this.note!=note) {
            this.note = note;
            this.setChanged(true);
        }
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        if(this.active!=active) {
            this.active = active;
            this.setChanged(true);
        }
    }


    public boolean isChanged() {
        return changed;
    }

    private void setChanged(boolean changed) {
        this.changed = changed;
    }

    public Collection<Consumed> getAllConsumed() {
        return allConsumed;
    }

    public void setAllConsumed(Collection<Consumed> allConsumed) {
        this.allConsumed = allConsumed;
    }

    public Collection<ConsumeIndividual> getAllConsumeIndividual() {
        return allConsumeIndividual;
    }

    public void setAllConsumeIndividual(Collection<ConsumeIndividual> allConsumeIndividual) {
        this.allConsumeIndividual = allConsumeIndividual;
    }

    public Collection<ConsumeInterval> getAllConsumeInterval() {
        return allConsumeInterval;
    }

    public void setAllConsumeInterval(Collection<ConsumeInterval> allConsumeInterval) {
        this.allConsumeInterval = allConsumeInterval;
    }

    public Collection<PillCoords> getAllPillCoords(){return  allPillCoords;}

    public void setAllPillCoords(Collection<PillCoords> allPillCoords){
        this.allPillCoords = allPillCoords;
    }

    @Override
    public ContentValues getContentValues() {
        final ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_ID,getId());
        values.put(DbHelper.COLUMN_DESC, getDescription());
        values.put(DbHelper.COLUMN_DURATION, getDuration());
        values.put(DbHelper.COLUMN_AMOUNT, getAmount());
        values.put(DbHelper.COLUMN_WIDTH, getWidth());
        values.put(DbHelper.COLUMN_LENGTH, getLength());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        getPicture().compress(Bitmap.CompressFormat.PNG, 0, stream);
        values.put(DbHelper.COLUMN_PICTURE, stream.toByteArray());
        values.put(DbHelper.COLUMN_CREATEDATE, simpleDateFormat.format(getCreateDate().getTime()));
        if(getEndDate()!=null) {
            values.put(DbHelper.COLUMN_ENDDATE, simpleDateFormat.format(getEndDate().getTime()));
        }
        else{
            values.put(DbHelper.COLUMN_ENDDATE, (String)null);
        }
        values.put(DbHelper.COLUMN_NOTE, getNote());
        values.put(DbHelper.COLUMN_ACTIVE, getActive());

        return values;
    }

    @Override
    public String getTableName() {
        return DbHelper.TABLE_MEDI_DATA;
    }

    @Override
    public String getPrimaryFieldName() {
        return DbHelper.COLUMN_ID;
    }

    @Override
    public String getPrimaryFieldValue() {
        return String.valueOf(getId());
    }

    private static Data copyContentValuesToObject(ContentValues contentValues, DbAdapter dbAdapter) {
        Data data = new Data();
        data.setId(contentValues.getAsInteger(DbHelper.COLUMN_ID));
        data.setDescription(contentValues.getAsString(DbHelper.COLUMN_DESC));
        data.setDuration(contentValues.getAsInteger(DbHelper.COLUMN_DURATION));
        data.setAmount(contentValues.getAsInteger(DbHelper.COLUMN_AMOUNT));
        data.setWidth(contentValues.getAsInteger(DbHelper.COLUMN_WIDTH));
        data.setLength(contentValues.getAsInteger(DbHelper.COLUMN_LENGTH));
        data.setPicture(BitmapFactory.decodeByteArray(contentValues.getAsByteArray(DbHelper.COLUMN_PICTURE),0,contentValues.getAsByteArray(DbHelper.COLUMN_PICTURE).length));
        try{
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(simpleDateFormat.parse(contentValues.getAsString(DbHelper.COLUMN_CREATEDATE)));
            data.setCreateDate(calendar);
        }catch(Exception e) {data.setCreateDate(null);}
        try{
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(simpleDateFormat.parse(contentValues.getAsString(DbHelper.COLUMN_ENDDATE)));
            data.setEndDate(calendar);
        }catch(Exception e) {data.setEndDate(null);}
        data.setNote(contentValues.getAsString(DbHelper.COLUMN_NOTE));
        data.setActive(contentValues.getAsInteger(DbHelper.COLUMN_ACTIVE));
        data.setChanged(false);
        return data;
    }

    public static Data getDataById(String id, DbAdapter dbAdapter) {
        Data data = new Data();
        data.setId(Integer.parseInt(id));
        ContentValues contentValues = dbAdapter.getByObject(data);
        if(contentValues!= null) {
            data = copyContentValuesToObject(contentValues, dbAdapter);
        }else{data=null;}

        data.setAllConsumed(Consumed.getAllConsumedByMedid(data.getId(), dbAdapter));
        data.setAllConsumeIndividual(ConsumeIndividual.getAllConsumeIndividualByMedid(data.getId(), dbAdapter));
        data.setAllConsumeInterval(ConsumeInterval.getAllConsumeIntervalByMedid(data.getId(), dbAdapter));
        data.setAllPillCoords(PillCoords.getAllPillCoordsByMedid(data.getId(),dbAdapter));
        data.setChanged(false);
        return data;
    }

    public static Collection<Data> getAllDataFromTable(DbAdapter dbAdapter){
        Collection<Data> allData = new ArrayList<Data>();
        try{
            Collection<ContentValues> allContentValues = dbAdapter.getAllByTable(DbHelper.TABLE_MEDI_DATA);
            for(ContentValues contentValues : allContentValues){
                Data data = copyContentValuesToObject(contentValues, dbAdapter);
                data.setAllConsumed(Consumed.getAllConsumedByMedid(data.getId(), dbAdapter));
                data.setAllConsumeIndividual(ConsumeIndividual.getAllConsumeIndividualByMedid(data.getId(), dbAdapter));
                data.setAllConsumeInterval(ConsumeInterval.getAllConsumeIntervalByMedid(data.getId(), dbAdapter));
                data.setAllPillCoords(PillCoords.getAllPillCoordsByMedid(data.getId(),dbAdapter));
                allData.add(data);
            }
        }catch (Exception e) {
            System.out.printf(e.getMessage());
        }

        return  allData;
    }



}

