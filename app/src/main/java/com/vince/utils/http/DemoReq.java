package com.vince.utils.http;

/**
 *description:网络请求实例
 *author:vince
 */
public class DemoReq implements HttpCallback
{
    private ReqParam mParams;
    protected HandlerCallBack mHandlerCallBack;

    public DemoReq()
    {
    }

    public HandlerCallBack getmHandlerCallBack() {
        return mHandlerCallBack;
    }

    public void setmHandlerCallBack(HandlerCallBack mHandlerCallBack) {
        this.mHandlerCallBack = mHandlerCallBack;
    }

    public ReqParam getReqParam()
    {
        return mParams;
    }

    public void setReqParam(ReqParam params)
    {
        mParams = params;
    }

    public void clean()
    {

        if(mParams != null)
        {
            mParams.clean();
        }
    }

    private void doRequest()
    {
        final ReqParam req = new ReqParam(HttpConfig.GETBOOKLIST);
       /* req.addParam(HttpConfig.TITLE, mParams.get(HttpConfig.TITLE));
        req.addParam(HttpConfig.PACKAGENAME, mParams.get(HttpConfig.PACKAGENAME));*/

        HttpReq httpReq = new HttpReq(req){
            @Override
            protected Object processResponse(Object response) throws Exception
            {
               return null;
            }

            @Override
            protected Object requestOldResponse()
            {
                return null;
            }
        };
        httpReq.setCallBack(this);
        HttpService.getInstance().addImmediateReq(httpReq);
    }

    @Override
    public boolean onResult(String type, Object object)
    {
        if(object == null)
        {
            if(mHandlerCallBack != null)
            {
                mHandlerCallBack.onHandlerCallBack(HttpConfig.ERROR_NO_DATA, object);
            }
        }
        else
        {
            if(mHandlerCallBack != null)
            {
                mHandlerCallBack.onHandlerCallBack(type, object);
            }
        }
        return true;
    }
}
