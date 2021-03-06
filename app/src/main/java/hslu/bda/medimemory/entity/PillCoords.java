package hslu.bda.medimemory.entity;

import android.content.ContentValues;

import org.opencv.core.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import hslu.bda.medimemory.contract.DbObject;
import hslu.bda.medimemory.database.DbAdapter;
import hslu.bda.medimemory.database.DbHelper;

/**
 * Created by Andy on 21.04.2016.
 */
public class PillCoords implements DbObject {
    private int id;
    private int mediid;
    private Point coords;
    private int height;
    private int width;
    private boolean changed;

    /**
     * empty Constructor
     */
    public PillCoords(){this.setId(-1);}


    public PillCoords(int mediid, Point coords, int width, int height){
        this.setId(-1);
        this.setMediid(mediid);
        this.setCoords(coords);
        this.setWidth(width);
        this.setHeight(height);
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
        if(this.mediid!=mediid){
            this.mediid = mediid;
            this.setChanged(true);
        }
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        if(this.coords==null || !coords.equals(coords)) {
            this.coords = coords;
            this.setChanged(true);
        }
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if(this.height!=height) {
            this.height = height;
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
        values.put(DbHelper.COLUMN_XCOORD, getCoords().x);
        values.put(DbHelper.COLUMN_YCOORD, getCoords().y);
        values.put(DbHelper.COLUMN_WIDTH, getWidth());
        values.put(DbHelper.COLUMN_HEIGHT, getHeight());


        return values;
    }

    @Override
    public String getTableName() {
        return DbHelper.TABLE_MEDI_PILL_LOC;
    }

    @Override
    public String getPrimaryFieldName() {
        return DbHelper.COLUMN_ID;
    }

    @Override
    public String getPrimaryFieldValue() {
        return String.valueOf(getId());
    }

    public static Collection<PillCoords> getAllPillCoordsByMedid(int medid, DbAdapter dbAdapter){
        Collection<PillCoords> allPillCoords = new ArrayList<PillCoords>();
        Collection<ContentValues> allContentValues =
                dbAdapter.getAllByTable(DbHelper.TABLE_MEDI_PILL_LOC,
                        new String[]{DbHelper.COLUMN_MEDIID}, new String[]{String.valueOf(medid)});
        if(allContentValues!=null) {
            for(ContentValues contentValues:allContentValues){
                allPillCoords.add(copyContentValuesToObject(contentValues));
            }
        }

        return allPillCoords;
    }

    private static PillCoords copyContentValuesToObject(ContentValues contentValues) {
        PillCoords pillCoords = new PillCoords();
        pillCoords.setId(contentValues.getAsInteger(DbHelper.COLUMN_ID));
        pillCoords.setMediid(contentValues.getAsInteger(DbHelper.COLUMN_MEDIID));
        Point point = new Point();
        point.x = contentValues.getAsDouble(DbHelper.COLUMN_XCOORD);
        point.y = contentValues.getAsDouble(DbHelper.COLUMN_YCOORD);
        pillCoords.setWidth(contentValues.getAsInteger(DbHelper.COLUMN_WIDTH));
        pillCoords.setHeight(contentValues.getAsInteger(DbHelper.COLUMN_HEIGHT));
        pillCoords.setCoords(point);
        pillCoords.setChanged(false);
        return  pillCoords;
    }

    public static PillCoords getPillCoordById(String id, DbAdapter dbAdapter) {
        PillCoords pillCoords = new PillCoords();
        pillCoords.setId(Integer.parseInt(id));
        ContentValues contentValues = dbAdapter.getByObject(pillCoords);
        if(contentValues!= null) {
            pillCoords = copyContentValuesToObject(contentValues);
        }else{pillCoords=null;}

        return pillCoords;
    }

    public static PillCoords getNextPillByMedid(int mediid, DbAdapter dbAdapter){
        PillCoords pillCoords = null;
        Collection<PillCoords> allPillCoords = getAllPillCoordsByMedid(mediid,dbAdapter);
        Collection<Consumed> allConsumedPills = Consumed.getAllConsumedByMedid(mediid, dbAdapter);

        for(PillCoords element:allPillCoords){
            boolean isConsumed = false;
            for(Consumed consumed :allConsumedPills){
                if(consumed.getPillCoord().getId()==element.getId()){
                    isConsumed = true;
                    break;
                }
            }
            if(!isConsumed){
                pillCoords=element;
                break;
            }
        }
        return pillCoords;
    }



}
