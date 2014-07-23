package ar.com.freesales.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ar.com.freesales.entity.user.UserDetail;
import ar.com.freesales.exception.UserNotFoundException;

/**+
 * @author gattex
 *
 */
@Repository(value="userDetailDao")
public class UserDetailDao{
	
	@Autowired
	@Qualifier("freesalesSessionFactory")
	private SessionFactory sessionFactory;
	
	public UserDetail getUserByUserName(String name) throws UserNotFoundException{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserDetail.class);
		criteria.add(Restrictions.eq("userName", name));
		@SuppressWarnings("unchecked")
		List<UserDetail> list = criteria.list();
		if (list == null || list.size() == 0){
			throw new UserNotFoundException();
		}
		return list.get(0);
	}

}
