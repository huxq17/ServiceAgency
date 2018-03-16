# ServiceAgency


这是一个解耦的工具，将接口和实现完全分离，只需要在实现类上添加ServiceAgent注解，ServiceAgency会自动将实现和接口关联，开发者在使用的时候不需要传入实现，具体看下面。
## Config build.gradle：
在项目根目录build.gradle中：
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.buyi.huxq17:agencyplugin:1.0.0'
    }
}
```
app moudle中：

```
apply plugin: 'service_agency'
dependencies {
    compile 'com.buyi.huxq17:serviceagency:1.0.0'
}
```
## Example:
### Define Interface and its Implement

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
   ImageLoader imageLoader = ServiceAgency.getService(ImageLoader.class);
   imageLoader.loadImage(imageview, view);

   //Release memory if need.
   ServiceAgency.clear();
```