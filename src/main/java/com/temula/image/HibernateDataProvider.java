package com.temula.image;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateDataProvider {

	private static ServiceRegistry serviceRegistry;
	private static SessionFactory sessionFactory=null;//configureSessionFactory();
	static final Logger logger = Logger.getLogger(ImageResource.class.getName());

	static{
		configureSessionFactory();
	}

	
	public static void main(String args[]){
		System.out.println("Starting hibernate!");
	}
	
	private static SessionFactory configureSessionFactory() throws HibernateException {
	    Configuration configuration = new Configuration();
	    configuration.configure();
	    serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    return sessionFactory;
	}

    private static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

	

 	public List<Image> get(Image image) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        
        Integer imageId = new Integer(image.getImageId());
        List<Image>list=null;
        if(imageId>0){
            Criteria criteria = session.createCriteria(image.getClass());
            criteria.add(Restrictions.eq("imageId", imageId));
        	list = criteria.list();
        }
        else{
        	Query q = session.createQuery("select imageId,imageName from Image");
        	List objlist= q.list();
        	list = new ArrayList<Image>();
        	for(int i=0;i<objlist.size();i++){
        		Image image2 = new Image();
        		Object[]arr = (Object[])objlist.get(i);
        		image2.setImageId((Integer)arr[0]);
        		image2.setImageName((String)arr[1]);
        		list.add(image2);
        	}
        }
 	        
        tx1.commit();
        session.close();
        return list;
	}

	public Status put( Image t) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(t);
        tx1.commit();
        session.close();
        return Status.CREATED;
	}

	public Status post(List<Image> list) {
        Session session = getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        for ( int i=0; i<list.size(); i++ ) {
            session.save(list.get(i));
            if ( i % 20 == 0 ) { //20, same as the JDBC batch size
                //flush a batch of inserts and release memory:
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        session.close();
        return Status.CREATED;
	}

	public Status delete(Image t) {
        Session session = getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(t);
        tx1.commit();
        session.close();
        return Status.OK;
	}

}
