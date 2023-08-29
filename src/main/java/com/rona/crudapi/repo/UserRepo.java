package com.rona.crudapi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rona.crudapi.dto.ClassEntity;
import com.rona.crudapi.dto.ClassScheduleEntity;
import com.rona.crudapi.dto.StudentGradeEntity;
import com.rona.crudapi.dto.UserEntity;
import com.rona.crudapi.models.User;

public interface UserRepo extends JpaRepository<User, Integer>{	
	// get user by id
//	User findAllById(int id);
	User findByEmail(String email);
	
	// get user info	
	@Query(value = ""
			+ "SELECT "
				+ " user.id, user.email, user.firstname, user.lastname, user.age, user.address, user.contact, user.role AS role_id, user_role.name AS role "
			+ " FROM user"
			+ " LEFT JOIN user_role"
				+ " ON user.role = user_role.id"
			+ " WHERE email = ?1 "
			+ " LIMIT 1", nativeQuery = true)
	UserEntity getUserInfo(String email);
//	
//	// update user info	
//	@Query(value = ""
//			+ " UPDATE user"
//			+ " SET"
//				+ " firstname = ?3,"
//				+ " lastname = ?4,"
//				+ " age = ?5,"
//				+ " address = ?6,"
//				+ " contact = ?7"
//			+ " WHERE id = ?1", nativeQuery = true)
//	User updateUserInfo(int id, String fName, String lName, int age, String address, String contact);
//	
	// get class info by student id
	@Query(value = ""
			+ "SELECT "
				+ "	 class.id,"
				+ "	 class.name,"
				+ "  class.year_from,"
				+ "  class.year_to,"
				+ "	 CONCAT(user.firstname, ' ', user.lastname) AS teacher"
			+ "	 FROM class"
			+ "	 RIGHT JOIN ("
				+ "	 SELECT cs.class_id FROM class_student cs "
				+ "	 LEFT JOIN user ON cs.student_id = user.id"
				+ "	 WHERE user.id = ?1 LIMIT 1"
			+ "	 ) AS uc ON class.id = uc.class_id"
			+ "	 LEFT JOIN user"
				+ "	 ON class.teacher_id = user.id"
			+ "	 LIMIT 1", nativeQuery = true)
	ClassEntity getStudentClass(int id);
	
	// get class schedule by student id
	@Query(value = ""
			+ "SELECT"
				+ "	subject.id,"
				+ "	subject.name,"
				+ " ss.time_from,"
				+ " ss.time_to,"
				+ " ss.day,"
				+ " CONCAT(user.firstname, ' ', user.lastname) AS teacher"
			+ " FROM class_schedule cs"
				+ " RIGHT JOIN ("
				+ "	SELECT cs.class_id FROM class_student cs "
				+ "	LEFT JOIN user user ON cs.student_id = user.id"
				+ "	WHERE user.id = ?1 LIMIT 1"
			+ ") AS uc ON cs.class_id = uc.class_id"
			+ " LEFT JOIN subject_schedule ss"
				+ "	ON cs.schedule_id = ss.id"
			+ " LEFT JOIN subject"
				+ "	ON ss.subject_id = subject.id"
			+ " LEFT JOIN user"
				+ "	ON ss.teacher_id = user.id"
			+ " ORDER BY subject.id ASC", nativeQuery  = true)
	List<ClassScheduleEntity> getClassSchedule(int id);
	
	// get grade by student id
	@Query(value = ""
			+ " SELECT "
				+ " subject.id,"
				+ " subject.name,"
				+ " grade.grade,"
				+ " grade.term"
			+ " FROM grade"
			+ " LEFT JOIN subject"
				+ " ON grade.subject_id = subject.id"
			+ " WHERE grade.student_id = ?1"
			+ " ORDER BY subject.id ASC", nativeQuery  = true)
	List<StudentGradeEntity> getStudentGrade(int id);
}


