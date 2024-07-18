package com.aashdit.prod.cmc.specs.umt;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.aashdit.prod.cmc.model.umt.User;

@Component
public class UserSpecification  {

	public Specification<User> searchUser(String searchTerm)
	{
		return (root, criteriaQuery, criteriaBuilder) ->
		{
			criteriaQuery.distinct(true);
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("userId")));
			if (searchTerm != null && !searchTerm.equals(""))
			{
				Predicate predicateForFilter = criteriaBuilder.or(
						criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")), "%" + searchTerm.toUpperCase() + "%"),
						criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")), "%" + searchTerm.toUpperCase() + "%"),
						criteriaBuilder.like(criteriaBuilder.upper(root.get("userName")), "%" + searchTerm.toUpperCase() + "%"),
						criteriaBuilder.like(criteriaBuilder.upper(root.get("mobile")), "%" + searchTerm.toUpperCase() + "%"),
						criteriaBuilder.like(criteriaBuilder.upper(root.get("email")), "%" + searchTerm.toUpperCase() + "%"),
						criteriaBuilder.like(criteriaBuilder.upper(root.get("designation")), "%" + searchTerm.toUpperCase() + "%")
				);
				return criteriaBuilder.and(predicateForFilter);
			}
			
			//No Search term just match on userId.
			Predicate predicateForNone = criteriaBuilder.disjunction();
			return criteriaBuilder.or(predicateForNone);
		};
	}

}
