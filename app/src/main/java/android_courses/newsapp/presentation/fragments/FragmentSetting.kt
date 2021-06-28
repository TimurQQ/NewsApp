package android_courses.newsapp.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android_courses.newsapp.R
import android_courses.newsapp.Utill.Constants.Companion.APP_PREFERENCES
import android_courses.newsapp.Utill.Constants.Companion.IS_REGISTERED
import android_courses.newsapp.Utill.SaveData
import android_courses.newsapp.base.BaseActivity
import android_courses.newsapp.base.RestartInterface
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.auth.FirebaseAuth

class FragmentSetting : Fragment() {
    private val saveData:SaveData by lazy {
        SaveData(requireContext())
    }
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val mSettings: SharedPreferences by lazy {
        requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }
    private  var switchCompat: SwitchCompat? = null
    private lateinit var backButton: AppCompatImageButton
    private lateinit var buttonLogout: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchCompat=view.findViewById(R.id.on_off)
        switchCompat?.isChecked = saveData.loadLightModeState()
        switchCompat?.setOnCheckedChangeListener {_, isChecked ->
            saveData.setLightModeState(isChecked)
            (activity as? RestartInterface)?.restartApplication()
        }

        buttonLogout = view.findViewById(R.id.button_log_out)
        backButton = view.findViewById(R.id.jump)
        buttonLogout.setOnClickListener {
            mAuth.signOut()
            with (mSettings.edit()) {
                putBoolean(IS_REGISTERED, false)
                apply()
            }
            (requireActivity() as BaseActivity).fragmentRouter.openSplashFragment()
        }
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}