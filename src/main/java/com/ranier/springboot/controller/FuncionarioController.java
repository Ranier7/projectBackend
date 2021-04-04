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
import com.ranier.springboot.model.Funcionario;
import com.ranier.springboot.repository.FuncionarioRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@GetMapping("/funcionarios")
	public List<Funcionario> getAllFuncionarios() {
		return funcionarioRepository.findAll();
	}
	
	@PostMapping("/funcionarios")
	public Funcionario createFuncionario(@RequestBody Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}
	
	@GetMapping("/funcionarios/{id}")
	public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Long id) {
		Funcionario funcionario = funcionarioRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Funcionário não existe com esse id: " + id));
		return ResponseEntity.ok(funcionario);
	}
	
	@PutMapping("/funcionarios/{id}")
	public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioDetails){
		Funcionario funcionario = funcionarioRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Funcionário não existe com esse id: " + id));
		
		funcionario.setNome(funcionarioDetails.getNome());
		funcionario.setCpf(funcionarioDetails.getCpf());
		funcionario.setTelefone(funcionarioDetails.getTelefone());
		funcionario.setEmail(funcionarioDetails.getEmail());
		funcionario.setEmpresa(funcionarioDetails.getEmpresa());
		
		Funcionario updatedFuncionario =  funcionarioRepository.save(funcionario);
		
		return ResponseEntity.ok(updatedFuncionario);
		
	}
	
	@DeleteMapping("/funcionarios/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteFuncionario(@PathVariable Long id){
		Funcionario funcionario = funcionarioRepository.findById(id)
				.orElseThrow(() -> new ResouceNotFoundException("Funcionário não existe com esse id: " + id));
		
		funcionarioRepository.delete(funcionario);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
}
