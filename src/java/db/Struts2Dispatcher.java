package db;

import javax.servlet.*;
import org.apache.struts2.dispatcher.FilterDispatcher;
import org.hibernate.HibernateException;

public class Struts2Dispatcher extends FilterDispatcher
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        super.init(filterConfig);
        try
        {
            HibernateUtil.createSessionFactory();
            System.out.println("*** MYApplication initializing successfully ***");
        }
        catch (HibernateException e)
        {
            throw new ServletException(e);
        }
    }
}

