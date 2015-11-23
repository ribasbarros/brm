package br.com.brm.scp.batch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.brm.scp.api.service.repositories.PedidoRepository;
import br.com.brm.scp.batch.service.PedidoListener;

@Service
public class PedidoListenerImpl implements PedidoListener {

	@Autowired
	private PedidoRepository repository;
	
}
