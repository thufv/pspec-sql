package edu.thu.ss.spec.meta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.thu.ss.spec.util.SetUtil;

public class MapType extends BaseType {

	public static class EntryType {
		public String key;
		public BaseType valueType;

		public EntryType(String key, BaseType valueType) {
			this.key = key;
			this.valueType = valueType;
		}

		public BaseType getType(){
			return valueType;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((valueType == null) ? 0 : valueType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EntryType other = (EntryType) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (valueType == null) {
				if (other.valueType != null)
					return false;
			} else if (!valueType.equals(other.valueType))
				return false;
			return true;
		}

	}

	protected Map<String, EntryType> entries = new LinkedHashMap<>();

	public EntryType getEntry(String key) {
		return entries.get(key);
	}

	public Map<String, EntryType> getEntries() {
		return entries;
	}

	public BaseType getEntryType(String name) {
		EntryType entry = getEntry(name);
		if (entry == null) {
			return null;
		} else {
			return entry.valueType;
		}
	}

	public void addEntry(EntryType entry) {
		entries.put(entry.key, entry);
	}

	@Override
	public PrimitiveType[] toPrimitives() {
		if (primitives == null) {
			List<PrimitiveType> types = new ArrayList<>();

			for (EntryType entry : entries.values()) {
				PrimitiveType[] entryPrimitives = entry.valueType.toPrimitives();
				for (PrimitiveType primitive : entryPrimitives) {
					types.add(primitive);
				}
			}
			primitives = types.toArray(new PrimitiveType[types.size()]);
		}
		return primitives;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entries == null) ? 0 : entries.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapType other = (MapType) obj;
		if (entries == null) {
			if (other.entries != null)
				return false;
		} else if (!entries.equals(other.entries))
			return false;
		return true;
	}

	@Override
	public String toString(int l) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(SetUtil.spaces(l));
		sb.append("Map Type \n");
		for (EntryType entry : entries.values()) {
			sb.append(SetUtil.spaces(l));
			sb.append("key :");
			sb.append(entry.key);
			sb.append("\t");
			sb.append(entry.valueType.toString(l + 1));
			sb.append("\n");
		}
		return sb.toString();
	}

}
