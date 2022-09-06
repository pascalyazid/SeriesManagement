package com.seriesmanagement.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seriesmanagement.model.Category;
import com.seriesmanagement.model.Episode;
import com.seriesmanagement.model.Series;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DataHandler {

    /**
     * Konstruktor für den Datahandler
     * um die Ressourcen zu erkennen
     */
    private DataHandler() {
    }

    /**
     * reads alle Series
     *
     * @return
     */

    public static List<Series> readSeries() {
        List<Series> seriesList = new ArrayList<>();
        try {

            URL url = new URL("https://api.npoint.io/ad1a415040f72663293f");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("accept", "application/json");

            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            Series[] seriesList1 = mapper.readValue(responseStream, Series[].class);
            seriesList.addAll(Arrays.asList(seriesList1));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return seriesList;
    }

    /**
     * Save all Series
     *
     * @param list
     */

    public static void saveSeries(List list) {
        try {

            URL url = new URL ("https://api.npoint.io/ad1a415040f72663293f");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = new ObjectMapper().writeValueAsString(list.toArray());
            jsonInputString = "[" + jsonInputString.substring(1, jsonInputString.length() - 1) + "]";


            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input);
            }

            try(BufferedReader br = new BufferedReader(
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

    /**
     * Add a series
     *
     * @param series
     */
    public static boolean addSeries(Series series) {
        List<Series> seriesList = readSeries();
        if(!seriesList.contains(series)) {
            seriesList.add(series);
            saveSeries(seriesList);
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * remove a Series
     *
     * @param seriesUUID
     */
    public static boolean removeSeries(String seriesUUID) {
        List<Series> seriesList = readSeries();

        if(seriesList.removeIf(series1 -> series1.getSeriesUUID().contains(seriesUUID))) {
            saveSeries(seriesList);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Read a Series
     *
     * @param seriesUUD
     * @return
     */
    public static Series getSeries(String seriesUUD) {
        return readSeries().stream().filter(series -> series.getSeriesUUID().equals(seriesUUD)).findFirst().orElse(null);
    }

    /**
     * Check if a seris exists
     * @param seriesUUID
     * @return
     */
    public static boolean existSeries(String seriesUUID) {
        return readSeries().stream().anyMatch(series -> series.getSeriesUUID().equals(seriesUUID));
    }

    /**
     * Add an Episode to a Series
     *
     * @param episode
     * @param seriesUUID
     */

    public static void addEpisode(Episode episode, String seriesUUID) {
        List<Series> seriesList = readSeries();
        seriesList.stream().filter(series -> series.getSeriesUUID().equals(seriesUUID)).findFirst().orElseThrow(NullPointerException::new).getEpisodes().add(episode);
        saveSeries(seriesList);
    }

    /**
     * Reads all Categories
     */
    public static List<Category> readCategories() {
        List<Category> categoryList = new ArrayList<>();
        try {

            URL url = new URL("https://api.npoint.io/8b9137047c49d18ef89e");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("accept", "application/json");

            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            Category[] categoryList1 = mapper.readValue(responseStream, Category[].class);
            categoryList.addAll(Arrays.asList(categoryList1));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    /**
     * Save all Categories
     */
    public static void saveCategories(List list) {
        try {

            URL url = new URL ("https://api.npoint.io/8b9137047c49d18ef89e");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = new ObjectMapper().writeValueAsString(list.toArray());
            jsonInputString = "[" + jsonInputString.substring(1, jsonInputString.length() - 1) + "]";


            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input);
            }

            try(BufferedReader br = new BufferedReader(
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

    /**
     * Add a Category
     *
     * @param Category
     */
    public static boolean newCategory(Category Category) {
        List<Category> categoryList = readCategories();
        if(!categoryList.contains(categoryList)) {
            categoryList.add(Category);
            saveCategories(categoryList);
            return true;
        }
        return false;
    }


    /**
     * Check if a Category exists
     * @param categoryUUID
     * @return
     */
    public static boolean existCategory(String categoryUUID) {
        return readCategories().stream().anyMatch(category -> category.getCatUUID().equals(categoryUUID));
    }
    /**
     * remove a Category
     *
     * @param catUUID
     */

    public static boolean removeCategory(String catUUID) {
        List<Category> categoryList = readCategories();
        if(categoryList.removeIf(Category -> Category.getCatUUID().contains(catUUID))){
            saveCategories(categoryList);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Read a Category
     *
     * @param catUUID
     * @return
     */

    public static Category getCategory(String catUUID) {
        return readCategories().stream().filter(Category -> Category.getCatUUID().equals(catUUID)).findAny().orElse(null);
    }

    /**
     * reads all Episodes
     *
     * @return
     */

    public static List<Episode> readEpisodes() {
        List<Episode> episodeList = new ArrayList<>();
        try {

            URL url = new URL("https://api.npoint.io/0100b0063de742dc840b");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("accept", "application/json");

            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            Episode[] episodeList1 = mapper.readValue(responseStream, Episode[].class);
            episodeList.addAll(Arrays.asList(episodeList1));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return episodeList;
    }

    /**
     * save an Episode
     *
     * @param list
     */

    public static void saveEpisodes(List list) {
        try {

            URL url = new URL ("https://api.npoint.io/0100b0063de742dc840b");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = new ObjectMapper().writeValueAsString(list.toArray());
            jsonInputString = "[" + jsonInputString.substring(1, jsonInputString.length() - 1) + "]";


            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input);
            }

            try(BufferedReader br = new BufferedReader(
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

    /**
     * read an Episode
     *
     * @param episodeUUID
     * @return
     */

    public static Episode getEpisode(String episodeUUID) {
        return readEpisodes().stream().filter(episode -> episode.getEpisodeUUID().equals(episodeUUID)).findFirst().orElse(null);
    }

    /**
     * add an Episode
     *
     * @param episode
     */
    public static boolean newEpisode(Episode episode) {
        List<Episode> episodeList = readEpisodes();
        if(!episodeList.contains(episode)) {
            episodeList.add(episode);
            saveEpisodes(episodeList);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Check if an Episode exists
     * @param episodeUUID
     * @return
     */
    public static boolean existEpisode(String episodeUUID) {
        return readEpisodes().stream().anyMatch(episode -> episode.getEpisodeUUID().equals(episodeUUID));
    }

    /**
     * remove an Episode
     *
     * @param episodeUUID
     */
    public static boolean removeEpisode(String episodeUUID) {
        List<Episode> episodeList = readEpisodes();
        if(episodeList.removeIf(folge -> folge.getEpisodeUUID().contains(episodeUUID))){
            saveEpisodes(episodeList);
            return true;
        }
        else {
            return false;
        }
    }
    
    }
