package com.delacrixmorgan.kingscup.common

import android.app.Activity
import android.app.Fragment
import android.transition.Slide
import android.view.Gravity
import com.delacrixmorgan.kingscup.R

/**
 * Created by Delacrix Morgan on 18/11/2017.
 */
open class BaseFragment : Fragment() {
    fun showAddFragmentSlideDown(activity: Activity, fragment: Fragment) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            fragment.enterTransition = Slide(Gravity.BOTTOM).setDuration(200)

            activity.fragmentManager
                    .beginTransaction()
                    .add(R.id.mainContainer, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(fragment.javaClass.simpleName)
                    .commit()
        }
    }
}