package com.szzh.audio.newviewpager.indicator;

/**
 * Created by jzz
 * on 2017/4/4.
 *
 * desc:
 */

public class AccountAction {

    public static void login(String tel, String pwd, AccountContract.View view) {

        //开始进行网络请求
        if (!tel.equals("123")) {
            view.showAccountResponseError("请求失败");
        } else {
            view.showLoginSuccess();
        }

    }
}
