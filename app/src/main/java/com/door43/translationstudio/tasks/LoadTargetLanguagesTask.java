package com.door43.translationstudio.tasks;

import com.door43.translationstudio.AppContext;
import com.door43.util.tasks.ManagedTask;

/**
 * Performs the initial loading of the target languages.
 *
 */
public class LoadTargetLanguagesTask extends ManagedTask {
    public static final String TASK_ID = "load_target_languages";

    @Override
    public void start() {
        AppContext.getLibrary().getTargetLanguages();
    }
}
