package challenge.controller;

import challenge.dto.Shot;
import challenge.service.ShotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Controlador do endpoint de shots.
 */
@RestController
@RequestMapping("/shot")
public class ShotController {

    @Autowired
    private ShotService shotService;

    @RequestMapping(method = RequestMethod.GET)
    public Shot[] getShots(@RequestParam(value = "page", required = false) Integer page) {
        RestTemplate restTemplate = new RestTemplate();
        if(page == null) {
            page = 1;
        }
        return shotService.recoverShots(page);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{shotId}")
    public Shot getShot(@PathVariable Long shotId) {
        return shotService.recoverShotById(shotId);
    }
}
