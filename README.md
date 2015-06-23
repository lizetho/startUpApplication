# startUpApplication
<h1>Introduction </h1>
This is a start up application for material design.
This application has a simple navigation drawer, a toolbar and two activities with their respective fragments.
All activities extend from an Abstract activity wich already has the navigation drawer.

<h1>How to change the style </h1>

<h2>Style</h2>

The material theme lets you easily customize the status bar, so you can specify a color that fits your brand and provides enough contrast to show the white status icons.

This application is compatible to previous version than Lolipop, furthermore it's using 3 kind of styles : v21, v19 and previous versions.

The difference is visible in the status bar. V21 extends from the material theme and by default, android:statusBarColor inherits the value of android:colorPrimaryDark. However, you cannot change this color for v19 neither previous versions, so the propossed solution turns transparent the statusBarColor for v19.

Customize these files as you want.

<h2>Colors</h2>

Material design's color palette comprises primary and accent colors working harmoniously with each other. This application has a color palette genereted from <a href="materialpalette.com/">materialpalette.com/</a> 

The generated file "colors_light_blue_pink.xml" was included in the application at res/values/.
If you want to modify the palette, replace this file with the new file created from the same website; otherwise you can replace the values inside of "colors_light_blue_pink.xml" with your custom values.

Check the colors correspondence here : <a href="http://developer.android.com/training/material/images/ThemeColors.png">http://developer.android.com/training/material/images/ThemeColors.png</a>





