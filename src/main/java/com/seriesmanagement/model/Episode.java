package com.seriesmanagement.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.UUID;


@Entity
public class Episode {

    @Id
    private String episodeUUID = UUID.randomUUID().toString();


    @NotEmpty
    private String title;


    @NotNull
    private double duration;


    @NotEmpty
    private String desc;


    /**
     * Default Constructor
     */

    public Episode() {
    }
    /**
     * Setter for the UUID
     * @param episodeUUID
     */
    public void setEpisodeUUID(String episodeUUID) {
        this.episodeUUID = episodeUUID;
    }

    /**
     * Getter for all Attributes
     *
     * @return
     */

    public String getEpisodeUUID() {
        return episodeUUID;
    }

    public String getTitle() {
        return title;
    }

    public double getDuration() {
        return duration;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * Setter für den Titel
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter für die Länge
     *
     * @param duration
     */
    public void setDuration(double duration) {
        this.duration = duration;
    }

    /**
     * Setter für die Beschreibung
     *
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
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
            Method[] allMethods = Episode.class.getDeclaredMethods();
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
