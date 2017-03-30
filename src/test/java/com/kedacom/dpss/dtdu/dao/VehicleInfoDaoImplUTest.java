/**
* 描述: TODO 
* 作者:Administrator 
* 创建日期:Administrator  
* 修改人:Administrator   
* 修改日期: 2013-12-11
*/
package com.kedacom.dpss.dtdu.dao;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbConnection;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kedacom.dpss.dtdu.pojo.transport.Target;
import com.kedacom.dpss.dtdu.pojo.transport.VehiclePicInfoType;
import com.kedacom.dpss.dtdu.test.util.EntitiesHelper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Administrator
 *
 */
@ContextConfiguration(locations={"classpath:/springCfg/applicationContext.xml"})
public class VehicleInfoDaoImplUTest extends AbstractTransactionalJUnit4SpringContextTests {
	VehicleInfoDaoImpl vehicleInfoDaoImpl;
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbctemplate;
	
	@Autowired	 
	ComboPooledDataSource dataSource;	
	
	private  IDatabaseConnection dbunitConnection;
	private  Connection connection;
	
	/**
	 * Test method for {@link com.kedacom.dpss.dtdu.dao.VehicleInfoDaoImpl#InsertVehicleInfo(org.springframework.jdbc.core.JdbcTemplate, com.kedacom.dpss.dtdu.pojo.transport.Target)}.
	 * @throws SQLException 
	 * @throws DatabaseUnitException 
	 */
	@Before
	public  void beforeClass() throws DatabaseUnitException, SQLException{
		connection = dataSource.getConnection();
		dbunitConnection = new DatabaseConnection(connection,"DPSS");	
		vehicleInfoDaoImpl = new VehicleInfoDaoImpl();
	}
	
	@After
	public  void  afterClass() throws SQLException{

		if(connection != null){
			connection.close();
			connection =null;			
		}
		if(dbunitConnection!=null){
			dbunitConnection.close();
			dbunitConnection =null;
		}
	}

	protected IDataSet getDataSet(String name) throws Exception{	
		InputStream inputStream = getClass().getResourceAsStream(name);
		Reader reader = new InputStreamReader(inputStream);
		FlatXmlDataSet dataset = new FlatXmlDataSet(reader);
		return dataset;
	}
	
	@Test
	public void testInsertVehicleInfo() throws Exception{
		Target target = EntitiesHelper.newVehicle();
		IDataSet actualDataSet = dbunitConnection.createDataSet();
		ITable actualTable = actualDataSet.getTable("TD_VEHICLE_INFO");
		vehicleInfoDaoImpl.insertTargetInfo(jdbctemplate, target);
		
		IDataSet expectedDataSet = getDataSet("/dbdate/td_vehicle_info.xml");
		ITable expectedTable = expectedDataSet.getTable("TD_VEHICLE_INFO");
		
//		Assertion.assertEquals(expectedTable,actualTable);
	}

	public static void main(String args[]) throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException, IOException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = 172.16.239.142)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = orcl)))", "dpss", "dpss"); 
        IDatabaseConnection connection = new DatabaseConnection(conn,"DPSS");    
		IDataSet actualDataSet = connection.createDataSet();  
        ITable dataTable2 = actualDataSet.getTable("TD_VEHICLE_INFO");
		FlatXmlDataSet.write(actualDataSet,new FileOutputStream("C:/dbunit1.xml")); 
	}
}
