package bg.softuni.aquagate.web.interceptor;

import bg.softuni.aquagate.web.interceptor.annotation.PageTitle;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

@Component
public class TitleInterceptor extends WebRequestHandlerInterceptorAdapter {
    public TitleInterceptor(WebRequestInterceptor requestInterceptor) {
        super(requestInterceptor);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        String title = "AquaGate";

        if (modelAndView == null) {
            modelAndView = new ModelAndView();
            modelAndView.addObject("title");
        } else {
            if (handler instanceof HandlerMethod) {
                PageTitle methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(PageTitle.class);

                if (methodAnnotation != null) {
                    modelAndView
                            .addObject("title", title + " - " + methodAnnotation.value());
                }
            }
        }
    }
}

