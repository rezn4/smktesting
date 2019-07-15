package android.rezndm.test_lightit.auth.login

import android.os.Bundle
import android.rezndm.test_lightit.R
import android.rezndm.test_lightit.auth.register.RegisterFragment
import android.rezndm.test_lightit.products.ProductsFragment
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

    private val loginPresenter: LoginPresenter = LoginPresenterImpl(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginButton()
        setupSignUpButton()
        setupContinueWithoutRegBtn()

        val username = arguments?.getString(RegisterFragment.BUNDLE_USERNAME, "")
        val password = arguments?.getString(RegisterFragment.BUNDLE_PASSWORD, "")
        username_edt.setText(username)
        password_edt.setText(password)
    }

    private fun setupLoginButton(){
        button_login.setOnClickListener {
            val username: String = username_edt.text.toString()
            val password: String = password_edt.text.toString()

            if (username.isNotEmpty()){
                if (password.isNotEmpty()){
                    loginPresenter.requestLogin(username, password)
                } else {
                    makeToast(getString(R.string.warning_password_empty))
                }
            } else if (username.isEmpty() && password.isEmpty()){
                makeToast(getString(R.string.warning_data_empty))
            } else {
                makeToast(getString(R.string.warning_username_empty))
            }
        }
    }

    private fun setupSignUpButton(){
        btn_signup.setOnClickListener {
            val tx = activity?.supportFragmentManager?.beginTransaction()
            tx?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit)
            tx?.replace(R.id.container, RegisterFragment())?.addToBackStack(null)?.commit()
        }
    }

    private fun setupContinueWithoutRegBtn(){
        btn_continue_wo_reg.setOnClickListener {
            val tx = activity?.supportFragmentManager?.beginTransaction()
            tx?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit)
            tx?.replace(R.id.container, ProductsFragment())?.addToBackStack(null)?.commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun logIn(success: Boolean) {
        if (success){
            makeToast(getString(R.string.result_success_login))
        } else {
            makeToast(getString(R.string.result_not_success_login))
        }
    }

    private fun makeToast(text: String){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }


}