package lee.study.down.intercept;

import io.netty.handler.codec.http.HttpResponse;
import lee.study.down.intercept.common.ResponseTextIntercept;
import lee.study.down.util.ByteUtil;
import lee.study.down.util.HttpDownUtil;
import lee.study.proxyee.intercept.HttpProxyInterceptPipeline;

/**
 * 破解百度云PC浏览器版大文件下载限制
 */
public class BdyIntercept extends ResponseTextIntercept {

  private static final String hookJs = ByteUtil
      .readJsContent(Thread.currentThread().getContextClassLoader()
          .getResourceAsStream("hookjs/bdyHook.js"));

  @Override
  public boolean match(HttpResponse httpResponse, HttpProxyInterceptPipeline pipeline) {
    return HttpDownUtil.checkUrl(pipeline.getHttpRequest(), "^(pan|yun).baidu.com.*$")
        && isHtml(httpResponse, pipeline);
  }

  @Override
  public String hookResponse() {
    return hookJs;
  }

}
