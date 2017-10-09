//package com.minlia.module.tenant.hibernate.filter;
//
//import com.minlia.module.tenant.hibernate.TenantThreadLocal;
//import java.io.IOException;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import org.springframework.web.filter.GenericFilterBean;
//
//public class TenantFilter extends GenericFilterBean
//{
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//        throws IOException, ServletException
//    {
//        chain.doFilter(new TenantRequest((HttpServletRequest) request), response);
//    }
//
//    public static class TenantRequest extends HttpServletRequestWrapper
//    {
//        private String servletPath;
//
//        public TenantRequest(HttpServletRequest request)
//        {
//            super(request);
//
//            this.servletPath = request.getServletPath();
//
//            int indexOfSlash = servletPath.indexOf('/', 1);
//            if (indexOfSlash > 0)
//            {
//                String tenantCode = servletPath.substring(1, indexOfSlash);
//                try
//                {
//                    TenantThreadLocal.setTenant(tenantCode);
//                    this.servletPath = this.servletPath.substring(tenantCode.length() + 1);
//                }
//                catch (Exception e)
//                {
//                    throw new IllegalArgumentException(
//                        "No country in request - please prefix each request with country code");
//                }
//            }
//        }
//
//        @Override
//        public String getServletPath()
//        {
//            return servletPath;
//        }
//    }
//}
