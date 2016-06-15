package edu.thu.ss.spec.meta;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import edu.thu.ss.spec.util.PSpecUtil;

public class JoinCondition {

	public static class ColumnEntry implements Comparable<ColumnEntry> {
		public String column;
		public String target;

		public ColumnEntry(String column, String target) {
			this.column = column;
			this.target = target;
		}

		@Override
		public int compareTo(ColumnEntry o) {
			int compare1 = column.compareToIgnoreCase(o.column);
			if (compare1 != 0) {
				return compare1;
			} else {
				return target.compareToIgnoreCase(o.target);
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((column == null) ? 0 : column.hashCode());
			result = prime * result + ((target == null) ? 0 : target.hashCode());
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
			ColumnEntry other = (ColumnEntry) obj;
			if (column == null) {
				if (other.column != null)
					return false;
			} else if (!column.equals(other.column))
				return false;
			if (target == null) {
				if (other.target != null)
					return false;
			} else if (!target.equals(other.target))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return column + "=" + target;
		}
	}

	String joinTable;

	List<ColumnEntry> joinColumns = new LinkedList<>();

	public void addJoinColumn(String column, String target) {
		ColumnEntry entry1 = new ColumnEntry(column.toLowerCase(), target.toLowerCase());
		ListIterator<ColumnEntry> it = joinColumns.listIterator();
		while (it.hasNext()) {
			ColumnEntry entry2 = it.next();
			if (entry1.compareTo(entry2) <= 0) {
				it.previous();
				it.add(entry1);
				return;
			}
		}
		joinColumns.add(entry1);
	}

	public List<ColumnEntry> getJoinColumns() {
		return joinColumns;
	}

	public String getJoinTable() {
		return joinTable;
	}

	public void setJoinTable(String joinTable) {
		this.joinTable = joinTable;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("table: ");
		sb.append(joinTable);
		sb.append("\t");
		sb.append(PSpecUtil.format(joinColumns, ","));
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((joinColumns == null) ? 0 : joinColumns.hashCode());
		result = prime * result + ((joinTable == null) ? 0 : joinTable.hashCode());
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
		JoinCondition other = (JoinCondition) obj;
		if (joinColumns == null) {
			if (other.joinColumns != null)
				return false;
		} else if (!joinColumns.equals(other.joinColumns))
			return false;
		if (joinTable == null) {
			if (other.joinTable != null)
				return false;
		} else if (!joinTable.equals(other.joinTable))
			return false;
		return true;
	}

}
