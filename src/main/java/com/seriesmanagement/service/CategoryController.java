package com.seriesmanagement.service;

import com.seriesmanagement.data.DataHandler;
import com.seriesmanagement.data.UserData;
import com.seriesmanagement.model.Category;

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
public class CategoryController {

    CategoryController() {
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity list(
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID) throws IOException {

        if (UserData.allowedUser(userUUID, 0)) {
            return new ResponseEntity<>(DataHandler.readCategories(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }

    }

    @RequestMapping(value = "/category/get", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity get(
            @QueryParam("uuid") String uuid,
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID) throws IOException {

        if (UserData.allowedUser(userUUID, 0)) {
            if (DataHandler.existCategory(uuid)) {
                return new ResponseEntity<>(DataHandler.getCategory(uuid), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity create(
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID,
            @Valid @BeanParam Category category) throws IOException {

        if (UserData.allowedUser(userUUID, 1)) {
            if (DataHandler.newCategory(category)) {
                return ResponseEntity.status(HttpStatus.OK).body("Category created");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Could not create Category");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }

    @RequestMapping(value = "/category", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity update(
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID,
            @Valid @BeanParam Category category,
            @QueryParam("uuid") String uuid) throws IOException {

        if (UserData.allowedUser(userUUID, 1)) {
            if (DataHandler.existCategory(uuid)) {
                DataHandler.removeCategory(uuid);
                category.setCatUUID(uuid);
                DataHandler.newCategory(category);
                return ResponseEntity.status(HttpStatus.OK).body("Category " + uuid + " was updated");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category " + uuid + " not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }


    @RequestMapping(value = "/category", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity delete(
            @CookieValue(value = "userUUID", defaultValue = "userUUID") String userUUID,
            @QueryParam("uuid") String uuid) throws IOException {

        if (UserData.allowedUser(userUUID, 1)) {
            if (DataHandler.removeCategory(uuid)) {
                return ResponseEntity.status(HttpStatus.OK).body("Category " + uuid + " deleted");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category " + uuid + " not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }
}
