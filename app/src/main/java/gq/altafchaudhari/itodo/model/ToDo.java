package gq.altafchaudhari.itodo.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class ToDo implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String date;
    private String note_color;
    private boolean is_finished;



    public ToDo(String title, String description,String date,String note_color,boolean is_finished) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.note_color = note_color;
        this.is_finished = is_finished;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote_color() {
        return note_color;
    }

    public void setNote_color(String note_color) {
        this.note_color = note_color;
    }

    public boolean isIs_finished() {
        return is_finished;
    }

    public void setIs_finished(boolean is_finished) {
        this.is_finished = is_finished;
    }

    @Ignore
    public ToDo(int id, String title, String description,String date, String note_color, boolean is_finished) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.note_color = note_color;
        this.is_finished = is_finished;
    }

    protected ToDo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
    }

    public static final Creator<ToDo> CREATOR = new Creator<ToDo>() {
        @Override
        public ToDo createFromParcel(Parcel in) {
            return new ToDo(in);
        }

        @Override
        public ToDo[] newArray(int size) {
            return new ToDo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(note_color);
        dest.writeBoolean(is_finished);
    }
}
