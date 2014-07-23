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

import ar.com.freesales.entity.changelog.UnitOfWork;


@Repository
public class UnitOfWorkDao {
	
	@Autowired
	@Qualifier("freesalesSessionFactory")
	private SessionFactory sessionFactory;
	
	public List<UnitOfWork> getLastUnitOfWorkByHotel(Long idHotel){
		Session session =  this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UnitOfWork.class);
		criteria.createAlias("userDetail", "ud").createAlias("ud.hotel", "udh")
		.addOrder(Order.desc("date"))
		.add(Restrictions.eq("udh.id", idHotel));;
		criteria.setMaxResults(3); //TODO PARAMETRIZAR
		return criteria.list();
	}

}
