package challenge.service;

import challenge.dto.Shot;
import challenge.model.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * Responsável por recuperar as informações de favoritos do banco.
 */
@Service
@Transactional
public class BookmarkService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ShotService shotService;

    public List<Bookmark> recoverBookmarks() {
        Query query = em.createNamedQuery(Bookmark.FIND_ALL_BOOKMARKS);
        List<Bookmark> list = query.getResultList();
        fillShot(list);
        return list;
    }

    public List<Bookmark> recoverRecentBookmarksOnly(ZonedDateTime date) {
        Query query = em.createNamedQuery(Bookmark.FIND_ONLY_RECENT_BOOKMARKS);
        query.setParameter("date", Date.from(date.minusMinutes(2l).toInstant()));
        List<Bookmark> list = query.getResultList();
        fillShot(list);
        return list;
    }

    private void fillShot(List<Bookmark> list) {
        for (Bookmark bookmark : list) {
            Shot shot = shotService.recoverShotById(bookmark.getShotId());
            bookmark.setShot(shot);
        }
    }

    public boolean bookmark(Long shotId, Timestamp date) {
        Query query = em.createNamedQuery(Bookmark.FIND_BY_SHOT_ID);
        query.setParameter("shotId", shotId);
        List<Bookmark> list = query.getResultList();
        if(list.size() != 0){
            // Esse shot já está nos favoritos.
            return false;
        }
        // Este shot ainda não está nos favoritos.
        Bookmark newBookmark = new Bookmark(shotId, date);
        this.em.persist(newBookmark);
        return true;
    }

    public boolean unbookmark(Long shotId) {
        Query query = em.createNamedQuery(Bookmark.FIND_BY_SHOT_ID);
        query.setParameter("shotId", shotId);
        List<Bookmark> list = query.getResultList();
        if(list.size() != 1){
            // Erro: Deveria haver apenas 1 favorito por shot.
            return false;
        }
        // Encontrou o shot que deve ser removido.
        this.em.remove(list.get(0));
        return true;
    }
}
