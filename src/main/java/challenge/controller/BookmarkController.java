package challenge.controller;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import challenge.dto.Message;
import challenge.model.Bookmark;
import challenge.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador do enpoint de favoritos.
 */
@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    @Autowired
    private BookmarkService bookmarkService;

    @PersistenceContext
    private EntityManager em;

    @RequestMapping(method = RequestMethod.GET)
    public List<Bookmark> getBookmarks(@RequestParam(value = "recentOnly", required = false) Boolean recentOnly) {
        if(recentOnly == null){
            recentOnly = false; // O default é false.
        }
        if(recentOnly){
            return bookmarkService.recoverRecentBookmarksOnly(ZonedDateTime.now());
        }
        return bookmarkService.recoverBookmarks();
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public Message postBookmark(@RequestBody Map<String, Object> payload){
        Long shotId = new Long((Integer) payload.get("shotId"));
        boolean success = bookmarkService.bookmark(shotId, new Timestamp(new Date().getTime()));
        if(success){
            return new Message(SUCCESS);
        }
        return new Message(ERROR);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{shotId}")
    @Transactional
    public Message deleteBookmark(@PathVariable("shotId") Long shotId){
        boolean success = this.bookmarkService.unbookmark(shotId);
        if(success){
            return new Message(SUCCESS);
        }
        return new Message(ERROR);
    }

}
