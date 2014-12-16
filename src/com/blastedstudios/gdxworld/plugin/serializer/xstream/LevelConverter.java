package com.blastedstudios.gdxworld.plugin.serializer.xstream;

import java.util.List;

import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LevelConverter implements Converter{
	private final XStream xStream;
	private final ReflectionConverter reflectionConverter;

	public LevelConverter(XStream xStream){
		this.xStream = xStream;
		reflectionConverter = new ReflectionConverter(xStream.getMapper(), xStream.getReflectionProvider());
	}

	@Override public boolean canConvert(@SuppressWarnings("rawtypes") Class clazz) {
		return clazz == GDXLevel.class;
	}

	/**
	 * TODO levelconverter.marshal.custom.use does not work yet, needs plenty
	 * tweaks. First it will work here as the old version does, then I will
	 * push it to the respective plugins, then I will make custom handlers and
	 * effectively remove the code from this location and make each plugin
	 * completely self sustained. Long process, however, this is the first
	 * stage.
	 */
	@Override public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		GDXLevel level = (GDXLevel) value;
		if(Properties.getBool("levelconverter.marshal.custom.use", false)){
			writer.startNode("name");
			writer.setValue(level.getName());
			writer.endNode();
			marshalObject(writer, context, "coordinates", level.getCoordinates());
			marshalObject(writer, context, "tiles", level.getTiles());
			marshalObject(writer, context, "circles", level.getCircles());
			marshalObject(writer, context, "polygons", level.getPolygons());
			marshalObject(writer, context, "npcs", level.getNpcs());
			marshalObject(writer, context, "paths", level.getPaths());
			marshalObject(writer, context, "joints", level.getJoints());
			marshalObject(writer, context, "backgrounds", level.getBackgrounds());
			marshalObject(writer, context, "lights", level.getLights());
			marshalObject(writer, context, "groups", level.getGroups());
			marshalObject(writer, context, "particles", level.getParticles());
			marshalObject(writer, context, "sounds", level.getSounds());
			marshalObject(writer, context, "animations", level.getAnimations());
			marshalObject(writer, context, "properties", level.getProperties());
			marshalObject(writer, context, "lightAmbient", level.getLightAmbient());
		}else
			reflectionConverter.marshal(level, writer, context);
	}

	/**
	 * TODO see marshal method javadocs
	 */
	@Override public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if(Properties.getBool("levelconverter.marshal.custom.use", false)){
			GDXLevel level = new GDXLevel();
			while(reader.hasMoreChildren()){
				reader.moveDown();
				if(reader.getNodeName().equals("name"))
					level.setName(reader.getValue());
				else if(reader.getNodeName().equals("coordinates"))
					reflectionConverter.doUnmarshal(level.getCoordinates(), reader, context);
				else if(reader.getNodeName().equals("tiles"))
					reflectionConverter.doUnmarshal(level.getTiles(), reader, context); //TODO wont work
				else if(reader.getNodeName().equals("circles"))
					unmarshalListAdd(reader, level.getCircles());
				else if(reader.getNodeName().equals("polygons"))
					unmarshalListAdd(reader, level.getPolygons());
				else if(reader.getNodeName().equals("npcs"))
					unmarshalListAdd(reader, level.getNpcs());
				else if(reader.getNodeName().equals("paths"))
					unmarshalListAdd(reader, level.getPaths());
				else if(reader.getNodeName().equals("joints"))
					unmarshalListAdd(reader, level.getJoints());
				else if(reader.getNodeName().equals("backgrounds"))
					unmarshalListAdd(reader, level.getBackgrounds());
				else if(reader.getNodeName().equals("lights"))
					unmarshalListAdd(reader, level.getLights());
				else if(reader.getNodeName().equals("groups"))
					unmarshalListAdd(reader, level.getGroups());
				else if(reader.getNodeName().equals("particles"))
					unmarshalListAdd(reader, level.getParticles());
				else if(reader.getNodeName().equals("sounds"))
					unmarshalListAdd(reader, level.getSounds());
				else if(reader.getNodeName().equals("animations"))
					unmarshalListAdd(reader, level.getAnimations());
				else if(reader.getNodeName().equals("properties")){
					//	reader.moveDown();
					//	while(reader.hasMoreChildren()){
					//		reader.mo
					//	}
					//	reader.moveUp();
					reflectionConverter.doUnmarshal(level.getProperties(), reader, context);
				}else if(reader.getNodeName().equals("lightAmbient"))
					reflectionConverter.doUnmarshal(level.getLightAmbient(), reader, context);
				reader.moveUp();
			}
			return level;
		}else
			return (GDXLevel) reflectionConverter.unmarshal(reader, context);
	}

	@SuppressWarnings("unchecked")
	private <T> void unmarshalListAdd(HierarchicalStreamReader reader, List<T> theList){
		reader.moveDown();
		Object obj = xStream.unmarshal(reader);
		//TODO this should be some kind of iterable, either addAll or iterate over and add
		theList.add((T)obj);
		reader.moveUp();
	}

	private void marshalObject(HierarchicalStreamWriter writer, MarshallingContext context, String name, Object obj){
		writer.startNode(name);
		xStream.marshal(obj, writer);
		writer.endNode();
	}
}
