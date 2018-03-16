package com.buyi.huxq17

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class AgencyPlugin implements Plugin<Project> {
    void apply(Project project) {
        if (project.plugins.hasPlugin(AppPlugin)) {
            registerTransform(project, project.extensions.getByType(AppExtension))
        }
    }

    void registerTransform(Project project, BaseExtension extension) {
        def agencyTransform = new AgencyTransform(project)
        extension.registerTransform(agencyTransform)
    }
}