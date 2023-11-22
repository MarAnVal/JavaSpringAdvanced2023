package bg.softuni.aquagate.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

@Component
public class FaviconInterceptor extends WebRequestHandlerInterceptorAdapter {
    @Autowired
    public FaviconInterceptor(WebRequestInterceptor requestInterceptor) {
        super(requestInterceptor);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {

        String favicon = "/images/favicon.png";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", favicon);
        }
    }
}
