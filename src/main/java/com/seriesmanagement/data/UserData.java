package com.seriesmanagement.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seriesmanagement.model.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * data handler for reading and writing the users
 * <p>
 * Serienliste
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
     * Funktion überprüft ob ein User mit gegebenem Nutzernamen und Passwort existiert
     *
     * @param username
     * @param password
     * @return
     */
    public static User findUser(String username, String password) {
        User user = new User();
        List<User> userList = readUsers();

        for (User entry : userList) {
            if (entry.getUsername().equals(username) &&
                    entry.getPassword().equals(password)) {
                user = entry;
                break;
            } else {
                user.setUsername(null);
                user.setPassword(null);
            }
        }
        return user;
    }

    /**
     * Funktion um einen neuen Nutzer zu erstellen
     *
     * @param username
     * @param password
     */
    public static void addUSer(String username, String password, boolean read, boolean write, boolean edit) {
        User user = new User(username, password, read, write, edit);
        users.add(user);
    }

    /**
     * Liest alle User aus dem JSON-File und gibt sie als Liste zurück
     *
     * @return
     */
    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try {

            URL url = new URL("https://api.npoint.io/5e1de3694aacc4d38ae4");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("accept", "application/json");

            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            User[] users1 = mapper.readValue(responseStream, User[].class);
            users.addAll(Arrays.asList(users1));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
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
