package edu.thu.ss;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.TSourceToken;
import gudusoft.gsqlparser.TStatementList;
import gudusoft.gsqlparser.nodes.TCTE;
import gudusoft.gsqlparser.nodes.TCTEList;
import gudusoft.gsqlparser.nodes.TJoin;
import gudusoft.gsqlparser.nodes.TTable;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;

public class ParserExample {

	public static void main(String[] args) {
		TGSqlParser parser = new TGSqlParser(EDbVendor.dbvmssql);
		parser.sqlfilename = "query2.sql";
		int ret = parser.parse();
		if (ret != 0) {
			System.out.println(parser.getErrormessage());
			return;
		}
		TStatementList list = parser.sqlstatements;
		for (int i = 0; i < list.size(); i++) {
			transform((TSelectSqlStatement) list.get(i));
		}
	}

	private static void transform(TSelectSqlStatement stmt) {
		
		TCTEList list = stmt.getCteList();
		TCTE cte = list.getCTE(0);
		TSelectSqlStatement sub = cte.getSubquery();
		for (int i = 0; i < stmt.joins.size(); i++) {
			TJoin join = stmt.joins.getJoin(i);
			TTable table = join.getTable();
			if (table.isCTEName()) {
				table.setString(sub.toString() + " " + table.getAliasClause().getAliasName().toString());
			}
		}
		list.setString(" ");
		stmt.getTopClause().setString(" ");
		TSourceToken token = stmt.getStartToken();
		if (token.astext.equalsIgnoreCase("with")) {
			token.removeMyFromTokenList();
			token = token.nextSolidToken();
		}

		System.out.println(stmt);

	}
}
