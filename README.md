Replace the path with your folder path.

```gradle
gradle.ext.exoplayerRoot = 'C:/Users/webdev1/Documents/DevSudheer/samples/Lib/ExoPlayer'
gradle.ext.exoplayerModulePrefix = 'exoplayer-'
apply from: new File(gradle.ext.exoplayerRoot, 'core_settings.gradle')
```