package server.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RoomDTO {
    private int id;
    private String groupIP;
    private String type;
    private String creator;
    private Date time;
    private Date update_time;

    public static RoomDTO fromModel(ResultSet rs) throws SQLException {
        RoomDTO d = new RoomDTO();
        d.groupIP = rs.getString(1);
        d.type = rs.getString(2).trim();
        d.creator = rs.getString(3).trim();
        d.time = rs.getDate(4);
        d.update_time = rs.getDate(5);
        d.id = rs.getInt(6);
        return d;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupIP() {
        return groupIP;
    }

    public void setGroupIP(String groupIP) {
        this.groupIP = groupIP;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
