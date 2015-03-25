package de.geofroggerfx.model;

/**
 * Created by Andreas on 25.03.2015.
 */
public enum LogType {
    FOUND_IT("Found it"),
    WRITE_NOTE("Write note"),
    ATTENDED("Attended"),
    WILL_ATTEND("Will Attend"),
    OWNER_MAINTENANCE("Owner Maintenance"),
    DIDNT_FIND_IT("Didn't find it"),
    ENABLE_LISTING("Enable Listing"),
    PUBLISH_LISTING("Publish Listing"),
    Needs_Maintenance("Needs Maintenance"),
    TEMPORARILY_DISABLE_LISTING("Temporarily Disable Listing"),
    POST_REVIEWER_NOTE("Post Reviewer Note"),
    ARCHIVE("Archive"),
    ANNOUNCEMENT("Announcement"),
    NEEDS_ARCHIVED("Needs Archived"),
    UPDATE_COORDINATES("Update Coordinates"),
    WEBCAM_PHOTO_TAKEN("Webcam Photo Taken"),
    UNARCHIVE("Unarchive"),
    RETRACT_LISTING("Retract Listing");

    private String groundspeakString;

    private LogType(String groundspeakString) {
        this.groundspeakString = groundspeakString;
    }

    public String toGroundspeakString() {
        return groundspeakString;
    }

    public static LogType groundspeakStringToType(String groundspeakString) {
        for (LogType t : LogType.values()) {
            if (t.toGroundspeakString().equals(groundspeakString)) {
                return t;
            }
        }
        throw new IllegalArgumentException("unknown logtype:" + groundspeakString);
    }
}
