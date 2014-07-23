package ar.com.freesales.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ar.com.freesales.entity.changelog.DateBatch;

/**
 * Implementacion de un dao generico
 * @author chino
 *
 */
@Repository(value="changeLogDao")
@SuppressWarnings("unchecked")
public class ChangeLogDao{
	
	@Autowired
	@Qualifier("freesalesSessionFactory")
	private SessionFactory sessionFactory;

	public List<DateBatch> getDatesByBatch(Long idLote) {
		Session session =  this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(DateBatch.class);
		criteria.add(Restrictions.eq("idDateBatch", idLote));
		return criteria.list();
	}
	
	public List<DateBatch> getOrderDatesByBatch(Long idLote) {
		Session session =  this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(DateBatch.class);
		criteria.add(Restrictions.eq("idDateBatch", idLote));
		criteria.addOrder(Order.asc("date"));
		return criteria.list();
	}
	
}
