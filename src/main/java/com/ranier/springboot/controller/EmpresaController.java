package com.ranier.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranier.springboot.exception.ResouceNotFoundException;
import com.ranier.springboot.model.Empresa;
import com.ranier.springboot.model.Funcionario;
import com.ranier.springboot.repository.EmpresaRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@GetMapping("/empresas")
	public List<Empresa> getAllEmpresa() {
		return empresaRepository.findAll();
	}
	
	@PostMapping("/empresas")
	public Empresa createEmpresa(@RequestBody Empresa empresa) {
		return empresaRepository.save(empresa);
	}
	
	@GetMapping("/empresas/{id}")
	public ResponseEntity<Empresa> getEmpresaById(@PathVariable Integer id) {
		Empresa empresa = empresaRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Empresa não existe com esse id: " + id));
		return ResponseEntity.ok(empresa);
	}
	
	@PutMapping("/empresas/{id}")
	public ResponseEntity<Empresa> updateEmpresa(@PathVariable Integer id, @RequestBody Empresa empresaDetails){
		Empresa empresa = empresaRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Empresa não existe com esse id: " + id));
		
		empresa.setCnpj(empresaDetails.getCnpj());
		empresa.setRazaoSocial(empresaDetails.getRazaoSocial());
		empresa.setNomeFantasia(empresaDetails.getNomeFantasia());
		empresa.setTelefone(empresaDetails.getTelefone());
		
		Empresa updatedEmpresa =  empresaRepository.save(empresa);
		
		return ResponseEntity.ok(updatedEmpresa);
		
	}
	
	@DeleteMapping("/empresas/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmpresa(@PathVariable Integer id){
		Empresa empresa = empresaRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Empresa não existe com esse id: " + id));
		
		empresaRepository.delete(empresa);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

}
