package br.com.capelli.centrodebelezacapelli.model.notafiscal.converters;

import java.text.NumberFormat;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import br.com.capelli.centrodebelezacapelli.model.notafiscal.Item;

public class ItemConverter implements Converter {

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		
		return clazz.equals(Item.class);
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		
		NumberFormat f = NumberFormat.getInstance();
		f.setMinimumFractionDigits(2);
		f.setMaximumFractionDigits(2);
	
		Item item = (Item) object;
		
		writer.startNode("codigo_local_prestacao_servico");
		context.convertAnother(item.getCodigoCidade());
		writer.endNode();
		
		writer.startNode("codigo_item_lista_servico");
		context.convertAnother(item.getCodigoServico());
		writer.endNode();
		
		writer.startNode("descritivo");
		context.convertAnother(item.getDescricaoServico());
		writer.endNode();
		
		writer.startNode("aliquota_item_lista_servico");
		context.convertAnother(f.format(item.getAliquotaItemServico()));
		writer.endNode();
		
		writer.startNode("situacao_tributaria");
		context.convertAnother(item.getCodigoSituacaoTributaria());
		writer.endNode();
		
		writer.startNode("tributa_municipio_prestador");
		context.convertAnother(item.isTributaMunicipioPrestador() ? 1 : 0);
		writer.endNode();
		
		writer.startNode("valor_deducao");
		context.convertAnother(f.format(item.getValorDeducao()));
		writer.endNode();
		
		writer.startNode("valor_tributavel");
		context.convertAnother(f.format(item.getValorTributavel()));
		writer.endNode();
		
		writer.startNode("valor_issrf");
		context.convertAnother(f.format(item.getValorISSRF()));
		writer.endNode();
		
		writer.startNode("unidade_codigo");
		context.convertAnother(item.getUnidadeCodigo());
		writer.endNode();
		
		writer.startNode("unidade_quantidade");
		context.convertAnother(item.getQuantidadeItem());
		writer.endNode();
		
		writer.startNode("unidade_valor_unitario");
		context.convertAnother(f.format(item.getValorItem()));
		writer.endNode();
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
