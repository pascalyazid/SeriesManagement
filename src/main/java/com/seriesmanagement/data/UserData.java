package com.seriesmanagement.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seriesmanagement.model.Series;
import com.seriesmanagement.model.User;
import com.seriesmanagement.service.Config;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * DataHandler for reading and writing the users
 *
 * @author Pascal Thuma
 */
public class UserData {

    private static User activeUser;
    public static List<User> users;

    /**
     * Konstrukter UserData.java
     */
    private UserData() {
        users = readUsers();
        //createUser();
        activeUser = users.get(0);
    }

    /**
     * Create a new User
     *
     * @param user
     */
    public static void addUser(User user) {
        List<User> userList = readUsers();
        userList.add(user);
        saveUsers(userList);
    }

    /**
     * Checks if a user with the given username already exists
     * @param username
     * @return
     */
    public static boolean existUser(String username) {
        return readUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public static boolean login(String username, String passwd) {
        return readUsers().stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(passwd));
    }

    /**
     * Reads all users from the json file
     *
     * @return
     */
    public static List<User> readUsers() {
        List<User> userList = new ArrayList<>();
        try {


            Type listType = new TypeToken<List<User>>() {
            }.getType();

            InputStream fis = new FileInputStream(Config.getProperty("userJSON"));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Reader reader = new BufferedReader(isr);

            userList = new Gson().fromJson(reader, listType);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Read a User
     *
     * @param username
     * @return
     */
    public static User getUser(String username) {
        return readUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    /**
     * Übeprüft, ob ein User die benötigten Rechte hat um eine Funktion zu benutzen
     *
     * @param role
     * @param uuid
     * @return
     */
    public static boolean allowedUser(String uuid, int role) {
        List<User> users = readUsers();
        if(uuid != null && users.stream().anyMatch(user1 -> user1.getUserUUID().equals(uuid))) {
            User user = users.stream().filter(user1 -> user1.getUserUUID().equals(uuid)).findFirst().orElse(null);
            if(user.getRole(role)) {
                return true;
            }
            return false;
        }
        else {
            return false;
        }
    }

    /**
     * Speichert die Liste von Nutzern ins JSON-File
     *
     * @param list
     */
    public static void saveUsers(List list) {
        try {

            URL url = new URL("https://api.npoint.io/5e1de3694aacc4d38ae4");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = new ObjectMapper().writeValueAsString(list.toArray());
            jsonInputString = "[" + jsonInputString.substring(1, jsonInputString.length() - 1) + "]";


            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
