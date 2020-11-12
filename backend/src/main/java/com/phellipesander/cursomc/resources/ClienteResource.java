package com.phellipesander.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.phellipesander.cursomc.dto.ClienteDTO;
import com.phellipesander.cursomc.dto.ClienteNewDTO;
import com.phellipesander.cursomc.entity.Cliente;
import com.phellipesander.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Long id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@RequestParam(value = "value") String email) {
		Cliente obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}


	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> categorias = service.findAll();
		List<ClienteDTO> categoriasDto = categorias.stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriasDto);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Long id) {
		objDto.setId(id);
		Cliente obj = service.fromDTO(objDto);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Cliente> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orederBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> pages = service.findPage(page, linesPerPage, orederBy, direction);
		Page<ClienteDTO> categoriasDto = pages.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(categoriasDto);
	}

	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile multipartFile) {
		URI uri = service.uploadProfilePicture(multipartFile);
		return ResponseEntity.created(uri).build();
	}

}