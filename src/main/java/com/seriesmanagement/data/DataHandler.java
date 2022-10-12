package com.seriesmanagement.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seriesmanagement.model.Category;
import com.seriesmanagement.model.Episode;
import com.seriesmanagement.model.Series;
import com.seriesmanagement.service.Config;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DataHandler {

    /**
     * Konstruktor für den Datahandler
     * um die Ressourcen zu erkennen
     */
    private DataHandler() {}

    /**
     * reads alle Series
     *
     * @return
     */

    public static List<Series> readSeries() {
        List<Series> seriesList = new ArrayList<>();
        try {

            Type listType = new TypeToken<List<Series>>() {
            }.getType();

            InputStream fis = new FileInputStream(Config.getProperty("seriesJSON"));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Reader reader = new BufferedReader(isr);

            seriesList = new Gson().fromJson(reader, listType);

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

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(new File(String.valueOf(Paths.get(Config.getProperty("seriesJSON")))), list);

            } catch (IOException e) {
                e.printStackTrace();
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

            Type listType = new TypeToken<List<Category>>() {
            }.getType();

            InputStream fis = new FileInputStream(Config.getProperty("categoryJSON"));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Reader reader = new BufferedReader(isr);

            categoryList = new Gson().fromJson(reader, listType);


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

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(new File(String.valueOf(Paths.get(Config.getProperty("categoryJSON")))), list);

            } catch (IOException e) {
                e.printStackTrace();
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

            Type listType = new TypeToken<List<Episode>>() {
            }.getType();

            InputStream fis = new FileInputStream(Config.getProperty("episodeJSON"));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            Reader reader = new BufferedReader(isr);

            episodeList = new Gson().fromJson(reader, listType);


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

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(new File(String.valueOf(Paths.get(Config.getProperty("episodeJSON")))), list);

            } catch (IOException e) {
                e.printStackTrace();
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
