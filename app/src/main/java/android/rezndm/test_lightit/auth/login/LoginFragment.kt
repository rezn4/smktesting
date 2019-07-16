package android.rezndm.test_lightit.auth.login

import android.os.Bundle
import android.preference.PreferenceManager
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.auth.register.RegisterFragment
import android.rezndm.test_lightit.model.Const
import android.rezndm.test_lightit.model.LoginDataAnswer
import android.rezndm.test_lightit.model.Repository
import android.rezndm.test_lightit.products.ProductsFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: Fragment(), LoginView {

    companion object {
        val LOG_TAG: String = LoginFragment::class.java.name
    }

    private lateinit var loginPresenter: LoginPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginPresenter = LoginPresenterImpl(this,
            Repository(PreferenceManager.getDefaultSharedPreferences(activity))
        )

        setupLoginButton()
        setupSignUpButton()
        setupContinueWithoutRegBtn()

        val username = arguments?.getString(Const.BUNDLE_USERNAME_KEY, "")
        val password = arguments?.getString(Const.BUNDLE_PASSWORD_KEY, "")

        username_edt.setText(username)
        password_edt.setText(password)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun logIn(loginDataAnswer: LoginDataAnswer) {
        if (loginDataAnswer.success){
            loginPresenter.onTokenReceived(loginDataAnswer.token)
            val tx = activity?.supportFragmentManager?.beginTransaction()
            tx?.replace(R.id.container, ProductsFragment())?.commit()
            makeToast(getString(R.string.result_success_login))
        } else {
            makeToast(getString(R.string.result_not_success_login))
        }
    }

    private fun setupLoginButton(){
        button_login.setOnClickListener {
            val username: String = username_edt.text.toString()
            val password: String = password_edt.text.toString()

            if (username.isEmpty() || password.isEmpty()){
                makeToast(getString(R.string.warning_data_empty))
            } else {
                loginPresenter.requestLogin(username, password)
            }
        }
    }

    private fun setupSignUpButton(){
        btn_signup.setOnClickListener {
            launchFragment(RegisterFragment())
        }
    }

    private fun setupContinueWithoutRegBtn(){
        btn_continue_wo_reg.setOnClickListener {
            launchFragment(ProductsFragment())
        }
    }

    private fun launchFragment(fragment: Fragment){
        val tx = activity?.supportFragmentManager?.beginTransaction()
        tx?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit)
        tx?.replace(R.id.container, fragment)?.addToBackStack(null)?.commit()
    }

    private fun makeToast(text: String){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}