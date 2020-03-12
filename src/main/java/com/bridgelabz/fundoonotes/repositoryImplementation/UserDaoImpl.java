/**
 * By using hibernate processing the data to database
 */


//package com.bridgelabz.fundoonotes.repositoryImplementation;
///**
// * @author shaik shaiksha vali
// * 
// * */
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.hibernate.query.Query;
//import org.hibernate.Session;
//import org.springframework.stereotype.Repository;
//
//import com.bridgelabz.fundoonotes.dto.UserPasswordUpdateDetails;
//import com.bridgelabz.fundoonotes.model.UserInformation;
//import com.bridgelabz.fundoonotes.repository.UserDao;
//
//@Repository
//public class UserDaoImpl implements UserDao {
//
//	@PersistenceContext
//	private EntityManager entityManger;
//
//	/* Query for save the data into userDatabase */
//	@Override
//	public UserInformation save(UserInformation information) {
//		Session currentSession = entityManger.unwrap(Session.class);
//		currentSession.saveOrUpdate(information);
//		return information;
//	}
//
//	/* Query to find the user by id */
//	@Override
//	public UserInformation findUserById(Long id) {
//		Session currentSession = entityManger.unwrap(Session.class);
//		Query<UserInformation> q = currentSession.createQuery("FROM UserInformation where id=:id");
//		q.setParameter("id", id);
//		return (UserInformation) q.uniqueResult();
//	}
//
//	/* Query to get the user information by email */
//	@Override
//	public UserInformation getUser(String email) {
//		Session currentSession = entityManger.unwrap(Session.class);
//		Query<UserInformation> q = currentSession.createQuery("FROM UserInformation where email=:email");
//		q.setParameter("email", email);
//
//		return (UserInformation) q.uniqueResult();
//	}
//
//	/* Query to update the PasswordInformation */
//	@Override
//	public boolean update(UserPasswordUpdateDetails information, Long id) {
//		Session currentSession = entityManger.unwrap(Session.class);
//		Query<UserInformation> q = currentSession.createQuery("update UserInformation set password=:p" + " " + " where id=:id");
//		q.setParameter(" p", information.getPassword());
//		q.setParameter("id", id);
//		int status = q.executeUpdate();
//		if (status > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/*
//	 * Query to update the verify to TRUE once the token taken from the
//	 * RegisteredUser
//	 */
//	@Override
//	public boolean verify(Long id) {
//		Session currentSession = entityManger.unwrap(Session.class);
//		Query<UserInformation> q = currentSession.createQuery("update UserInformation set is_verified =:p" + " " + " " + " where user_id=:id");
//		q.setParameter("p", true);
//		q.setParameter("i", id);
//		int status = q.executeUpdate();
//		if (status > 0) {
//			return true;
//
//		} else {
//			return false;
//		}
//
//	}
//
//	/* Query to list out all the User Information */
//	@Override
//	public List<UserInformation> getUsers() {
//		Session currentSession = entityManger.unwrap(Session.class);
//		List<UserInformation> userList = currentSession.createQuery("FROM UserInformation").getResultList();
//		System.out.println(userList);
//		return userList;
//	}
//
//}
