package com.buyi.huxq17

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AgencyPlugin implements Plugin<Project> {

    void apply(Project project) {
        if (project.plugins.hasPlugin(AppPlugin)) {
            def android = project.extensions.getByType(AppExtension)
            def agencyTransform = new AgencyTransform(project)
            android.registerTransform(agencyTransform)
        }
    }
}