package com.seriesmanagement.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.sound.midi.MidiChannel;
import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.lang.reflect.Method;
import java.util.UUID;

@Entity
public class Category {
    @NotEmpty
    @Size(min = 2, max = 40)
    @Pattern(regexp = "^[a-zA-Z0-9_ ]*$")
    private String title;

    @Id
    private String catUUID;


    /**
     * Default Konstruktor
     */

    public Category() {
        this.catUUID = UUID.randomUUID().toString();
    }

    /**
     * Setter for the UUID
     * @param catUUID
     */
    public void setCatUUID(String catUUID) {
        this.catUUID = catUUID;
    }

    /**
     * Getter for all Attributes
     *
     * @return
     */
    public String getCatUUID() {return catUUID;}

    public String getTitle() {return title;}

    /**
     * Setter for the Title
     *
     * @param title
     */

    public void setTitle(String title) {
        this.title = title;
    }


    /**
     *Checks if a function containing the Attributes name exists to filter and sort it
     * @param field
     * @return
     */
    public String showField(String field) {
        Object data = null;
        try {
            String functionName = "get" + field;
            Method[] allMethods = Category.class.getDeclaredMethods();
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
