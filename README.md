# LikeViewDemo

自定义控件 | 仿《最美有物》点赞效果 来自http://www.jianshu.com/p/818116513dc3

# 技术点
---
采用shape作为LinearLayout的背景，它是一个圆角矩形

<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">

    <corners android:radius="100dp" />
    <solid android:color="@color/colorYellow" />
    <padding
        android:bottom="5dp"
        android:left="5dp"
        android:right="5dp"
        android:top="5dp" />
</shape>

当LinearLayout宽高相等且radius大于宽/高的一半，shape就变成一个圆形。

LinearLayout里面主要的控件是那个笑脸（其他还有文字与分割线的）。将LinearLayout设为wrap_content，表示适应子view的大小。此时增加笑脸ImageView的margin_bottom，LinearLayout为了适应子View的大小就会拉伸，此时LinearLayout的背景随着拉伸变成了圆角矩形，上下是两个半圆。配合帧动画，文本的透明动画等，就可以实现点赞的动画了。

值得注意的是，ImageView增加margin_bottom时，LinearLayout必须底部固定，向上拉伸，需要配合Gravity.BOTTOM属性，实现右下角固定，不然默认就是左上角固定LinearLayout向下拉伸。

动态添加View, addView（view, params) 将params设为wrap_content,表示被添加的view是wrap_content，不表示自己以wrap_content包含该view。所以动态添加LinearLayout的时候，如果要将LinearLayout设为wrap_content，需要设置LinearLayout上一层的params。








