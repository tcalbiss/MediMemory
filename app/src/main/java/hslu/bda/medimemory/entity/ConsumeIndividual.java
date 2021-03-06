package hslu.bda.medimemory.entity;

import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import hslu.bda.medimemory.contract.DbObject;
import hslu.bda.medimemory.database.DbAdapter;
import hslu.bda.medimemory.database.DbHelper;

/**
 * Created by manager on 07.03.2016.
 */
public class ConsumeIndividual implements DbObject {

    private int id;
    private int mediid;
    private Day daypart;
    private Eat eatpart;
    private boolean changed;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    /**
     * empty Constructor
     */
    public ConsumeIndividual(){this.setId(-1);}


    public ConsumeIndividual(int mediid, Day daypart, Eat eatpart){
        this.setId(-1);
        this.setMediid(mediid);
        this.setDaypart(daypart);
        this.setEatpart(eatpart);
        this.setChanged(true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(this.id!=id) {
            this.id = id;
            this.setChanged(true);
        }
    }

    public int getMediid() {
        return mediid;
    }

    public void setMediid(int mediid) {
        if(this.mediid != mediid) {
            this.mediid = mediid;
            this.setChanged(true);
        }
    }

    public Day getDaypart() {
        return daypart;
    }

    public void setDaypart(Day daypart) {
        if(this.daypart == null || !this.daypart.getContentValues().equals(daypart)) {
            this.daypart = daypart;
            this.setChanged(true);
        }
    }

    public Eat getEatpart() {
        return eatpart;
    }

    public void setEatpart(Eat eatpart) {
        if(this.eatpart == null || !this.eatpart.getContentValues().equals(eatpart)) {
            this.eatpart = eatpart;
            this.setChanged(true);
        }
    }

    public boolean isChanged() {
        return changed;
    }

    private void setChanged(boolean changed) {
        this.changed = changed;
    }
    @Override
    public ContentValues getContentValues() {
        final ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_ID,getId());
        values.put(DbHelper.COLUMN_MEDIID, getMediid());
        values.put(DbHelper.COLUMN_DAYPART, getDaypart().getId());
        values.put(DbHelper.COLUMN_EATPART, getEatpart().getId());

        return values;
    }

    @Override
    public String getTableName() {
        return DbHelper.TABLE_MEDI_CONSINDIV;
    }

    @Override
    public String getPrimaryFieldName() {
        return DbHelper.COLUMN_ID;
    }

    @Override
    public String getPrimaryFieldValue() {
        return String.valueOf(getId());
    }

    public static Collection<ConsumeIndividual> getAllConsumeIndividualByMedid(int medid, DbAdapter dbAdapter){
        Collection<ConsumeIndividual> allConsumeIndividuals = new ArrayList<ConsumeIndividual>();
        Collection<ContentValues> allContentValues =
                dbAdapter.getAllByTable(DbHelper.TABLE_MEDI_CONSINDIV,
                        new String[]{DbHelper.COLUMN_MEDIID},new String[]{String.valueOf(medid)});
        if(allContentValues!=null) {
            for(ContentValues contentValues:allContentValues){
                allConsumeIndividuals.add(copyContentValuesToObject(contentValues, dbAdapter));
            }
        }

        return allConsumeIndividuals;
    }

    /**
     * Returns only active medis
     * @param dbAdapter
     * @return
     */
    public static Collection<ConsumeIndividual> getAllConsumeIndividual(DbAdapter dbAdapter){
        Collection<ConsumeIndividual> allConsumeIndividuals = new ArrayList<ConsumeIndividual>();
        Collection<ContentValues> allContentValues =
                dbAdapter.getAllByTable(DbHelper.TABLE_MEDI_CONSINDIV);
        if(allContentValues!=null) {
            for(ContentValues contentValues:allContentValues){
                ConsumeIndividual consumeIndividual = copyContentValuesToObject(contentValues, dbAdapter);
                if(dbAdapter.isMediActive(consumeIndividual.getMediid())) {
                    allConsumeIndividuals.add(consumeIndividual);
                }
            }
        }

        return allConsumeIndividuals;
    }

    private static ConsumeIndividual copyContentValuesToObject(ContentValues contentValues, DbAdapter dbAdapter) {
        ConsumeIndividual consumeIndividual = new ConsumeIndividual();
        consumeIndividual.setId(contentValues.getAsInteger(DbHelper.COLUMN_ID));
        consumeIndividual.setMediid(contentValues.getAsInteger(DbHelper.COLUMN_MEDIID));
        consumeIndividual.setDaypart(Day.getDayById(contentValues.getAsString(DbHelper.COLUMN_DAYPART), dbAdapter));
        consumeIndividual.setEatpart(Eat.getEatById(contentValues.getAsString(DbHelper.COLUMN_EATPART), dbAdapter));
        consumeIndividual.setChanged(false);
        return  consumeIndividual;
    }

}

