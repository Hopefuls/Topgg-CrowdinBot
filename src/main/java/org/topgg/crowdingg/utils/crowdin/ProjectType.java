package org.topgg.crowdingg.utils.crowdin;

public enum ProjectType {
    BACKEND("456000"),
    WEB("450156");

    public final String projectId;

    ProjectType(String projectId) {
        this.projectId = projectId;
    }
}
