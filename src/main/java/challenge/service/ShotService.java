package challenge.service;

import challenge.dto.Shot;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Responsável por fazer as chamadas para api do Dribbble.
 */
@Service
public class ShotService {

    private static final String APP_TOKEN = "b69877f06b27628ca751cc07df485f1c66a0212762168ee28648fe314471aef5";
    private static final String SHOTS_ENDPOINT = "https://api.dribbble.com/v1/shots";

    public Shot recoverShotById(Long shotId) {
        RestTemplate restTemplate = new RestTemplate();
        Shot shot = restTemplate.getForObject(SHOTS_ENDPOINT +"/"+shotId+"?access_token="+APP_TOKEN, Shot.class);
        return shot;
    }

    public Shot[] recoverShots(Integer page) {
        RestTemplate restTemplate = new RestTemplate();
        Shot[] shots = restTemplate.getForObject(SHOTS_ENDPOINT + "?access_token=" + APP_TOKEN+"&page="+page+"&per_page=30&sort=views", Shot[].class);
        return shots;
    }
}
