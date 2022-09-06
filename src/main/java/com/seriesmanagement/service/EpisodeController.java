package com.seriesmanagement.service;

import com.seriesmanagement.data.DataHandler;
import com.seriesmanagement.data.UserData;
import com.seriesmanagement.model.Episode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.util.List;


@RestController
public class EpisodeController {
    EpisodeController() {
    }

    @RequestMapping(value = "/episode", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity list(@CookieValue("userUUID") String userUUID) throws IOException {
        if (UserData.allowedUser(userUUID, 0)) {
            return new ResponseEntity<>(DataHandler.readEpisodes(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }

    }

    @RequestMapping(value = "/episode/get", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity get(@QueryParam("uuid") String uuid, @CookieValue("userUUID") String userUUID) throws IOException {
        if (UserData.allowedUser(userUUID, 0)) {
            if (DataHandler.existEpisode(uuid)) {
                return new ResponseEntity<>(DataHandler.getEpisode(uuid), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Episode not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }

    @RequestMapping(value = "/episode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity create(
            @CookieValue("userUUID") String userUUID,
            @Valid @BeanParam Episode episode) throws IOException {

        if (UserData.allowedUser(userUUID, 1)) {
            if(DataHandler.newEpisode(episode)) {
                return ResponseEntity.status(HttpStatus.OK).body("Episode created");
            }
            else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Could not create Episode");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }

    @RequestMapping(value = "/episode", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity update(
            @CookieValue("userUUID") String userUUID,
            @Valid @BeanParam Episode episode,
            @QueryParam("uuid") String uuid) throws IOException {

        if (UserData.allowedUser(userUUID, 1)) {
            List<Episode> episodeList = DataHandler.readEpisodes();
            if(DataHandler.existEpisode(uuid)) {
                episodeList.set(episodeList.indexOf(DataHandler.getEpisode(uuid)), episode);
                return ResponseEntity.status(HttpStatus.OK).body("Episode " + uuid + " was updated");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Episode " + uuid + " not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }


    @RequestMapping(value = "/episode", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity delete(
            @CookieValue("userUUID") String userUUID,
            @QueryParam("uui") String uuid) throws IOException {

        if (UserData.allowedUser(userUUID, 1)) {
            if(DataHandler.removeEpisode(uuid)) {
                return ResponseEntity.status(HttpStatus.OK).body("Episode " + uuid + " deleted");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Episode " + uuid + " not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }
}
