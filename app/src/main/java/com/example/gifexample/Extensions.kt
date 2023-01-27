package com.example.gifexample

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.res.Resources
import android.view.View
import android.view.animation.LinearInterpolator


object Extensions {

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun View.visible(duration: Long = 200L) {
        animate().alpha(1f).setDuration(duration)
            .setListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }).interpolator = LinearInterpolator()
    }

    fun View.gone(offset: Long = 0L, duration: Long = 0L) {
        animate().alpha(0f).setStartDelay(offset).setDuration(duration)
            .setListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
    }

    fun View.shake() {
        val rotate = ObjectAnimator.ofFloat(this,
            "rotation", 0f, 10f, 0f, -10f, 0f)
        rotate.repeatCount = 18
        rotate.duration = 50
        rotate.start()
    }

    fun View.rotate() {
        val rotate = ObjectAnimator.ofFloat(this,
            "rotation", 0f, 360f)
        rotate.duration = 200
        rotate.start()
    }

    fun View.pulse() {
        val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(this,
            PropertyValuesHolder.ofFloat("scaleX", 1.3f),
            PropertyValuesHolder.ofFloat("scaleY", 1.3f))
        scaleDown.duration = 300
        scaleDown.repeatCount = ObjectAnimator.RESTART
        scaleDown.repeatMode = ObjectAnimator.REVERSE
        scaleDown.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) = rotate()
            override fun onAnimationEnd(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        scaleDown.start()
    }
}