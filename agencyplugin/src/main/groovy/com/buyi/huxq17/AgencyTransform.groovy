package com.buyi.huxq17

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile

class AgencyTransform extends Transform {
    private Project mProject
    private ServiceConfig serviceConfig
    private File sourceDir
    File classOutputDir

    File getSourceDir() {
        return sourceDir
    }

    AgencyTransform(Project project) {
        mProject = project
    }

    @Override
    String getName() {
        return "AgencyTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        Context context = transformInvocation.getContext()
        initServiceConfig(context.variantName)
        Collection<TransformInput> inputs = transformInvocation.getInputs()
//        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs()
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider()
//        classOutputDir = outputProvider.getContentLocation("serviceagency", getOutputTypes(), getScopes(), Format.DIRECTORY)

        inputs.each { TransformInput input ->
            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                jarInput.status
                loadJar(jarInput.file)
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
            input.directoryInputs.each { DirectoryInput directoryInput ->
                Map<File, Status> changedFiles = directoryInput.changedFiles
                if (changedFiles == null || changedFiles.isEmpty()) {
                    loadDirectory(directoryInput.file.absolutePath)
                } else {
                    loadChangeFiles(changedFiles)
                }

                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                classOutputDir = dest
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
        serviceConfig.commit()
    }

    void initServiceConfig(String variantName) {
        serviceConfig = new ServiceConfig(mProject, this)
//        System.out.println("android path=" + project.android.bootClasspath[0].toString())
        mProject.android.applicationVariants.all { variant ->
            if (variant.name.contains(variantName)) {
                sourceDir = variant.getVariantData().getScope().getBuildConfigSourceOutputDir()
                println "project.buildDir=" + mProject.buildDir.toString() + "\\intermediates\\classes\\debug"
//                    variant.getCompileClasspath().each { fileDependency ->
//                        if (fileDependency.path.contains("support-annotations")) {
//                            pool.insertClassPath(fileDependency.path)
//                            println "test " + fileDependency.path
//                        }
//                    }
            }
        }
    }

    void loadChangeFiles(Map<File, Status> changedFiles) {
        changedFiles.keySet().each { file ->
            serviceConfig.update(file)
        }
    }

    void loadDirectory(String path) {
        File dir = new File(path)
        serviceConfig.addClassPath(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                loadJarOrClass(file)
            }
        } else {
            loadJarOrClass(dir)
        }
    }

    void loadJarOrClass(File file) {
        String fileName = file.name
        if (isArtifactClass(fileName)) {
            serviceConfig.update(fileName, new FileInputStream(file))
        } else if (fileName.endsWith(".jar")) {
            loadJar(file)
        }
    }

    void loadJar(File jar) {
        serviceConfig.addClassPath(jar.absolutePath)
        JarFile jarFile = new JarFile(jar)
        Enumeration<JarEntry> entries = jarFile.entries()
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement()
            String fileName = entry.name
            if (isArtifactClass(fileName)) {
                InputStream jarInputStream = jarFile.getInputStream(entry)
                serviceConfig.update(fileName, jarInputStream)
            }
        }
    }

    boolean isArtifactClass(String fileName) {
        return fileName.endsWith(".class") &&
                !fileName.contains('R$') &&
                !fileName.contains('R.class') &&
                !fileName.contains("BuildConfig.class")
    }
}
