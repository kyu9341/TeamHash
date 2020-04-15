package com.example.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.model.Emp;
import com.example.rest.service.EmpService;

@RestController
@RequestMapping("emp")
public class EmpController {
	@Autowired
	private EmpService empService;

	// 모든 사원 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Emp>> getAllEmps() {
		List<Emp> emps = empService.findAll();
		return new ResponseEntity<List<Emp>>(emps, HttpStatus.OK);
	}

	// empno로 한명의 사원 조회
	@GetMapping(value = "/{empno}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Emp> getEmp(@PathVariable("empno") int empno) {
		return new ResponseEntity<Emp>(empService.findById(empno), HttpStatus.OK);
	}

	// empno로 사원 삭제
	@DeleteMapping(value = "/{empno}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteEmp(@PathVariable("empno") int empno) {
		empService.deleteById(empno);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	// empno로 사원 수정(empno로 사원 찾아 인자로 넘어오는 을 Emp 객체의 ename, sal로 수정함)
	@PutMapping(value = "/{empno}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Emp> updateEmp(@PathVariable("empno") int empno, @RequestBody Emp emp) {
		empService.updateById(empno, emp);
		return new ResponseEntity<Emp>(emp, HttpStatus.OK);
	}

	// 사원 입력
	@PostMapping
	public ResponseEntity<Emp> save(@RequestBody Emp emp) {
		return new ResponseEntity<Emp>(empService.save(emp), HttpStatus.OK);
	}

	// 급여를 기준으로 사원 검색 (sal > sal1 and sal < sal2)
	@GetMapping(value = "/{sal1}/{sal2}")
	public ResponseEntity<List<Emp>> getEmpBySalBetween(@PathVariable int sal1, @PathVariable int sal2) {
		List<Emp> emps = empService.findBySalBetween(sal1, sal2);
		return new ResponseEntity<List<Emp>>(emps, HttpStatus.OK);
	}
}
