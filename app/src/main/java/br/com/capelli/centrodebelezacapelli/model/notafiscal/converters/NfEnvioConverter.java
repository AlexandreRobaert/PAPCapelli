package br.com.capelli.centrodebelezacapelli.model.notafiscal.converters;

import java.text.NumberFormat;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import br.com.capelli.centrodebelezacapelli.model.notafiscal.Nf;

public class NfEnvioConverter implements Converter {

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz.equals(Nf.class);
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		NumberFormat f = NumberFormat.getInstance();
		f.setMinimumFractionDigits(2);
		f.setMaximumFractionDigits(2);
		
		Nf nf = (Nf) object;
		
		writer.startNode("valor_total");
		context.convertAnother(f.format(nf.getValorTotal()));
		writer.endNode();
		
		writer.startNode("valor_desconto");
		context.convertAnother(f.format(nf.getValorDesconto()));
		writer.endNode();
		
		writer.startNode("valor_ir");
		context.convertAnother(f.format(nf.getValorir()));
		writer.endNode();
		
		writer.startNode("valor_inss");
		context.convertAnother(f.format(nf.getValorinss()));
		writer.endNode();
		
		writer.startNode("valor_contribuicao_social");
		context.convertAnother(f.format(nf.getValorContribuicaoSocial()));
		writer.endNode();
		
		writer.startNode("valor_rps");
		context.convertAnother(f.format(nf.getValorrps()));
		writer.endNode();
		
		writer.startNode("valor_pis");
		context.convertAnother(f.format(nf.getValorpis()));
		writer.endNode();
		
		writer.startNode("valor_cofins");
		context.convertAnother(f.format(nf.getValorcofins()));
		writer.endNode();
		
		writer.startNode("observacao");
		context.convertAnother(nf.getObservacoes());
		writer.endNode();
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
