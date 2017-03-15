package challenge.service;

import challenge.dto.Shot;
import challenge.model.Bookmark;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
public class BookmarkServiceTest {

    @Autowired
    private BookmarkService bookmarkService;

    @MockBean
    private ShotService shotServiceMock;

    @Test
    public void testGetBookmark(){
        Long shotId = 1L;
        Shot shot = new Shot();
        when(shotServiceMock.recoverShotById(shotId)).thenReturn(shot);
        bookmarkService.bookmark(shotId, new Timestamp(new Date().getTime()));
        List<Bookmark> bookmarks = bookmarkService.recoverBookmarks();
        assertEquals(1, bookmarks.size());
        Bookmark bookmark = bookmarks.get(0);
        assertEquals(shotId, bookmark.getShotId());
        assertEquals(shot, bookmark.getShot());
    }

    @Test
    public void testGetBookmarks(){
        Long shotId1 = 1L;
        Long shotId2 = 2L;
        Shot shot1 = new Shot();
        Shot shot2 = new Shot();
        when(shotServiceMock.recoverShotById(shotId1)).thenReturn(shot1);
        when(shotServiceMock.recoverShotById(shotId2)).thenReturn(shot2);
        ZonedDateTime date1 = ZonedDateTime.of(2017, 03, 21, 10, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime date2 = ZonedDateTime.of(2017, 03, 21, 10, 30, 0, 0, ZoneId.systemDefault());
        ZonedDateTime date3 = ZonedDateTime.of(2017, 03, 21, 11, 0, 0, 0, ZoneId.systemDefault());
        bookmarkService.bookmark(shotId1, new Timestamp(date1.toInstant().toEpochMilli()));
        bookmarkService.bookmark(shotId2, new Timestamp(date3.toInstant().toEpochMilli()));
        List<Bookmark> bookmarks = bookmarkService.recoverRecentBookmarksOnly(date2);
        assertEquals(1, bookmarks.size());
        Bookmark bookmark = bookmarks.get(0);
        assertEquals(shotId2, bookmark.getShotId());
        assertEquals(shot2, bookmark.getShot());
    }

    @Test
    public void testUnbookmark(){
        Long shotId = 1L;
        Shot shot1 = new Shot();
        when(shotServiceMock.recoverShotById(shotId)).thenReturn(shot1);
        bookmarkService.bookmark(1L, new Timestamp(1000));
        List<Bookmark> bookmarks = bookmarkService.recoverBookmarks();
        assertEquals(1, bookmarks.size());
        Bookmark bookmark = bookmarks.get(0);
        assertEquals(shotId, bookmark.getShotId());
        bookmarkService.unbookmark(shotId);
        bookmarks = bookmarkService.recoverBookmarks();
        assertTrue(bookmarks.isEmpty());
    }
}
