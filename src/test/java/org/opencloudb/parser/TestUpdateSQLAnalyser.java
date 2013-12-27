package org.opencloudb.parser;

import java.sql.SQLSyntaxErrorException;

import junit.framework.Assert;

import org.junit.Test;
import org.opencloudb.mpp.JoinRel;
import org.opencloudb.mpp.UpdateParsInf;
import org.opencloudb.mpp.UpdateSQLAnalyser;
import org.opencloudb.parser.SQLParserDelegate;

import com.akiban.sql.parser.QueryTreeNode;

public class TestUpdateSQLAnalyser {
	@Test
	public void testUpdateSQL() throws SQLSyntaxErrorException {
		String sql = null;
		QueryTreeNode ast = null;
		UpdateParsInf parsInf = null;
		
		sql = "update A set A.qcye='aaaaa',A.colm2='ddd'";
		ast = SQLParserDelegate.parse(sql, SQLParserDelegate.DEFAULT_CHARSET);
		parsInf = UpdateSQLAnalyser.analyse(ast);
		Assert.assertEquals("A".toUpperCase(), parsInf.tableName);
		Assert.assertEquals(2, parsInf.columnPairMap.size());
		Assert.assertNull("should no where condiont", parsInf.ctx);
		
		sql = "update A set A.qcye=B.qcye,A.colm2='ddd', colm3=5555 where A.kmdm=B.kmdm and   A.fmonth=B.fmonth and   A.fmonth=0";
		ast = SQLParserDelegate.parse(sql, SQLParserDelegate.DEFAULT_CHARSET);
		parsInf = UpdateSQLAnalyser.analyse(ast);
		Assert.assertEquals("A".toUpperCase(), parsInf.tableName);
		Assert.assertEquals(3, parsInf.columnPairMap.size());
		Assert.assertEquals(2, parsInf.ctx.tablesAndCondtions.size());
		Assert.assertEquals(1, parsInf.ctx.tablesAndCondtions.get("A").size());
		Assert.assertEquals(0, parsInf.ctx.tablesAndCondtions.get("B").size());
		Assert.assertEquals(2, parsInf.ctx.joinList.size());
		Assert.assertEquals(new JoinRel("A", "kmdm", "B", "kmdm"),
				parsInf.ctx.joinList.get(0));

		// Assert.assertNotNull(parsInf.fromQryNode);

		sql = "UPDATE db1.A SET HIGH=B.NEW where  A.HIGH=B.OLD";
		ast = SQLParserDelegate.parse(sql, SQLParserDelegate.DEFAULT_CHARSET);
		parsInf = UpdateSQLAnalyser.analyse(ast);
		Assert.assertEquals("A", parsInf.tableName);
		Assert.assertEquals(1, parsInf.columnPairMap.size());
		Assert.assertEquals("?", parsInf.columnPairMap.get("HIGH"));
		Assert.assertEquals(2, parsInf.ctx.tablesAndCondtions.size());
		Assert.assertEquals(1, parsInf.ctx.joinList.size());
		Assert.assertEquals(new JoinRel("A", "HIGH", "B", "OLD"),
				parsInf.ctx.joinList.get(0));

		sql = "Update HouseInfo Set UpdateTime = 'now',I_Valid='\"&I_Valid&\"' Where I_ID In (1,2,3)";
		ast = SQLParserDelegate.parse(sql, SQLParserDelegate.DEFAULT_CHARSET);
		parsInf = UpdateSQLAnalyser.analyse(ast);
		Assert.assertEquals("HouseInfo".toUpperCase(), parsInf.tableName);
		Assert.assertEquals(2, parsInf.columnPairMap.size());
		Assert.assertEquals("?",
				parsInf.columnPairMap.get("I_Valid".toUpperCase()));
		Assert.assertEquals(3,
				parsInf.ctx.tablesAndCondtions.get("HouseInfo".toUpperCase())
						.get("I_ID").size());
		

	}
}
