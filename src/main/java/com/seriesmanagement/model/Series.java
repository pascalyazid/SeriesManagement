package com.seriesmanagement.model;


import com.seriesmanagement.util.ImgURL;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Series {

    @Id
    private String seriesUUID = UUID.randomUUID().toString();
    /**
     * Bean-Validation for the attributes
     */

    @NotEmpty
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
    private String catUUID;


    @NotEmpty
    @Size(min = 2, max = 40)
    @Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
    private String title;



    @NotEmpty
    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private String releaseDate;


    @NotEmpty
    @Size(min = 2, max = 500, message = "The description must contain between {min} and {max} characters")
    @Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
    private String desc;


    @NotNull
    @Min(value = 0, message = "The min amount is 0")
    @Max(value = 9999, message = "The max amount is 9999")
    private int episodeCount;


    //@ImgURL
    @Pattern(regexp = "[^\\s]+(\\.(?i)(jpg|png|bmp))$")
    private String imgURL;

    // TODO: 6/14/2022 Custom Validation Constraint

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    private int rating;


    @NotNull
    private boolean watched;

    @OneToMany
    private List<Episode> episodes;


    public Series() {
        this.episodes = new ArrayList<>();
    }

    /**
     * Getter for all Attributes
     *
     * @return
     */

    public String getSeriesUUID() {
        return seriesUUID;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getDesc() {
        return desc;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public String getImgURL() {
        return imgURL;
    }

    public int getRating() {
        return rating;
    }

    public String getCatUUID() {return catUUID;}

    public boolean isWatched() {
        return watched;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * Setter für the UUUID
     *
     * @param seriesUUID
     */
    public void setSeriesUUID(String seriesUUID) {
        this.seriesUUID = seriesUUID;
    }


    /**
     * Setter for the Titel
     *
     * @param title
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for the Release Date
     *
     * @param releaseDate
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Setter for the Description
     *
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Setter setter for the Episode count
     *
     * @param episodeCount
     */
    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    /**
     * Setter for the Image URL
     *
     * @param imgURL
     */

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    /**
     * Setter for the Rating
     *
     * @param rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }


    /**
     * Setter for the Episodes
     *
     * @param episodes
     */
    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    /**
     * Setter for the catUUID
     *
     * @param catUUID
     */
    public void setCatUUID(String catUUID) {
        this.catUUID = catUUID;
    }

    /**
     * Setter for Watched
     *
     * @param watched
     */
    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    /**
     *Checks if a function containing the Attributes name exists to filter and sortt it
     * @param field
     * @return
     */
    public String showField(String field) {
        Object data = null;
        try {
            String functionName = "get" + field;
            Method[] allMethods = Series.class.getDeclaredMethods();
            for (Method m : allMethods) {
                if (m.getName().contains(functionName)) {
                    data = m.invoke(this);
                }
            }
        } catch (Exception e) {
            data = null;
        }
        return data.toString();
    }
}
