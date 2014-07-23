package ar.com.freesales.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**+
 * Implementacion de un dao generico
 * @author chino
 *
 */
@Repository(value="genericDao")
@SuppressWarnings("unchecked")
public class GenericDao{
	
	@Autowired
	@Qualifier("freesalesSessionFactory")
	private SessionFactory sessionFactory;

	public void delete(Object entidad) {
		this.sessionFactory.getCurrentSession().delete(entidad);
	}

	public Serializable save(Object entidad) {
		return this.sessionFactory.getCurrentSession().save(entidad);
		
	}
	public void update(Object entidad) {
		this.sessionFactory.getCurrentSession().update(entidad);
	}

	public <T> T get(Class<T> clazz, Long id) {
		return (T)this.sessionFactory.getCurrentSession().get(clazz, id);
	}

	public <T> List<T> getAll(Class<T> clazz) {
		Criteria createCriteria = this.sessionFactory.getCurrentSession().createCriteria(clazz);
		return createCriteria.list();
	}
}
