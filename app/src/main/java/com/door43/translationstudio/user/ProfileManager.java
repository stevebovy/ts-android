package com.door43.translationstudio.user;

import com.door43.tools.reporting.Logger;
import com.door43.translationstudio.R;
import com.door43.translationstudio.SettingsActivity;
import com.door43.translationstudio.git.Repo;
import com.door43.translationstudio.git.tasks.GitSyncAsyncTask;
import com.door43.translationstudio.git.tasks.repo.CommitTask;
import com.door43.translationstudio.git.tasks.repo.PushTask;
import com.door43.translationstudio.AppContext;
import com.door43.util.FileUtilities;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by joel on 2/9/2015.
 * // TODO: 9/29/2015 this should be changed the a User class in the core
 */
public class ProfileManager {
    private static Profile mProfile = null;

    /**
     * Returns the profile
     * @return
     */
    public static Profile getProfile() {
        if(mProfile == null) {
            File profileDir = new File(getRepositoryPath());
            if (profileDir.exists() && profileDir.isDirectory()) {
                File contactFile = new File(profileDir, "contact.json");
                if(contactFile.exists()) {
                    try {
                        JSONObject contactJson = new JSONObject(FileUtils.readFileToString(contactFile));
                        mProfile = new Profile(contactJson.getString("name"), contactJson.getString("email"));
                        if (contactJson.has("phone")) {
                            mProfile.setPhone(contactJson.getString("phone"));
                        }
                    } catch (Exception e) {
                        Logger.e(ProfileManager.class.getName(), "failed to load the profile details", e);
                    }
                }
            }
        }
        return mProfile;
    }

    /**
     * Specifies the user profile details
     * @param profile
     */
    public static void setProfile(Profile profile) {
        // TODO: the profile needs to be a git repo that gets pushed to the server
        File profileDir = new File(getRepositoryPath());
        if(profileDir.exists()) {
            FileUtilities.deleteRecursive(profileDir);
        }
        profileDir.mkdirs();

        File contactFile = new File(profileDir, "contact.json");
        File avatarFile = new File(profileDir, "avatar.png");

        // write contact details
        JSONObject contactJson = new JSONObject();
        try {
            contactJson.put("name", profile.getName());
            contactJson.put("email", profile.getEmail());
            contactJson.put("phone", profile.getPhone());

            FileUtils.write(contactFile, contactJson.toString());
        } catch (Exception e) {
            Logger.e(ProfileManager.class.getName(), "failed to write the profile contact details", e);
            return;
        }

        // TODO: write avatar

        // TODO: write signature, certificate

        mProfile = profile;
    }

    /**
     * Returns the local path to the repository
     * @return
     */
    public static String getRepositoryPath() {
        return AppContext.context().getFilesDir() + "/" + AppContext.PROFILES_DIR + "/profile/";
    }

    /**
     * Returns the remote repository url
     * @return
     */
    public static String getRemotePath() {
        String server = AppContext.context().getUserPreferences().getString(SettingsActivity.KEY_PREF_GIT_SERVER, AppContext.context().getResources().getString(R.string.pref_default_git_server));
        return server + ":tS/" + AppContext.udid() + "/profile";
    }

    /**
     * Pushes the profile to the server
     */
    @Deprecated
    public static void pushAsync() {
        if(getProfile() != null) {

            final String remotePath = getRemotePath();
            final Repo repo = new Repo(getRepositoryPath());

            // TODO: manually perform the commit
            CommitTask add = new CommitTask(repo, ".", new CommitTask.OnAddComplete() {
                @Override
                public void success() {
                    // TODO: manually perform the push
                    PushTask push = new PushTask(repo, remotePath, true, true, new GitSyncAsyncTask.AsyncTaskCallback() {
                        public boolean doInBackground(Void... params) {
                            return false;
                        }

                        public void onPreExecute() {
                        }

                        public void onProgressUpdate(String... progress) {
                        }

                        @Override
                        public void onPostExecute(Boolean isSuccess) {
                            if (!isSuccess) {
                                Logger.e(ProfileManager.class.getName(), "failed to push the profile to the server");
                            }
                        }
                    });
                    push.executeTask();
                }

                @Override
                public void error(Throwable e) {
                    Logger.e(ProfileManager.class.getName(), "failed to commit chnages to the profile repo", e);
                }
            });
            add.executeTask();
        }
    }

}
