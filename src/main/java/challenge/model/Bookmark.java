package challenge.model;

import challenge.dto.Shot;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NamedQueries({
        @NamedQuery(name= Bookmark.FIND_ALL_BOOKMARKS, query = "FROM Bookmark ORDER BY date DESC"),
        @NamedQuery(name= Bookmark.FIND_ONLY_RECENT_BOOKMARKS, query = "FROM Bookmark WHERE date >= :date ORDER BY date DESC"),
        @NamedQuery(name= Bookmark.FIND_BY_SHOT_ID, query = "FROM Bookmark WHERE shotId = :shotId")
})
public class Bookmark {

    public static final String FIND_ALL_BOOKMARKS = "FIND_ALL_BOOKMARKS";
    public static final String FIND_ONLY_RECENT_BOOKMARKS = "FIND_ONLY_RECENT_BOOKMARKS";
    public static final String FIND_BY_SHOT_ID = "FIND_BY_SHOT_ID";
    public static final String HIBERNATE_SEQUENCE = "hibernate_sequence";

    public Bookmark() {
    }

    public Bookmark(Long shotId, Timestamp date) {
        this.shotId = shotId;
        this.date = date;
    }

    private Long id;
    private Long shotId;
    private Timestamp date;
    private transient Shot shot;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = HIBERNATE_SEQUENCE)
    @SequenceGenerator(name = HIBERNATE_SEQUENCE, sequenceName = HIBERNATE_SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShotId() {
        return shotId;
    }

    public void setShotId(Long shotId) {
        this.shotId = shotId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Transient
    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }
}
