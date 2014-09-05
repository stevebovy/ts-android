package com.door43.translationstudio.projects;

import java.util.ArrayList;

/**
 * Projects are translation containers. They may also be called "Books".
 * Projects will first look for information (by slug) within the installed package, and will augment
 * any existing/missing information with data found on the server (requires internet connection).
 */
public class Project {

    private ArrayList<Chapter> mChapters = new ArrayList<Chapter>();
    private final String mTitle;
    private final String mSlug;
    private final String mDescription;
    private int mSelectedChapter;

    /**
     * Create a new project definition
     * @param title The human readable title of the project.
     * @param slug The machine readable slug identifying the project.
     * @param description A short description of the project.
     */
    public Project(String title, String slug, String description) {
        mTitle = title;
        mSlug = slug;
        mDescription = description;
    }

    /**
     * Get the project title
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the project slug
     * @return
     */
    public String getSlug() {
        return mSlug;
    }

    /**
     * Get the project description
     * @return
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Returns the number of chapters in this project
     * @return
     */
    public int numChapters() {
        return mChapters.size();
    }

    /**
     * Get a chapter by index
     * @param i the index of the chapter starting at 0
     * @return
     */
    public Chapter getChapter(int i) {
        if(mChapters.size() > i && i >= 0) {
            return mChapters.get(i);
        } else {
            // out of bounds
            return null;
        }
    }

    /**
     * Sets the currently selected chapter in the application
     * @param i
     * @return boolean return true of the index is valid
     */
    public boolean setSelectedChapter(int i) {
        if (mChapters.size() > i && i >= 0) {
            mSelectedChapter = i;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the currently selected chapter in the application
     * @return
     */
    public Chapter getSelectedChapter() {
        return getChapter(mSelectedChapter);
    }

    /**
     * Adds a chapter to the project
     * @param c the chapter to add
     * @return
     */
    public Chapter addChapter(Chapter c) {
        if(!this.mChapters.contains(c)) {
            this.mChapters.add(c);
            return c;
        } else {
            // TODO: is this nessesary? need to double check that the object signatures are different. If they are the same we should just always return the input chapter.
            return getChapter(this.mChapters.indexOf(c));
        }
    }
}