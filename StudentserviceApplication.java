1. StudentserviceApplication.java (default we dont create it)

package com.tns.Studentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentserviceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(StudentserviceApplication.class, args);
	}
}


2.Student.java (create a class program with variables)

package com.tns.Studentservice;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student
{
	private Integer S_id;
	private String S_name;
	
	
	public Student() 
	{
		super();
	}
	
	public Student(Integer s_id, String s_name)
	{
		super();
		S_id = s_id;
		S_name = s_name;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getS_id() {
		return S_id;
	}
	public void setS_id(Integer s_id) {
		S_id = s_id;
	}
	public String getS_name() {
		return S_name;
	}
	public void setS_name(String s_name)
	{
		S_name = s_name;
	}
	@Override
	public String toString()
	{
		return "Student[Student id:"+S_id+" Student name"+S_name+"]";
	}
}


3.Student_Service_Repository.java (interface)

package com.tns.Studentservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Student_Service_Repository extends JpaRepository<Student, Integer> 
{

}


4. Student_Service.java (create service)

package com.tns.Studentservice;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class Student_Service 
{
	@Autowired
	private Student_Service_Repository repo;
	
	public List<Student> listAll()
	{
		return repo.findAll();
	}
	
	public void save(Student stud)
	{
		repo.save(stud);
	}
	
	public Student get(Integer s_id)
	{
		return repo.findById(s_id).get();
	}
	public void delete(Integer s_id)
	{
		repo.deleteById(s_id);
	}
	
}


5. Student_service_Controller.java (controller)

package com.tns.Studentservice;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Student_service_Controller
{
	@Autowired(required=true)
	private Student_Service service;
	
	@GetMapping("/studentservice")
	public java.util.List<Student> list()
	{
		return service.listAll();
	}
	
	@GetMapping("/studentservice/{s_id}")
	public ResponseEntity<Student> get(@PathVariable Integer S_id)
	{
		try
		{
			Student stud=service.get(S_id);
			return new ResponseEntity<Student>(stud,HttpStatus.OK);
		}
		catch(NoResultException e)
		{
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/studentservice")
	public void add(@RequestBody Student stud)
	{
		service.save(stud);
	}
	
	@PutMapping("/studentservice/{s_id}")
	public ResponseEntity<?> update(@RequestBody Student stud, @PathVariable Integer S_id)
	{
		Student existstud=service.get(S_id);
		service.save(existstud);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/studentservice/{s_id}")
	public void delete(@PathVariable Integer s_id)
	{
		service.delete(s_id);
	}
}