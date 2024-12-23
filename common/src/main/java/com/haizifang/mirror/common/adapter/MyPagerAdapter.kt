package com.cash.finian.loan.okrd.ifectiv.common.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class MyPagerAdapter : PagerAdapter {

    private var mViews: List<View>

    constructor(mViews: List<View>) : super() {
        this.mViews = mViews
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }


    override fun getCount(): Int {
        return mViews.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(mViews[position])
        return mViews[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(mViews[position])
    }
}
