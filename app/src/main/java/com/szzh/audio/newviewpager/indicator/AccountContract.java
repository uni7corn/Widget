package com.szzh.audio.newviewpager.indicator;

/**
 * Created by jzz
 * on 2017/4/4.
 *
 * desc:
 */

public interface AccountContract {

    interface View extends BaseView<Presenter> {

        void showCheckAccountInfoError(String error);

        void showAccountResponseError(String error);

        void showLoginLoading();

        void showLoginSuccess();

        void showCallSmsCodeSuccess();

        void showCallSmsCodeLoading();

        void showRegisterSuccess();

    }

    interface Presenter extends BasePresenter {

        void login(String tel, String pwd);

        void callSmsCode(String tel);

        void register(String tel, String smsCode, String pwd, boolean isAgree);

    }
}
