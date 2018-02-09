package com.buyi.huxq17

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AgencyPlugin implements Plugin<Project> {

    void apply(Project project) {
        //AppExtension就是build.gradle中android{...}这一块
        def android = project.extensions.getByType(AppExtension)
        def agencyTransform = new AgencyTransform(project)
        android.registerTransform(agencyTransform)
    }
}