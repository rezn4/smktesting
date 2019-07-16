package android.rezndm.test_lightit.auth.register

import android.os.Bundle
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.auth.login.LoginFragment
import android.rezndm.test_lightit.model.Const
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_signup.*



class RegisterFragment: Fragment(), RegisterView {

    private val registerPresenter = RegisterPresenterImpl(this)

    override fun handleRegistrationResult(success: Boolean, username: String, password: String) {
        if (success){
            clearFragmentsBackstack()
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            val loginFragment = LoginFragment()
            val args = Bundle()
            args.putString(Const.BUNDLE_USERNAME_KEY, username)
            args.putString(Const.BUNDLE_PASSWORD_KEY, password)
            loginFragment.arguments = args
            fragmentTransaction?.replace(R.id.container, loginFragment)?.commit()
        } else {
            makeToast(getString(R.string.warning_reg_error))
        }
    }

    private fun clearFragmentsBackstack() {
        val fm = activity?.supportFragmentManager

        val entry = fm?.getBackStackEntryAt(0)

        if (entry != null) {
            fm.popBackStack(
                entry.id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        fm?.executePendingTransactions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRegisterButton()
    }

    private fun setupRegisterButton(){
        reg_register_btn.setOnClickListener {
            val username = reg_username_edt.text.toString()
            val password = reg_password_edt.text.toString()
            val confirmPassword = reg_confirm_edt.text.toString()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                makeToast(getString(R.string.warning_data_empty))
            } else if (password == confirmPassword) {
                registerPresenter.requestRegister(username, password)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    private fun makeToast(text: String){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}