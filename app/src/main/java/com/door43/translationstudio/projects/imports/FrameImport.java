package com.door43.translationstudio.projects.imports;

/**
 * This manages importing a frame
 */
@Deprecated
public class FrameImport extends ImportRequest {
    public final String frameId;
    public final String frameTitle;

    public FrameImport(String frameId, String frameTitle) {
        this.frameId = frameId;
        this.frameTitle = frameTitle;
    }

    @Override
    public String getId() {
        return frameId;
    }

    @Override
    public String getTitle() {
        return frameTitle;
    }
}
