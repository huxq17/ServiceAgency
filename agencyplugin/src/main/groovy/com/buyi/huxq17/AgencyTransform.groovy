package com.buyi.huxq17

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

public class AgencyTransform extends Transform {
    private Project mProject
    private ServiceConfig serviceConfig
    private File sourceDir

    File getSourceDir() {
        return sourceDir
    }

    public AgencyTransform(Project project) {
        mProject = project
    }

    @Override
    public String getName() {
        return "AgencyTransform"
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    public Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    public boolean isIncremental() {
        return false
    }
    String classOutputDir

    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        Context context = transformInvocation.getContext()
        initServiceConfig(context.variantName)
        Collection<TransformInput> inputs = transformInvocation.getInputs()
        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs()
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider()
        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                classOutputDir = directoryInput.file.absolutePath
                println "classOutputDir=$classOutputDir"
                updateServiceConfig(classOutputDir)
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)

                FileUtils.copyDirectory(directoryInput.file, dest)
            }
            input.jarInputs.each { JarInput jarInput ->
                //jar文件一般是第三方依赖库jar文件
                // 重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
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

    void updateServiceConfig(String path) {
        File dir = new File(path)
        serviceConfig.addClassPath(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                if (filePath.endsWith(".class")
                        && !filePath.contains('R$')
                        && !filePath.contains('R.class')
                        && !filePath.contains("BuildConfig.class")) {
                    serviceConfig.update(filePath)
                }
            }
        } else {
            serviceConfig.update(path)
        }
    }
}
