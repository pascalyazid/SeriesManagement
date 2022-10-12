package com.seriesmanagement.service;

import com.seriesmanagement.data.UserData;
import com.seriesmanagement.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import java.io.IOException;

@RestController
public class UserController {

    /**
     * Returns a List of all users
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity list(
            HttpServletRequest request) throws IOException {
        String userUUID = (String) request.getSession().getAttribute("userUUID");
        if (UserData.allowedUser(userUUID, 3)) {
            return ResponseEntity.status(HttpStatus.OK).body(UserData.readUsers());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }

    /**
     * Checks if the User is logged in
     *
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "/user/loggedIn", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity loggedIn(
            HttpServletRequest request
    ) throws IOException {
        String userUUID = (String) request.getSession().getAttribute("userUUID");
        if (userUUID != "userUUID" && UserData.allowedUser(userUUID, 0)) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity create(
            HttpServletRequest request,
            @Valid @BeanParam User user) throws IOException {
        String userUUID = (String) request.getSession().getAttribute("userUUID");
        if (UserData.allowedUser(userUUID, 4)) {
            if (!UserData.existUser(user.getUsername())) {
                UserData.addUser(user);
                return ResponseEntity.status(HttpStatus.CREATED).body("User created");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Insufficient Permissions");
        }
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity login(
            HttpServletRequest request,
            @FormParam("username") String username,
            @FormParam("password") String password) throws IOException {
        if (UserData.login(username, password)) {
            request.getSession().invalidate();
            request.getSession().setAttribute("userUUID", UserData.getUser(username).getUserUUID());

            return ResponseEntity.status(HttpStatus.OK).body(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity logout(
            HttpServletRequest request) throws IOException {
            request.getSession().invalidate();
            return ResponseEntity.status(HttpStatus.OK).body(true);
    }


}
