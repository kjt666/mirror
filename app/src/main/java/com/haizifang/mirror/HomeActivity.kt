package com.haizifang.mirror

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.haizifang.mirror.databinding.ActivityMainBinding
import com.perfectcorp.perfectlib.MakeupCam

class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private var lastQuitTime = 0L
    private lateinit var makeupCam: MakeupCam

    companion object {
        const val TAG = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immerse()

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.apply {
            MakeupCam.create(cameraSurfaceView, object : MakeupCam.CreateCallback {
                override fun onSuccess(mc: MakeupCam) {
                    Log.d(TAG, "MakeupCam created.");
                    makeupCam = mc
                    var fragment =  LifeCycleFragment();
                    fragment.setMakeupCam(makeupCam);
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fl_contanier, fragment)
                        .commitAllowingStateLoss();
                }

                override fun onFailure(t: Throwable?) {
                    Log.e(TAG, "MakeupCam create failed.", t);
                }
            })
        }
    }

    class LifeCycleFragment : Fragment() {

        private lateinit var makeupCam: MakeupCam

        fun setMakeupCam(makeupCam: MakeupCam){
            this.makeupCam = makeupCam
        }

        override fun onStart() {
            super.onStart()
            makeupCam.onStarted()
        }

        override fun onResume() {
            super.onResume()
            makeupCam.onResumed()
        }

        override fun onPause() {
            makeupCam.onPaused()
            super.onPause()
        }

        override fun onStop() {
            makeupCam.onStopped()
            super.onStop()
        }

        override fun onDestroy() {
            makeupCam.onDestroyed()
            super.onDestroy()
        }

    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}