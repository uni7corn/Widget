package com.szzh.audio.newviewpager.indicator;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.szzh.audio.newviewpager.annotation.NetAnnotation;

/**
 * Created by jzz
 * on 2017/4/4.
 * <p>
 * desc:
 */

public class AccountPresenter implements AccountContract.Presenter {


    private AccountContract.View mView;

    public static AccountPresenter init(AccountContract.View view) {
        return new AccountPresenter(view);
    }


    private AccountPresenter(AccountContract.View view) {
        view.setPresenter(this);
        this.mView = view;
    }

    @Override
    public void start() {

    }

    @NetAnnotation
    @Override
    public void login(String tel, String pwd) {

        AccountContract.View view = checkViewIsNull();

        if (checkTel(tel, view)) return;

        checkPwd(pwd, view);

        //发出登录请求
        AccountAction.login(tel, pwd, view);

    }

    @Override
    public void callSmsCode(String tel) {
        AccountContract.View view = checkViewIsNull();
        checkTel(tel, view);

        //发起获取验证码请求
    }

    @Override
    public void register(String tel, String smsCode, String pwd, boolean isAgree) {
        AccountContract.View view = checkViewIsNull();
        checkTel(tel, view);
        if (TextUtils.isEmpty(smsCode)) {
            view.showCheckAccountInfoError("验证码不能为空");
            return;
        }
        checkPwd(pwd, view);
        if (isAgree) {
            //发起注册请求
        } else {
            view.showCheckAccountInfoError("请同意相关条约...");
        }
    }

    private boolean checkTel(String tel, AccountContract.View view) {
        if (TextUtils.isEmpty(tel)) {
            view.showCheckAccountInfoError("手机号码不能为空！！！");
            return true;
        }

        if (!StringUtils.machPhoneNum(tel)) {
            view.showCheckAccountInfoError("手机号码格式不正确，请输入正确的手机号码");
            return true;
        }
        return false;
    }

    @NonNull
    private AccountContract.View checkViewIsNull() {
        AccountContract.View view = this.mView;
        if (view == null)
            throw new NullPointerException("view is null");
        return view;
    }

    private void checkPwd(String pwd, AccountContract.View view) {
        if (TextUtils.isEmpty(pwd)) {
            view.showCheckAccountInfoError("密码不能为空");
            return;
        }
    }
}
