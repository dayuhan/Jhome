package com.bracket.common.register;

/**
 * 授权标识
 *
 * @author Daxv 2017-04-28
 */
public abstract class RegAPI {

    private boolean _isVerifyFlag = false;

    public void setVerifyFlag(boolean isVerifyFlag) {
        this.setProductCodeWithCache(isVerifyFlag);
    }

    public boolean getVerifyFlag() {
        _isVerifyFlag = this.getProductCodeWithCache();
        return _isVerifyFlag;
    }

    protected abstract boolean getProductCodeWithCache();

    protected abstract void setProductCodeWithCache(boolean isVerifyFlag);
}
