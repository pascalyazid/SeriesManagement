package com.seriesmanagement.service;

import com.seriesmanagement.data.DataHandler;
import com.seriesmanagement.data.UserData;
import com.seriesmanagement.model.Series;
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
public class SeriesController {

    SeriesController() {
    }

    @RequestMapping(value = "/series", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity list(
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID) throws IOException {
        if (UserData.allowedUser(userUUID, 0)) {
            return new ResponseEntity<>(DataHandler.readSeries(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }

    }

    @RequestMapping(value = "/series/get", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity get(
            @QueryParam("uuid") String uuid,
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID)throws IOException {
        if (UserData.allowedUser(userUUID, 0)) {
            if (DataHandler.existSeries(uuid)) {
                return new ResponseEntity<>(DataHandler.getSeries(uuid), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Series not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }

    @RequestMapping(value = "/series", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity create(
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID,
            @Valid @BeanParam Series series) throws IOException {

        if (UserData.allowedUser(userUUID, 1)) {
            if (DataHandler.addSeries(series)) {
                return ResponseEntity.status(HttpStatus.OK).body("Series created");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Could not create Series");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }

    @RequestMapping(value = "/series", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity update(
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID,
            @Valid @BeanParam Series series,
            @QueryParam("uuid") String uuid) throws IOException {

        if (UserData.allowedUser(userUUID, 1)) {
            List<Series> seriesList = DataHandler.readSeries();
            if (DataHandler.existSeries(uuid)) {
                seriesList.set(seriesList.indexOf(DataHandler.getSeries(uuid)), series);
                return ResponseEntity.status(HttpStatus.OK).body("Series " + uuid + " was updated");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Series " + uuid + " not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }


    @RequestMapping(value = "/series", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity delete(
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID,
            @QueryParam("uuid") String uuid) throws IOException {

        if (UserData.allowedUser(userUUID, 1)) {
            if (DataHandler.removeSeries(uuid)) {
                return ResponseEntity.status(HttpStatus.OK).body("Series " + uuid + " deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Series " + uuid + " not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }

}
