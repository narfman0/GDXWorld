package com.blastedstudios.gdxworld.plugin.serializer.xstream;

import com.blastedstudios.gdxworld.world.GDXLevel;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class LevelConverter implements Converter{
	private final ReflectionConverter reflectionConverter;
			
	public LevelConverter(Mapper mapper, ReflectionProvider reflectionProvider){
		reflectionConverter = new ReflectionConverter(mapper, reflectionProvider);
	}
	
	@Override public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz == GDXLevel.class;
	}

	@Override public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		GDXLevel level = (GDXLevel) value;
        reflectionConverter.marshal(level, writer, context);
	}

	@Override public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        GDXLevel level = (GDXLevel) reflectionConverter.unmarshal(reader, context);
        return level;
	}

}
