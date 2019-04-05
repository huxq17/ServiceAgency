# ServiceAgency
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-ServiceAgency-green.svg?style=flat )]( https://android-arsenal.com/details/1/6853 )
## Introduction

这是一个将接口和实现完全分离的gradle插件,适用于在使用第三方框架时进行解耦的场景。只需要在实现类上添加ServiceAgent注解，ServiceAgency会在项目构建阶段自动将实现和接口关联，开发者在使用的时候不需要传入实现，可以通过把ServiceAgent注解添加到另一个实现类上来达到快速替换实现的目的，具体看下面。

This is a gradle plugin that completely separates the interface and its implementation,suitable for decoupling when using third-party frameworks.
Use by adding ServiceAgent annotation on interface's implementation class. ServiceAgency  will auto associate interface and its implementation during the build phase,so we can use interface without its implementation completely.The usage is as follows. 
## Config build.gradle：
Root project build.gradle：
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.buyi.huxq17:agencyplugin:1.2.0'
    }
}
```
App module build.gradle：

```
apply plugin: 'service_agency'
```
## Example:
### Define interface and its implementation

Interface：

```
public interface ImageLoader {
    void loadImage(String url, ImageView imageview);
}
```

Implements and add ServiceAgent annotation:
```
@ServiceAgent
public class ImageLoaderImpl implements ImageLoader {

    @Override
    public void loadImage(String url, ImageView imageview) {
        Picasso.with(WXEnvironment.getApplication()).load(url).into(imageview);
    }

}
```

### Usage：

```
   ImageLoader imageLoader = ServiceAgency.getService(this,ImageLoader.class);
          //getServiceWithoutCache will not cache service's instance.
          //ImageLoader imageLoader = ServiceAgency.getServiceWithoutCache(this,ImageLoader.class);
          imageLoader.test("MainActivity onCreate");
          //OR
          INetworkClient networkClient = ServiceAgency.getService(INetworkClient.class);
          networkClient.get();
          //Release memory if need.
          ServiceAgency.clear();
```

## Change Logs<br/>
    2019-4-5：
    1.Add getService(Context, Class<T>) and getServiceWithoutCache(Context, Class<T>) method,release v1.2.0.
    2019-4-4：
    1.Fix bugs,release v1.1.3.
    2018-11-16：
    1.Fix bugs,release v1.1.2.
    2018-9-30：
    1.Support Instant Run and release v1.1.0.

## LICENSE

[Apache License 2.0](LICENSE)
