package com.phellipesander.cursomc.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phellipesander.cursomc.entity.Categoria;
import com.phellipesander.cursomc.entity.Cidade;
import com.phellipesander.cursomc.entity.Cliente;
import com.phellipesander.cursomc.entity.Endereco;
import com.phellipesander.cursomc.entity.Estado;
import com.phellipesander.cursomc.entity.ItemPedido;
import com.phellipesander.cursomc.entity.Pagamento;
import com.phellipesander.cursomc.entity.PagamentoComBoleto;
import com.phellipesander.cursomc.entity.PagamentoComCartao;
import com.phellipesander.cursomc.entity.Pedido;
import com.phellipesander.cursomc.entity.Produto;
import com.phellipesander.cursomc.entity.enums.EstadoPagamento;
import com.phellipesander.cursomc.entity.enums.TipoCliente;
import com.phellipesander.cursomc.repositories.CategoriaRepository;
import com.phellipesander.cursomc.repositories.CidadeRepository;
import com.phellipesander.cursomc.repositories.ClienteRepository;
import com.phellipesander.cursomc.repositories.EnderecoRepository;
import com.phellipesander.cursomc.repositories.EstadoRepository;
import com.phellipesander.cursomc.repositories.ItemPedidoRepository;
import com.phellipesander.cursomc.repositories.PagamentoRepository;
import com.phellipesander.cursomc.repositories.PedidoRepository;
import com.phellipesander.cursomc.repositories.ProdutoRepository;

@Service
public class DbService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));

		Produto p1 = new Produto(null, "Computador", new BigDecimal(2000));
		Produto p2 = new Produto(null, "Impressora", new BigDecimal(800));
		Produto p3 = new Produto(null, "Mouse", new BigDecimal(80));
		Produto p4 = new Produto(null, "Mesa de escritório", new BigDecimal(300));
		Produto p5 = new Produto(null, "Toalha", new BigDecimal(50));
		Produto p6 = new Produto(null, "Colcha", new BigDecimal(200));
		Produto p7 = new Produto(null, "TV true color", new BigDecimal(1200));
		Produto p8 = new Produto(null, "Roçadeira", new BigDecimal(800));
		Produto p9 = new Produto(null, "Abajour", new BigDecimal(100));
		Produto p10 = new Produto(null, "Pendente", new BigDecimal(180));
		Produto p11 = new Produto(null, "Shampoo", new BigDecimal(90));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "phellipe.sander1@gmail.com", "123456789", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("12349987", "12548632"));
		Endereco e1 = new Endereco(null, "Rua flores", "300", "apto 505", "Jardim", "123456789", cli1, c1);
		Endereco e2 = new Endereco(null, "Av Matos", "124", "sala 404", "Centro", "485699632", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:37"), cli1, e1);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, new BigDecimal(00), 1, new BigDecimal(2000));
		ItemPedido ip2 = new ItemPedido(ped1, p3, new BigDecimal(00), 2, new BigDecimal(80));
		ItemPedido ip3 = new ItemPedido(ped2, p2, new BigDecimal(100), 1, new BigDecimal(800));

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
