package priv.zxy.moonstep.login.module.biz;

import priv.zxy.moonstep.login.module.bean.ErrorCode;

/**
 *  Created by Zxy on 2018/9/20
 */

public interface OnRegisterListener {

    public void registerSuccess();

    public void registerFail(ErrorCode errorCode);
}
