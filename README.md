# Multi Images View

Multi images view is a library to make a good looking group of images ( album ) with see more functionality  , It also has powerfull and generic image slider .

  - Circle or Normal images view in the main images view .
  - Get the number of images in the view according to view size or with the number
    you want .
  - Pretty Slider view 

# Installation

  - ###### Step 1. Add the JitPack repository to your build file
  ```
    allprojects {
		repositories {
		    ...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  - ###### Step 2. Add the dependency to your gradle file 
```
	dependencies {
	        implementation 'com.github.Tripl3Dev:Multi-images-view:1.0'
	}
```

# Usage
#### In your Activity 
 1-  Get your view instance 
 ```kotlin 
	var imagesView =  findViewById<MultibleImageView>(R.id.multibleImage)
```
2- Intialize the view config 

```kotlin 
 imagesView.intialize()
 ```
 3- Set images list , you can set two types :
 - List of strings . 
 - List of any object implements ImageModelI .

 #### In Xml 
 ##### First Multi Images main view 
<img src="https://raw.githubusercontent.com/Tripl3Dev/Multi-images-view/master/screenshot1.png" width="400">

| Attribute | Usage | Types |
| ------ | ------ | ------ |
|app:mi_imagesType |  Choose your image types in MultiImageView | circleImage , normalImage |
|app:mi_spaceBetweenImages|Space between the images |Dimension|
|app:mi_circleImageBorderColor|circle image border color |Color|
|app:mi_circleImageBorderWidth|circle image border width|Dimension|
|app:mi_imageWidth|each image width|Dimension|
|app:mi_maxImagesNum|max images will be created in the multi images view |Integer|
|app:mi_finalImageTextSize|Final image overlay text size|Dimension|
|app:mi_textColor|Final image overlay text color |color|
|app:mi_finalImageOverlayColor|Final image overLay color|Color|
|app:mi_scaleType|image scale type|MATRIX,FIT_XY , ..etc|

##### Second Slider view

<img src="https://raw.githubusercontent.com/Tripl3Dev/Multi-images-view/master/SliderView.jpg" width=“400” height=“450”>

- Features Attributes
 
| Attribute | Usage | Types |
| ------ | ------ | ------ |
|app:mi_sliderTransitionAnimation|change images animation transition in the slider |alpha ,cube & Rotate|
|app:mi_sliderOrientationJustPortrait|to make the Slider activity orientation just portrait|Boolean|
|app:mi_imagesListOrientation|Slider images list orientation|HORIZONTAL  ,VERTICAL|

- Custom  layouts you can change in the slider
>You should use that Mandatory views in each layout with the same "Id" provided

| Attribute | Usage | Mandatory views @+id/ : Type  |
| ------ | ------ | ------ |
|app:mi_customSliderActivityView|change the whole Slider activity| 1-  closeButton:ImageView  2-multibleImageSlider:ViewPager 3-multibleImageList:RecyclerView|
|app:mi_customSlideViewWithDesc|change the image with description view|1-imageDescView:TextView 2-imageSlideView:ImageView 3-imageProgress(optional):ProgressBar|
|app:mi_sliderImagesListSelectedView app:mi_sliderImagesListUnSelectedView|change the slider images list view selected or unselected |1-slideViewItemImage:ImageView 2-imageProgress(optional):progressBar|
> In mi_customSlideViewWithDesc layout it's better to use TouchImageView instead of
>ImageView for @+id/imageSlideView to get the features of Zooming , ... 

# Credits
* [Dodenhof CircleImageView](https://github.com/hdodenhof/CircleImageView)
* [Miaoyongjun PageTransformer](https://github.com/miaoyongjun/PageTransformer)
* [ Mike Ortiz TouchImageView](https://github.com/MikeOrtiz/TouchImageView)

